/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.twitchsnitch.importer.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitchsnitch.importer.service.DBStatsService;
import com.twitchsnitch.importer.service.OAuthService;
import com.twitchsnitch.importer.service.TwitchDataService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.security.PermitAll;

@Route("")
@PermitAll
@UIScope
public class ViewComponent extends VerticalLayout {

    private final TwitchDataService twitchDataService;
    private final DBStatsService dbStatsService;

    private final OAuthService oAuthService;


    public ViewComponent(TwitchDataService twitchDataService, DBStatsService dbStatsService, OAuthService oAuthService) {
        this.twitchDataService = twitchDataService;
        this.dbStatsService = dbStatsService;
        this.oAuthService = oAuthService;
        formatLayout();
    }

    public void formatLayout(){

        //db
        H3 tokenHeadline = new H3("TOKENS INFO");
        Div tokenInfo = new Div();
        try {
            tokenInfo.setText(oAuthService.objectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oAuthService.getTokens()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Button newTokens = new Button("newTokens", event -> oAuthService.newTokens(false));
        Button refreshTokens = new Button("refreshTokens", event -> oAuthService.newTokens(true));

        add(tokenHeadline);
        add(new HorizontalLayout(tokenInfo));
        add(new HorizontalLayout(newTokens, refreshTokens));

        //db
        H3 dbHeadline = new H3("DATABASE INFO");
        Div dbInfo = new Div();
        dbInfo.setText(dbStatsService.getDBInfo());
        Button addDB = new Button("addDB", event -> twitchDataService.addDB());
        Button dropDB = new Button("dropDB", event -> twitchDataService.dropDB());
        Button dropDBConstraints = new Button("dropDBConstraints", event -> twitchDataService.dropDBConstraints());
        Button addDBConstraints = new Button("addDBConstraints", event -> twitchDataService.addDBConstraints());

        add(dbHeadline);
        add(new HorizontalLayout(dbInfo));
        add(new HorizontalLayout(addDB, dropDB, dropDBConstraints, addDBConstraints));

        //languages
        H3 languagesHeadline = new H3("LANGUAGE INFO");
        Div languageInfo = new Div();
        languageInfo.setText("Languages without sully_id: " + dbStatsService.getLanguagesWithOutSullyIds());
        Button importLanguages = new Button("importLanguages", event -> twitchDataService.importSullyLanguages());

        add(languagesHeadline);
        add(new HorizontalLayout(languageInfo));
        add(new HorizontalLayout(importLanguages));

        //games
        H3 gamesHeadline = new H3("GAMES INFO");
        Div gamesInfo = new Div();
        gamesInfo.setText("Total Games: " + dbStatsService.getAllGamesCount() + " | Games without twitch_id: " + dbStatsService.getAllGamesWithoutTwitchIds().size());
        Button importTwitchGameData = new Button("importTwitchGameData", event -> twitchDataService.importTwitchGameData());
        Button importGames1 = new Button("importGames1", event -> twitchDataService.importGames1());
        Button importGames2 = new Button("importGames2", event -> twitchDataService.importGames2());

        add(gamesHeadline);
        add(new HorizontalLayout(gamesInfo));
        add(new HorizontalLayout(importTwitchGameData, importGames1, importGames2));

        //channels
        H3 channelsHeadline = new H3("CHANNEL INFO");
        Div channelsInfo = new Div();
        channelsInfo.setText("Total Channels: " + dbStatsService.getTotalChannels() + " | Total Channels without twitch id: " + dbStatsService.getTotalChannelsWithoutTwitchId() + " | Total Channels without twitch or sully id: " + dbStatsService.getTotalChannelsWithoutTwitchIdOrSullyIdCount());
        Button importTwitchUsers = new Button("importTwitchUsers", event -> twitchDataService.importTwitchUsers());
        Button importTwitchUsersWithoutAnyId = new Button("importTwitchUsersWithoutAnyId", event -> twitchDataService.importTwitchUsersWithoutEitherId());
        Button importChannels = new Button("importChannels", event -> twitchDataService.importChannels());
        Button sullyDeepSearchPhase1 = new Button("Phase1", event -> twitchDataService.sullyDeepSearchPhase1());
        Button sullyDeepSearchPhase2 = new Button("Phase2", event -> twitchDataService.sullyDeepSearchPhase2());
        Button sullyDeepSearchPhase3 = new Button("Phase3", event -> twitchDataService.sullyDeepSearchPhase3());
        Button sullyDeepSearchPhase4 = new Button("Phase4", event -> twitchDataService.sullyDeepSearchPhase4());
        Button sullyDeepSearchPhase5 = new Button("Phase5", event -> twitchDataService.sullyDeepSearchPhase5());

        add(channelsHeadline);
        add(new HorizontalLayout(channelsInfo));
        add(new HorizontalLayout(importTwitchUsersWithoutAnyId, importTwitchUsers, importChannels, sullyDeepSearchPhase1, sullyDeepSearchPhase2, sullyDeepSearchPhase3, sullyDeepSearchPhase4, sullyDeepSearchPhase5));

        //streams
        H3 streamsHeadline = new H3("STREAMS INFO");
        Div streamsInfo = new Div();
        streamsInfo.setText("Total number of streams: " + dbStatsService.getTotalStreams());
        Button importLiveStreams = new Button("importLiveStreams", event -> twitchDataService.importLiveStreams());

        add(streamsHeadline);
        add(new HorizontalLayout(streamsInfo));
        add(new HorizontalLayout(importLiveStreams));

        //chatters info
        H3 chattersHeadline = new H3("Chatters INFO");
        Div chattersInfo = new Div();
        chattersInfo.setText("Total number of chatters: " + dbStatsService.getNumberOfChatters());
        Button importChattersOnDB = new Button("importChattersOnDB", event -> twitchDataService.importChattersOnDB());

        add(chattersHeadline);
        add(new HorizontalLayout(chattersInfo));
        add(new HorizontalLayout(importChattersOnDB));


        //todo followers to and followers from db stats

        //followers
        H3 followersHeadline = new H3("Followers INFO");
        Div followersInfo = new Div();
        followersInfo.setText("Users without followers to : " + dbStatsService.getUsersWithoutFollowsTo() + " | Users without followers from: " + dbStatsService.getUsersWithoutFollowsFrom());

        Button importFollowsTo = new Button("importFollowsTo", event -> twitchDataService.importFollowsTo());
        Button importFollowsFrom = new Button("importFollowsFrom", event -> twitchDataService.importFollowsFrom());

        add(followersHeadline);
        add(new HorizontalLayout(followersInfo));
        add(new HorizontalLayout(importFollowsTo, importFollowsFrom));

        //teams

        H3 teamsHeadline = new H3("TEAMS INFO");
        Div teamsInfo = new Div();
        teamsInfo.setText("Teams Total: " + dbStatsService.getTeamsCount() + " | Teams without sully id: " + dbStatsService.getAllTeamsWithoutSullyId() + " | Teams without twitch id: " + dbStatsService.getTeamsWithoutTwitchId().size() );

        Button importTwitchTeams = new Button("importTwitchTeams", event -> twitchDataService.importTwitchTeams());
        Button importTeams1 = new Button("importTeams1", event -> twitchDataService.importTeams1());
        Button importTeams2 = new Button("importTeams2", event -> twitchDataService.importTeams2());

        add(teamsHeadline);
        add(new HorizontalLayout(teamsInfo));
        add(new HorizontalLayout(importTwitchTeams, importTeams1, importTeams2));



        //alternate
        Button importGameFinder = new Button("importGameFinder", event -> twitchDataService.importGameFinder());
        Button importRaidPicker = new Button("importRaidPicker", event -> twitchDataService.importRaidPicker());
        Button importChannelGames = new Button("importChannelGames", event -> twitchDataService.importChannelGames());
        Button importChannelStreams = new Button("importChannelStreams", event -> twitchDataService.importChannelStreams());

    }

}
