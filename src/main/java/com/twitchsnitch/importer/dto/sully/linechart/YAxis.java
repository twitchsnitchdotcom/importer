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
"display",
"ticks",
"id",
"distribution"
})
@Generated("jsonschema2pojo")
public class YAxis {

@JsonProperty("display")
private Boolean display;
@JsonProperty("ticks")
private Ticks__1 ticks;
@JsonProperty("id")
private String id;
@JsonProperty("distribution")
private String distribution;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("display")
public Boolean getDisplay() {
return display;
}

@JsonProperty("display")
public void setDisplay(Boolean display) {
this.display = display;
}

@JsonProperty("ticks")
public Ticks__1 getTicks() {
return ticks;
}

@JsonProperty("ticks")
public void setTicks(Ticks__1 ticks) {
this.ticks = ticks;
}

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("distribution")
public String getDistribution() {
return distribution;
}

@JsonProperty("distribution")
public void setDistribution(String distribution) {
this.distribution = distribution;
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