package com.twitchsnitch.importer.dto.sully;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualChannelPageDTO {

    private String displayName;
    private String url;
    private String profilePic;
    private String createdAt;
    private Integer views;
    private String language;
    private String status;
    private Integer followers;
    private Boolean mature;
    private Integer lastOnlineDays;
    private Integer followerRank;
    private Integer followerRankChange;
    private Integer followerGainRank;
    private Integer followerGainRankChange;
    private Integer  peakViewerRank;
    private Integer  peakViewerRankChange;
    private Integer  averageViewerRank;
    private Integer  averageViewerRankChange;
    private Integer  viewRank;
    private Integer  viewRankChange;
    private Integer  viewGainRank;
    private Integer  viewGainRankChange;
    private Integer  averageViewers;
    private Integer  averageViewersChange;
    private Double averageViewersChangePercentage;
    private Integer  hoursWatched;
    private Integer  hoursWatchedChange;
    private Double hoursWatchedChangePercentage;
    private Integer  followersGained;
    private Integer  followersGainedChange;
    private Double followersGainedChangePercentage;
    private Integer  viewsGained;
    private Integer  viewsGainedChange;
    private Double viewsGainedChangePercentage;
    private Double  peakViewers;
    private Double  peakViewersChange;
    private Double peakViewersChangePercentage;
    private Double  hoursStreamed;
    private Double  hoursStreamedChange;
    private Double hoursStreamedChangePercentage;
    private Map<String, String> teams = new HashMap<>();
    private List<IndividualChannelStreamDTO> individualChannelStreamDTOList = new ArrayList<>();

    public List<IndividualChannelStreamDTO> getIndividualChannelStreamDTOList() {
        return individualChannelStreamDTOList;
    }

    public void setIndividualChannelStreamDTOList(List<IndividualChannelStreamDTO> individualChannelStreamDTOList) {
        this.individualChannelStreamDTOList = individualChannelStreamDTOList;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, String> teams) {
        this.teams = teams;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Boolean getMature() {
        return mature;
    }

    public void setMature(Boolean mature) {
        this.mature = mature;
    }

    public Integer getLastOnlineDays() {
        return lastOnlineDays;
    }

    public void setLastOnlineDays(Integer lastOnlineDays) {
        this.lastOnlineDays = lastOnlineDays;
    }

    public Integer getFollowerRank() {
        return followerRank;
    }

    public void setFollowerRank(Integer followerRank) {
        this.followerRank = followerRank;
    }

    public Integer getFollowerRankChange() {
        return followerRankChange;
    }

    public void setFollowerRankChange(Integer followerRankChange) {
        this.followerRankChange = followerRankChange;
    }

    public Integer getFollowerGainRank() {
        return followerGainRank;
    }

    public void setFollowerGainRank(Integer followerGainRank) {
        this.followerGainRank = followerGainRank;
    }

    public Integer getFollowerGainRankChange() {
        return followerGainRankChange;
    }

    public void setFollowerGainRankChange(Integer followerGainRankChange) {
        this.followerGainRankChange = followerGainRankChange;
    }

    public Integer getPeakViewerRank() {
        return peakViewerRank;
    }

    public void setPeakViewerRank(Integer peakViewerRank) {
        this.peakViewerRank = peakViewerRank;
    }

    public Integer getPeakViewerRankChange() {
        return peakViewerRankChange;
    }

    public void setPeakViewerRankChange(Integer peakViewerRankChange) {
        this.peakViewerRankChange = peakViewerRankChange;
    }

    public Integer getAverageViewerRank() {
        return averageViewerRank;
    }

    public void setAverageViewerRank(Integer averageViewerRank) {
        this.averageViewerRank = averageViewerRank;
    }

    public Integer getAverageViewerRankChange() {
        return averageViewerRankChange;
    }

    public void setAverageViewerRankChange(Integer averageViewerRankChange) {
        this.averageViewerRankChange = averageViewerRankChange;
    }

    public Integer getViewRank() {
        return viewRank;
    }

    public void setViewRank(Integer viewRank) {
        this.viewRank = viewRank;
    }

    public Integer getViewRankChange() {
        return viewRankChange;
    }

    public void setViewRankChange(Integer viewRankChange) {
        this.viewRankChange = viewRankChange;
    }

    public Integer getViewGainRank() {
        return viewGainRank;
    }

    public void setViewGainRank(Integer viewGainRank) {
        this.viewGainRank = viewGainRank;
    }

    public Integer getViewGainRankChange() {
        return viewGainRankChange;
    }

    public void setViewGainRankChange(Integer viewGainRankChange) {
        this.viewGainRankChange = viewGainRankChange;
    }

    public Integer getAverageViewers() {
        return averageViewers;
    }

    public void setAverageViewers(Integer averageViewers) {
        this.averageViewers = averageViewers;
    }

    public Integer getAverageViewersChange() {
        return averageViewersChange;
    }

    public void setAverageViewersChange(Integer averageViewersChange) {
        this.averageViewersChange = averageViewersChange;
    }

    public Double getAverageViewersChangePercentage() {
        return averageViewersChangePercentage;
    }

    public void setAverageViewersChangePercentage(Double averageViewersChangePercentage) {
        this.averageViewersChangePercentage = averageViewersChangePercentage;
    }

    public Integer getHoursWatched() {
        return hoursWatched;
    }

    public void setHoursWatched(Integer hoursWatched) {
        this.hoursWatched = hoursWatched;
    }

    public Integer getHoursWatchedChange() {
        return hoursWatchedChange;
    }

    public void setHoursWatchedChange(Integer hoursWatchedChange) {
        this.hoursWatchedChange = hoursWatchedChange;
    }

    public Double getHoursWatchedChangePercentage() {
        return hoursWatchedChangePercentage;
    }

    public void setHoursWatchedChangePercentage(Double hoursWatchedChangePercentage) {
        this.hoursWatchedChangePercentage = hoursWatchedChangePercentage;
    }

    public Integer getFollowersGained() {
        return followersGained;
    }

    public void setFollowersGained(Integer followersGained) {
        this.followersGained = followersGained;
    }

    public Integer getFollowersGainedChange() {
        return followersGainedChange;
    }

    public void setFollowersGainedChange(Integer followersGainedChange) {
        this.followersGainedChange = followersGainedChange;
    }

    public Double getFollowersGainedChangePercentage() {
        return followersGainedChangePercentage;
    }

    public void setFollowersGainedChangePercentage(Double followersGainedChangePercentage) {
        this.followersGainedChangePercentage = followersGainedChangePercentage;
    }

    public Integer getViewsGained() {
        return viewsGained;
    }

    public void setViewsGained(Integer viewsGained) {
        this.viewsGained = viewsGained;
    }

    public Integer getViewsGainedChange() {
        return viewsGainedChange;
    }

    public void setViewsGainedChange(Integer viewsGainedChange) {
        this.viewsGainedChange = viewsGainedChange;
    }

    public Double getViewsGainedChangePercentage() {
        return viewsGainedChangePercentage;
    }

    public void setViewsGainedChangePercentage(Double viewsGainedChangePercentage) {
        this.viewsGainedChangePercentage = viewsGainedChangePercentage;
    }

    public Double getPeakViewers() {
        return peakViewers;
    }

    public void setPeakViewers(Double peakViewers) {
        this.peakViewers = peakViewers;
    }

    public Double getPeakViewersChange() {
        return peakViewersChange;
    }

    public void setPeakViewersChange(Double peakViewersChange) {
        this.peakViewersChange = peakViewersChange;
    }

    public Double getPeakViewersChangePercentage() {
        return peakViewersChangePercentage;
    }

    public void setPeakViewersChangePercentage(Double peakViewersChangePercentage) {
        this.peakViewersChangePercentage = peakViewersChangePercentage;
    }

    public Double getHoursStreamed() {
        return hoursStreamed;
    }

    public void setHoursStreamed(Double hoursStreamed) {
        this.hoursStreamed = hoursStreamed;
    }

    public Double getHoursStreamedChange() {
        return hoursStreamedChange;
    }

    public void setHoursStreamedChange(Double hoursStreamedChange) {
        this.hoursStreamedChange = hoursStreamedChange;
    }

    public Double getHoursStreamedChangePercentage() {
        return hoursStreamedChangePercentage;
    }

    public void setHoursStreamedChangePercentage(Double hoursStreamedChangePercentage) {
        this.hoursStreamedChangePercentage = hoursStreamedChangePercentage;
    }


}
