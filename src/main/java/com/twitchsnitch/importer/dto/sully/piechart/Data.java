package com.twitchsnitch.importer.dto.sully.piechart;

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
"labels",
"xLabels",
"yLabels",
"datasets"
})
@Generated("jsonschema2pojo")
public class Data {

@JsonProperty("labels")
private List<String> labels = null;
@JsonProperty("xLabels")
private Object xLabels;
@JsonProperty("yLabels")
private Object yLabels;
@JsonProperty("datasets")
private List<Dataset> datasets = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("labels")
public List<String> getLabels() {
return labels;
}

@JsonProperty("labels")
public void setLabels(List<String> labels) {
this.labels = labels;
}

@JsonProperty("xLabels")
public Object getxLabels() {
return xLabels;
}

@JsonProperty("xLabels")
public void setxLabels(Object xLabels) {
this.xLabels = xLabels;
}

@JsonProperty("yLabels")
public Object getyLabels() {
return yLabels;
}

@JsonProperty("yLabels")
public void setyLabels(Object yLabels) {
this.yLabels = yLabels;
}

@JsonProperty("datasets")
public List<Dataset> getDatasets() {
return datasets;
}

@JsonProperty("datasets")
public void setDatasets(List<Dataset> datasets) {
this.datasets = datasets;
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