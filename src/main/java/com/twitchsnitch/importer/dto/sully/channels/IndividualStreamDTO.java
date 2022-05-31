package com.twitchsnitch.importer.dto.sully.channels;

import java.util.ArrayList;
import java.util.List;

public class IndividualStreamDTO {

    private long averageViewers;
    private Double viewsPerHour;

    private List<IndividualStreamDataDTO> gamesPlayed = new ArrayList();

    public long getAverageViewers() {
        return averageViewers;
    }

    public void setAverageViewers(long averageViewers) {
        this.averageViewers = averageViewers;
    }

    public Double getViewsPerHour() {
        return viewsPerHour;
    }

    public void setViewsPerHour(Double viewsPerHour) {
        this.viewsPerHour = viewsPerHour;
    }

    public List<IndividualStreamDataDTO> getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(List<IndividualStreamDataDTO> gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "IndividualStreamDTO{" +
                "averageViewers=" + averageViewers +
                ", viewsPerHour=" + viewsPerHour +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
