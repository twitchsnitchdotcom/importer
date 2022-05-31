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
"hour",
"day",
"week"
})
@Generated("jsonschema2pojo")
public class DisplayFormats {

@JsonProperty("hour")
private String hour;
@JsonProperty("day")
private String day;
@JsonProperty("week")
private String week;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("hour")
public String getHour() {
return hour;
}

@JsonProperty("hour")
public void setHour(String hour) {
this.hour = hour;
}

@JsonProperty("day")
public String getDay() {
return day;
}

@JsonProperty("day")
public void setDay(String day) {
this.day = day;
}

@JsonProperty("week")
public String getWeek() {
return week;
}

@JsonProperty("week")
public void setWeek(String week) {
this.week = week;
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