package com.twitchsnitch.importer.dto.sully.channels;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"rownum",
"streamtime",
"viewtime",
"viewsgained",
"followers",
"avgviewers",
"maxviewers",
"followersperhour",
"gamesplayed",
"viewsperhour"
})
@Generated("jsonschema2pojo")
public class ChannelGameDatum {

    @JsonProperty("rownum")
    private Long rownum;
    @JsonProperty("streamtime")
    private Long streamtime;
    @JsonProperty("viewtime")
    private Long viewtime;
    @JsonProperty("viewsgained")
    private Long viewsgained;
    @JsonProperty("followers")
    private Long followers;
    @JsonProperty("avgviewers")
    private Long avgviewers;
    @JsonProperty("maxviewers")
    private Long maxviewers;
    @JsonProperty("followersperhour")
    private Double followersperhour;
    @JsonProperty("gamesplayed")
    private String gamesplayed;
    @JsonProperty("viewsperhour")
    private Double viewsperhour;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rownum")
    public Long getRownum() {
        return rownum;
    }

    @JsonProperty("rownum")
    public void setRownum(Long rownum) {
        this.rownum = rownum;
    }

    @JsonProperty("streamtime")
    public Long getStreamtime() {
        return streamtime;
    }

    @JsonProperty("streamtime")
    public void setStreamtime(Long streamtime) {
        this.streamtime = streamtime;
    }

    @JsonProperty("viewtime")
    public Long getViewtime() {
        return viewtime;
    }

    @JsonProperty("viewtime")
    public void setViewtime(Long viewtime) {
        this.viewtime = viewtime;
    }

    @JsonProperty("viewsgained")
    public Long getViewsgained() {
        return viewsgained;
    }

    @JsonProperty("viewsgained")
    public void setViewsgained(Long viewsgained) {
        this.viewsgained = viewsgained;
    }

    @JsonProperty("followers")
    public Long getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    @JsonProperty("avgviewers")
    public Long getAvgviewers() {
        return avgviewers;
    }

    @JsonProperty("avgviewers")
    public void setAvgviewers(Long avgviewers) {
        this.avgviewers = avgviewers;
    }

    @JsonProperty("maxviewers")
    public Long getMaxviewers() {
        return maxviewers;
    }

    @JsonProperty("maxviewers")
    public void setMaxviewers(Long maxviewers) {
        this.maxviewers = maxviewers;
    }

    @JsonProperty("followersperhour")
    public Double getFollowersperhour() {
        return followersperhour;
    }

    @JsonProperty("followersperhour")
    public void setFollowersperhour(Double followersperhour) {
        this.followersperhour = followersperhour;
    }

    @JsonProperty("gamesplayed")
    public String getGamesplayed() {
        return gamesplayed;
    }

    @JsonProperty("gamesplayed")
    public void setGamesplayed(String gamesplayed) {
        this.gamesplayed = gamesplayed;
    }

    @JsonProperty("viewsperhour")
    public Double getViewsperhour() {
        return viewsperhour;
    }

    @JsonProperty("viewsperhour")
    public void setViewsperhour(Double viewsperhour) {
        this.viewsperhour = viewsperhour;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}