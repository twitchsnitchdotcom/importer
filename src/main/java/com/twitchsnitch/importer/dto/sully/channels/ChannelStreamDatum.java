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
"starttime",
"endtime",
"length",
"viewgain",
"followergain",
"avgviewers",
"maxviewers",
"followersperhour",
"gamesplayed",
"viewsperhour",
"channeldisplayname",
"channellogo",
"channelurl",
"startDateTime",
"streamId",
"streamUrl",
"viewminutes"
})
@Generated("jsonschema2pojo")
public class ChannelStreamDatum {

@JsonProperty("rownum")
private Long rownum;
@JsonProperty("starttime")
private String starttime;
@JsonProperty("endtime")
private String endtime;
@JsonProperty("length")
private Long length;
@JsonProperty("viewgain")
private Long viewgain;
@JsonProperty("followergain")
private Long followergain;
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
@JsonProperty("channeldisplayname")
private String channeldisplayname;
@JsonProperty("channellogo")
private String channellogo;
@JsonProperty("channelurl")
private String channelurl;
@JsonProperty("startDateTime")
private String startDateTime;
@JsonProperty("streamId")
private Long streamId;
@JsonProperty("streamUrl")
private String streamUrl;
@JsonProperty("viewminutes")
private Long viewminutes;
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

@JsonProperty("starttime")
public String getStarttime() {
return starttime;
}

@JsonProperty("starttime")
public void setStarttime(String starttime) {
this.starttime = starttime;
}

@JsonProperty("endtime")
public String getEndtime() {
return endtime;
}

@JsonProperty("endtime")
public void setEndtime(String endtime) {
this.endtime = endtime;
}

@JsonProperty("length")
public Long getLength() {
return length;
}

@JsonProperty("length")
public void setLength(Long length) {
this.length = length;
}

@JsonProperty("viewgain")
public Long getViewgain() {
return viewgain;
}

@JsonProperty("viewgain")
public void setViewgain(Long viewgain) {
this.viewgain = viewgain;
}

@JsonProperty("followergain")
public Long getFollowergain() {
return followergain;
}

@JsonProperty("followergain")
public void setFollowergain(Long followergain) {
this.followergain = followergain;
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

@JsonProperty("channeldisplayname")
public String getChanneldisplayname() {
return channeldisplayname;
}

@JsonProperty("channeldisplayname")
public void setChanneldisplayname(String channeldisplayname) {
this.channeldisplayname = channeldisplayname;
}

@JsonProperty("channellogo")
public String getChannellogo() {
return channellogo;
}

@JsonProperty("channellogo")
public void setChannellogo(String channellogo) {
this.channellogo = channellogo;
}

@JsonProperty("channelurl")
public String getChannelurl() {
return channelurl;
}

@JsonProperty("channelurl")
public void setChannelurl(String channelurl) {
this.channelurl = channelurl;
}

@JsonProperty("startDateTime")
public String getStartDateTime() {
return startDateTime;
}

@JsonProperty("startDateTime")
public void setStartDateTime(String startDateTime) {
this.startDateTime = startDateTime;
}

@JsonProperty("streamId")
public Long getStreamId() {
return streamId;
}

@JsonProperty("streamId")
public void setStreamId(Long streamId) {
this.streamId = streamId;
}

@JsonProperty("streamUrl")
public String getStreamUrl() {
return streamUrl;
}

@JsonProperty("streamUrl")
public void setStreamUrl(String streamUrl) {
this.streamUrl = streamUrl;
}

@JsonProperty("viewminutes")
public Long getViewminutes() {
return viewminutes;
}

@JsonProperty("viewminutes")
public void setViewminutes(Long viewminutes) {
this.viewminutes = viewminutes;
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