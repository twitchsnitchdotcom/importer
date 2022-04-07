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

    @GetMapping("/import/streamtags")
    public void importStreamTags() {
        twitchDataService.importGlobalStreamTags();
    }

    @GetMapping("/import/games")
    public void importGames(){
        twitchDataService.importGames();
    }

    @GetMapping("/import/teams")
    public void importTeams(){
        twitchDataService.importSullyTeams();
    }

    @GetMapping("/import/channels")
    public void importChannels(){
        twitchDataService.importChannels();
    }


}
