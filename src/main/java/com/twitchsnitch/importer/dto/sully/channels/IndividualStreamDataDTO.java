package com.twitchsnitch.importer.dto.sully.channels;

public class IndividualStreamDataDTO {

    private String gameName;
    private long watchTime;
    private String streamLength;

    private long averageViewers;
    private long maxViewers;
    private String maxViewersPerformance;

    private Double viewsPerHour;
    private long views;
    private String viewsPerformance;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public long getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(long watchTime) {
        this.watchTime = watchTime;
    }

    public String getStreamLength() {
        return streamLength;
    }

    public void setStreamLength(String streamLength) {
        this.streamLength = streamLength;
    }

    public long getAverageViewers() {
        return averageViewers;
    }

    public void setAverageViewers(long averageViewers) {
        this.averageViewers = averageViewers;
    }

    public long getMaxViewers() {
        return maxViewers;
    }

    public void setMaxViewers(long maxViewers) {
        this.maxViewers = maxViewers;
    }

    public String getMaxViewersPerformance() {
        return maxViewersPerformance;
    }

    public void setMaxViewersPerformance(String maxViewersPerformance) {
        this.maxViewersPerformance = maxViewersPerformance;
    }

    public Double getViewsPerHour() {
        return viewsPerHour;
    }

    public void setViewsPerHour(Double viewsPerHour) {
        this.viewsPerHour = viewsPerHour;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getViewsPerformance() {
        return viewsPerformance;
    }

    public void setViewsPerformance(String viewsPerformance) {
        this.viewsPerformance = viewsPerformance;
    }
}
