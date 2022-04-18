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
"fontSize",
"minRotation",
"maxRotation"
})
@Generated("jsonschema2pojo")
public class Ticks {

@JsonProperty("fontSize")
private Long fontSize;
@JsonProperty("minRotation")
private Long minRotation;
@JsonProperty("maxRotation")
private Long maxRotation;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("fontSize")
public Long getFontSize() {
return fontSize;
}

@JsonProperty("fontSize")
public void setFontSize(Long fontSize) {
this.fontSize = fontSize;
}

@JsonProperty("minRotation")
public Long getMinRotation() {
return minRotation;
}

@JsonProperty("minRotation")
public void setMinRotation(Long minRotation) {
this.minRotation = minRotation;
}

@JsonProperty("maxRotation")
public Long getMaxRotation() {
return maxRotation;
}

@JsonProperty("maxRotation")
public void setMaxRotation(Long maxRotation) {
this.maxRotation = maxRotation;
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