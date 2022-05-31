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
"averageviewers",
"averagechannels",
"peraverageviewers",
"peraveragechannels",
"perrecentavgviewers",
"perpast1avgviewers",
"perpast2avgviewers",
"perpast3avgviewers",
"perrecentavgchannels",
"perpast1avgchannels",
"perpast2avgchannels",
"perpast3avgchannels",
"channelsabove",
"channelssame",
"channelsbelow",
"viewersabove",
"viewerssame",
"viewersbelow",
"estposition",
"viewerratio",
"viewerratiosameblow",
"gametrendchannelsrecent",
"gametrendchannels3day",
"gametrendviewersrecent",
"gametrendviewers3day",
"twitchgametrendchannelsrecent",
"twitchgametrendchannels3day",
"twitchgametrendviewersrecent",
"twitchgametrendviewers3day",
"id",
"name",
"logo",
"url",
"baseOnly"
})
@Generated("jsonschema2pojo")
public class ChannelGamePickerDatum {

@JsonProperty("rownum")
private Long rownum;
@JsonProperty("averageviewers")
private Long averageviewers;
@JsonProperty("averagechannels")
private Long averagechannels;
@JsonProperty("peraverageviewers")
private Double peraverageviewers;
@JsonProperty("peraveragechannels")
private Double peraveragechannels;
@JsonProperty("perrecentavgviewers")
private Double perrecentavgviewers;
@JsonProperty("perpast1avgviewers")
private Double perpast1avgviewers;
@JsonProperty("perpast2avgviewers")
private Double perpast2avgviewers;
@JsonProperty("perpast3avgviewers")
private Double perpast3avgviewers;
@JsonProperty("perrecentavgchannels")
private Double perrecentavgchannels;
@JsonProperty("perpast1avgchannels")
private Double perpast1avgchannels;
@JsonProperty("perpast2avgchannels")
private Double perpast2avgchannels;
@JsonProperty("perpast3avgchannels")
private Double perpast3avgchannels;
@JsonProperty("channelsabove")
private Long channelsabove;
@JsonProperty("channelssame")
private Long channelssame;
@JsonProperty("channelsbelow")
private Long channelsbelow;
@JsonProperty("viewersabove")
private Long viewersabove;
@JsonProperty("viewerssame")
private Long viewerssame;
@JsonProperty("viewersbelow")
private Long viewersbelow;
@JsonProperty("estposition")
private String estposition;
@JsonProperty("viewerratio")
private Double viewerratio;
@JsonProperty("viewerratiosameblow")
private Double viewerratiosameblow;
@JsonProperty("gametrendchannelsrecent")
private Double gametrendchannelsrecent;
@JsonProperty("gametrendchannels3day")
private Double gametrendchannels3day;
@JsonProperty("gametrendviewersrecent")
private Double gametrendviewersrecent;
@JsonProperty("gametrendviewers3day")
private Double gametrendviewers3day;
@JsonProperty("twitchgametrendchannelsrecent")
private Double twitchgametrendchannelsrecent;
@JsonProperty("twitchgametrendchannels3day")
private Double twitchgametrendchannels3day;
@JsonProperty("twitchgametrendviewersrecent")
private Double twitchgametrendviewersrecent;
@JsonProperty("twitchgametrendviewers3day")
private Double twitchgametrendviewers3day;
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

@JsonProperty("rownum")
public Long getRownum() {
return rownum;
}

@JsonProperty("rownum")
public void setRownum(Long rownum) {
this.rownum = rownum;
}

@JsonProperty("averageviewers")
public Long getAverageviewers() {
return averageviewers;
}

@JsonProperty("averageviewers")
public void setAverageviewers(Long averageviewers) {
this.averageviewers = averageviewers;
}

@JsonProperty("averagechannels")
public Long getAveragechannels() {
return averagechannels;
}

@JsonProperty("averagechannels")
public void setAveragechannels(Long averagechannels) {
this.averagechannels = averagechannels;
}

@JsonProperty("peraverageviewers")
public Double getPeraverageviewers() {
return peraverageviewers;
}

@JsonProperty("peraverageviewers")
public void setPeraverageviewers(Double peraverageviewers) {
this.peraverageviewers = peraverageviewers;
}

@JsonProperty("peraveragechannels")
public Double getPeraveragechannels() {
return peraveragechannels;
}

@JsonProperty("peraveragechannels")
public void setPeraveragechannels(Double peraveragechannels) {
this.peraveragechannels = peraveragechannels;
}

@JsonProperty("perrecentavgviewers")
public Double getPerrecentavgviewers() {
return perrecentavgviewers;
}

@JsonProperty("perrecentavgviewers")
public void setPerrecentavgviewers(Double perrecentavgviewers) {
this.perrecentavgviewers = perrecentavgviewers;
}

@JsonProperty("perpast1avgviewers")
public Double getPerpast1avgviewers() {
return perpast1avgviewers;
}

@JsonProperty("perpast1avgviewers")
public void setPerpast1avgviewers(Double perpast1avgviewers) {
this.perpast1avgviewers = perpast1avgviewers;
}

@JsonProperty("perpast2avgviewers")
public Double getPerpast2avgviewers() {
return perpast2avgviewers;
}

@JsonProperty("perpast2avgviewers")
public void setPerpast2avgviewers(Double perpast2avgviewers) {
this.perpast2avgviewers = perpast2avgviewers;
}

@JsonProperty("perpast3avgviewers")
public Double getPerpast3avgviewers() {
return perpast3avgviewers;
}

@JsonProperty("perpast3avgviewers")
public void setPerpast3avgviewers(Double perpast3avgviewers) {
this.perpast3avgviewers = perpast3avgviewers;
}

@JsonProperty("perrecentavgchannels")
public Double getPerrecentavgchannels() {
return perrecentavgchannels;
}

@JsonProperty("perrecentavgchannels")
public void setPerrecentavgchannels(Double perrecentavgchannels) {
this.perrecentavgchannels = perrecentavgchannels;
}

@JsonProperty("perpast1avgchannels")
public Double getPerpast1avgchannels() {
return perpast1avgchannels;
}

@JsonProperty("perpast1avgchannels")
public void setPerpast1avgchannels(Double perpast1avgchannels) {
this.perpast1avgchannels = perpast1avgchannels;
}

@JsonProperty("perpast2avgchannels")
public Double getPerpast2avgchannels() {
return perpast2avgchannels;
}

@JsonProperty("perpast2avgchannels")
public void setPerpast2avgchannels(Double perpast2avgchannels) {
this.perpast2avgchannels = perpast2avgchannels;
}

@JsonProperty("perpast3avgchannels")
public Double getPerpast3avgchannels() {
return perpast3avgchannels;
}

@JsonProperty("perpast3avgchannels")
public void setPerpast3avgchannels(Double perpast3avgchannels) {
this.perpast3avgchannels = perpast3avgchannels;
}

@JsonProperty("channelsabove")
public Long getChannelsabove() {
return channelsabove;
}

@JsonProperty("channelsabove")
public void setChannelsabove(Long channelsabove) {
this.channelsabove = channelsabove;
}

@JsonProperty("channelssame")
public Long getChannelssame() {
return channelssame;
}

@JsonProperty("channelssame")
public void setChannelssame(Long channelssame) {
this.channelssame = channelssame;
}

@JsonProperty("channelsbelow")
public Long getChannelsbelow() {
return channelsbelow;
}

@JsonProperty("channelsbelow")
public void setChannelsbelow(Long channelsbelow) {
this.channelsbelow = channelsbelow;
}

@JsonProperty("viewersabove")
public Long getViewersabove() {
return viewersabove;
}

@JsonProperty("viewersabove")
public void setViewersabove(Long viewersabove) {
this.viewersabove = viewersabove;
}

@JsonProperty("viewerssame")
public Long getViewerssame() {
return viewerssame;
}

@JsonProperty("viewerssame")
public void setViewerssame(Long viewerssame) {
this.viewerssame = viewerssame;
}

@JsonProperty("viewersbelow")
public Long getViewersbelow() {
return viewersbelow;
}

@JsonProperty("viewersbelow")
public void setViewersbelow(Long viewersbelow) {
this.viewersbelow = viewersbelow;
}

@JsonProperty("estposition")
public String getEstposition() {
return estposition;
}

@JsonProperty("estposition")
public void setEstposition(String estposition) {
this.estposition = estposition;
}

@JsonProperty("viewerratio")
public Double getViewerratio() {
return viewerratio;
}

@JsonProperty("viewerratio")
public void setViewerratio(Double viewerratio) {
this.viewerratio = viewerratio;
}

@JsonProperty("viewerratiosameblow")
public Double getViewerratiosameblow() {
return viewerratiosameblow;
}

@JsonProperty("viewerratiosameblow")
public void setViewerratiosameblow(Double viewerratiosameblow) {
this.viewerratiosameblow = viewerratiosameblow;
}

@JsonProperty("gametrendchannelsrecent")
public Double getGametrendchannelsrecent() {
return gametrendchannelsrecent;
}

@JsonProperty("gametrendchannelsrecent")
public void setGametrendchannelsrecent(Double gametrendchannelsrecent) {
this.gametrendchannelsrecent = gametrendchannelsrecent;
}

@JsonProperty("gametrendchannels3day")
public Double getGametrendchannels3day() {
return gametrendchannels3day;
}

@JsonProperty("gametrendchannels3day")
public void setGametrendchannels3day(Double gametrendchannels3day) {
this.gametrendchannels3day = gametrendchannels3day;
}

@JsonProperty("gametrendviewersrecent")
public Double getGametrendviewersrecent() {
return gametrendviewersrecent;
}

@JsonProperty("gametrendviewersrecent")
public void setGametrendviewersrecent(Double gametrendviewersrecent) {
this.gametrendviewersrecent = gametrendviewersrecent;
}

@JsonProperty("gametrendviewers3day")
public Double getGametrendviewers3day() {
return gametrendviewers3day;
}

@JsonProperty("gametrendviewers3day")
public void setGametrendviewers3day(Double gametrendviewers3day) {
this.gametrendviewers3day = gametrendviewers3day;
}

@JsonProperty("twitchgametrendchannelsrecent")
public Double getTwitchgametrendchannelsrecent() {
return twitchgametrendchannelsrecent;
}

@JsonProperty("twitchgametrendchannelsrecent")
public void setTwitchgametrendchannelsrecent(Double twitchgametrendchannelsrecent) {
this.twitchgametrendchannelsrecent = twitchgametrendchannelsrecent;
}

@JsonProperty("twitchgametrendchannels3day")
public Double getTwitchgametrendchannels3day() {
return twitchgametrendchannels3day;
}

@JsonProperty("twitchgametrendchannels3day")
public void setTwitchgametrendchannels3day(Double twitchgametrendchannels3day) {
this.twitchgametrendchannels3day = twitchgametrendchannels3day;
}

@JsonProperty("twitchgametrendviewersrecent")
public Double getTwitchgametrendviewersrecent() {
return twitchgametrendviewersrecent;
}

@JsonProperty("twitchgametrendviewersrecent")
public void setTwitchgametrendviewersrecent(Double twitchgametrendviewersrecent) {
this.twitchgametrendviewersrecent = twitchgametrendviewersrecent;
}

@JsonProperty("twitchgametrendviewers3day")
public Double getTwitchgametrendviewers3day() {
return twitchgametrendviewers3day;
}

@JsonProperty("twitchgametrendviewers3day")
public void setTwitchgametrendviewers3day(Double twitchgametrendviewers3day) {
this.twitchgametrendviewers3day = twitchgametrendviewers3day;
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