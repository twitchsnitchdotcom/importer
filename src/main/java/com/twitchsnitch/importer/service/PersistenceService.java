package com.twitchsnitch.importer.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.channels.*;
import com.twitchsnitch.importer.dto.sully.games.GamesDatum;
import com.twitchsnitch.importer.dto.sully.games.GamesTable;
import com.twitchsnitch.importer.dto.sully.teams.TeamsDatum;
import com.twitchsnitch.importer.dto.sully.teams.TeamsTable;
import com.twitchsnitch.importer.dto.twitch.*;
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
        ResultSummary run = client.query("MATCH (n) DETACH DELETE n").in(database).run();
        log.debug("Nodes deleted from the DB: " + run.counters().nodesDeleted());
    }

    public void dropDBConstraints() {
        ResultSummary dropConstraints = client.query("CALL apoc. schema. assert({}, {});").in(database).run();
        logResultSummaries("", dropConstraints);
    }

    public void runDBConstraints() {

        ResultSummary gameDisplayNameConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.display_name IS UNIQUE;").in(database).run();
        ResultSummary languageConstraint = client.query("CREATE CONSTRAINT FOR (l:Language) REQUIRE l.key IS UNIQUE;").in(database).run();
        ResultSummary channelLoginConstraint = client.query("CREATE CONSTRAINT FOR (c:Channel) REQUIRE c.login IS UNIQUE;").in(database).run();
        ResultSummary userLoginConstraint = client.query("CREATE CONSTRAINT FOR (u:User) REQUIRE u.login IS UNIQUE;").in(database).run();
        ResultSummary teamNameConstraint = client.query("CREATE CONSTRAINT FOR (t:Team) REQUIRE t.name IS UNIQUE;").in(database).run();
        ResultSummary gameFinderCompositeConstraint = client.query("CREATE CONSTRAINT FOR (g:GameFinder) REQUIRE g.composite_sully_id IS UNIQUE;").in(database).run();
        ResultSummary raidFinderCompositeConstraint = client.query("CREATE CONSTRAINT FOR (r:RaidFinder) REQUIRE r.composite_sully_id IS UNIQUE;").in(database).run();


        ResultSummary languageNameIndex = client.query("CREATE INDEX FOR (l:Language) ON (l.name);").in(database).run();
        ResultSummary gameSullyIdIndex = client.query("CREATE INDEX FOR (g:Game) ON (g.sully_id);").in(database).run();
        ResultSummary gameTwitchIdIndex = client.query("CREATE INDEX FOR (g:Game) ON (g.twitch_id);").in(database).run();
        ResultSummary channelSullyIdIndex = client.query("CREATE INDEX FOR (c:Channel) ON (c.sully_id);").in(database).run();
        ResultSummary channelTwitchIdIndex = client.query("CREATE INDEX FOR (c:Channel) ON (c.twitch_id);").in(database).run();
        ResultSummary teamSullyIdIndex = client.query("CREATE INDEX FOR (t:Team) ON (t.sully_id);").in(database).run();
        ResultSummary teamTwitchIdIndex = client.query("CREATE INDEX FOR (t:Team) ON (t.twitch_id);").in(database).run();

        logResultSummaries("gameDisplayNameConstraint", gameDisplayNameConstraint);
        logResultSummaries("languageConstraint", languageConstraint);
        logResultSummaries("channelLoginConstraint", channelLoginConstraint);
        logResultSummaries("userLoginConstraint", userLoginConstraint);
        logResultSummaries("teamNameConstraint", teamNameConstraint);
        logResultSummaries("gameFinderCompositeConstraint", gameFinderCompositeConstraint);
        logResultSummaries("raidFinderCompositeConstraint", raidFinderCompositeConstraint);

        logResultSummaries("languageNameIndex", languageNameIndex);
        logResultSummaries("gameSullyIdIndex", gameSullyIdIndex);
        logResultSummaries("gameTwitchIdIndex", gameTwitchIdIndex);
        logResultSummaries("channelSullyIdIndex", channelSullyIdIndex);
        logResultSummaries("channelTwitchIdIndex", channelTwitchIdIndex);
        logResultSummaries("teamSullyIdIndex", teamSullyIdIndex);
        logResultSummaries("teamTwitchIdIndex", teamTwitchIdIndex);

    }

    private void logResultSummaries(String key, ResultSummary resultSummary) {
        System.out.println("-------------------------------------------------------------------------");
        log.debug("RESULT SUMMARY FOR: " + key);

        if (resultSummary.counters().nodesCreated() > 0) {
            log.debug("Nodes created: " + resultSummary.counters().nodesCreated());
        }
        if (resultSummary.counters().labelsAdded() > 0) {
            log.debug("Labels added: " + resultSummary.counters().labelsAdded());
        }
        if (resultSummary.counters().relationshipsCreated() > 0) {
            log.debug("Relationships added: " + resultSummary.counters().relationshipsCreated());
        }
        if (resultSummary.counters().relationshipsDeleted() > 0) {
            log.debug("Relationships deleted: " + resultSummary.counters().relationshipsDeleted());
        }
        if (resultSummary.counters().indexesAdded() > 0) {
            log.debug("Indexes added: " + resultSummary.counters().indexesAdded());
        }
        if (resultSummary.counters().indexesRemoved() > 0) {
            log.debug("Indexes removed: " + resultSummary.counters().indexesRemoved());
        }
        if (resultSummary.counters().constraintsAdded() > 0) {
            log.debug("Constraints added: " + resultSummary.counters().constraintsAdded());
        }
        if (resultSummary.counters().constraintsRemoved() > 0) {
            log.debug("Constraints added: " + resultSummary.counters().constraintsRemoved());
        }
        if (resultSummary.counters().propertiesSet() > 0) {
            log.debug("Properties set: " + resultSummary.counters().propertiesSet());
        }

        System.out.println("-------------------------------------------------------------------------");

    }

    //TWITCH METHODS
    public Set<Long> getChannelsWithoutChannelGameData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<Long> sullyChannelStreams = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) WHERE exists (c.sully_id) AND NOT (c)-[:HAS_CHANEL_GAME]->() RETURN c.sully_id").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannelStreams.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Sully channel streams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannelStreams;
    }

    //todo be extended to be actually accurate
    public Set<Long> getChannelStreamsWithoutIndividualData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<Long> sullyChannelStreams = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:ChannelStreams) WHERE exists (c.sully_id) AND NOT (c)-[:HAS_STREAM]->() RETURN c.sully_id").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannelStreams.add((Long) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Sully channel streams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannelStreams;
    }

    public Set<String> getAllSullyChannels(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> sullyChannels = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) RETURN c.sully_id").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                sullyChannels.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Sully channels  took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return sullyChannels;
    }

    public Set<String> getAllGamesWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> gamesWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (g:Game) WHERE g.twitch_id IS NULL RETURN g.name LIMIT 100").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                gamesWithoutTwitchIds.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Games Without TwitchIds took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return gamesWithoutTwitchIds;
    }

    public Set<String> getUsersWithoutTwitchFollowsTo(Integer limit) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutFollowsTo = new HashSet<>();
        Collection<Map<String, Object>> all;
        if (limit != null) {
            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_to IS NULL RETURN c.twitch_id ORDER BY c.followers DESC").fetch().all();

        } else {
            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_to IS NULL RETURN c.twitch_id ORDER BY c.followers DESC LIMIT " + limit).fetch().all();

        }
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutFollowsTo.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Users without twitch_follows_to took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutFollowsTo;
    }

    public Set<String> getUsersWithoutTwitchFollowsFrom(Integer limit) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutFollowsFrom = new HashSet<>();
        Collection<Map<String, Object>> all;
        if (limit != null) {
            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_from IS NULL RETURN c.twitch_id ORDER BY c.followers DESC").fetch().all();

        } else {
            all = client.query("MATCH (c:Channel) WHERE c.twitch_follows_from IS NULL RETURN c.twitch_id ORDER BY c.followers DESC LIMIT " + limit).fetch().all();

        }
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutFollowsFrom.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Users without twitch_follows_from took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutFollowsFrom;
    }

    public Set<String> getUsersWithoutTwitchId(Integer limit){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> usersWithoutTwitchId = new HashSet<>();
        Collection<Map<String, Object>> all;
        if (limit != null) {
            all = client.query("MATCH (u:User) WHERE u.twitch_id IS NULL RETURN u.login").fetch().all();

        } else {
            all = client.query("MATCH (u:User) WHERE u.twitch_id IS NULL RETURN u.login LIMIT " + limit).fetch().all();

        }
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                usersWithoutTwitchId.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Users without twitch_id took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return usersWithoutTwitchId;
    }

    public Set<String> getTeamsWithoutTwitchId(Integer limit){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> teamsWithoutTwitchId = new HashSet<>();
        Collection<Map<String, Object>> all;
        if (limit != null) {
            all = client.query("MATCH (t:Team) WHERE t.twitch_id IS NULL RETURN t.login").fetch().all();

        } else {
            all = client.query("MATCH (t:Team) WHERE t.twitch_id IS NULL RETURN t.login LIMIT " + limit).fetch().all();

        }
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                teamsWithoutTwitchId.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All teams without twitch_id took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return teamsWithoutTwitchId;
    }

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
        ResultSummary run = client.query("UNWIND $json.data as data \n" +
                        "MATCH (t:Team{login:$login})\n" +
                        "SET t.created_at = datetime(replace(trim(split($json.created_at.created_at,\"+\")[0]), \" \", \"T\")),\n" +
                        "    t.updated_at = datetime(replace(trim(split($json.updated_at,\"+\")[0]), \" \", \"T\")),\n" +
                        "    t.info = $json.info,\n" +
                        "    t.twitch_id = $json.id\n" +
                        "    WITH data\n" +
                        "WHERE data IS NOT NULL \n" +
                        "    UNWIND data as team\n" +
                "WHERE team.user_login IS NOT NULL\n" +
                        "MERGE (u:User{login:team.user_login})-[:MEMBER_OF]->(t) ;").in(database)
                .bind(json).to("json")
                .bind(login).to("login")
                .run();
        logResultSummaries("updateTeamWithTwitchData", run);
    }

    public void persistTwitchStreams(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as stream\n" +
                        "                    MERGE (l:LiveStream{twitch_id:stream.id})\n" +
                        "                    SET     l.title = stream.title,\n" +
                        "                    l.viewer_count = stream.viewercount,\n" +
                        "                    l.started_at = datetime(stream.started_at),\n" +
                        "                    l.thumbnail_url = stream.thumbnail_url,\n" +
                        "                    l.is_mature = stream.is_mature\n" +
                        "                    MERGE (u:User{login:stream.user_login})\n" +
                        "                    MERGE (u)-[:PLAYS]->(g:Games{twitch_id:stream.game_id})\n" +
                        "                    MERGE (lang:Language{name:stream.language})\n" +
                        "                    MERGE (u)-[:HAS_LANGUAGE]->(lang)\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchStreams", run);
    }

    public void persistTwitchChatters(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.chatters as chatters\n" +
                        "                    FOREACH(vip in chatters.vips| MERGE(u:User {login:vip}) MERGE(u)-[:VIP]->(s))\n" +
                        "                    FOREACH(mod in chatters.moderators | MERGE(u:User {login:mod})\n" +
                        "                    MERGE(u)-[:MODERATOR]->(s))\n" +
                        "                    FOREACH(chatter in chatters.viewers| MERGE(u:User {login:chatter})\n" +
                        "                    MERGE(u)-[:CHATTER]->(s))").in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchChatters", run);
    }

    public void persistTwitchLanguage(String key, String name) {
        ResultSummary run = client.query(
                        "CREATE (l:Language{key:$key}) SET l.name = $name").in(database)
                .bind(key).to("key")
                .bind(name).to("name")
                .run();
        logResultSummaries("persistTwitchLanguage", run);
    }

    public void persistTwitchFollowersTo(Map jsonMap) {
        ResultSummary run = client.query("UNWIND json.data as follower\n" +
                        "                    MERGE (f:User{login:follower.from_login})\n" +
                        "                    MERGE (t:User{login:follower.to_login})\n" +
                        "                    t.twitch_followers_to = json.total,\n" +
                        "                    MERGE (f)-[:FOLLOWS{followed_at:datetime(follower.followed_at)}]->(t);\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchFollowersTo", run);
    }

    public void persistTwitchFollowersFrom(Map jsonMap) {
        ResultSummary run = client.query("UNWIND json.data as follower\n" +
                        "                    MERGE (f:User{login:follower.from_login})\n" +
                        "                    MERGE (t:User{login:follower.to_login})\n" +
                        "                    f.twitch_followers_to = json.total,\n" +
                        "                    MERGE (f)-[:FOLLOWS{followed_at:datetime(follower.followed_at)}]->(t);\n"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistTwitchFollowersFrom", run);
    }

    //SULLY METHODS

    @Async
    public void persistSullyChannels(Integer daysPerspective, Map jsonMap) {
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
                        "                    c.followers_gained_while_playing = channel.followersgainedwhileplaying,\n" +
                        "                    c.previous_view_minutes = channel.previousviewminutes,\n" +
                        "                    c.previous_streamed_minutes = channel.previousstreamedminutes,\n" +
                        "                    c.previous_max_viewers = channel.previousmaxviewers,\n" +
                        "                    c.previous_avg_viewers = channel.previousavgviewers,\n" +
                        "                    c.previous_follower_gain = channel.previousfollowergain,\n" +
                        "                    c.previous_views_gained = channel.previousviewsgained,\n" +
                        "                    c.partner = channel.partner,\n" +
                        "                    c.affiliate = channel.affiliate,\n" +
                        "                    c.mature = channel.mature,\n" +
                        "                    c.status = channel.status,\n" +
                        "                    c.profile_image_url = channel.logo,\n" +
                        "                    c.twitch_link = channel.twitchurl,\n" +
                        "                    c.sully_id = channel.id,\n" +
                        "                    c.display_name = channel.displayname\n" +
                        "MERGE (l:Language{name:channel.language}) \n" +
                        " MERGE (c)-[:HAS_LANGUAGE]->(l)").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries("persistSullyChannels", run);
        stopWatch.stop();
        log.debug("Persisting Sully Channels took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyTeams(Integer daysPerspective, Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as team\n" +
                        "MERGE (t:Team{display_name:team.name})\n" +
                        "          SET       t.members = team.members,\n" +
                        "                    t.stream_time = team.streamtime,\n" +
                        "                    t.watch_time = team.watchtime,\n" +
                        "                    t.max_viewers = team.maxviewers,\n" +
                        "                    t.avg_viewers = team.avgviewers,\n" +
                        "                    t.max_channels = team.maxchannels,\n" +
                        "                    t.avg_channels = team.avgchannels,\n" +
                        "                    t.row_number = team.rownum,\n" +
                        "                    t.sully_id = team.id,\n" +
                        "                    t.profile_image_url = team.logo;").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries("persistSullyTeams", run);
        stopWatch.stop();
        log.debug("Persisting Sully Teams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    //cant be async because we need to bind the games onto it for the twitch id
    //@Async
    public void persistSullyGames(Integer daysPerspective, Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:Game{display_name:game.name})\n" +
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
        log.debug("Persisting Sully Games took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyChannelStreams(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as stream\n" +
                        "MERGE (s:Stream{sully_id:stream.streamId})\n" +
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
                        "MATCH (c:Channel{login:stream.channelurl}) \n" +
                        "MERGE (c)-[:STREAMED]->(s);"
                ).in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries("persistSullyChannelStreams", run);

    }

    //
    @Async
    public void persistSullyChannelIndividualStream(IndividualStreamDTO individualStreamDTO) {
        ResultSummary run = client.query(""
                ).in(database)
                .bind(null).to("json")
                .run();

        logResultSummaries("persistSullyChannelIndividualStream", run);

    }

    @Async
    public void persistSullyChannelGames(Long sullyId, Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:ChannelGame{sully_id:game.streamId})\n" +
                        "            SET     g.stream_time = game.streamtime,\n" +
                        "                    g.view_time = game.viewtime,\n" +
                        "                    g.views_gained = game.viewsgained,\n" +
                        "                    g.followers = game.followers,\n" +
                        "                    g.avg_viewers = game.avgviewers,\n" +
                        "                    g.max_viewers = game.maxviewers,\n" +
                        "                    g.followers_per_hour = game.followersperhour,\n" +
                        "                    g.games_played = game.gamesplayed,\n" +
                        "                    g.views_per_hour = game.viewsperhour\n" +
                        "MERGE (c:Channel{sully_id:$sullyId})-[:HAS_CHANNEL_GAME]->(g)").in(database)
                .bind(jsonMap).to("json")
                .bind(sullyId).to("sullyId")
                .run();
        logResultSummaries("persistSullyChannelGames", run);

    }

    @Async
    public void persistSullyChannelRaidFinder(String channelLogin, Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json as rf\n" +
                        "MERGE (r:RaidFinder{composite_sully_id:rf.id#$channelLogin})\n" +
                        "            SET     r.live_minutes = rf.liveMinutes,\n" +
                        "                    r.live_viewers = rf.liveViewers,\n" +
                        "                    r.overlapping_streams = rf.overlappingStreams,\n" +
                        "                    r.other_channel_streams = rf.otherChannelStreams,\n" +
                        "                    r.overlapping_ended_during = rf.overlappingEndedDuring,\n" +
                        "                    r.overlapping_ended_after = rf.overlappingEndedAfter,\n" +
                        "                    MERGE (raided:Channel{login:rf.url})<-[:RAID_RECIPIENT]-(r)-[:RAID_DONOR]->(c:Channel{login:$channelLogin});").in(database)
                .bind(jsonMap).to("json")
                .bind(channelLogin).to("channelLogin")
                .run();

        logResultSummaries("persistSullyChannelRaidFinder", run);
    }

    @Async
    public void persistSullyChannelGameFinder(String channelLogin, Map jsonMap) {

        ResultSummary run = client.query("UNWIND $json.data as gf\n" +
                        "MERGE (g:GameFinder{composite_sully_id:gf.id#$channelLogin})\n" +
                        "            SET     g.average_viewers = gf.averageviewers,\n" +
                        "                    g.average_channels = gf.averagechannels,\n" +
                        "                    g.per_average_viewers = gf.peraverageviewers,\n" +
                        "                    g.per_average_channels = gf.peraveragechannels,\n" +
                        "                    g.per_recent_avg_viewers = gf.perrecentavgviewers,\n" +
                        "                    g.per_past_1_avg_viewers = gf.perpast1avgviewers,\n" +
                        "                    g.per_past_2_avg_viewers = gf.perpast2avgviewers,\n" +
                        "                    g.per_past3_avg_viewers = gf.perpast3avgviewers,\n" +
                        "                    g.per_recent_avg_channels = gf.perrecentavgchannels,\n" +
                        "                    g.per_past_1_avg_channels = gf.perpast1avgchannels,\n" +
                        "                    g.per_past_2_avg_channels = gf.perpast2avgchannels,\n" +
                        "                    g.per_past_3_avg_channels = gf.perpast3avgchannels,\n" +
                        "                    g.channels_above = gf.channelsabove,\n" +
                        "                    g.channels_same = gf.channelssame,\n" +
                        "                    g.channels_below = gf.channelsbelow,\n" +
                        "                    g.viewers_above = gf.viewersabove,\n" +
                        "                    g.viewers_same = gf.viewerssame,\n" +
                        "                    g.viewers_below = gf.viewersbelow,\n" +
                        "                    g.est_position = gf.estposition,\n" +
                        "                    g.viewer_ratio = gf.viewerratio,\n" +
                        "                    g.viewer_ratio_same_blow = gf.viewerratiosameblow,\n" +
                        "                    g.game_trend_channels_recent = gf.gametrendchannelsrecent,\n" +
                        "                    g.game_trend_channels_3_day = gf.gametrendchannels3day,\n" +
                        "                    g.game_trend_viewers_recent = gf.gametrendviewersrecent,\n" +
                        "                    g.game_trend_viewers_3_day = gf.gametrendviewers3day,\n" +
                        "                    g.twitch_game_trend_channels_recent = gf.twitchgametrendchannelsrecent,\n" +
                        "                    g.twitch_game_trend_channels_3_day = gf.twitchgametrendchannels3day,\n" +
                        "                    g.twitch_game_trend_viewers_recent = gf.twitchgametrendviewersrecent,\n" +
                        "                    g.twitch_game_trend_viewers_3_day = gf.twitchgametrendviewers3day,\n" +
                        "                    g.name = gf.name,\n" +
                        "                    MATCH (game:Game{sully_id:gf.id)\n" +
                        "                    MERGE (game)<-[:IN_GAME]-(g)-[:GAME_FOUND]->(c:Channel{login:$channelLogin});").in(database)
                .bind(jsonMap).to("json")
                .bind(jsonMap).to("channelLogin")
                .run();

        logResultSummaries("persistSullyChannelGameFinder", run);
    }

}
