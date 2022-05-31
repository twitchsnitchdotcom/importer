package com.twitchsnitch.importer.dto.twitch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChattersDTO {

    private List<String> broadcaster;

    private List<String> vips;

    private List<String> moderators;

    private List<String> staff;

    private List<String> admins;

    @JsonProperty("global_mods")
    private List<String> globalModerators;

    private List<String> viewers;

    public List<String> getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(List<String> broadcaster) {
        this.broadcaster = broadcaster;
    }

    public List<String> getVips() {
        return vips;
    }

    public void setVips(List<String> vips) {
        this.vips = vips;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public List<String> getStaff() {
        return staff;
    }

    public void setStaff(List<String> staff) {
        this.staff = staff;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public List<String> getGlobalModerators() {
        return globalModerators;
    }

    public void setGlobalModerators(List<String> globalModerators) {
        this.globalModerators = globalModerators;
    }

    public List<String> getViewers() {
        return viewers;
    }

    public void setViewers(List<String> viewers) {
        this.viewers = viewers;
    }
}
