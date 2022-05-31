package com.twitchsnitch.importer.dto.sully.linechart;

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
"xAxes",
"yAxes"
})
@Generated("jsonschema2pojo")
public class Scales {

@JsonProperty("xAxes")
private List<XAxis> xAxes = null;
@JsonProperty("yAxes")
private List<YAxis> yAxes = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("xAxes")
public List<XAxis> getxAxes() {
return xAxes;
}

@JsonProperty("xAxes")
public void setxAxes(List<XAxis> xAxes) {
this.xAxes = xAxes;
}

@JsonProperty("yAxes")
public List<YAxis> getyAxes() {
return yAxes;
}

@JsonProperty("yAxes")
public void setyAxes(List<YAxis> yAxes) {
this.yAxes = yAxes;
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