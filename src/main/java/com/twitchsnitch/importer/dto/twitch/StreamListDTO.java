package com.twitchsnitch.importer.dto.twitch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.twitch4j.helix.domain.HelixPagination;
import com.twitchsnitch.importer.dto.twitch.StreamDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StreamListDTO {

    private Map map;

    @JsonProperty("data")
    private List<StreamDTO> streams;

    private HelixPagination pagination;

    public List<StreamDTO> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamDTO> streams) {
        this.streams = streams;
    }

    public HelixPagination getPagination() {
        return pagination;
    }

    public void setPagination(HelixPagination pagination) {
        this.pagination = pagination;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
