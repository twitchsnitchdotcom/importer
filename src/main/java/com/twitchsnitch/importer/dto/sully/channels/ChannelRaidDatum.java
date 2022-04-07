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
"liveMinutes",
"liveViewers",
"overlappingStreams",
"otherChannelStreams",
"overlappingEndedDuring",
"overlappingEndedAfter",
"previewLarge",
"preview",
"currentGame",
"avgLengthMins",
"streams",
"viewminutes",
"streamedminutes",
"maxviewers",
"avgviewers",
"milestonefollowers",
"rownum",
"followers",
"followersgained",
"viewsgained",
"followersgainedwhileplaying",
"partner",
"affiliate",
"mature",
"language",
"languageId",
"status",
"previousviewminutes",
"previousstreamedminutes",
"previousmaxviewers",
"previousavgviewers",
"previousfollowergain",
"previousviewsgained",
"gamesPlayed",
"id",
"logo",
"twitchurl",
"url",
"displayname",
"baseOnly"
})
@Generated("jsonschema2pojo")
public class ChannelRaidDatum {

@JsonProperty("liveMinutes")
private Long liveMinutes;
@JsonProperty("liveViewers")
private Long liveViewers;
@JsonProperty("overlappingStreams")
private Long overlappingStreams;
@JsonProperty("otherChannelStreams")
private Long otherChannelStreams;
@JsonProperty("overlappingEndedDuring")
private Long overlappingEndedDuring;
@JsonProperty("overlappingEndedAfter")
private Long overlappingEndedAfter;
@JsonProperty("previewLarge")
private String previewLarge;
@JsonProperty("preview")
private String preview;
@JsonProperty("currentGame")
private String currentGame;
@JsonProperty("avgLengthMins")
private Long avgLengthMins;
@JsonProperty("streams")
private Long streams;
@JsonProperty("viewminutes")
private Long viewminutes;
@JsonProperty("streamedminutes")
private Long streamedminutes;
@JsonProperty("maxviewers")
private Long maxviewers;
@JsonProperty("avgviewers")
private Long avgviewers;
@JsonProperty("milestonefollowers")
private Object milestonefollowers;
@JsonProperty("rownum")
private Long rownum;
@JsonProperty("followers")
private Long followers;
@JsonProperty("followersgained")
private Long followersgained;
@JsonProperty("viewsgained")
private Long viewsgained;
@JsonProperty("followersgainedwhileplaying")
private Long followersgainedwhileplaying;
@JsonProperty("partner")
private Boolean partner;
@JsonProperty("affiliate")
private Boolean affiliate;
@JsonProperty("mature")
private Boolean mature;
@JsonProperty("language")
private String language;
@JsonProperty("languageId")
private Long languageId;
@JsonProperty("status")
private String status;
@JsonProperty("previousviewminutes")
private Object previousviewminutes;
@JsonProperty("previousstreamedminutes")
private Object previousstreamedminutes;
@JsonProperty("previousmaxviewers")
private Object previousmaxviewers;
@JsonProperty("previousavgviewers")
private Object previousavgviewers;
@JsonProperty("previousfollowergain")
private Object previousfollowergain;
@JsonProperty("previousviewsgained")
private Object previousviewsgained;
@JsonProperty("gamesPlayed")
private String gamesPlayed;
@JsonProperty("id")
private Long id;
@JsonProperty("logo")
private String logo;
@JsonProperty("twitchurl")
private String twitchurl;
@JsonProperty("url")
private String url;
@JsonProperty("displayname")
private String displayname;
@JsonProperty("baseOnly")
private Boolean baseOnly;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("liveMinutes")
public Long getLiveMinutes() {
return liveMinutes;
}

@JsonProperty("liveMinutes")
public void setLiveMinutes(Long liveMinutes) {
this.liveMinutes = liveMinutes;
}

@JsonProperty("liveViewers")
public Long getLiveViewers() {
return liveViewers;
}

@JsonProperty("liveViewers")
public void setLiveViewers(Long liveViewers) {
this.liveViewers = liveViewers;
}

@JsonProperty("overlappingStreams")
public Long getOverlappingStreams() {
return overlappingStreams;
}

@JsonProperty("overlappingStreams")
public void setOverlappingStreams(Long overlappingStreams) {
this.overlappingStreams = overlappingStreams;
}

@JsonProperty("otherChannelStreams")
public Long getOtherChannelStreams() {
return otherChannelStreams;
}

@JsonProperty("otherChannelStreams")
public void setOtherChannelStreams(Long otherChannelStreams) {
this.otherChannelStreams = otherChannelStreams;
}

@JsonProperty("overlappingEndedDuring")
public Long getOverlappingEndedDuring() {
return overlappingEndedDuring;
}

@JsonProperty("overlappingEndedDuring")
public void setOverlappingEndedDuring(Long overlappingEndedDuring) {
this.overlappingEndedDuring = overlappingEndedDuring;
}

@JsonProperty("overlappingEndedAfter")
public Long getOverlappingEndedAfter() {
return overlappingEndedAfter;
}

@JsonProperty("overlappingEndedAfter")
public void setOverlappingEndedAfter(Long overlappingEndedAfter) {
this.overlappingEndedAfter = overlappingEndedAfter;
}

@JsonProperty("previewLarge")
public String getPreviewLarge() {
return previewLarge;
}

@JsonProperty("previewLarge")
public void setPreviewLarge(String previewLarge) {
this.previewLarge = previewLarge;
}

@JsonProperty("preview")
public String getPreview() {
return preview;
}

@JsonProperty("preview")
public void setPreview(String preview) {
this.preview = preview;
}

@JsonProperty("currentGame")
public String getCurrentGame() {
return currentGame;
}

@JsonProperty("currentGame")
public void setCurrentGame(String currentGame) {
this.currentGame = currentGame;
}

@JsonProperty("avgLengthMins")
public Long getAvgLengthMins() {
return avgLengthMins;
}

@JsonProperty("avgLengthMins")
public void setAvgLengthMins(Long avgLengthMins) {
this.avgLengthMins = avgLengthMins;
}

@JsonProperty("streams")
public Long getStreams() {
return streams;
}

@JsonProperty("streams")
public void setStreams(Long streams) {
this.streams = streams;
}

@JsonProperty("viewminutes")
public Long getViewminutes() {
return viewminutes;
}

@JsonProperty("viewminutes")
public void setViewminutes(Long viewminutes) {
this.viewminutes = viewminutes;
}

@JsonProperty("streamedminutes")
public Long getStreamedminutes() {
return streamedminutes;
}

@JsonProperty("streamedminutes")
public void setStreamedminutes(Long streamedminutes) {
this.streamedminutes = streamedminutes;
}

@JsonProperty("maxviewers")
public Long getMaxviewers() {
return maxviewers;
}

@JsonProperty("maxviewers")
public void setMaxviewers(Long maxviewers) {
this.maxviewers = maxviewers;
}

@JsonProperty("avgviewers")
public Long getAvgviewers() {
return avgviewers;
}

@JsonProperty("avgviewers")
public void setAvgviewers(Long avgviewers) {
this.avgviewers = avgviewers;
}

@JsonProperty("milestonefollowers")
public Object getMilestonefollowers() {
return milestonefollowers;
}

@JsonProperty("milestonefollowers")
public void setMilestonefollowers(Object milestonefollowers) {
this.milestonefollowers = milestonefollowers;
}

@JsonProperty("rownum")
public Long getRownum() {
return rownum;
}

@JsonProperty("rownum")
public void setRownum(Long rownum) {
this.rownum = rownum;
}

@JsonProperty("followers")
public Long getFollowers() {
return followers;
}

@JsonProperty("followers")
public void setFollowers(Long followers) {
this.followers = followers;
}

@JsonProperty("followersgained")
public Long getFollowersgained() {
return followersgained;
}

@JsonProperty("followersgained")
public void setFollowersgained(Long followersgained) {
this.followersgained = followersgained;
}

@JsonProperty("viewsgained")
public Long getViewsgained() {
return viewsgained;
}

@JsonProperty("viewsgained")
public void setViewsgained(Long viewsgained) {
this.viewsgained = viewsgained;
}

@JsonProperty("followersgainedwhileplaying")
public Long getFollowersgainedwhileplaying() {
return followersgainedwhileplaying;
}

@JsonProperty("followersgainedwhileplaying")
public void setFollowersgainedwhileplaying(Long followersgainedwhileplaying) {
this.followersgainedwhileplaying = followersgainedwhileplaying;
}

@JsonProperty("partner")
public Boolean getPartner() {
return partner;
}

@JsonProperty("partner")
public void setPartner(Boolean partner) {
this.partner = partner;
}

@JsonProperty("affiliate")
public Boolean getAffiliate() {
return affiliate;
}

@JsonProperty("affiliate")
public void setAffiliate(Boolean affiliate) {
this.affiliate = affiliate;
}

@JsonProperty("mature")
public Boolean getMature() {
return mature;
}

@JsonProperty("mature")
public void setMature(Boolean mature) {
this.mature = mature;
}

@JsonProperty("language")
public String getLanguage() {
return language;
}

@JsonProperty("language")
public void setLanguage(String language) {
this.language = language;
}

@JsonProperty("languageId")
public Long getLanguageId() {
return languageId;
}

@JsonProperty("languageId")
public void setLanguageId(Long languageId) {
this.languageId = languageId;
}

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

@JsonProperty("previousviewminutes")
public Object getPreviousviewminutes() {
return previousviewminutes;
}

@JsonProperty("previousviewminutes")
public void setPreviousviewminutes(Object previousviewminutes) {
this.previousviewminutes = previousviewminutes;
}

@JsonProperty("previousstreamedminutes")
public Object getPreviousstreamedminutes() {
return previousstreamedminutes;
}

@JsonProperty("previousstreamedminutes")
public void setPreviousstreamedminutes(Object previousstreamedminutes) {
this.previousstreamedminutes = previousstreamedminutes;
}

@JsonProperty("previousmaxviewers")
public Object getPreviousmaxviewers() {
return previousmaxviewers;
}

@JsonProperty("previousmaxviewers")
public void setPreviousmaxviewers(Object previousmaxviewers) {
this.previousmaxviewers = previousmaxviewers;
}

@JsonProperty("previousavgviewers")
public Object getPreviousavgviewers() {
return previousavgviewers;
}

@JsonProperty("previousavgviewers")
public void setPreviousavgviewers(Object previousavgviewers) {
this.previousavgviewers = previousavgviewers;
}

@JsonProperty("previousfollowergain")
public Object getPreviousfollowergain() {
return previousfollowergain;
}

@JsonProperty("previousfollowergain")
public void setPreviousfollowergain(Object previousfollowergain) {
this.previousfollowergain = previousfollowergain;
}

@JsonProperty("previousviewsgained")
public Object getPreviousviewsgained() {
return previousviewsgained;
}

@JsonProperty("previousviewsgained")
public void setPreviousviewsgained(Object previousviewsgained) {
this.previousviewsgained = previousviewsgained;
}

@JsonProperty("gamesPlayed")
public String getGamesPlayed() {
return gamesPlayed;
}

@JsonProperty("gamesPlayed")
public void setGamesPlayed(String gamesPlayed) {
this.gamesPlayed = gamesPlayed;
}

@JsonProperty("id")
public Long getId() {
return id;
}

@JsonProperty("id")
public void setId(Long id) {
this.id = id;
}

@JsonProperty("logo")
public String getLogo() {
return logo;
}

@JsonProperty("logo")
public void setLogo(String logo) {
this.logo = logo;
}

@JsonProperty("twitchurl")
public String getTwitchurl() {
return twitchurl;
}

@JsonProperty("twitchurl")
public void setTwitchurl(String twitchurl) {
this.twitchurl = twitchurl;
}

@JsonProperty("url")
public String getUrl() {
return url;
}

@JsonProperty("url")
public void setUrl(String url) {
this.url = url;
}

@JsonProperty("displayname")
public String getDisplayname() {
return displayname;
}

@JsonProperty("displayname")
public void setDisplayname(String displayname) {
this.displayname = displayname;
}

@JsonProperty("baseOnly")
public Boolean getBaseOnly() {
return baseOnly;
}

@JsonProperty("baseOnly")
public void setBaseOnly(Boolean baseOnly) {
this.baseOnly = baseOnly;
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