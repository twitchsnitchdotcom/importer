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

    @GetMapping("/import/games")
    public void importGames() {
        twitchDataService.importGames();
        twitchDataService.importTwitchGameData();
    }

    @GetMapping("/import/channels")
    public void importChannels() {
        twitchDataService.importChannels();
    }

    @GetMapping("/import/users/twitch")
    public void importChannelsTwitch() {
        twitchDataService.importTwitchUsers();
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
        twitchDataService.importTwitchTeams(100);
    }

    @GetMapping("/import/streams")
    public void importStreams() {
        twitchDataService.importLiveStreams(200);
    }

    @GetMapping("/import/follows/to")
    public void importFollowsTo() {
        twitchDataService.importFollowsTo();
    }

    @GetMapping("/import/follows/from")
    public void importFollowsFrom() {
        twitchDataService.importFollowsFrom();
    }

}
