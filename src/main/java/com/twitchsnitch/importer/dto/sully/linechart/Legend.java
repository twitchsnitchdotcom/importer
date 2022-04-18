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
"position"
})
@Generated("jsonschema2pojo")
public class Legend {

@JsonProperty("display")
private Boolean display;
@JsonProperty("position")
private String position;
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

@JsonProperty("position")
public String getPosition() {
return position;
}

@JsonProperty("position")
public void setPosition(String position) {
this.position = position;
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