package com.twitchsnitch.importer.dto.sully;

import java.util.ArrayList;
import java.util.List;

public class RaidFinderDTO {

    private List<String> gameIds = new ArrayList<>();

    private boolean dataIsSet = false;

    private int lowRange;

    private int highRange;

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

    public int getLowRange() {
        return lowRange;
    }

    public void setLowRange(int lowRange) {
        this.lowRange = lowRange;
    }

    public int getHighRange() {
        return highRange;
    }

    public void setHighRange(int highRange) {
        this.highRange = highRange;
    }
}
