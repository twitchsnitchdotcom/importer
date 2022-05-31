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
"text",
"colour",
"indexFrom",
"indexTo"
})
@Generated("jsonschema2pojo")
public class LabelIndex {

@JsonProperty("text")
private Object text;
@JsonProperty("colour")
private Object colour;
@JsonProperty("indexFrom")
private Long indexFrom;
@JsonProperty("indexTo")
private Long indexTo;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("text")
public Object getText() {
return text;
}

@JsonProperty("text")
public void setText(Object text) {
this.text = text;
}

@JsonProperty("colour")
public Object getColour() {
return colour;
}

@JsonProperty("colour")
public void setColour(Object colour) {
this.colour = colour;
}

@JsonProperty("indexFrom")
public Long getIndexFrom() {
return indexFrom;
}

@JsonProperty("indexFrom")
public void setIndexFrom(Long indexFrom) {
this.indexFrom = indexFrom;
}

@JsonProperty("indexTo")
public Long getIndexTo() {
return indexTo;
}

@JsonProperty("indexTo")
public void setIndexTo(Long indexTo) {
this.indexTo = indexTo;
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