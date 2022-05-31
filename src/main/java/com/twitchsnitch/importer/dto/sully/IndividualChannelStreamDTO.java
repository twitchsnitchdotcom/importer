package com.twitchsnitch.importer.dto.sully;

import java.util.HashMap;
import java.util.Map;

public class IndividualChannelStreamDTO {

    private String dateTime;
    private Long sullyId;
    private Double streamLength;
    private Double averageViewers;
    private Integer peakViewers;
    private Double watchTime;
    private Integer followers;
    private Integer views;
    private Map<String, String> games = new HashMap<>();

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getSullyId() {
        return sullyId;
    }

    public void setSullyId(Long sullyId) {
        this.sullyId = sullyId;
    }

    public Double getStreamLength() {
        return streamLength;
    }

    public void setStreamLength(Double streamLength) {
        this.streamLength = streamLength;
    }

    public Double getAverageViewers() {
        return averageViewers;
    }

    public void setAverageViewers(Double averageViewers) {
        this.averageViewers = averageViewers;
    }

    public Integer getPeakViewers() {
        return peakViewers;
    }

    public void setPeakViewers(Integer peakViewers) {
        this.peakViewers = peakViewers;
    }

    public Double getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Double watchTime) {
        this.watchTime = watchTime;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Map<String, String> getGames() {
        return games;
    }

    public void setGames(Map<String, String> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "IndividualChannelStreamDTO{" +
                "dateTime='" + dateTime + '\'' +
                ", sullyId=" + sullyId +
                ", streamLength=" + streamLength +
                ", averageViewers=" + averageViewers +
                ", peakViewers=" + peakViewers +
                ", watchTime=" + watchTime +
                ", followers=" + followers +
                ", views=" + views +
                ", game=" + games +
                '}';
    }
}
