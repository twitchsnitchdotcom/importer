package com.twitchsnitch.importer.dto.sully.teams;

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
"watchtime",
"maxviewers",
"avgviewers",
"members",
"maxchannels",
"avgchannels",
"id",
"name",
"logo",
"twitchurl",
"baseOnly"
})
@Generated("jsonschema2pojo")
public class TeamsDatum {

@JsonProperty("rownum")
private Long rownum;
@JsonProperty("streamtime")
private Long streamtime;
@JsonProperty("watchtime")
private Long watchtime;
@JsonProperty("maxviewers")
private Long maxviewers;
@JsonProperty("avgviewers")
private Long avgviewers;
@JsonProperty("members")
private Long members;
@JsonProperty("maxchannels")
private Long maxchannels;
@JsonProperty("avgchannels")
private Long avgchannels;
@JsonProperty("id")
private Long id;
@JsonProperty("name")
private String name;
@JsonProperty("logo")
private String logo;
@JsonProperty("twitchurl")
private String twitchurl;
@JsonProperty("baseOnly")
private Boolean baseOnly;
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

@JsonProperty("watchtime")
public Long getWatchtime() {
return watchtime;
}

@JsonProperty("watchtime")
public void setWatchtime(Long watchtime) {
this.watchtime = watchtime;
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

@JsonProperty("members")
public Long getMembers() {
return members;
}

@JsonProperty("members")
public void setMembers(Long members) {
this.members = members;
}

@JsonProperty("maxchannels")
public Long getMaxchannels() {
return maxchannels;
}

@JsonProperty("maxchannels")
public void setMaxchannels(Long maxchannels) {
this.maxchannels = maxchannels;
}

@JsonProperty("avgchannels")
public Long getAvgchannels() {
return avgchannels;
}

@JsonProperty("avgchannels")
public void setAvgchannels(Long avgchannels) {
this.avgchannels = avgchannels;
}

@JsonProperty("id")
public Long getId() {
return id;
}

@JsonProperty("id")
public void setId(Long id) {
this.id = id;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
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