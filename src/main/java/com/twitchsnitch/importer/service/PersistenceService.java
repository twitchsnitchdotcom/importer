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
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PersistenceService {

    private Neo4jClient client;

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

    public void deleteDBData() {
        ResultSummary run = client.query("MATCH (n) DETACH DELETE n").run();
        log.debug("Nodes deleted from the DB: " + run.counters().nodesDeleted());
    }

    public void runDBConstraints() {

        //TODO run some sort of check to see if these constraints exist before trying to add new ones
        //GAMES
        //ResultSummary gameNameConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.name IS UNIQUE;").run();
        //can only have one uniqueness constraint per label?
//        ResultSummary gameTwitchIdConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.twitch_id IS UNIQUE;").run();
//        ResultSummary gameSullyIdConstraint = client.query("CREATE CONSTRAINT FOR (g:Game) REQUIRE g.sully_id IS UNIQUE;").run();
//        client.query("CREATE CONSTRAINT FOR (g:GameSummary) REQUIRE g.days IS UNIQUE;").run();
//        client.query("CREATE CONSTRAINT FOR (g:GamePerspective) REQUIRE g.days IS UNIQUE;").run();

        //LANGUAGE
        ResultSummary languageConstraint = client.query("CREATE CONSTRAINT FOR (l:Language) REQUIRE l.name IS UNIQUE;").run();


        //STREAM TAGS
        ResultSummary gameNameConstraint = client.query("CREATE CONSTRAINT FOR (s:StreamTag) REQUIRE s.twitch_id IS UNIQUE;").run();
        //Channels

        //Teams

        //Users


//        CREATE CONSTRAINT ON (s:Stream) ASSERT s.name IS UNIQUE;
//        CREATE CONSTRAINT ON (u:User) ASSERT u.name IS UNIQUE;
//        CREATE CONSTRAINT ON (g:Game) ASSERT g.name IS UNIQUE;
//        CREATE CONSTRAINT ON (l:Language) ASSERT l.name IS UNIQUE;
//        CREATE CONSTRAINT ON (t:Team) ASSERT t.id IS UNIQUE;


    }

    private void logResultSummaries(ResultSummary resultSummary) {
        log.debug("Nodes created: " + resultSummary.counters().nodesCreated());
        log.debug("Labels added: " + resultSummary.counters().labelsAdded());
        log.debug("Properties set: " + resultSummary.counters().propertiesSet());
    }

    public Set<String> getAllGamesWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> gamesWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (g:Game) WHERE g.twitch_id IS NULL SET g.twitch_id = false RETURN g.name LIMIT 100").fetch().all();
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

    public Set<String> getAllTeamsWithoutTwitchIds() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> teamsWithoutTwitchIds = new HashSet<>();
        Collection<Map<String, Object>> all = client.query("MATCH (t:Team) WHERE t.twitch_id IS NULL RETURN t.name").fetch().all();
        for (Map<String, Object> objectMap : all) {
            log.debug(objectMap.toString());
        }
        stopWatch.stop();
        log.debug("Get All Teams Without TwitchIds took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
        return teamsWithoutTwitchIds;
    }

    @Async
    public void persistSullyGameSummaries(Set<GamesTable> gamesTableSet) {
        for (GamesTable table : gamesTableSet) {
            ResultSummary run = client.query("MERGE (g:GameSummary" + table.getDays() + "Days{day_range:$dayRange})\n" +
                            "            SET     g.records_total = $recordsTotal,\n" +
                            "                    g.view_minutes = $viewminutes,\n" +
                            "                    g.streamed_minutes = $streamedminutes,\n" +
                            "                    g.max_channels = $maxchannels,\n" +
                            "                    g.unique_channels = $uniquechannels,\n" +
                            "                    g.avg_channels = $avgchannels,\n" +
                            "                    g.max_viewers = $maxviewers,\n" +
                            "                    g.avg_viewers = $avgviewers,\n" +
                            "                    g.avg_ratio = $avgratio,\n" +
                            "                    g.change_unique_channels = $changeuniquechannels,\n" +
                            "                    g.change_viewer_minutes = $changeviewerminutes,\n" +
                            "                    g.change_streamed_minutes = $changestreamedminutes,\n" +
                            "                    g.change_average_viewers = $changeaverageviewers,\n" +
                            "                    g.change_min_viewers = $changeminviewers,\n" +
                            "                    g.change_max_viewers = $changemaxviewers,\n" +
                            "                    g.change_average_channels = $changeaveragechannels,\n" +
                            "                    g.change_min_channels = $changeminchannels,\n" +
                            "                    g.change_max_channels = $changemaxchannels,\n" +
                            "                    g.change_average_ratio = $changeaverageratio,\n" +
                            "                    g.views_gained = $viewsgained,\n" +
                            "                    g.followers_gained = $followersgained,\n" +
                            "                    g.vphs = $vphs,\n" +
                            "                    g.fphs = $fphs;")
                    .bind(table.getDays()).to("dayRange")
                    .bind(table.getRecordsTotal()).to("recordsTotal")
                    .bind(table.getProgressMap().get("viewminutes")).to("viewminutes")
                    .bind(table.getProgressMap().get("streamedminutes")).to("streamedminutes")
                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
                    .bind(table.getProgressMap().get("uniquechannels")).to("uniquechannels")
                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
                    .bind(table.getProgressMap().get("avgratio")).to("avgratio")
                    .bind(table.getProgressMap().get("changeuniquechannels")).to("changeuniquechannels")
                    .bind(table.getProgressMap().get("changeviewerminutes")).to("changeviewerminutes")
                    .bind(table.getProgressMap().get("changestreamedminutes")).to("changestreamedminutes")
                    .bind(table.getProgressMap().get("changeaverageviewers")).to("changeaverageviewers")
                    .bind(table.getProgressMap().get("changeminviewers")).to("changeminviewers")
                    .bind(table.getProgressMap().get("changemaxviewers")).to("changemaxviewers")
                    .bind(table.getProgressMap().get("changeaveragechannels")).to("changeaveragechannels")
                    .bind(table.getProgressMap().get("changeminchannels")).to("changeminchannels")
                    .bind(table.getProgressMap().get("changemaxchannels")).to("changemaxchannels")
                    .bind(table.getProgressMap().get("changeaverageratio")).to("changeaverageratio")
                    .bind(table.getProgressMap().get("viewsgained")).to("viewsgained")
                    .bind(table.getProgressMap().get("followersgained")).to("followersgained")
                    .bind(table.getProgressMap().get("vphs")).to("vphs")
                    .bind(table.getProgressMap().get("fphs")).to("fphs")
                    .run();

            logResultSummaries(run);
        }
    }

    @Async
    public void persistSullyChannelSummaries(Set<ChannelsTable> channelsTables) {
        for (ChannelsTable table : channelsTables) {
            ResultSummary run = client.query("MERGE (c:ChannelSummary" + table.getDays() + "Days{day_range:$dayRange})\n" +
                            "            SET     c.records_total = $recordsTotal,\n" +
                            "                    c.view_minutes = $viewminutes,\n" +
                            "                    c.max_viewers = $maxviewers,\n" +
                            "                    c.avg_viewers = $avgviewers,\n" +
                            "                    c.streamed_minutes = $streamedminutes,\n" +
                            "                    c.followers = $followers,\n" +
                            "                    c.followers_gained = $followersgained;")
                    .bind(table.getDays()).to("dayRange")
                    .bind(table.getRecordsTotal()).to("recordsTotal")
                    .bind(table.getProgressMap().get("viewminutes")).to("viewminutes")
                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
                    .bind(table.getProgressMap().get("streamedminutes")).to("streamedminutes")
                    .bind(table.getProgressMap().get("followers")).to("followers")
                    .bind(table.getProgressMap().get("followersgained")).to("followersgained")
                    .run();
            logResultSummaries(run);
        }
    }

    @Async
    public void persistSullyTeamSummaries(Set<TeamsTable> teamsTables) {
        for (TeamsTable table : teamsTables) {
            ResultSummary run = client.query("MERGE (t:TeamsSummary" + table.getDays() + "Days{day_range:$dayRange})\n" +
                            "            SET     t.records_total = $recordsTotal,\n" +
                            "                    t.watch_time = $watchtime,\n" +
                            "                    t.max_viewers = $maxviewers,\n" +
                            "                    t.avg_viewers = $avgviewers,\n" +
                            "                    t.stream_time = $streamtime,\n" +
                            "                    t.members = $members,\n" +
                            "                    t.max_channels = $maxchannels,\n" +
                            "                    t.avg_channels = $avgchannels;")
                    .bind(table.getDays()).to("dayRange")
                    .bind(table.getRecordsTotal()).to("recordsTotal")
                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
                    .bind(table.getProgressMap().get("members")).to("members")
                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
                    .run();
            logResultSummaries(run);
        }
    }

    //    @Async
//    public void persistSullyChannelPerspective(Map jsonMap) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        for (ChannelsTable table : allChannels) {
//            for (ChannelDatum data : table.getData()) {
//                ResultSummary run = client.query("MERGE (c:Channel{sully_id:$id})\n" +
//                                "            SET     c.view_minutes = $viewminutes,\n" +
//                                "                    c.streamed_minutes = $streamedminutes,\n" +
//                                "                    c.max_viewers = $maxviewers,\n" +
//                                "                    c.avg_viewers = $avgviewers,\n" +
//                                "                    c.followers = $followers,\n" +
//                                "                    c.followers_gained = $followersgained,\n" +
//                                "                    c.views_gained = $viewsgained,\n" +
//                                "                    c.followers_gained_while_playing = $followersgainedwhileplaying,\n" +
//                                "                    c.partner = $partner,\n" +
//                                "                    c.affiliate = $affiliate,\n" +
//                                "                    c.mature = $mature,\n" +
//                                "                    c.language = $language,\n" +
//                                "                    c.status = $status,\n" +
//                                "                    c.previous_view_minutes = $previousviewminutes,\n" +
//                                "                    c.previous_streamed_minutes = $previousstreamedminutes,\n" +
//                                "                    c.previous_max_viewers = $previousmaxviewers,\n" +
//                                "                    c.previous_avg_viewers = $previousavgviewers,\n" +
//                                "                    c.previous_follower_gain = $previousfollowergain,\n" +
//                                "                    c.previous_views_gained = $previousviewsgained,\n" +
//                                "                    c.logo = $logo,\n" +
//                                "                    c.twitch_url = $twitchurl,\n" +
//                                "                    c.url = $url,\n" +
//                                "                    c.display_name = $displayname")
//                        .bind(data.getId()).to("id")
//                        .bind(data.getViewminutes()).to("viewminutes")
//                        .bind(data.getStreamedminutes()).to("streamedminutes")
//                        .bind(data.getMaxviewers()).to("maxviewers")
//                        .bind(data.getAvgviewers()).to("avgviewers")
//                        .bind(data.getFollowers()).to("followers")
//                        .bind(data.getFollowersgained()).to("followersgained")
//                        .bind(data.getViewsgained()).to("viewsgained")
//                        .bind(data.getFollowersgainedwhileplaying()).to("followersgainedwhileplaying")
//                        .bind(data.getPartner()).to("partner")
//                        .bind(data.getAffiliate()).to("affiliate")
//                        .bind(data.getMature()).to("mature")
//                        .bind(data.getLanguage()).to("language")
//                        .bind(data.getStatus()).to("status")
//                        .bind(data.getPreviousviewminutes()).to("previousviewminutes")
//                        .bind(data.getPreviousstreamedminutes()).to("previousstreamedminutes")
//                        .bind(data.getPreviousmaxviewers()).to("previousmaxviewers")
//                        .bind(data.getPreviousavgviewers()).to("previousavgviewers")
//                        .bind(data.getPreviousfollowergain()).to("previousfollowergain")
//                        .bind(data.getPreviousviewsgained()).to("previousviewsgained")
//                        .bind(data.getLogo()).to("logo")
//                        .bind(data.getTwitchurl()).to("twitchurl")
//                        .bind(data.getUrl()).to("url")
//                        .bind(data.getDisplayname()).to("displayname")
//                        .run();
//                logResultSummaries(run);
//            }
//        }
//        stopWatch.stop();
//        log.debug("Persisting Sully Channels took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
//    }
//
    @Async
    public void persistSullyChannels(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as channel" +
                        " MERGE (c:Channel{sully_id:channel.id})\n" +
                        "          SET       c.followers = channel.followers,\n" +
                        "                    c.partner = channel.partner,\n" +
                        "                    c.affiliate = channel.affiliate,\n" +
                        "                    c.mature = channel.mature,\n" +
                        "                    c.language = channel.language,\n" +
                        "                    c.status = channel.status,\n" +
                        "                    c.logo = channel.logo,\n" +
                        "                    c.twitch_url = channel.twitchurl,\n" +
                        "                    c.url = channel.url,\n" +
                        "                    c.display_name = channel.displayname" +
                        " MERGE (c)-[:HAS_LANGUAGE]->(l:Language{name:channel.language})")
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Channels took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

//    @Async
//    public void persistSullyTeamsPerspective(Set<TeamsTable> allTeams) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        for (TeamsTable table : allTeams) {
//            for (TeamsDatum data : table.getData()) {
//                ResultSummary run = client.query("MERGE (t:Team{sully_id:$id})\n" +
//                                "            SET     t.stream_time = $streamtime,\n" +
//                                "                    t.watch_time = $watchtime,\n" +
//                                "                    t.max_viewers = $maxviewers,\n" +
//                                "                    t.avg_viewers = $avgviewers,\n" +
//                                "                    t.members = $members,\n" +
//                                "                    t.max_channels = $maxchannels,\n" +
//                                "                    t.avg_channels = $avgchannels,\n" +
//                                "                    t.name = $name,\n" +
//                                "                    t.logo = $logo,\n" +
//                                "                    t.twitch_url = $twitchurl;")
//                        .bind(data.getId()).to("id")
//                        .bind(data.getStreamtime()).to("streamtime")
//                        .bind(data.getWatchtime()).to("watchtime")
//                        .bind(data.getMaxviewers()).to("maxviewers")
//                        .bind(data.getAvgviewers()).to("avgviewers")
//                        .bind(data.getMembers()).to("members")
//                        .bind(data.getMaxchannels()).to("maxchannels")
//                        .bind(data.getAvgchannels()).to("avgchannels")
//                        .bind(data.getName()).to("name")
//                        .bind(data.getLogo()).to("logo")
//                        .bind(data.getTwitchurl()).to("twitchurl")
//                        .run();
//                logResultSummaries(run);
//            }
//        }
//        stopWatch.stop();
//        log.debug("Persisting Sully Teams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
//    }

    @Async
    public void persistSullyTeams(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
                ResultSummary run = client.query("UNWIND json.data as team" +
                                "MERGE (t:Team{sully_id:team.id})\n" +
                                "          SET          t.members = team.members,\n" +
                                "                    t.name = team.name,\n" +
                                "                    t.logo = team.logo,\n" +
                                "                    t.url = team.twitchurl;")
                        .bind(jsonMap).to("json")
                        .run();
                logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Teams took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyGamesPerspective(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
                ResultSummary run = client.query("UNWIND $json.data as gp" +
                                "MERGE (g:Game{sully_id:$id})\n" +
                                "            SET     g.view_minutes = $viewminutes,\n" +
                                "                    g.streamed_minutes = $streamedminutes,\n" +
                                "                    g.max_channels = $maxchannels,\n" +
                                "                    g.unique_channels = $uniquechannels,\n" +
                                "                    g.avg_channels = $avgchannels,\n" +
                                "                    g.max_viewers = $maxviewers,\n" +
                                "                    g.avg_viewers = $avgviewers,\n" +
                                "                    g.avg_ratio = $avgratio,\n" +
                                "                    g.views_gained = $viewsgained,\n" +
                                "                    g.followers_gained = $followersgained,\n" +
                                "                    g.previous_view_minutes = $previousviewminutes,\n" +
                                "                    g.previous_streamed_minutes = $previousstreamedminutes,\n" +
                                "                    g.previous_max_channels = $previousmaxchannels,\n" +
                                "                    g.previous_unique_channels = $previousuniquechannels,\n" +
                                "                    g.previous_avg_channels = $previousavgchannels,\n" +
                                "                    g.previous_max_viewers = $previousmaxviewers,\n" +
                                "                    g.previous_avg_viewers = $previousavgviewers,\n" +
                                "                    g.previous_avg_ratio = $previousavgratio,\n" +
                                "                    g.previous_vphs = $previousvphs,\n" +
                                "                    g.fphs = $fphs,\n" +
                                "                    g.vphs = $vphs,\n")
                        .bind(jsonMap).to("json")
                        .run();
                logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Sully Games Perspective took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyGames(Map jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query("UNWIND $json.data as game\n" +
                        "MERGE (g:Game{sully_id:game.id})\n" +
                        "            SET     g.name = game.name,\n" +
                        "                    g.logo = game.logo,\n" +
                        "                    g.url = game.url;")
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries(run);

        stopWatch.stop();
        log.debug("Persisting Sully Games took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    @Async
    public void persistSullyChannelStreams(Set<ChannelStream> channelStreams) {
        //TODO make the relationship parts
        for (ChannelStream channelStream : channelStreams) {
            for (ChannelStreamDatum data : channelStream.getData()) {
                ResultSummary run = client.query("MERGE (s:Stream{sully_id:$streamId})\n" +
                                "            SET     s.start_time = $starttime,\n" +
                                "                    s.end_time = $endtime,\n" +
                                "                    s.length = $length,\n" +
                                "                    s.view_gain = $viewgain,\n" +
                                "                    s.follower_gain = $followergain,\n" +
                                "                    s.avg_viewers = $avgviewers,\n" +
                                "                    s.max_viewers = $maxviewers,\n" +
                                "                    s.followers_per_hour = $followersperhour,\n" +
                                "                    s.games_played = $gamesplayed,\n" +
                                "                    s.views_per_hour = $viewsperhour,\n" +
                                "                    s.channel_display_name = $channeldisplayname,\n" +
                                "                    s.channel_logo = $channellogo,\n" +
                                "                    s.channel_url = $channelurl,\n" +
                                "                    s.start_date_time = $startDateTime,\n" +
                                "                    s.stream_url = $streamUrl,\n" +
                                "                    s.view_minutes = $viewminutes;")
                        .bind(data.getStreamId()).to("streamId")
                        .bind(data.getStarttime()).to("starttime")
                        .bind(data.getEndtime()).to("endtime")
                        .bind(data.getLength()).to("length")
                        .bind(data.getViewgain()).to("viewgain")
                        .bind(data.getFollowergain()).to("followergain")
                        .bind(data.getAvgviewers()).to("avgviewers")
                        .bind(data.getMaxviewers()).to("maxviewers")
                        .bind(data.getFollowersperhour()).to("followersperhour")
                        .bind(data.getGamesplayed()).to("gamesplayed")
                        .bind(data.getViewsperhour()).to("viewsperhour")
                        .bind(data.getChanneldisplayname()).to("channeldisplayname")
                        .bind(data.getChannellogo()).to("channellogo")
                        .bind(data.getChannelurl()).to("channelurl")
                        .bind(data.getStartDateTime()).to("startDateTime")
                        .bind(data.getStreamUrl()).to("streamUrl")
                        .bind(data.getViewminutes()).to("viewminutes")
                        .run();

                logResultSummaries(run);
            }
        }
    }

    @Async
    public void persistSullyChannelGames(Set<ChannelGame> channelGames) {
        for (ChannelGame game : channelGames) {
            //todo at the moment its a hybrid node, but needs to be thought about more
            for (ChannelGameDatum data : game.getData()) {
                ResultSummary run = client.query("MERGE (g:ChannelGame{sully_id:$streamId})\n" + //todo need to fix this as there is no id
                                "            SET     s.stream_time = $streamtime,\n" +
                                "                    s.view_time = $viewtime,\n" +
                                "                    s.views_gained = $viewsgained,\n" +
                                "                    s.followers = $followers,\n" +
                                "                    s.avg_viewers = $avgviewers,\n" +
                                "                    s.max_viewers = $maxviewers,\n" +
                                "                    s.followers_per_hour = $followersperhour,\n" +
                                "                    s.games_played = $gamesplayed,\n" +
                                "                    s.views_per_hour = $viewsperhour;")
                        .bind(null).to("streamId")//todo need to fix this or wont run
                        .bind(data.getStreamtime()).to("streamtime")
                        .bind(data.getViewtime()).to("viewtime")
                        .bind(data.getViewsgained()).to("viewsgained")
                        .bind(data.getFollowers()).to("followers")
                        .bind(data.getAvgviewers()).to("avgviewers")
                        .bind(data.getMaxviewers()).to("maxviewers")
                        .bind(data.getFollowersperhour()).to("followersperhour")
                        .bind(data.getGamesplayed()).to("gamesplayed")
                        .bind(data.getViewsperhour()).to("viewsperhour")
                        .run();
                logResultSummaries(run);
            }
        }

    }

    //todo becomes an intermediary node
    @Async
    public void persistSullyChannelRaidFinder(Set<ChannelRaidFinder> channelRaidFinders) {
        for (ChannelRaidFinder channelRaidFinder : channelRaidFinders) {
            for (ChannelRaidDatum data : channelRaidFinder.getData()) {
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
                                "                    r.display_name = $displayname;")
                        .bind(data.getId()).to("id")
                        .bind(data.getLiveMinutes()).to("liveMinutes")
                        .bind(data.getLiveViewers()).to("liveViewers")
                        .bind(data.getOverlappingStreams()).to("overlappingStreams")
                        .bind(data.getOtherChannelStreams()).to("otherChannelStreams")
                        .bind(data.getOverlappingEndedDuring()).to("overlappingEndedDuring")
                        .bind(data.getOverlappingEndedAfter()).to("overlappingEndedAfter")
                        .bind(data.getPreviewLarge()).to("previewLarge")
                        .bind(data.getPreview()).to("preview")
                        .bind(data.getCurrentGame()).to("currentGame")
                        .bind(data.getAvgLengthMins()).to("avgLengthMins")
                        .bind(data.getStreams()).to("streams")
                        .bind(data.getViewminutes()).to("viewminutes")
                        .bind(data.getStreamedminutes()).to("streamedminutes")
                        .bind(data.getMaxviewers()).to("maxviewers")
                        .bind(data.getAvgviewers()).to("avgviewers")
                        .bind(data.getFollowers()).to("followers")
                        .bind(data.getFollowersgained()).to("followersgained")
                        .bind(data.getViewsgained()).to("viewsgained")
                        .bind(data.getFollowersgainedwhileplaying()).to("followersgainedwhileplaying")
                        .bind(data.getPartner()).to("partner")
                        .bind(data.getAffiliate()).to("affiliate")
                        .bind(data.getMature()).to("mature")
                        .bind(data.getLanguage()).to("language")
                        .bind(data.getStatus()).to("status")
                        .bind(data.getGamesPlayed()).to("gamesPlayed")
                        .bind(data.getLogo()).to("logo")
                        .bind(data.getTwitchurl()).to("twitchurl")
                        .bind(data.getUrl()).to("url")
                        .bind(data.getDisplayname()).to("displayname")
                        .run();

                logResultSummaries(run);
            }
        }
    }

    //todo becomes an intermediary node
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
                                "                    g.url = $url;")
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

    @Async
    public void persistTwitchStreamTag(LinkedHashMap jsonMap) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResultSummary run = client.query(
                        "UNWIND $json.data as tag " +
                                "MERGE (s:StreamTag{twitch_id:tag.tag_id})\n" +
                                "            SET     s.is_auto = tag.is_auto")
                .bind(jsonMap).to("json")
                .run();
        logResultSummaries(run);
        stopWatch.stop();
        log.debug("Persisting Twitch Stream Tags took: " + stopWatch.getLastTaskTimeMillis() / 1000 + " seconds");
    }

    public void updateGameWithTwitchId(Map json) {
        ResultSummary run = client.query("UNWIND $json.data as game " +
                "MATCH (g:Game) WHERE g.name = game.name SET g.twitch_id = game.id;").bind(json).to("json").run();
        logResultSummaries(run);
    }

//    @Async
//    public void persistTwitchChatters(Set<ChatterListDTO> chatterListDTOS){
//        for(ChatterListDTO data: chatterListDTOS){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//
//    }
//
//    @Async
//    public void persistTwitchFollowersFrom(Set<FollowList> followListsFrom){
//        for(FollowList data: followListsFrom){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//
//    }
//
//    @Async
//    public void persistTwitchFollowersTo(Set<FollowList> followListsTo){
//        for(FollowList data: followListsTo){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//    }

//    @Async
//    public void persistTwitchUser(Set<User> users){
//        for(User data: users){
//            ResultSummary run = client.query("MERGE (t:TeamsSummaryDays{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//
//    }
//
//    @Async
//    public void persistTwitchChannelTeam(Set<TeamMember> teamMembers){
//        for(TeamMember data: teamMembers){
//
//        }
//
//    }
//
//    @Async
//    public void persistTwitchGame(Set<Game> games){
//        for(Game data: games){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//
//    }
//
//    @Async
//    public void persistTwitchStreams(Set<StreamListDTO> streamListDTOS){
//        for(StreamListDTO data: streamListDTOS){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//    }
//
//    //this will eventually become the list that we use to do our first business development
//    @Async
//    public void persistTwitchChannelSchedule(Set<StreamSchedule> streamSchedules){
//        for(StreamSchedule data: streamSchedules){
//            ResultSummary run = client.query("MERGE (t:TeamsSummary"  + table.getDays() +"Days{day_range:$dayRange})\n" +
//                            "            SET     t.records_total = $recordsTotal,\n" +
//                            "                    t.watch_time = $watchtime,\n" +
//                            "                    t.max_viewers = $maxviewers,\n" +
//                            "                    t.avg_viewers = $avgviewers,\n" +
//                            "                    t.stream_time = $streamtime,\n" +
//                            "                    t.members = $members,\n" +
//                            "                    t.max_channels = $maxchannels,\n" +
//                            "                    t.avg_channels = $avgchannels;")
//                    .bind(table.getDays()).to("dayRange")
//                    .bind(table.getRecordsTotal()).to("recordsTotal")
//                    .bind(table.getProgressMap().get("watchtime")).to("watchtime")
//                    .bind(table.getProgressMap().get("maxviewers")).to("maxviewers")
//                    .bind(table.getProgressMap().get("avgviewers")).to("avgviewers")
//                    .bind(table.getProgressMap().get("streamtime")).to("streamtime")
//                    .bind(table.getProgressMap().get("members")).to("members")
//                    .bind(table.getProgressMap().get("maxchannels")).to("maxchannels")
//                    .bind(table.getProgressMap().get("avgchannels")).to("avgchannels")
//                    .run();
//            logResultSumamries(run);
//        }
//    }


}
