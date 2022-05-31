package com.twitchsnitch.importer.dto.twitch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twitchsnitch.importer.dto.twitch.ChattersDTO;

import java.util.LinkedHashMap;

public class ChatterListDTO {

    private LinkedHashMap map;

    @JsonProperty("chatter_count")
    private Integer chatterCount;

    private ChattersDTO chatters;

    public Integer getChatterCount() {
        return chatterCount;
    }

    public void setChatterCount(Integer chatterCount) {
        this.chatterCount = chatterCount;
    }

    public ChattersDTO getChatters() {
        return chatters;
    }

    public void setChatters(ChattersDTO chatters) {
        this.chatters = chatters;
    }

    public LinkedHashMap getMap() {
        return map;
    }

    public void setMap(LinkedHashMap map) {
        this.map = map;
    }
}
