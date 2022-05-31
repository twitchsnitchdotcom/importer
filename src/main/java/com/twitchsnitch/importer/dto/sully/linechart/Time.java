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
"displayFormats",
"unit",
"tooltipFormat"
})
@Generated("jsonschema2pojo")
public class Time {

@JsonProperty("displayFormats")
private DisplayFormats displayFormats;
@JsonProperty("unit")
private String unit;
@JsonProperty("tooltipFormat")
private String tooltipFormat;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("displayFormats")
public DisplayFormats getDisplayFormats() {
return displayFormats;
}

@JsonProperty("displayFormats")
public void setDisplayFormats(DisplayFormats displayFormats) {
this.displayFormats = displayFormats;
}

@JsonProperty("unit")
public String getUnit() {
return unit;
}

@JsonProperty("unit")
public void setUnit(String unit) {
this.unit = unit;
}

@JsonProperty("tooltipFormat")
public String getTooltipFormat() {
return tooltipFormat;
}

@JsonProperty("tooltipFormat")
public void setTooltipFormat(String tooltipFormat) {
this.tooltipFormat = tooltipFormat;
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