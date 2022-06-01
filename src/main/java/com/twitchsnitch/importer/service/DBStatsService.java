package com.twitchsnitch.importer.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DBStatsService {

    private final PersistenceService persistenceService;

    public DBStatsService(PersistenceService persistenceService){
        this.persistenceService = persistenceService;
    }

    public Long getUsersWithoutFollowsTo(){
        return persistenceService.getUsersWithoutFollowsTo();
    }

    public Long getUsersWithoutFollowsFrom(){
        return persistenceService.getUsersWithoutFollowsFrom();
    }


    public Long getTotalChannels(){
        return persistenceService.getTotalChannels();
    }

    public Long getTotalChannelsWithoutTwitchId(){
        return persistenceService.getTotalChannelsWithoutTwitchId();
    }

    public Integer getTotalChannelsWithoutTwitchIdOrSullyId(){
        return persistenceService.getUsersWithoutTwitchIdOrSullyId().size();
    }

    public Long getTotalChannelsWithoutSullyIdCount(){
        return persistenceService.getUsersWithoutSullyIdCount();
    }

    public List<String> getAllUsersWithoutSullyId(){
        return persistenceService.getAllUsersWithoutSullyId();
    }

    public List<String> getAllGamesWithoutSullyId(){
        return persistenceService.getAllGamesWithoutSullyId();
    }

    public Long getTeamsCount(){
        return persistenceService.getTeamsCount();
    }
    public Integer getAllTeamsWithoutSullyId(){
        return persistenceService.getAllTeamsWithoutSullyId().size();
    }

    public Long getTotalStreams(){
        return persistenceService.getTotalStreams();
    }

    public Long getNumberOfChatters(){
        return persistenceService.getNumberOfChatters();
    }

    public Set<Long> getChannelsWithoutChannelGameData(){
        return persistenceService.getChannelsWithoutChannelGameData();
    }

    public Set<Long> getChannelStreamsWithoutIndividualData(){
        return persistenceService.getChannelStreamsWithoutIndividualData();
    }

    public Set<Long> getAllSullyChannels(){
        return persistenceService.getAllSullyChannels();
    }

    public Set<String> getAllGamesWithoutTwitchIds(){
        return persistenceService.getAllGamesWithoutTwitchIds();
    }

    public Long getAllGamesCount(){
        return persistenceService.getAllGamesCount();
    }

    public Set<String> getUsersWithoutTwitchId(){
        return persistenceService.getUsersWithoutTwitchId();
    }

    public Set<String> getUsersWithoutTwitchFollowsTo( ){
        return persistenceService.getUsersWithoutTwitchFollowsTo();
    }

    public Set<String> getUsersWithoutTwitchFollowsFrom(){
        return persistenceService.getUsersWithoutTwitchFollowsFrom();
    }

    public Set<String> getTeamsWithoutTwitchId(){
        return persistenceService.getTeamsWithoutTwitchId();
    }

    public Long getTwitchIdNotSetCountChannel(){
        return persistenceService.getTwitchIdNotSetCountChannel();
    }

    public Long getTwitchIdNotSetCountGame(){
        return persistenceService.getTwitchIdNotSetCountGame();
    }

    public Long getTwitchIdNotSetCountUser(){
        return persistenceService.getTwitchIdNotSetCountUser();
    }
    public String getDBInfo(){ return persistenceService.getDBInfo();}

    public String getLanguagesWithOutSullyIds() {
        return persistenceService.getSullyLanguageIdNotSetCount().toString();
    }



}
