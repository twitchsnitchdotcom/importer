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
"label",
"data",
"borderColor",
"borderWidth",
"backgroundColor",
"hoverBackgroundColor",
"hoverBorderColor"
})
@Generated("jsonschema2pojo")
public class Dataset {

@JsonProperty("label")
private String label;
@JsonProperty("data")
private List<Double> data = null;
@JsonProperty("borderColor")
private List<String> borderColor = null;
@JsonProperty("borderWidth")
private Double borderWidth;
@JsonProperty("backgroundColor")
private List<String> backgroundColor = null;
@JsonProperty("hoverBackgroundColor")
private List<String> hoverBackgroundColor = null;
@JsonProperty("hoverBorderColor")
private List<String> hoverBorderColor = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("label")
public String getLabel() {
return label;
}

@JsonProperty("label")
public void setLabel(String label) {
this.label = label;
}

@JsonProperty("data")
public List<Double> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<Double> data) {
this.data = data;
}

@JsonProperty("borderColor")
public List<String> getBorderColor() {
return borderColor;
}

@JsonProperty("borderColor")
public void setBorderColor(List<String> borderColor) {
this.borderColor = borderColor;
}

@JsonProperty("borderWidth")
public Double getBorderWidth() {
return borderWidth;
}

@JsonProperty("borderWidth")
public void setBorderWidth(Double borderWidth) {
this.borderWidth = borderWidth;
}

@JsonProperty("backgroundColor")
public List<String> getBackgroundColor() {
return backgroundColor;
}

@JsonProperty("backgroundColor")
public void setBackgroundColor(List<String> backgroundColor) {
this.backgroundColor = backgroundColor;
}

@JsonProperty("hoverBackgroundColor")
public List<String> getHoverBackgroundColor() {
return hoverBackgroundColor;
}

@JsonProperty("hoverBackgroundColor")
public void setHoverBackgroundColor(List<String> hoverBackgroundColor) {
this.hoverBackgroundColor = hoverBackgroundColor;
}

@JsonProperty("hoverBorderColor")
public List<String> getHoverBorderColor() {
return hoverBorderColor;
}

@JsonProperty("hoverBorderColor")
public void setHoverBorderColor(List<String> hoverBorderColor) {
this.hoverBorderColor = hoverBorderColor;
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