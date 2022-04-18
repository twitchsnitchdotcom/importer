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
"mode",
"intersect",
"bodyFontSize",
"titleFontSize",
"position",
"caretPadding"
})
@Generated("jsonschema2pojo")
public class Tooltips {

@JsonProperty("mode")
private String mode;
@JsonProperty("intersect")
private Boolean intersect;
@JsonProperty("bodyFontSize")
private Long bodyFontSize;
@JsonProperty("titleFontSize")
private Long titleFontSize;
@JsonProperty("position")
private String position;
@JsonProperty("caretPadding")
private Long caretPadding;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("mode")
public String getMode() {
return mode;
}

@JsonProperty("mode")
public void setMode(String mode) {
this.mode = mode;
}

@JsonProperty("intersect")
public Boolean getIntersect() {
return intersect;
}

@JsonProperty("intersect")
public void setIntersect(Boolean intersect) {
this.intersect = intersect;
}

@JsonProperty("bodyFontSize")
public Long getBodyFontSize() {
return bodyFontSize;
}

@JsonProperty("bodyFontSize")
public void setBodyFontSize(Long bodyFontSize) {
this.bodyFontSize = bodyFontSize;
}

@JsonProperty("titleFontSize")
public Long getTitleFontSize() {
return titleFontSize;
}

@JsonProperty("titleFontSize")
public void setTitleFontSize(Long titleFontSize) {
this.titleFontSize = titleFontSize;
}

@JsonProperty("position")
public String getPosition() {
return position;
}

@JsonProperty("position")
public void setPosition(String position) {
this.position = position;
}

@JsonProperty("caretPadding")
public Long getCaretPadding() {
return caretPadding;
}

@JsonProperty("caretPadding")
public void setCaretPadding(Long caretPadding) {
this.caretPadding = caretPadding;
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