package com.twitchsnitch.importer.dto.sully.games;

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
"viewminutes",
"streamedminutes",
"maxchannels",
"uniquechannels",
"avgchannels",
"maxviewers",
"avgviewers",
"avgratio",
"rownum",
"changeuniquechannels",
"changeviewerminutes",
"changestreamedminutes",
"changeaverageviewers",
"changemaxviewers",
"changeaveragechannels",
"changemaxchannels",
"changeaverageratio",
"viewsgained",
"followersgained",
"previousviewminutes",
"previousstreamedminutes",
"previousmaxchannels",
"previousuniquechannels",
"previousavgchannels",
"previousmaxviewers",
"previousavgviewers",
"previousavgratio",
"previousvphs",
"fphs",
"vphs",
"previouschangeuniquechannels",
"previouschangeviewerminutes",
"previouschangestreamedminutes",
"previouschangeaverageviewers",
"previouschangemaxviewers",
"previouschangeaveragechannels",
"previouschangemaxchannels",
"previouschangeaverageratio",
"id",
"name",
"logo",
"url",
"baseOnly"
})
@Generated("jsonschema2pojo")
public class GamesDatum {

@JsonProperty("viewminutes")
private Long viewminutes;
@JsonProperty("streamedminutes")
private Long streamedminutes;
@JsonProperty("maxchannels")
private Long maxchannels;
@JsonProperty("uniquechannels")
private Long uniquechannels;
@JsonProperty("avgchannels")
private Long avgchannels;
@JsonProperty("maxviewers")
private Long maxviewers;
@JsonProperty("avgviewers")
private Long avgviewers;
@JsonProperty("avgratio")
private Double avgratio;
@JsonProperty("rownum")
private Long rownum;
@JsonProperty("changeuniquechannels")
private Object changeuniquechannels;
@JsonProperty("changeviewerminutes")
private Object changeviewerminutes;
@JsonProperty("changestreamedminutes")
private Object changestreamedminutes;
@JsonProperty("changeaverageviewers")
private Object changeaverageviewers;
@JsonProperty("changemaxviewers")
private Object changemaxviewers;
@JsonProperty("changeaveragechannels")
private Object changeaveragechannels;
@JsonProperty("changemaxchannels")
private Object changemaxchannels;
@JsonProperty("changeaverageratio")
private Object changeaverageratio;
@JsonProperty("viewsgained")
private Long viewsgained;
@JsonProperty("followersgained")
private Long followersgained;
@JsonProperty("previousviewminutes")
private Long previousviewminutes;
@JsonProperty("previousstreamedminutes")
private Long previousstreamedminutes;
@JsonProperty("previousmaxchannels")
private Long previousmaxchannels;
@JsonProperty("previousuniquechannels")
private Long previousuniquechannels;
@JsonProperty("previousavgchannels")
private Long previousavgchannels;
@JsonProperty("previousmaxviewers")
private Long previousmaxviewers;
@JsonProperty("previousavgviewers")
private Long previousavgviewers;
@JsonProperty("previousavgratio")
private Double previousavgratio;
@JsonProperty("previousvphs")
private Double previousvphs;
@JsonProperty("fphs")
private Double fphs;
@JsonProperty("vphs")
private Double vphs;
@JsonProperty("previouschangeuniquechannels")
private Object previouschangeuniquechannels;
@JsonProperty("previouschangeviewerminutes")
private Object previouschangeviewerminutes;
@JsonProperty("previouschangestreamedminutes")
private Object previouschangestreamedminutes;
@JsonProperty("previouschangeaverageviewers")
private Object previouschangeaverageviewers;
@JsonProperty("previouschangemaxviewers")
private Object previouschangemaxviewers;
@JsonProperty("previouschangeaveragechannels")
private Object previouschangeaveragechannels;
@JsonProperty("previouschangemaxchannels")
private Object previouschangemaxchannels;
@JsonProperty("previouschangeaverageratio")
private Object previouschangeaverageratio;
@JsonProperty("id")
private Long id;
@JsonProperty("name")
private String name;
@JsonProperty("logo")
private String logo;
@JsonProperty("url")
private String url;
@JsonProperty("baseOnly")
private Boolean baseOnly;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

@JsonProperty("maxchannels")
public Long getMaxchannels() {
return maxchannels;
}

@JsonProperty("maxchannels")
public void setMaxchannels(Long maxchannels) {
this.maxchannels = maxchannels;
}

@JsonProperty("uniquechannels")
public Long getUniquechannels() {
return uniquechannels;
}

@JsonProperty("uniquechannels")
public void setUniquechannels(Long uniquechannels) {
this.uniquechannels = uniquechannels;
}

@JsonProperty("avgchannels")
public Long getAvgchannels() {
return avgchannels;
}

@JsonProperty("avgchannels")
public void setAvgchannels(Long avgchannels) {
this.avgchannels = avgchannels;
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

@JsonProperty("avgratio")
public Double getAvgratio() {
return avgratio;
}

@JsonProperty("avgratio")
public void setAvgratio(Double avgratio) {
this.avgratio = avgratio;
}

@JsonProperty("rownum")
public Long getRownum() {
return rownum;
}

@JsonProperty("rownum")
public void setRownum(Long rownum) {
this.rownum = rownum;
}

@JsonProperty("changeuniquechannels")
public Object getChangeuniquechannels() {
return changeuniquechannels;
}

@JsonProperty("changeuniquechannels")
public void setChangeuniquechannels(Object changeuniquechannels) {
this.changeuniquechannels = changeuniquechannels;
}

@JsonProperty("changeviewerminutes")
public Object getChangeviewerminutes() {
return changeviewerminutes;
}

@JsonProperty("changeviewerminutes")
public void setChangeviewerminutes(Object changeviewerminutes) {
this.changeviewerminutes = changeviewerminutes;
}

@JsonProperty("changestreamedminutes")
public Object getChangestreamedminutes() {
return changestreamedminutes;
}

@JsonProperty("changestreamedminutes")
public void setChangestreamedminutes(Object changestreamedminutes) {
this.changestreamedminutes = changestreamedminutes;
}

@JsonProperty("changeaverageviewers")
public Object getChangeaverageviewers() {
return changeaverageviewers;
}

@JsonProperty("changeaverageviewers")
public void setChangeaverageviewers(Object changeaverageviewers) {
this.changeaverageviewers = changeaverageviewers;
}

@JsonProperty("changemaxviewers")
public Object getChangemaxviewers() {
return changemaxviewers;
}

@JsonProperty("changemaxviewers")
public void setChangemaxviewers(Object changemaxviewers) {
this.changemaxviewers = changemaxviewers;
}

@JsonProperty("changeaveragechannels")
public Object getChangeaveragechannels() {
return changeaveragechannels;
}

@JsonProperty("changeaveragechannels")
public void setChangeaveragechannels(Object changeaveragechannels) {
this.changeaveragechannels = changeaveragechannels;
}

@JsonProperty("changemaxchannels")
public Object getChangemaxchannels() {
return changemaxchannels;
}

@JsonProperty("changemaxchannels")
public void setChangemaxchannels(Object changemaxchannels) {
this.changemaxchannels = changemaxchannels;
}

@JsonProperty("changeaverageratio")
public Object getChangeaverageratio() {
return changeaverageratio;
}

@JsonProperty("changeaverageratio")
public void setChangeaverageratio(Object changeaverageratio) {
this.changeaverageratio = changeaverageratio;
}

@JsonProperty("viewsgained")
public Long getViewsgained() {
return viewsgained;
}

@JsonProperty("viewsgained")
public void setViewsgained(Long viewsgained) {
this.viewsgained = viewsgained;
}

@JsonProperty("followersgained")
public Long getFollowersgained() {
return followersgained;
}

@JsonProperty("followersgained")
public void setFollowersgained(Long followersgained) {
this.followersgained = followersgained;
}

@JsonProperty("previousviewminutes")
public Long getPreviousviewminutes() {
return previousviewminutes;
}

@JsonProperty("previousviewminutes")
public void setPreviousviewminutes(Long previousviewminutes) {
this.previousviewminutes = previousviewminutes;
}

@JsonProperty("previousstreamedminutes")
public Long getPreviousstreamedminutes() {
return previousstreamedminutes;
}

@JsonProperty("previousstreamedminutes")
public void setPreviousstreamedminutes(Long previousstreamedminutes) {
this.previousstreamedminutes = previousstreamedminutes;
}

@JsonProperty("previousmaxchannels")
public Long getPreviousmaxchannels() {
return previousmaxchannels;
}

@JsonProperty("previousmaxchannels")
public void setPreviousmaxchannels(Long previousmaxchannels) {
this.previousmaxchannels = previousmaxchannels;
}

@JsonProperty("previousuniquechannels")
public Long getPreviousuniquechannels() {
return previousuniquechannels;
}

@JsonProperty("previousuniquechannels")
public void setPreviousuniquechannels(Long previousuniquechannels) {
this.previousuniquechannels = previousuniquechannels;
}

@JsonProperty("previousavgchannels")
public Long getPreviousavgchannels() {
return previousavgchannels;
}

@JsonProperty("previousavgchannels")
public void setPreviousavgchannels(Long previousavgchannels) {
this.previousavgchannels = previousavgchannels;
}

@JsonProperty("previousmaxviewers")
public Long getPreviousmaxviewers() {
return previousmaxviewers;
}

@JsonProperty("previousmaxviewers")
public void setPreviousmaxviewers(Long previousmaxviewers) {
this.previousmaxviewers = previousmaxviewers;
}

@JsonProperty("previousavgviewers")
public Long getPreviousavgviewers() {
return previousavgviewers;
}

@JsonProperty("previousavgviewers")
public void setPreviousavgviewers(Long previousavgviewers) {
this.previousavgviewers = previousavgviewers;
}

@JsonProperty("previousavgratio")
public Double getPreviousavgratio() {
return previousavgratio;
}

@JsonProperty("previousavgratio")
public void setPreviousavgratio(Double previousavgratio) {
this.previousavgratio = previousavgratio;
}

@JsonProperty("previousvphs")
public Double getPreviousvphs() {
return previousvphs;
}

@JsonProperty("previousvphs")
public void setPreviousvphs(Double previousvphs) {
this.previousvphs = previousvphs;
}

@JsonProperty("fphs")
public Double getFphs() {
return fphs;
}

@JsonProperty("fphs")
public void setFphs(Double fphs) {
this.fphs = fphs;
}

@JsonProperty("vphs")
public Double getVphs() {
return vphs;
}

@JsonProperty("vphs")
public void setVphs(Double vphs) {
this.vphs = vphs;
}

@JsonProperty("previouschangeuniquechannels")
public Object getPreviouschangeuniquechannels() {
return previouschangeuniquechannels;
}

@JsonProperty("previouschangeuniquechannels")
public void setPreviouschangeuniquechannels(Object previouschangeuniquechannels) {
this.previouschangeuniquechannels = previouschangeuniquechannels;
}

@JsonProperty("previouschangeviewerminutes")
public Object getPreviouschangeviewerminutes() {
return previouschangeviewerminutes;
}

@JsonProperty("previouschangeviewerminutes")
public void setPreviouschangeviewerminutes(Object previouschangeviewerminutes) {
this.previouschangeviewerminutes = previouschangeviewerminutes;
}

@JsonProperty("previouschangestreamedminutes")
public Object getPreviouschangestreamedminutes() {
return previouschangestreamedminutes;
}

@JsonProperty("previouschangestreamedminutes")
public void setPreviouschangestreamedminutes(Object previouschangestreamedminutes) {
this.previouschangestreamedminutes = previouschangestreamedminutes;
}

@JsonProperty("previouschangeaverageviewers")
public Object getPreviouschangeaverageviewers() {
return previouschangeaverageviewers;
}

@JsonProperty("previouschangeaverageviewers")
public void setPreviouschangeaverageviewers(Object previouschangeaverageviewers) {
this.previouschangeaverageviewers = previouschangeaverageviewers;
}

@JsonProperty("previouschangemaxviewers")
public Object getPreviouschangemaxviewers() {
return previouschangemaxviewers;
}

@JsonProperty("previouschangemaxviewers")
public void setPreviouschangemaxviewers(Object previouschangemaxviewers) {
this.previouschangemaxviewers = previouschangemaxviewers;
}

@JsonProperty("previouschangeaveragechannels")
public Object getPreviouschangeaveragechannels() {
return previouschangeaveragechannels;
}

@JsonProperty("previouschangeaveragechannels")
public void setPreviouschangeaveragechannels(Object previouschangeaveragechannels) {
this.previouschangeaveragechannels = previouschangeaveragechannels;
}

@JsonProperty("previouschangemaxchannels")
public Object getPreviouschangemaxchannels() {
return previouschangemaxchannels;
}

@JsonProperty("previouschangemaxchannels")
public void setPreviouschangemaxchannels(Object previouschangemaxchannels) {
this.previouschangemaxchannels = previouschangemaxchannels;
}

@JsonProperty("previouschangeaverageratio")
public Object getPreviouschangeaverageratio() {
return previouschangeaverageratio;
}

@JsonProperty("previouschangeaverageratio")
public void setPreviouschangeaverageratio(Object previouschangeaverageratio) {
this.previouschangeaverageratio = previouschangeaverageratio;
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

@JsonProperty("url")
public String getUrl() {
return url;
}

@JsonProperty("url")
public void setUrl(String url) {
this.url = url;
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
