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

    @GetMapping("/phase/1")
    public void phase1(){

        twitchDataService.dropDBConstraints();
        twitchDataService.addDBConstraints();
        twitchDataService.importLanguages();

        twitchDataService.importChannels();
        twitchDataService.importGames();
        twitchDataService.importTeams();


    }

    @GetMapping("/phase/2")
    public void phase2() {

        twitchDataService.importFollowsTo();
        twitchDataService.importFollowsFrom();
        
        twitchDataService.importTwitchUsers();
        twitchDataService.importTwitchGameData();
        twitchDataService.importTwitchTeams();

        twitchDataService.importChannelStreams();
        twitchDataService.importChannelGames();

        twitchDataService.importChattersOnDB();
        twitchDataService.importTwitchUsers();
    }

    @GetMapping("/phase/3")
    public void phase3() {

        twitchDataService.importFollowsTo();
        twitchDataService.importFollowsFrom();
        twitchDataService.importLiveStreams(null);
        twitchDataService.importGameFinder();
        twitchDataService.importRaidPicker();
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
        twitchDataService.importRaidPicker();
    }
}
