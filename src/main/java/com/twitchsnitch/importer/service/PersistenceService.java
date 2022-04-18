package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.RaidFinderDTO;
import com.twitchsnitch.importer.dto.sully.channels.*;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.summary.ResultSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PersistenceService {

    private Neo4jClient client;

    @Value("${database}")
    private String database;

    private final static Logger log = LoggerFactory.getLogger(PersistenceService.class);

    @PostConstruct
    public void initNeo4j() {
        Driver driver = GraphDatabase
                .driver("neo4j://165.232.187.113:7687", AuthTokens.basic("neo4j", "traidable"));
        client = Neo4jClient.create(driver);
    }

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    public void addDatabase(String database) {
        ResultSummary run = client.query("CREATE DATABASE " + database).in(database).run();
        logResultSummaries("addDatabase", run);
    }

    public void dropDatabase(String database) {
        ResultSummary run = client.query("DROP DATABASE " + database).in(database).run();
        logResultSummaries("dropDatabase", run);
    }

    //DB METHODS
    public void deleteDBData() {
        //drop db development
        //create db development
        ResultSummary run = client.query("call apoc.periodic.iterate(\n" +
                "\"MATCH (p) return id(p) AS id\", \n" +
                "\"MATCH (n) WHERE id(n) = id DETACH DELETE n\", \n" +
                "{batchSize:1000})").in(database).run();
        log.trace("Nodes deleted from the DB: " + run.counters().nodesDeleted());
    }

    public void dropDBConstraints() {
        ResultSummary dropConstraints = client.query("CALL apoc. schema. assert({}, {});").in(database).run();
        logResultSummaries("", dropConstraints);
    }

    public void runDBConstraints() {

        ResultSummary gameDisplayNameConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.name IS UNIQUE;").in(database).run();
        ResultSummary languageConstraint = client.query("CREATE CONSTRAINT FOR (l:Language) REQUIRE l.key IS UNIQUE;").in(database).run();
        ResultSummary channelLoginConstraint = client.query("CREATE CONSTRAINT FOR (c:Channel) REQUIRE c.login IS UNIQUE;").in(database).run();
        ResultSummary userLoginConstraint = client.query("CREATE CONSTRAINT FOR (u:User) REQUIRE u.login IS UNIQUE;").in(database).run();
        ResultSummary teamNameConstraint = client.query("CREATE CONSTRAINT FOR (t:Team) REQUIRE t.login IS UNIQUE;").in(database).run();
        //ResultSummary gameFinderCompositeConstraint = client.query("CREATE CONSTRAINT FOR (g:GameFinder) REQUIRE g.composite_sully_id IS UNIQUE;").in(database).run();
        ResultSummary raidFinderCompositeConstraint = client.query("CREATE CONSTRAINT FOR (r:RaidFinder) REQUIRE r.composite_sully_id IS UNIQUE;").in(database).run();
        ResultSummary liveStreamConstraint = client.query("CREATE CONSTRAINT FOR (l:LiveStream) REQUIRE l.twitch_id IS UNIQUE;").in(database).run();


        ResultSummary liveStreamTwitchIdIndex = client.query("CREATE INDEX FOR (l:LiveStream) ON (l.sully_id);").in(database).run();
        ResultSummary languageNameIndex = client.query("CREATE INDEX FOR (l:Language) ON (l.name);").in(database).run();
        ResultSummary gameSullyIdIndex = client.query("CREATE INDEX FOR (g:Game) ON (g.sully_id);").in(database).run();
        ResultSummary gameTwitchIdIndex = client.query("CREATE INDEX FOR (g:Game) ON (g.twitch_id);").in(database).run();
        ResultSummary channelSullyIdIndex = client.query("CREATE INDEX FOR (c:Channel) ON (c.sully_id);").in(database).run();
        ResultSummary channelTwitchIdIndex = client.query("CREATE INDEX FOR (c:Channel) ON (c.twitch_id);").in(database).run();

        logResultSummaries("liveStreamConstraint", liveStreamConstraint);
        logResultSummaries("gameDisplayNameConstraint", gameDisplayNameConstraint);
        logResultSummaries("languageConstraint", languageConstraint);
        logResultSummaries("channelLoginConstraint", channelLoginConstraint);
        logResultSummaries("userLoginConstraint", userLoginConstraint);
        logResultSummaries("teamNameConstraint", teamNameConstraint);
        //logResultSummaries("gameFinderCompositeConstraint", gameFinderCompositeConstraint);
        logResultSummaries("raidFinderCompositeConstraint", raidFinderCompositeConstraint);

        logResultSummaries("liveStreamSullyIdIndex", liveStreamTwitchIdIndex);
        logResultSummaries("languageNameIndex", languageNameIndex);
        logResultSummaries("gameSullyIdIndex", gameSullyIdIndex);
        logResultSummaries("gameTwitchIdIndex", gameTwitchIdIndex);
        logResultSummaries("channelSullyIdIndex", channelSullyIdIndex);
        logResultSummaries("channelTwitchIdIndex", channelTwitchIdIndex);

    }

    private void logResultSummaries(String key, ResultSummary resultSummary) {
        log.trace("RESULT SUMMARY FOR: " + key);

        if (resultSummary.counters().nodesCreated() > 0) {
            log.trace("Nodes created: " + resultSummary.counters().nodesCreated());
        }
        if (resultSummary.counters().labelsAdded() > 0) {
            log.trace("Labels added: " + resultSummary.counters().labelsAdded());
        }
        if (resultSummary.counters().relationshipsCreated() > 0) {
            log.trace("Relationships added: " + resultSummary.counters().relationshipsCreated());
        }
        if (resultSummary.counters().relationshipsDeleted() > 0) {
            log.trace("Relationships deleted: " + resultSummary.counters().relationshipsDeleted());
        }
        if (resultSummary.counters().indexesAdded() > 0) {
            log.trace("Indexes added: " + resultSummary.counters().indexesAdded());
        }
        if (resultSummary.counters().indexesRemoved() > 0) {
            log.trace("Indexes removed: " + resultSummary.counters().indexesRemoved());
        }
        if (resultSummary.counters().constraintsAdded() > 0) {
            log.trace("Constraints added: " + resultSummary.counters().constraintsAdded());
        }
        if (resultSummary.counters().constraintsRemoved() > 0) {
            log.trace("Constraints added: " + resultSummary.counters().constraintsRemoved());
        }
        if (resultSummary.counters().propertiesSet() > 0) {
            log.trace("Properties set: " + resultSummary.counters().propertiesSet());
        }

    }

    //TWITCH METHODS
    public RaidFinderDTO getRaidFinder(String login) {
        RaidFinderDTO raidFinderDTO = new RaidFinderDTO();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long lowRange = 999999999;
        long highRange = 0;
        Collection<Map<String, Object>> all = client.query("MATCH p=(c:Channel{login:$login})-[r:GAME_METADATA]->(g:Game) RETURN g.sully_id as id,toInteger(r.avg_viewers * 0.5) as lowRange, toInteger(r.avg_viewers * 1.5) as highRange LIMIT 4").in(database).bind(login).to("login").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("id")) {
                    Long value = (Long) entry.getValue();
                    raidFinderDTO.getGameIds().add(value.toString());
                    raidFinderDTO.setDataIsSet(true);
                }
                if (entry.getKey().equalsIgnoreCase("lowRange")) {
                    Long value = (Long) entry.getValue();
                    if (value < lowRange) {
                        lowRange = value;
                    }
                }

                if (entry.getKey().equalsIgnoreCase("highRange")) {
                    Long value = (Long) entry.getValue();
                    if (value > highRange) {
                        highRange = value;
                    }
                }
            }
        }
        raidFinderDTO.setLowRange(lowRange);
        raidFinderDTO.setHighRange(highRange);
        stopWatch.stop();
        log.trace("Get All Raid finders took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return raidFinderDTO;
    }

    public List<Long> getAllSullyLanguageIds(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> allSullyLanguageIds = new ArrayList<>();
        Collection<Map<String, Object>> all = client.query("MATCH (l:Language) WHERE NOT l.sully_id = -1 RETURN l.sully_id").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                allSullyLanguageIds.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get all language sully ids: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return allSullyLanguageIds;
    }

    public List<String> getAllUsersWithoutSullyId(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> getAllUsersWithoutSullyId = new ArrayList<>();
        Collection<Map<String, Object>> all = client.query("MATCH (u:User) where u.sully_id IS NULL RETURN u.login").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                getAllUsersWithoutSullyId.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Users without sully id: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return getAllUsersWithoutSullyId;
    }

    public List<String> getLiveStreams() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> liveStreams = new ArrayList<>();
        Collection<Map<String, Object>> all = client.query("MATCH (l:LiveStream)<-[:LIVE_STREAMING]-(c:Channel) RETURN c.login").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                liveStreams.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All live streams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return liveStreams;
    }

    public Set<Long> getChannelsWithoutChannelGameData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<Long> sullyChannelStreams = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) WHERE exists (c.sully_id) and NOT (c)-[:GAME_METADATA]->() RETURN c.sully_id").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannelStreams.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Sully channel streams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannelStreams;
    }

    public Set<Long> getChannelStreamsWithoutIndividualData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<Long> sullyChannelStreams = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (cs:ChannelStream) WHERE NOT (cs)-[:STREAM_METADATA]->() RETURN cs.sully_id").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannelStreams.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Sully channel streams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannelStreams;
    }

    public Set<Long> getAllSullyChannels() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<Long> sullyChannels = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) RETURN c.sully_id").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannels.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Sully channels  took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannels;
    }

    public Set<String> getAllGamesWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> gamesWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (g:Game) WHERE g.twitch_id IS NULL RETURN g.name").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                gamesWithoutTwitchIds.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Games Without TwitchIds took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        log.trace("FOUND:" + gamesWithoutTwitchIds.size() + " Games Without TwitchIds");
        return gamesWithoutTwitchIds;
    }

    public Set<String> getUsersWithoutTwitchId() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutTwitchId = new HashSet<>();
        Collection<Map<String, Object>> all;
        all = client.query("MATCH (u:User) WHERE u.twitch_id IS NULL RETURN u.login").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutTwitchId.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("FOUND:" + usersWithoutTwitchId.size() + " Users Without TwitchIds");
        log.trace("Get All Users without twitch_id took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutTwitchId;
    }

    public Set<String> getUsersWithoutTwitchFollowsTo( ) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutFollowsTo = new HashSet<>();
        Collection<Map<String, Object>> all;

            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_to IS NULL AND c.twitch_id IS NOT NULL RETURN c.twitch_id ORDER BY c.followers ASC").in(database).fetch().all();


        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutFollowsTo.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Users without twitch_follows_to took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutFollowsTo;
    }

    public Set<String> getUsersWithoutTwitchFollowsFrom() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutFollowsFrom = new HashSet<>();
        Collection<Map<String, Object>> all;

            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_from IS NULL AND c.twitch_id IS NOT NULL RETURN c.twitch_id ORDER BY c.followers ASC").in(database).fetch().all();


        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutFollowsFrom.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All Users without twitch_follows_from took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutFollowsFrom;
    }

    public Set<String> getTeamsWithoutTwitchId() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> teamsWithoutTwitchId = new HashSet<>();
        Collection<Map<String, Object>> all;
        all = client.query("MATCH (t:Team) WHERE t.twitch_id IS NULL RETURN t.login").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                teamsWithoutTwitchId.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.trace("Get All teams without twitch_id took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return teamsWithoutTwitchId;
    }

    public Long getTwitchIdNotSetCountChannel() {
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) WHERE c.twitch_id IS NULL RETURN count(c)").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                long notsetcount = (Long) entry.getValue();
                log.trace("Get All Users without twitch_id is : " + notsetcount);
                return notsetcount;
            }
        }
        return null;

    }

    public Long getTwitchIdNotSetCountGame() {
        Collection<Map<String, Object>> all = client.query("MATCH (g:Game) WHERE g.twitch_id IS NULL RETURN count(g)").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                long notsetcount = (Long) entry.getValue();
                log.trace("Get All Games without twitch_id is : " + notsetcount);
                return notsetcount;
            }
        }
        return null;

    }

    public Long getTwitchIdNotSetCountUser() {
        Collection<Map<String, Object>> all = client.query("MATCH (u:User) WHERE u.twitch_id IS NULL RETURN count(u)").in(database).fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                long notsetcount = (Long) entry.getValue();
                log.trace("Get All Users without twitch_id is : " + notsetcount);
                return notsetcount;
            }
        }
        return null;
    }

    //updates


    public void updateGameWithTwitchData(Map json) {
        ResultSummary run = client.query("UNWIND $json.data as game " +
                "MATCH (g:Game) WHERE g.name = game.name SET g.twitch_id = game.id;").in(database).bind(json).to("json").run();
        logResultSummaries("updateGameWithTwitchData", run);
    }


    public void updateUserWithTwitchData(Map json) {
        ResultSummary run = client.query("UNWIND $json.data as user " +
                "MATCH (u:User) WHERE u.login = user.login SET u.twitch_id = user.id, u.created_at = datetime(user.created_at), u.total_view_count = user.view_count;").in(database).bind(json).to("json").run();
        logResultSummaries("updateUserWithTwitchData", run);
    }

    public void updateTeamWithTwitchData(String login, Map json) {
        ResultSummary run = client.query("UNWIND $json.data as row \n" +
                        "MATCH (t:Team{login:$login})\n" +
                        "SET t.created_at = datetime(replace(trim(split(row.created_at,\"+\")[0]), \" \", \"T\")),\n" +
                        "    t.updated_at = datetime(replace(trim(split(row.updated_at,\"+\")[0]), \" \", \"T\")),\n" +
                        "    t.info = row.info,\n" +
                        "    t.twitch_id = row.id\n" +
                        "    WITH row,t\n" +
                        "    UNWIND row.users as member\n" +
                        "MERGE (u:User{login:member.user_login})\n" +
                        "MERGE (u)-[:MEMBER_OF]->(t);").in(database)
                .bind(json).to("json")
                .bind(login).to("login")
                .run();
        logResultSummaries("updateTeamWithTwitchData", run);
    }

    //persists

    public void persistChattersOnDB() {
        ResultSummary run = client.query("// Import mods/vip/chatters for each stream\n" +
                "CALL apoc.periodic.iterate(\n" +
                "   // Return all stream nodes\n" +
                "  'MATCH (s:Channel) RETURN s',\n" +
                "  'WITH s, \"http://tmi.twitch.tv/group/user/\" + s.login + \"/chatters\" as url     \n" +
                "  //Fetch chatter information  \n" +
                "  CALL apoc.load.json(url) YIELD value     \n" +
                "  WITH s, value.chatters as chatters     \n" +
                "  // Store information about vips\n" +
                "  FOREACH (vip in chatters.vips | \n" +
                "          MERGE (u:User{login:vip}) \n" +
                "          MERGE (u)-[v:VIP]->(s))\n" +
                "  //Store information about moderators\n" +
                "  FOREACH (mod in chatters.moderators | \n" +
                "          MERGE (u:User{login:mod}) \n" +
                "          MERGE (u)-[:MODERATOR]->(s))\n" +
                "  //Store information about regular users\n" +
                "  FOREACH (chatter in chatters.viewers | \n" +
                "          MERGE (u:User{login:chatter}) \n" +
                "          MERGE (u)-[c:CHATTER]->(s))',\n" +
                "{batchSize:1})").in(database).run();
        logResultSummaries("runChattersOnDB", run);
    }


    public void persistTwitchStreams(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as stream\n" +
                        "                    MERGE (l:LiveStream{twitch_id:stream.id})\n" +
                        "                    SET  l.title = stream.title,\n" +
                        "                    l.viewer_count = stream.viewercount,\n" +
                        "                    l.started_at = datetime(stream.started_at),\n" +
                        "                    l.thumbnail_url = stream.thumbnail_url,\n" +
                        "                    l.is_mature = stream.is_mature WITH l, stream\n" +
                        "                    MATCH (u:User) WHERE u.login = stream.user_login\n" +
                        "                    MERGE (u)-[:LIVE_STREAMING]->(l)\n" +
                        "                    MERGE (l)-[:PLAYS]->(g:Game{twitch_id:stream.game_id})\n" +
                        "                    MERGE (lang:Language{key:stream.language})\n" +
                        "                    MERGE (l)-[:HAS_LANGUAGE]->(lang)\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchStreams", run);
    }


    public void persistTwitchChatters(String login, Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.chatters as chatters\n" +
                        "                    FOREACH(vip in chatters.vips| MERGE(u:User {login:vip}) MERGE(u)-[:VIP]->(s:User{login:$login}))\n" +
                        "                    FOREACH(mod in chatters.moderators | MERGE(s:User{login:$login})\n" +
                        "                    MERGE(u)-[:MODERATOR]->(s:User{login:$login}))\n" +
                        "                    FOREACH(chatter in chatters.viewers| MERGE(u:User {login:chatter})\n" +
                        "                    MERGE(u)-[:CHATTER]->(s:User{login:$login}))").in(database)
                .bind(jsonMap).to("json")
                .bind(login).to("login")
                .run();

        logResultSummaries("persistTwitchChatters", run);
    }


    public void persistTwitchLanguage(String key, String name, Integer sullyId) {
        ResultSummary run = client.query(
                        "CREATE (l:Language{key:$key}) SET l.name = $name, l.sully_id = $sullyId").in(database)
                .bind(key).to("key")
                .bind(name).to("name")
                .bind(sullyId).to("sullyId")
                .run();
        logResultSummaries("persistTwitchLanguage", run);
    }


    public void persistTwitchFollowersTo(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as follower\n" +
                        "                    MERGE (f:User{login:follower.from_login})\n" +
                        "                    MERGE (t:User{login:follower.to_login})\n" +
                        "                    SET t.twitch_followers_to = $json.total\n" +
                        "                    MERGE (f)-[:FOLLOWS{followed_at:datetime(follower.followed_at)}]->(t);\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchFollowersTo", run);
    }


    public void persistTwitchFollowersFrom(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as follower\n" +
                        "                    MERGE (f:User{login:follower.from_login})\n" +
                        "                    SET f.twitch_followers_from = $json.total\n" +
                        "                    MERGE (t:User{login:follower.to_login})\n" +
                        "                    MERGE (f)-[:FOLLOWS{followed_at:datetime(follower.followed_at)}]->(t);\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchFollowersFrom", run);
    }


    public void persistSullyChannels(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as channel\n" +
                        " MERGE (c:Channel{login:channel.url})\n" +
                        "          SET       c.followers = channel.followers,\n" +
                        "                    c.view_minutes = channel.viewminutes,\n" +
                        "                    c:User,\n" +
                        "                    c.row_number = channel.rownum,\n" +
                        "                    c.streamed_minutes = channel.streamedminutes,\n" +
                        "                    c.max_viewers = channel.maxviewers,\n" +
                        "                    c.avg_viewers = channel.avgviewers,\n" +
                        "                    c.followers_gained = channel.followersgained,\n" +
                        "                    c.views_gained = channel.viewsgained,\n" +
                        "                    c.partner = channel.partner,\n" +
                        "                    c.affiliate = channel.affiliate,\n" +
                        "                    c.mature = channel.mature,\n" +
                        "                    c.status = channel.status,\n" +
                        "                    c.profile_image_url = channel.logo,\n" +
                        "                    c.twitch_link = channel.twitchurl,\n" +
                        "                    c.sully_id = channel.id,\n" +
                        "                    c.name = channel.displayname\n" +
                        "MERGE (l:Language{name:channel.language}) \n" +
                        " MERGE (c)-[:HAS_LANGUAGE]->(l)").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries("persistSullyChannels", run);
        stopWatch.stop();
        log.trace("Persisting Sully Channels took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }


    /**

     "watchtime", "Watch time (hours)", "M", 1));
     "streamtime", "Stream time (hours)", "M", 1));
     "maxviewers", "Peak viewers", "N", 1));
     "avgviewers", "Average viewers", "N", 1));
     "maxchannels", "Peak members", "N", 1));
     "avgchannels", "Average members", "N", 1));
     "members", "Members", "N", 1));

     * @param jsonMap
     */
    public void persistSullyTeams(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as team\n" +
                        "MERGE (t:Team{name:team.name})\n" +
                        "          SET       t.members = team.members,\n" +
                        "                    t.stream_time = team.streamtime,\n" +
                        "                    t.watch_time = team.watchtime,\n" +
                        "                    t.max_viewers = team.maxviewers,\n" +
                        "                    t.avg_viewers = team.avgviewers,\n" +
                        "                    t.max_channels = team.maxchannels,\n" +
                        "                    t.avg_channels = team.avgchannels,\n" +
                        "                    t.login = team.twitchurl,\n" +
                        "                    t.row_number = team.rownum,\n" +
                        "                    t.sully_id = team.id,\n" +
                        "                    t.profile_image_url = team.logo;").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries("persistSullyTeams", run);
        stopWatch.stop();
        log.trace("Persisting Sully Teams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    /**

   "viewminutes", "Watch time (hours)", "M", 5));
     "streamedminutes", "Stream time (hours)", "M", 5));
     "maxviewers", "Peak viewers", "N", 5));
     "maxchannels", "Peak channels", "N", 5));
     "uniquechannels", "Streamers", "N", 5));
     "avgviewers", "Average viewers", "N", 5));
     "avgchannels", "Average channels", "N", 5));
     "avgratio", "Average viewer ratio", "D", 5, null, null, null, null));
      hviewsgained", "Views gained", "N", 1)),
     "vphs", "VPR", "Number of views gained per hour streamed by all channels"

     "changeviewerminutes", "Change watch time (hours)", "M", 5));
     "changestreamedminutes", "Change stream time (hours)", "M", 5));
     "changemaxviewers", "Change peak viewers", "N", 5));
     "changemaxchannels", "Change peak channels", "N", 5));
     "changeuniquechannels", "Change streamers", "N", 5));
     "changeaverageviewers", "Change average viewers", "N", 5));
     "changeaveragechannels", "Change average channels", "N", 5));
     "changeaverageratio", "Change average viewer ratio", "D", 5));
     "name", "", "https://www.twitch.tv/directory/game/{0}",

     viewminutes", "Watch time (hours)", "M", 1));
     "streamedminutes", "Stream time (hours)", "M", 1));
     "maxviewers", "Peak viewers", "N", 1));
     "avgviewers", "Average viewers", "N", 1));
     "followers", "Followers", "N", 1));
     "partner", "Partnered", "B", null, null, "Partnered"));
     "mature", "Mature", "B", null, null, "Mature"));
     "language", "Language"));
     "displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));


     * @param jsonMap
     */
    public void persistSullyGames(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:Game{name:game.name})\n" +
                        "            SET     g.view_minutes = game.viewminutes,\n" +
                        "                    g.streamed_minutes = game.streamedminutes,\n" +
                        "                    g.row_number = game.rownum,\n" +
                        "                    g.max_channels = game.maxchannels,\n" +
                        "                    g.unique_channels = game.uniquechannels,\n" +
                        "                    g.avg_channels = game.avgchannels,\n" +
                        "                    g.max_viewers = game.maxviewers,\n" +
                        "                    g.avg_viewers = game.avgviewers,\n" +
                        "                    g.avg_ratio = game.avgratio,\n" +
                        "                    g.views_gained = game.viewsgained,\n" +
                        "                    g.followers_gained = game.followersgained,\n" +
                        "                    g.previous_view_minutes = game.previousviewminutes,\n" +
                        "                    g.previous_streamed_minutes = game.previousstreamedminutes,\n" +
                        "                    g.previous_max_channels = game.previousmaxchannels,\n" +
                        "                    g.previous_unique_channels = game.previousuniquechannels,\n" +
                        "                    g.previous_avg_channels = game.previousavgchannels,\n" +
                        "                    g.previous_max_viewers = game.previousmaxviewers,\n" +
                        "                    g.previous_avg_viewers = game.previousavgviewers,\n" +
                        "                    g.previous_avg_ratio = game.previousavgratio,\n" +
                        "                    g.previous_vphs = game.previousvphs,\n" +
                        "                    g.fphs = game.fphs,\n" +
                        "                    g.vphs = game.vphs,\n" +
                        "                    g.sully_id = game.id,\n" +
                        "                    g.logo = game.logo;").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries("persistSullyGames", run);
        stopWatch.stop();
        log.trace("Persisting Sully Games took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }


    /**

     //NOT CORRECT
     "displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", h), "url", "displayname"));
     "viewminutes", "Watch time (hours)", "M", 5));
     "streamedminutes", "Stream time (hours)", "M", 5));
     "maxviewers", "Peak viewers", "N", 5));
     "avgviewers", "Average viewers", "N", 5));
     "followers", "Followers", "N", 5));
     "followersgained", "Followers gained", "N", 5));
     "viewsgained", "Views gained", "N", 5));
     "partner", "Partnered", "B", null, null, "Partnered", null, null, null, !0));
     "mature", "Mature", "B", null, null, "Mature", null, null, null, !0));
     "language", "Language", null, null, null, null, null, null, null, !0));
     "displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png"));

     * @param jsonMap
     */

    /**

     channeldisplayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel"), "channelurl", "channeldisplayname"));
     "starttime", "Stream start time", c, "channelurl", "starttime", "channeldisplayname", "View detailed stream stats", "streamId", "endtime", "to:"));
     "streamUrl", "Stream URL"));
     "length", "Stream length", "MM", 1));
     "viewminutes", "Watch time (hours)", "M", 1));
     "avgviewers", "Avg viewers", "N", 1));
     "maxviewers", "Peak viewers", "N", 1));
     "followergain", "Followers gained", "N", 1));
     "followersperhour", "Followers per hour", "D", 1));
     "viewgain", "Views", "N", 1));
     "viewsperhour", "Views per hour", "D", 1));
     "gamesplayed", "Games", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));

     * @param jsonMap
     */

    public void persistSullyChannelStreams(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as stream\n" +
                        "MERGE (s:ChannelStream{sully_id:stream.streamId})\n" +
                        "            SET     s.start_time = stream.starttime,\n" +
                        "                    s.end_time = stream.endtime,\n" +
                        "                    s.length = stream.length,\n" +
                        "                    s.row_number = stream.rownum,\n" +
                        "                    s.view_gain = stream.viewgain,\n" +
                        "                    s.follower_gain = stream.followergain,\n" +
                        "                    s.avg_viewers = stream.avgviewers,\n" +
                        "                    s.max_viewers = stream.maxviewers,\n" +
                        "                    s.followers_per_hour = stream.followersperhour,\n" +
                        "                    s.views_per_hour = stream.viewsperhour,\n" +
                        "                    s.start_date_time = stream.startDateTime,\n" +
                        "                    s.sully_stream_url = stream.streamUrl,\n" +
                        "                    s.view_minutes = stream.viewminutes\n" +
                        "MERGE (c:Channel{login:stream.channelurl}) \n" +
                        "MERGE (c)-[:STREAMED]->(s);"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistSullyChannelStreams", run);

    }


    public void persistSullyChannelIndividualStream(Long channelStreamId, IndividualStreamDTO individualStreamDTO) {
        //MATCH (cs:ChannelStream) WHERE NOT (cs)-[:METADATA]->() RETURN cs.sully_id

        for (IndividualStreamDataDTO data : individualStreamDTO.getGamesPlayed()) {
            ResultSummary run = client.query("MATCH (c:ChannelStream{sully_id:$channelStreamId})\n" +
                            "MATCH (g:Game{name:$gameName})\n" +
                            "MERGE (c)-[m:STREAM_METADATA]->(g)\n" +
                            "SET m.watch_time = $watchTime,\n" +
                            "m.stream_length = $streamLength,\n" +
                            "m.average_viewers = $averageViewers,\n" +
                            "m.max_viewers = $maxViewers,\n" +
                            "m.max_viewers_performance = $maxViewersPerformance,\n" +
                            "m.views_per_hour = $viewsPerHour,\n" +
                            "m.views = $views,\n" +
                            "m.views_performance= $viewsPerformance;"
                    ).in(database)
                    .bind(channelStreamId).to("channelStreamId")
                    .bind(data.getGameName()).to("gameName")
                    .bind(data.getWatchTime()).to("watchTime")
                    .bind(data.getStreamLength()).to("streamLength")
                    .bind(data.getAverageViewers()).to("averageViewers")
                    .bind(data.getMaxViewers()).to("maxViewers")
                    .bind(data.getMaxViewersPerformance()).to("maxViewersPerformance")
                    .bind(data.getViewsPerHour()).to("viewsPerHour")
                    .bind(data.getViews()).to("views")
                    .bind(data.getViewsPerHour()).to("viewsPerformance")
                    .run();
            logResultSummaries("persistSullyChannelIndividualStream", run);
        }


    }


    /**


     "gamesplayed", "Game", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
     "streamtime", "Stream time (hours)", "M", 1));
     "viewtime", "Total watch time (hours)", "M", 1));
     "avgviewers", "Average viewers", "N", 1));
     "maxviewers", "Peak viewers", "N", 1));
     "viewsgained", "Views", "N", 1));
     "viewsperhour", "Views per hour", "D", 1));

     * @param channelId
     * @param jsonMap
     */
    public void persistSullyChannelGames(Long channelId, Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MATCH (g:Game{name:split(game.gamesplayed,\"|\")[0]})\n" +
                        "            MATCH (c:Channel{sully_id:$channelId}) \n" +
                        "MERGE (c)-[m:GAME_METADATA]->(g) \n" +
                        "            SET     m.stream_time = game.streamtime,\n" +
                        "                    m.view_time = game.viewtime,\n" +
                        "                    m.row_num = game.rownum,\n" +
                        "                    m.views_gained = game.viewsgained,\n" +
                        "                    m.followers = game.followers,\n" +
                        "                    m.avg_viewers = game.avgviewers,\n" +
                        "                    m.max_viewers = game.maxviewers,\n" +
                        "                    m.followers_per_hour = game.followersperhour,\n" +
                        "                    m.views_per_hour = game.viewsperhour\n").in(database)
                .bind(jsonMap).to("json")
                .bind(channelId).to("channelId")
                .run();
        logResultSummaries("persistSullyChannelGames", run);

    }


    /**


     "logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    "displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
     "liveMinutes", "Live for", "MM", 1, null, null, "avgLengthMins", "liveMinutes", {
     tooltip: "Length of time the channel has been live. Percentage progress is an estimate based on previous streams.",
     "liveViewers", "Current viewers", "N", 1, null, null, null, null, {
     tooltip: "Current viewership of the channel.",
     "avgviewers", "Avg viewers", "N", 1, null, null, null, null, {
     tooltip: "The channels average viewers in recent history.",
     "overlappingStreams", "Overlapping streams", "N", 1, null, null, "streams", "overlappingStreams", {
     tooltip: "The number of streams were both channels were live at the same time.",
     "overlappingEndedDuring", "Ended during", "N", 1, null, null, null, null, {
     "The number of streams where the result channels stream ended during " + a + "s stream.",
     "overlappingEndedAfter", "Ended after", "N", 1, null, null, null, null, {
     tooltip: "The number of streams where the result channels stream ended after " + a + "s stream.",
     "status", "Status"));
     "mature", "Mature", "B", null, null, "Mature"));
     "language", "Language"));
     "currentGame", "Streaming", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
     "gamesPlayed", "Recently streamed", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
     "displayname", "Preview", "preview", "previewLarge", "", ""));
     "displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));


     * @param channelLogin
     * @param jsonMap
     */
    public void persistSullyChannelRaidFinder(String channelLogin, Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as rf\n" +
                        "MATCH (c:Channel{login:$channelLogin})\n" +
                        //"WITH c, rf\n" +
                        "MATCH (c2:Channel{login:rf.url}) WHERE NOT c.login = c2.login\n" +
                        "                    MERGE (c2)<-[cr:CAN_RAID]-(c)\n" +
                        "            SET     cr.live_minutes = rf.liveMinutes,\n" +
                        "                    cr.live_viewers = rf.liveViewers,\n" +
                        "                    cr.overlapping_streams = rf.overlappingStreams,\n" +
                        "                    cr.other_channel_streams = rf.otherChannelStreams,\n" +
                        "                    cr.overlapping_ended_during = rf.overlappingEndedDuring,\n" +
                        "                    cr.overlapping_ended_after = rf.overlappingEndedAfter;").in(database)
                .bind(jsonMap).to("json")
                .bind(channelLogin).to("channelLogin")
                .run();

        logResultSummaries("persistSullyChannelRaidFinder", run);
    }


    /**

     "estimatedposition", "Estimated directory position", "", 4));
     "historicfollowersperhour", b, "D", 1));
     "standardisedfollowergain", "Followers per hour (estimated)", "D", 1));
     "aboveviewers", "Viewers in larger channels", "N", 3, p));
     "inrangeviewers", "Viewers in similar viewership", "N", 2, p));
     "belowviewers", "Viewers in equal/smaller", "N", 1, p));
     "abovechannels", "Larger channels", "N", 3, y));
     "inrangechannels", "Channels with similar viewership", "N", 2, y));
     "belowchanels", "Equal/Smaller channels", "N", 1, y));
     estposition", "Estimated position", "N", 1);
     "viewerratio", "Viewer ratio", "D1", 1);
     "averageviewers", "Average viewers", "N", 1);
     "peraverageviewers", "% Twitch viewers", "P", 1));
     "gametrendviewersrecent", "Viewer recent trend", null, s);
     "viewersabove", "Above/Below", ["viewersabove", "viewerssame", "viewersbelow"], ""));
     "gametrendviewers3day", "3 day trend", null, s);
     "averagechannels", "Average channels", "N", 1);
     "peraveragechannels", "% Twitch channels", "P", 1));
     "gametrendchannelsrecent", "Channel recent trend", null, s);
     "channelsabove", "Above/Below", ["channelsabove", "channelssame", "channelsbelow"], ""));
     "gametrendchannels3day", "3 day trend", null, s);

     * @param jsonMap
     */
    public void persistSullyChannelGameFinder(Map jsonMap) {

        ResultSummary run = client.query("UNWIND $json.data as gf\n" +
                        "MATCH (g:Game{sully_id:gf.id})\n" +
                        "            SET     g.gf_average_viewers = gf.averageviewers,\n" +
                        "                    g.gf_row_num = gf.rownum,\n" +
                        "                    g.gf_average_channels = gf.averagechannels,\n" +
                        "                    g.gf_per_average_viewers = gf.peraverageviewers,\n" +
                        "                    g.gf_per_average_channels = gf.peraveragechannels,\n" +
                        "                    g.gf_per_recent_avg_viewers = gf.perrecentavgviewers,\n" +
                        "                    g.gf_per_past_1_avg_viewers = gf.perpast1avgviewers,\n" +
                        "                    g.gf_per_past_2_avg_viewers = gf.perpast2avgviewers,\n" +
                        "                    g.gf_per_past3_avg_viewers = gf.perpast3avgviewers,\n" +
                        "                    g.gf_per_recent_avg_channels = gf.perrecentavgchannels,\n" +
                        "                    g.gf_per_past_1_avg_channels = gf.perpast1avgchannels,\n" +
                        "                    g.gf_per_past_2_avg_channels = gf.perpast2avgchannels,\n" +
                        "                    g.gf_per_past_3_avg_channels = gf.perpast3avgchannels,\n" +
                        "                    g.gf_channels_above = gf.channelsabove,\n" +
                        "                    g.gf_channels_same = gf.channelssame,\n" +
                        "                    g.gf_channels_below = gf.channelsbelow,\n" +
                        "                    g.gf_viewers_above = gf.viewersabove,\n" +
                        "                    g.gf_viewers_same = gf.viewerssame,\n" +
                        "                    g.gf_viewers_below = gf.viewersbelow,\n" +
                        "                    g.gf_est_position = gf.estposition,\n" +
                        "                    g.gf_viewer_ratio = gf.viewerratio,\n" +
                        "                    g.gf_viewer_ratio_same_blow = gf.viewerratiosameblow,\n" +
                        "                    g.gf_game_trend_channels_recent = gf.gametrendchannelsrecent,\n" +
                        "                    g.gf_game_trend_channels_3_day = gf.gametrendchannels3day,\n" +
                        "                    g.gf_game_trend_viewers_recent = gf.gametrendviewersrecent,\n" +
                        "                    g.gf_game_trend_viewers_3_day = gf.gametrendviewers3day,\n" +
                        "                    g.gf_twitch_game_trend_channels_recent = gf.twitchgametrendchannelsrecent,\n" +
                        "                    g.gf_twitch_game_trend_channels_3_day = gf.twitchgametrendchannels3day,\n" +
                        "                    g.gf_twitch_game_trend_viewers_recent = gf.twitchgametrendviewersrecent,\n" +
                        "                    g.gf_twitch_game_trend_viewers_3_day = gf.twitchgametrendviewers3day;").in(database)
                .bind(jsonMap).to("json")
                .bind(jsonMap).to("channelLogin")
                .run();

        logResultSummaries("persistSullyChannelGameFinder", run);
    }


    public void persistTwitchGames(Map map) {
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:Game{name:game.name})\n" +
                        "SET g.twitch_id = toInteger(game.id),\n" +
                        "g.box_art_url = game.box_art_url;"
                ).in(database)
                .bind(map).to("json")
                .run();

        logResultSummaries("persistTwitchGames", run);
    }

}
