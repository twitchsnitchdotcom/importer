package com.twitchsnitch.importer.dto.sully;

import java.util.ArrayList;
import java.util.List;

public class RaidFinderDTO {

    private List<String> gameIds = new ArrayList<>();

    private boolean dataIsSet = false;

    private Long lowRange;

    private Long highRange;

    public boolean isDataIsSet() {
        return dataIsSet;
    }

    public void setDataIsSet(boolean dataIsSet) {
        this.dataIsSet = dataIsSet;
    }

    public List<String> getGameIds() {
        return gameIds;
    }

    public void setGameIds(List<String> gameIds) {
        this.gameIds = gameIds;
    }

    public Long getLowRange() {
        return lowRange;
    }

    public void setLowRange(Long lowRange) {
        this.lowRange = lowRange;
    }

    public Long getHighRange() {
        return highRange;
    }

    public void setHighRange(Long highRange) {
        this.highRange = highRange;
    }
}
