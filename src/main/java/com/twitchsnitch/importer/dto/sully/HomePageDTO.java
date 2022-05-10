package com.twitchsnitch.importer.dto.sully;

import java.util.ArrayList;
import java.util.List;

public class HomePageDTO {
    private Integer dayRange;
    private String hoursWatched;
    private Double hoursWatchedPercentageChange;
    private String hoursWatchedChangeAmount;

    private String averageViewers;
    private Double averageViewersPercentageChange;
    private String averageViewersChangeAmount;


    private String peakViewers;
    private Double peakViewersPercentageChange;
    private String peakViewersChangeAmount;

    private String peakStreams;
    private Double peakStreamsPercentageChange;
    private String peakStreamsChangeAmount;

    private String averageChannels;
    private Double averageChannelsPercentageChange;
    private String averageChannelsChangeAmount;

    private String gamesStreamed;
    private Double gamesStreamedPercentageChange;
    private String gamesStreamedChangeAmount;

    private String viewerRatio;

    private List<HomePageElementDTO> mostWatchedChannels = new ArrayList<>();
    private List<HomePageElementDTO> mostWatchedGames = new ArrayList<>();
    private List<HomePageElementDTO> trendingGames = new ArrayList<>();
    private List<HomePageElementDTO> fastestGrowingChannels = new ArrayList<>();
    private List<HomePageElementDTO> mostStreamedGames = new ArrayList<>();
    private List<HomePageElementDTO> mostViewedStreams = new ArrayList<>();

    public Integer getDayRange() {
        return dayRange;
    }

    public void setDayRange(Integer dayRange) {
        this.dayRange = dayRange;
    }

    public String getHoursWatched() {
        return hoursWatched;
    }

    public void setHoursWatched(String hoursWatched) {
        this.hoursWatched = hoursWatched;
    }

    public Double getHoursWatchedPercentageChange() {
        return hoursWatchedPercentageChange;
    }

    public void setHoursWatchedPercentageChange(Double hoursWatchedPercentageChange) {
        this.hoursWatchedPercentageChange = hoursWatchedPercentageChange;
    }

    public String getHoursWatchedChangeAmount() {
        return hoursWatchedChangeAmount;
    }

    public void setHoursWatchedChangeAmount(String hoursWatchedChangeAmount) {
        this.hoursWatchedChangeAmount = hoursWatchedChangeAmount;
    }

    public String getAverageViewers() {
        return averageViewers;
    }

    public void setAverageViewers(String averageViewers) {
        this.averageViewers = averageViewers;
    }

    public Double getAverageViewersPercentageChange() {
        return averageViewersPercentageChange;
    }

    public void setAverageViewersPercentageChange(Double averageViewersPercentageChange) {
        this.averageViewersPercentageChange = averageViewersPercentageChange;
    }

    public String getAverageViewersChangeAmount() {
        return averageViewersChangeAmount;
    }

    public void setAverageViewersChangeAmount(String averageViewersChangeAmount) {
        this.averageViewersChangeAmount = averageViewersChangeAmount;
    }

    public String getPeakViewers() {
        return peakViewers;
    }

    public void setPeakViewers(String peakViewers) {
        this.peakViewers = peakViewers;
    }

    public Double getPeakViewersPercentageChange() {
        return peakViewersPercentageChange;
    }

    public void setPeakViewersPercentageChange(Double peakViewersPercentageChange) {
        this.peakViewersPercentageChange = peakViewersPercentageChange;
    }

    public String getPeakViewersChangeAmount() {
        return peakViewersChangeAmount;
    }

    public void setPeakViewersChangeAmount(String peakViewersChangeAmount) {
        this.peakViewersChangeAmount = peakViewersChangeAmount;
    }

    public String getPeakStreams() {
        return peakStreams;
    }

    public void setPeakStreams(String peakStreams) {
        this.peakStreams = peakStreams;
    }

    public Double getPeakStreamsPercentageChange() {
        return peakStreamsPercentageChange;
    }

    public void setPeakStreamsPercentageChange(Double peakStreamsPercentageChange) {
        this.peakStreamsPercentageChange = peakStreamsPercentageChange;
    }

    public String getPeakStreamsChangeAmount() {
        return peakStreamsChangeAmount;
    }

    public void setPeakStreamsChangeAmount(String peakStreamsChangeAmount) {
        this.peakStreamsChangeAmount = peakStreamsChangeAmount;
    }

    public String getAverageChannels() {
        return averageChannels;
    }

    public void setAverageChannels(String averageChannels) {
        this.averageChannels = averageChannels;
    }

    public Double getAverageChannelsPercentageChange() {
        return averageChannelsPercentageChange;
    }

    public void setAverageChannelsPercentageChange(Double averageChannelsPercentageChange) {
        this.averageChannelsPercentageChange = averageChannelsPercentageChange;
    }

    public String getAverageChannelsChangeAmount() {
        return averageChannelsChangeAmount;
    }

    public void setAverageChannelsChangeAmount(String averageChannelsChangeAmount) {
        this.averageChannelsChangeAmount = averageChannelsChangeAmount;
    }

    public String getGamesStreamed() {
        return gamesStreamed;
    }

    public void setGamesStreamed(String gamesStreamed) {
        this.gamesStreamed = gamesStreamed;
    }

    public Double getGamesStreamedPercentageChange() {
        return gamesStreamedPercentageChange;
    }

    public void setGamesStreamedPercentageChange(Double gamesStreamedPercentageChange) {
        this.gamesStreamedPercentageChange = gamesStreamedPercentageChange;
    }

    public String getGamesStreamedChangeAmount() {
        return gamesStreamedChangeAmount;
    }

    public void setGamesStreamedChangeAmount(String gamesStreamedChangeAmount) {
        this.gamesStreamedChangeAmount = gamesStreamedChangeAmount;
    }

    public String getViewerRatio() {
        return viewerRatio;
    }

    public void setViewerRatio(String viewerRatio) {
        this.viewerRatio = viewerRatio;
    }

    public List<HomePageElementDTO> getMostWatchedChannels() {
        return mostWatchedChannels;
    }

    public void setMostWatchedChannels(List<HomePageElementDTO> mostWatchedChannels) {
        this.mostWatchedChannels = mostWatchedChannels;
    }

    public List<HomePageElementDTO> getMostWatchedGames() {
        return mostWatchedGames;
    }

    public void setMostWatchedGames(List<HomePageElementDTO> mostWatchedGames) {
        this.mostWatchedGames = mostWatchedGames;
    }

    public List<HomePageElementDTO> getTrendingGames() {
        return trendingGames;
    }

    public void setTrendingGames(List<HomePageElementDTO> trendingGames) {
        this.trendingGames = trendingGames;
    }

    public List<HomePageElementDTO> getFastestGrowingChannels() {
        return fastestGrowingChannels;
    }

    public void setFastestGrowingChannels(List<HomePageElementDTO> fastestGrowingChannels) {
        this.fastestGrowingChannels = fastestGrowingChannels;
    }

    public List<HomePageElementDTO> getMostStreamedGames() {
        return mostStreamedGames;
    }

    public void setMostStreamedGames(List<HomePageElementDTO> mostStreamedGames) {
        this.mostStreamedGames = mostStreamedGames;
    }

    public List<HomePageElementDTO> getMostViewedStreams() {
        return mostViewedStreams;
    }

    public void setMostViewedStreams(List<HomePageElementDTO> mostViewedStreams) {
        this.mostViewedStreams = mostViewedStreams;
    }

    @Override
    public String toString() {
        return "HomePageDTO{" +
                "dayRange=" + dayRange +
                ", hoursWatched='" + hoursWatched + '\'' +
                ", hoursWatchedPercentageChange=" + hoursWatchedPercentageChange +
                ", hoursWatchedChangeAmount='" + hoursWatchedChangeAmount + '\'' +
                ", averageViewers='" + averageViewers + '\'' +
                ", averageViewersPercentageChange=" + averageViewersPercentageChange +
                ", averageViewersChangeAmount='" + averageViewersChangeAmount + '\'' +
                ", peakViewers='" + peakViewers + '\'' +
                ", peakViewersPercentageChange=" + peakViewersPercentageChange +
                ", peakViewersChangeAmount='" + peakViewersChangeAmount + '\'' +
                ", peakStreams='" + peakStreams + '\'' +
                ", peakStreamsPercentageChange=" + peakStreamsPercentageChange +
                ", peakStreamsChangeAmount='" + peakStreamsChangeAmount + '\'' +
                ", averageChannels='" + averageChannels + '\'' +
                ", averageChannelsPercentageChange=" + averageChannelsPercentageChange +
                ", averageChannelsChangeAmount='" + averageChannelsChangeAmount + '\'' +
                ", gamesStreamed='" + gamesStreamed + '\'' +
                ", gamesStreamedPercentageChange=" + gamesStreamedPercentageChange +
                ", gamesStreamedChangeAmount='" + gamesStreamedChangeAmount + '\'' +
                ", viewerRatio='" + viewerRatio + '\'' +
                ", mostWatchedChannels=" + mostWatchedChannels +
                ", mostWatchedGames=" + mostWatchedGames +
                ", trendingGames=" + trendingGames +
                ", fastestGrowingChannels=" + fastestGrowingChannels +
                ", mostStreamedGames=" + mostStreamedGames +
                ", mostViewedStreams=" + mostViewedStreams +
                '}';
    }
}
