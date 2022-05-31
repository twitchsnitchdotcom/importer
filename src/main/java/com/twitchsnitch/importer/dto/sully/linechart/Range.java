package com.twitchsnitch.importer.dto.sully.linechart;

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
"dayRange",
"dayRangeOffset",
"isNamedRange",
"namedRange",
"month",
"year",
"range",
"startDayId",
"endDayId"
})
@Generated("jsonschema2pojo")
public class Range {

@JsonProperty("dayRange")
private Long dayRange;
@JsonProperty("dayRangeOffset")
private Long dayRangeOffset;
@JsonProperty("isNamedRange")
private Boolean isNamedRange;
@JsonProperty("namedRange")
private String namedRange;
@JsonProperty("month")
private Long month;
@JsonProperty("year")
private Long year;
@JsonProperty("range")
private String range;
@JsonProperty("startDayId")
private Long startDayId;
@JsonProperty("endDayId")
private Long endDayId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("dayRange")
public Long getDayRange() {
return dayRange;
}

@JsonProperty("dayRange")
public void setDayRange(Long dayRange) {
this.dayRange = dayRange;
}

@JsonProperty("dayRangeOffset")
public Long getDayRangeOffset() {
return dayRangeOffset;
}

@JsonProperty("dayRangeOffset")
public void setDayRangeOffset(Long dayRangeOffset) {
this.dayRangeOffset = dayRangeOffset;
}

@JsonProperty("isNamedRange")
public Boolean getIsNamedRange() {
return isNamedRange;
}

@JsonProperty("isNamedRange")
public void setIsNamedRange(Boolean isNamedRange) {
this.isNamedRange = isNamedRange;
}

@JsonProperty("namedRange")
public String getNamedRange() {
return namedRange;
}

@JsonProperty("namedRange")
public void setNamedRange(String namedRange) {
this.namedRange = namedRange;
}

@JsonProperty("month")
public Long getMonth() {
return month;
}

@JsonProperty("month")
public void setMonth(Long month) {
this.month = month;
}

@JsonProperty("year")
public Long getYear() {
return year;
}

@JsonProperty("year")
public void setYear(Long year) {
this.year = year;
}

@JsonProperty("range")
public String getRange() {
return range;
}

@JsonProperty("range")
public void setRange(String range) {
this.range = range;
}

@JsonProperty("startDayId")
public Long getStartDayId() {
return startDayId;
}

@JsonProperty("startDayId")
public void setStartDayId(Long startDayId) {
this.startDayId = startDayId;
}

@JsonProperty("endDayId")
public Long getEndDayId() {
return endDayId;
}

@JsonProperty("endDayId")
public void setEndDayId(Long endDayId) {
this.endDayId = endDayId;
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