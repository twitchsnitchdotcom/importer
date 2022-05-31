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
"label",
"data",
"fill",
"pointRadius",
"pointHoverRadius",
"showLine",
"spanGaps",
"backgroundColor",
"pointBackgroundColor",
"borderColor",
"borderWidth",
"cubicInterpolationMode",
"yAxisID"
})
@Generated("jsonschema2pojo")
public class Dataset {

@JsonProperty("label")
private String label;
@JsonProperty("data")
private List<Object> data = null;
@JsonProperty("fill")
private Boolean fill;
@JsonProperty("pointRadius")
private Double pointRadius;
@JsonProperty("pointHoverRadius")
private Double pointHoverRadius;
@JsonProperty("showLine")
private Boolean showLine;
@JsonProperty("spanGaps")
private Boolean spanGaps;
@JsonProperty("backgroundColor")
private String backgroundColor;
@JsonProperty("pointBackgroundColor")
private String pointBackgroundColor;
@JsonProperty("borderColor")
private String borderColor;
@JsonProperty("borderWidth")
private Double borderWidth;
@JsonProperty("cubicInterpolationMode")
private String cubicInterpolationMode;
@JsonProperty("yAxisID")
private String yAxisID;
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
public List<Object> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<Object> data) {
this.data = data;
}

@JsonProperty("fill")
public Boolean getFill() {
return fill;
}

@JsonProperty("fill")
public void setFill(Boolean fill) {
this.fill = fill;
}

@JsonProperty("pointRadius")
public Double getPointRadius() {
return pointRadius;
}

@JsonProperty("pointRadius")
public void setPointRadius(Double pointRadius) {
this.pointRadius = pointRadius;
}

@JsonProperty("pointHoverRadius")
public Double getPointHoverRadius() {
return pointHoverRadius;
}

@JsonProperty("pointHoverRadius")
public void setPointHoverRadius(Double pointHoverRadius) {
this.pointHoverRadius = pointHoverRadius;
}

@JsonProperty("showLine")
public Boolean getShowLine() {
return showLine;
}

@JsonProperty("showLine")
public void setShowLine(Boolean showLine) {
this.showLine = showLine;
}

@JsonProperty("spanGaps")
public Boolean getSpanGaps() {
return spanGaps;
}

@JsonProperty("spanGaps")
public void setSpanGaps(Boolean spanGaps) {
this.spanGaps = spanGaps;
}

@JsonProperty("backgroundColor")
public String getBackgroundColor() {
return backgroundColor;
}

@JsonProperty("backgroundColor")
public void setBackgroundColor(String backgroundColor) {
this.backgroundColor = backgroundColor;
}

@JsonProperty("pointBackgroundColor")
public String getPointBackgroundColor() {
return pointBackgroundColor;
}

@JsonProperty("pointBackgroundColor")
public void setPointBackgroundColor(String pointBackgroundColor) {
this.pointBackgroundColor = pointBackgroundColor;
}

@JsonProperty("borderColor")
public String getBorderColor() {
return borderColor;
}

@JsonProperty("borderColor")
public void setBorderColor(String borderColor) {
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

@JsonProperty("cubicInterpolationMode")
public String getCubicInterpolationMode() {
return cubicInterpolationMode;
}

@JsonProperty("cubicInterpolationMode")
public void setCubicInterpolationMode(String cubicInterpolationMode) {
this.cubicInterpolationMode = cubicInterpolationMode;
}

@JsonProperty("yAxisID")
public String getyAxisID() {
return yAxisID;
}

@JsonProperty("yAxisID")
public void setyAxisID(String yAxisID) {
this.yAxisID = yAxisID;
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