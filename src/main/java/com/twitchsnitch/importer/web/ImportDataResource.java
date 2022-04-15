package com.twitchsnitch.importer.web;

import com.twitchsnitch.importer.service.TwitchDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportDataResource {

    public ImportDataResource(TwitchDataService twitchDataService) {
        this.twitchDataService = twitchDataService;

    }

    private final TwitchDataService twitchDataService;

    @GetMapping("/e2e")
    public void e2e(){
        twitchDataService.dropDBConstraints();
        twitchDataService.addDBConstraints();
        twitchDataService.importLanguages();

        twitchDataService.importChannels();
        twitchDataService.importTwitchUsers();
        twitchDataService.getTwitchIdNotSetCountUser();

        twitchDataService.importGames();
        twitchDataService.importTwitchGameData();
        twitchDataService.getTwitchIdNotSetCountGame();

        twitchDataService.importTeams();
        twitchDataService.importTwitchTeams();

        twitchDataService.importChannelStreams();
        twitchDataService.importLiveStreams(100);
        twitchDataService.importChannelGames();

        twitchDataService.importFollowsTo();
        twitchDataService.importFollowsFrom();

        twitchDataService.importChattersOnDB();
        twitchDataService.importTwitchUsers();





    }

    @GetMapping("/db/add")
    public void addDB() {
        twitchDataService.addDB();
    }

    @GetMapping("/db/drop")
    public void dropDB() {
        twitchDataService.dropDB();
    }

    @GetMapping("/db/add/constraints")
    public void addDBConstraints() {
        twitchDataService.addDBConstraints();
    }

    @GetMapping("/db/drop/constraints")
    public void dropDBConstraints() {
        twitchDataService.dropDBConstraints();
    }

    @GetMapping("/import/languages")
    public void importLanguages() {
        twitchDataService.importLanguages();
    }

    @GetMapping("/import/channels")
    public void importChannels() {
        twitchDataService.importChannels();
    }

    @GetMapping("/import/users/twitch")
    public void importUsersTwitch() {
        twitchDataService.importTwitchUsers();
    }

    @GetMapping("/ids/notset/users")
    public Long idsNotSetUsers() {
        return twitchDataService.getTwitchIdNotSetCountUser();
    }

    @GetMapping("/import/chatters/ondb")
    public void importChattersOnDB() {
        twitchDataService.importChattersOnDB();
    }


    @GetMapping("/import/games")
    public void importGames() {
        twitchDataService.importGames();
    }

    @GetMapping("/import/games/twitch")
    public void importGamesTwitch() {
        twitchDataService.importTwitchGameData();
    }

    @GetMapping("/import/follows/to")
    public void importFollowsTo() {
        twitchDataService.importFollowsTo();
    }

    @GetMapping("/import/follows/from")
    public void importFollowsFrom() {
        twitchDataService.importFollowsFrom();
    }


    @GetMapping("/import/channels/games")
    public void importChannelGames() {
        twitchDataService.importChannelGames();
    }

    @GetMapping("/import/channels/streams")
    public void importChannelStreams() {
        twitchDataService.importChannelStreams();
    }

    @GetMapping("/import/teams")
    public void importTeams() {
        twitchDataService.importTeams();
    }

    @GetMapping("/import/teams/twitch")
    public void importTeamsTwitch() {
        twitchDataService.importTwitchTeams();
    }

    @GetMapping("/import/streams")
    public void importStreams() {
        twitchDataService.importLiveStreams(100);
    }

    @GetMapping("/import/game/finder")
    public void importGameFinder() {
        twitchDataService.importGameFinder();
    }

    @GetMapping("/import/raid/finder")
    public void importRaidFinder() {
        twitchDataService.importLiveStreams(1000);
        twitchDataService.importGameFinder();
    }
}
