package com.twitchsnitch.importer.web;
import com.twitchsnitch.importer.service.TwitchDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportDataResource {

    public ImportDataResource(TwitchDataService twitchDataService){
        this.twitchDataService = twitchDataService;

    }

    private final TwitchDataService twitchDataService;

    @GetMapping("/import/sully/games")
    public void importGames(){
        twitchDataService.importSullyGames();
    }

    @GetMapping("/import/teams")
    public void importTeams(){
        twitchDataService.importSullyTeams();
        //todo is a matcher to twitch games?
    }

    @GetMapping("/import/channels")
    public void importChannels(){
        twitchDataService.importSullyChannels();
        //todo is a matcher to twitch user?
    }


}
