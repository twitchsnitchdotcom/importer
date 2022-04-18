package com.twitchsnitch.importer.dto.sully.timezone;

import java.util.HashMap;
import java.util.List;
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
"zones",
"selected"
})
@Generated("jsonschema2pojo")
public class TimeZoneDTO {

@JsonProperty("zones")
private List<Zone> zones = null;
@JsonProperty("selected")
private String selected;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("zones")
public List<Zone> getZones() {
return zones;
}

@JsonProperty("zones")
public void setZones(List<Zone> zones) {
this.zones = zones;
}

@JsonProperty("selected")
public String getSelected() {
return selected;
}

@JsonProperty("selected")
public void setSelected(String selected) {
this.selected = selected;
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
