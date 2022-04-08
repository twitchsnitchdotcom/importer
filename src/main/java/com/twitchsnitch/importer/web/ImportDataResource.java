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

    @GetMapping("/db/drop")
    public void dropDBConstraints(){
        twitchDataService.dropDbConstraints();
    }

    @GetMapping("/db/run")
    public void runDBConstraints(){
        twitchDataService.runDBConstraints();
    }

    @GetMapping("/import/games")
    public void importGames(){
        twitchDataService.importGames();
    }

    @GetMapping("/import/teams")
    public void importTeams(){
        twitchDataService.importTeams();
    }

    @GetMapping("/import/channels")
    public void importChannels(){
        twitchDataService.importChannels();
    }


}
