package com.twitchsnitch.importer.dto.sully;

public class IndividualChannelAPIDTO {

    private Boolean affiliate; // false
    private Integer avgViewers; // avg_viewers 1658
    private String createdAt; // created_at "2013-11-21T21:59:20Z"
    private Integer followers; // followers	459636
    private Integer followersGained; // followers_gained	760
    private Integer followersGainedWhilePlaying; // followers_gained_while_playing	0
    private String login; // login	ml7support
    private Boolean mature;  // mature	false
    private Long maxViewers; // max_viewers	3202
    private String name;  // name	mL7support
    private Boolean partner;  // partner	true
    private Long previousAvgViewers;// previous_avg_viewers	1556
    private Long previousFollowerGain;// previous_follower_gain	822
    private Long previousMaxViewers;// previous_max_viewers	2571
    private Long previousStreamedMinutes;// previous_streamed_minutes	2205
    private Long previousViewMinutes;// previous_view_minutes	3431595
    private Long previousViewsGained;// previous_views_gained	59092
    private String profileImageUrl;//profile_image_url	https://static-cdn.jtvnw.net/jtv_user_pictures/94286e18-367b-4795-98a5-d8dd530d4d0a-profile_image-150x150.png?imenable=1&impolicy=user-profile-picture… Show all
    private Long rowNumber; //row_number	1218
    private String status;//	Partner
    private Long streamedMinutes;// streamed_minutes	1710
    private Long sullyId;//sully_id	279750
    private Long totalViewCount;//total_view_count	17564333
    private Long twitchId;//twitch_id	51929371
    private String twitchLink;//twitch_link	https://www.twitch.tv/ml7support
    private Long viewMinutes;//view_minutes	2835450
    private Long viewsGained;//views_gained	48942


    public Boolean getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Boolean affiliate) {
        this.affiliate = affiliate;
    }

    public Integer getAvgViewers() {
        return avgViewers;
    }

    public void setAvgViewers(Integer avgViewers) {
        this.avgViewers = avgViewers;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowersGained() {
        return followersGained;
    }

    public void setFollowersGained(Integer followersGained) {
        this.followersGained = followersGained;
    }

    public Integer getFollowersGainedWhilePlaying() {
        return followersGainedWhilePlaying;
    }

    public void setFollowersGainedWhilePlaying(Integer followersGainedWhilePlaying) {
        this.followersGainedWhilePlaying = followersGainedWhilePlaying;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getMature() {
        return mature;
    }

    public void setMature(Boolean mature) {
        this.mature = mature;
    }

    public Long getMaxViewers() {
        return maxViewers;
    }

    public void setMaxViewers(Long maxViewers) {
        this.maxViewers = maxViewers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public Long getPreviousAvgViewers() {
        return previousAvgViewers;
    }

    public void setPreviousAvgViewers(Long previousAvgViewers) {
        this.previousAvgViewers = previousAvgViewers;
    }

    public Long getPreviousFollowerGain() {
        return previousFollowerGain;
    }

    public void setPreviousFollowerGain(Long previousFollowerGain) {
        this.previousFollowerGain = previousFollowerGain;
    }

    public Long getPreviousMaxViewers() {
        return previousMaxViewers;
    }

    public void setPreviousMaxViewers(Long previousMaxViewers) {
        this.previousMaxViewers = previousMaxViewers;
    }

    public Long getPreviousStreamedMinutes() {
        return previousStreamedMinutes;
    }

    public void setPreviousStreamedMinutes(Long previousStreamedMinutes) {
        this.previousStreamedMinutes = previousStreamedMinutes;
    }

    public Long getPreviousViewMinutes() {
        return previousViewMinutes;
    }

    public void setPreviousViewMinutes(Long previousViewMinutes) {
        this.previousViewMinutes = previousViewMinutes;
    }

    public Long getPreviousViewsGained() {
        return previousViewsGained;
    }

    public void setPreviousViewsGained(Long previousViewsGained) {
        this.previousViewsGained = previousViewsGained;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStreamedMinutes() {
        return streamedMinutes;
    }

    public void setStreamedMinutes(Long streamedMinutes) {
        this.streamedMinutes = streamedMinutes;
    }

    public Long getSullyId() {
        return sullyId;
    }

    public void setSullyId(Long sullyId) {
        this.sullyId = sullyId;
    }

    public Long getTotalViewCount() {
        return totalViewCount;
    }

    public void setTotalViewCount(Long totalViewCount) {
        this.totalViewCount = totalViewCount;
    }

    public Long getTwitchId() {
        return twitchId;
    }

    public void setTwitchId(Long twitchId) {
        this.twitchId = twitchId;
    }

    public String getTwitchLink() {
        return twitchLink;
    }

    public void setTwitchLink(String twitchLink) {
        this.twitchLink = twitchLink;
    }

    public Long getViewMinutes() {
        return viewMinutes;
    }

    public void setViewMinutes(Long viewMinutes) {
        this.viewMinutes = viewMinutes;
    }

    public Long getViewsGained() {
        return viewsGained;
    }

    public void setViewsGained(Long viewsGained) {
        this.viewsGained = viewsGained;
    }

    @Override
    public String toString() {
        return "IndividualChannelPageDTO{" +
                "affiliate=" + affiliate +
                ", avgViewers=" + avgViewers +
                ", createdAt='" + createdAt + '\'' +
                ", followers=" + followers +
                ", followersGained=" + followersGained +
                ", followersGainedWhilePlaying=" + followersGainedWhilePlaying +
                ", login='" + login + '\'' +
                ", mature=" + mature +
                ", maxViewers=" + maxViewers +
                ", name='" + name + '\'' +
                ", partner=" + partner +
                ", previousAvgViewers=" + previousAvgViewers +
                ", previousFollowerGain=" + previousFollowerGain +
                ", previousMaxViewers=" + previousMaxViewers +
                ", previousStreamedMinutes=" + previousStreamedMinutes +
                ", previousViewMinutes=" + previousViewMinutes +
                ", previousViewsGained=" + previousViewsGained +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", rowNumber=" + rowNumber +
                ", status='" + status + '\'' +
                ", streamedMinutes=" + streamedMinutes +
                ", sullyId=" + sullyId +
                ", totalViewCount=" + totalViewCount +
                ", twitchId=" + twitchId +
                ", twitchLink='" + twitchLink + '\'' +
                ", viewMinutes=" + viewMinutes +
                ", viewsGained=" + viewsGained +
                '}';
    }
}
