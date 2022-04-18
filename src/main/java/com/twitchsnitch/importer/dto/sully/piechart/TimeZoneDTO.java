package com.twitchsnitch.importer.dto.sully.piechart;

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
"type",
"options",
"data",
"custom"
})
@Generated("jsonschema2pojo")
public class TimeZoneDTO {

@JsonProperty("type")
private String type;
@JsonProperty("options")
private Options options;
@JsonProperty("data")
private Data data;
@JsonProperty("custom")
private Custom custom;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("options")
public Options getOptions() {
return options;
}

@JsonProperty("options")
public void setOptions(Options options) {
this.options = options;
}

@JsonProperty("data")
public Data getData() {
return data;
}

@JsonProperty("data")
public void setData(Data data) {
this.data = data;
}

@JsonProperty("custom")
public Custom getCustom() {
return custom;
}

@JsonProperty("custom")
public void setCustom(Custom custom) {
this.custom = custom;
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