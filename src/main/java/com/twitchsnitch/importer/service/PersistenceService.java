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
import com.twitchsnitch.importer.dto.twitch.GameDTO;
import com.twitchsnitch.importer.dto.twitch.GameListDTO;
import com.twitchsnitch.importer.dto.twitch.StreamTagDTO;
import com.twitchsnitch.importer.dto.twitch.StreamTagListDTO;
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

    //DB METHODS
    public void deleteDBData() {
        ResultSummary run = client.query("MATCH (n) DETACH DELETE n").in(database).run();
        log.debug("Nodes deleted from the DB: " + run.counters().nodesDeleted());
    }

    public void dropDBConstraints() {
        ResultSummary dropConstraints = client.query("CALL apoc. schema. assert({}, {});").in(database).run();
        logResultSummaries(dropConstraints);
    }

    public void runDBConstraints() {

        ResultSummary gameDisplayNameConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.display_name IS UNIQUE;").in(database).run();
        ResultSummary languageConstraint = client.query("CREATE CONSTRAINT FOR (l:Language) REQUIRE l.name IS UNIQUE;").in(database).run();
        ResultSummary channelLoginConstraint = client.query("CREATE CONSTRAINT FOR (c:Channel) REQUIRE c.login IS UNIQUE;").in(database).run();
        ResultSummary userLoginConstraint = client.query("CREATE CONSTRAINT FOR (u:User) REQUIRE u.login IS UNIQUE;").in(database).run();
        ResultSummary teamNameConstraint = client.query("CREATE CONSTRAINT FOR (t:Team) REQUIRE t.name IS UNIQUE;").in(database).run();

        ResultSummary gameSullyIdIndex = client.query("CREATE INDEX ON :Game(sully_id);").in(database).run();
        ResultSummary gameTwitchIdIndex = client.query("CREATE INDEX ON :Game(twitch_id);").in(database).run();
        ResultSummary channelSullyIdIndex = client.query("CREATE INDEX ON :Channel(sully_id);").in(database).run();
        ResultSummary channelTwitchIdIndex = client.query("CREATE INDEX ON :Channel(twitch_id);").in(database).run();
        ResultSummary teamSullyIdIndex = client.query("CREATE INDEX ON :Team(sully_id);").in(database).run();
        ResultSummary teamTwitchIdIndex = client.query("CREATE INDEX ON :Team(twitch_id);").in(database).run();

        logResultSummaries(gameDisplayNameConstraint);
        logResultSummaries(languageConstraint);
        logResultSummaries(channelLoginConstraint);
        logResultSummaries(userLoginConstraint);
        logResultSummaries(teamNameConstraint);

        logResultSummaries(gameSullyIdIndex);
        logResultSummaries(gameTwitchIdIndex);
        logResultSummaries(channelSullyIdIndex);
        logResultSummaries(channelTwitchIdIndex);
        logResultSummaries(teamSullyIdIndex);
        logResultSummaries(teamTwitchIdIndex);

    }

    private void logResultSummaries(ResultSummary resultSummary) {

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

    }

    //TWITCH METHODS

    public Set<String> getAllGamesWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> gamesWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (g:Game) WHERE g.twitch_id IS NULL SET g.twitch_id = false RETURN g.name").fetch().all();
        for (Map<String, Object> objectMap : all) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                gamesWithoutTwitchIds.add((String) entry.getValue());
            }
        }
        stopWatch.stop();
        log.debug("Get All Games Without TwitchIds took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return gamesWithoutTwitchIds;
    }

    public Set<String> getAllChannelsWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> channelsWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (c:Channel) WHERE c.twitch_id IS NULL RETURN c.name").fetch().all();
        for (Map<String, Object> objectMap : all) {
            log.debug(objectMap.toString());
        }
        stopWatch.stop();
        log.debug("Get All Channels Without TwitchIds took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return channelsWithoutTwitchIds;
    }

    public void updateGameWithTwitchData(Map json) {
        ResultSummary run = client.query("UNWIND $json.data as game " +
                "MATCH (g:Game) WHERE g.name = game.name SET g.twitch_id = game.id;").in(database).bind(json).to("json").run();
        logResultSummaries(run);
    }

    public void updateUserWithTwitchData(Map json) {
        ResultSummary run = client.query("UNWIND $json.data as user " +
                "MATCH (u:User) WHERE u.login = user.login SET u.twitch_id = user.login, u.created_at = datetime(user.created_at), u.total_view_count = user.view_count;").in(database).bind(json).to("json").run();
        logResultSummaries(run);
    }

    public void updateTeamWithTwitchData(Map json) {
        ResultSummary run = client.query("MATCH (t:Team{name:$json.team_name})\n" +
                "SET t.created_at = datetime($json.created_at),\n" +
                "    t.updated_at = datetime($json.updated_at),\n" +
                "    t.info = $json.info,\n" +
                "    t.twitch_id = $json.id\n" +
                "UNWIND $json.data as member\n " +
                "MERGE (u:User{login:member.login})-[:MEMBER_OF]->(t);").in(database).bind(json).to("json").run();
        logResultSummaries(run);
    }

    //SULLY METHODS

    @Async
    public void persistSullyChannels(Integer daysPerspective, Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as channel" +
                        " MERGE (c:Channel{login:channel.url})\n" +
                        "          SET       c.followers = channel.followers,\n" +
                        "                    c.view_minutes = channel.viewminutes,\n" +
                        "                    c:User,\n" +
                        "                    c.row_number = channel.rownumber,\n" +
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
                        "                    c.display_name = channel.displayname" +
                        " MERGE (c)-[:HAS_LANGUAGE]->(l:Language{name:channel.language})").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Channels took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyTeams(Integer daysPerspective, Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND json.data as team" +
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
        logResultSummaries(run);
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
        logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Games took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    //in progress
    @Async
    public void persistSullyMembersOfTeam(long teamId, Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as member\n" +
                        "MATCH (t:Team{sully_id:$teamId})\n" +
                        "MATCH (c.Channel{sully_id:member.id})\n" +
                        "MERGE (c)-[:MEMBER_OF]->(t)").in(database)
                .bind(jsonMap).to("json")
                .bind(teamId).to("teamId")
                .run();
        logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Games took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    //in progress
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

        logResultSummaries(run);

    }

    @Async
    public void persistSullyChannelGames(Map jsonMap) {
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:ChannelGame{sully_id:$streamId})\n" + //todo need to fix this as there is no id
                        "            SET     s.stream_time = $streamtime,\n" +
                        "                    s.view_time = $viewtime,\n" +
                        "                    s.views_gained = $viewsgained,\n" +
                        "                    s.followers = $followers,\n" +
                        "                    s.avg_viewers = $avgviewers,\n" +
                        "                    s.max_viewers = $maxviewers,\n" +
                        "                    s.followers_per_hour = $followersperhour,\n" +
                        "                    s.games_played = $gamesplayed,\n" +
                        "                    s.views_per_hour = $viewsperhour;").in(database)
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries(run);

    }

    @Async
    public void persistSullyChannelRaidFinder(Map jsonMap) {
        ResultSummary run = client.query("MERGE (r:RaidFinder{sully_id:$id})\n" +
                        "            SET     r.live_minutes = $liveMinutes,\n" +
                        "                    r.live_viewers = $liveViewers,\n" +
                        "                    r.overlapping_streams = $overlappingStreams,\n" +
                        "                    r.other_channel_streams = $otherChannelStreams,\n" +
                        "                    r.overlapping_ended_during = $overlappingEndedDuring,\n" +
                        "                    r.overlapping_ended_after = $overlappingEndedAfter,\n" +
                        "                    r.preview_large = $previewLarge,\n" +
                        "                    r.preview = $preview,\n" +
                        "                    r.current_game = $currentGame,\n" +
                        "                    r.avg_length_mins = $avgLengthMins,\n" +
                        "                    r.streams = $streams,\n" +
                        "                    r.view_minutes = $viewminutes,\n" +
                        "                    r.streamed_minutes = $streamedminutes,\n" +
                        "                    r.max_viewers = $maxviewers,\n" +
                        "                    r.avg_viewers = $avgviewers,\n" +
                        "                    r.followers = $followers,\n" +
                        "                    r.followers_gained = $followersgained,\n" +
                        "                    r.views_gained = $viewsgained,\n" +
                        "                    r.followers_gained_while_playing = $followersgainedwhileplaying,\n" +
                        "                    r.partner = $partner,\n" +
                        "                    r.affiliate = $affiliate,\n" +
                        "                    r.mature = $mature,\n" +
                        "                    r.language = $language,\n" +
                        "                    r.status = $status,\n" +
                        "                    r.games_played = $gamesPlayed,\n" +
                        "                    r.logo = $logo,\n" +
                        "                    r.twitch_url = $twitchurl,\n" +
                        "                    r.url = $url,\n" +
                        "                    r.display_name = $displayname;").in(database)
                .bind(jsonMap).to("json")
                .run();

        logResultSummaries(run);
    }

    @Async
    public void persistSullyChannelGameFinder(Set<ChannelGamePicker> channelGamePickers) {

        for (ChannelGamePicker picker : channelGamePickers) {
            for (ChannelGamePickerDatum data : picker.getData()) {
                ResultSummary run = client.query("MERGE (g:GameFinder{sully_id:$Id})\n" +
                                "            SET     g.average_viewers = $averageviewers,\n" +
                                "                    g.average_channels = $averagechannels,\n" +
                                "                    g.per_average_viewers = $peraverageviewers,\n" +
                                "                    g.per_average_channels = $peraveragechannels,\n" +
                                "                    g.per_recent_avg_viewers = $perrecentavgviewers,\n" +
                                "                    g.per_past_1_avg_viewers = $perpast1avgviewers,\n" +
                                "                    g.per_past_2_avg_viewers = $perpast2avgviewers,\n" +
                                "                    g.per_past3_avg_viewers = $perpast3avgviewers,\n" +
                                "                    g.per_recent_avg_channels = $perrecentavgchannels,\n" +
                                "                    g.per_past_1_avg_channels = $perpast1avgchannels,\n" +
                                "                    g.per_past_2_avg_channels = $perpast2avgchannels,\n" +
                                "                    g.per_past_3_avg_channels = $perpast3avgchannels,\n" +
                                "                    g.channels_above = $channelsabove,\n" +
                                "                    g.channels_same = $channelssame,\n" +
                                "                    g.channels_below = $channelsbelow,\n" +
                                "                    g.viewers_above = $viewersabove,\n" +
                                "                    g.viewers_same = $viewerssame,\n" +
                                "                    g.viewers_below = $viewersbelow,\n" +
                                "                    g.est_position = $estposition,\n" +
                                "                    g.viewer_ratio = $viewerratio,\n" +
                                "                    g.viewer_ratio_same_blow = $viewerratiosameblow,\n" +
                                "                    g.game_trend_channels_recent = $gametrendchannelsrecent,\n" +
                                "                    g.game_trend_channels_3_day = $gametrendchannels3day,\n" +
                                "                    g.game_trend_viewers_recent = $gametrendviewersrecent,\n" +
                                "                    g.game_trend_viewers_3_day = $gametrendviewers3day,\n" +
                                "                    g.twitch_game_trend_channels_recent = $twitchgametrendchannelsrecent,\n" +
                                "                    g.twitch_game_trend_channels_3_day = $twitchgametrendchannels3day,\n" +
                                "                    g.twitch_game_trend_viewers_recent = $twitchgametrendviewersrecent,\n" +
                                "                    g.twitch_game_trend_viewers_3_day = $twitchgametrendviewers3day,\n" +
                                "                    g.name = $name,\n" +
                                "                    g.logo = $logo,\n" +
                                "                    g.url = $url;").in(database)
                        .bind(data.getId()).to("Id")
                        .bind(data.getAverageviewers()).to("averageviewers")
                        .bind(data.getAveragechannels()).to("averagechannels")
                        .bind(data.getPeraverageviewers()).to("peraverageviewers")
                        .bind(data.getPeraveragechannels()).to("peraveragechannels")
                        .bind(data.getPeraverageviewers()).to("perrecentavgviewers")
                        .bind(data.getPerpast1avgviewers()).to("perpast1avgviewers")
                        .bind(data.getPerpast2avgviewers()).to("perpast2avgviewers")
                        .bind(data.getPerpast3avgviewers()).to("perpast3avgviewers")
                        .bind(data.getPerrecentavgchannels()).to("perrecentavgchannels")
                        .bind(data.getPerpast1avgchannels()).to("perpast1avgchannels")
                        .bind(data.getPerpast2avgchannels()).to("perpast2avgchannels")
                        .bind(data.getPerpast3avgchannels()).to("perpast3avgchannels")
                        .bind(data.getChannelsabove()).to("channelsabove")
                        .bind(data.getChannelssame()).to("channelssame")
                        .bind(data.getChannelsbelow()).to("channelsbelow")
                        .bind(data.getViewersabove()).to("viewersabove")
                        .bind(data.getViewerssame()).to("viewerssame")
                        .bind(data.getViewersbelow()).to("viewersbelow")
                        .bind(data.getEstposition()).to("estposition")
                        .bind(data.getViewerratio()).to("viewerratio")
                        .bind(data.getViewerratiosameblow()).to("viewerratiosameblow")
                        .bind(data.getGametrendchannelsrecent()).to("gametrendchannelsrecent")
                        .bind(data.getGametrendchannels3day()).to("gametrendchannels3day")
                        .bind(data.getGametrendviewersrecent()).to("gametrendviewersrecent")
                        .bind(data.getGametrendviewers3day()).to("gametrendviewers3day")
                        .bind(data.getTwitchgametrendchannelsrecent()).to("twitchgametrendchannelsrecent")
                        .bind(data.getTwitchgametrendchannels3day()).to("twitchgametrendchannels3day")
                        .bind(data.getTwitchgametrendviewersrecent()).to("twitchgametrendviewersrecent")
                        .bind(data.getTwitchgametrendviewers3day()).to("twitchgametrendviewers3day")
                        .bind(data.getName()).to("name")
                        .bind(data.getLogo()).to("logo")
                        .bind(data.getUrl()).to("url")
                        .run();

                logResultSummaries(run);
            }
        }

    }


}
