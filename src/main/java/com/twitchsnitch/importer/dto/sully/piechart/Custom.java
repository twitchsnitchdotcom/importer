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
"minvalue",
"maxvalue",
"title",
"description",
"tooltiplabels",
"footerLabels",
"colourLookup",
"footerLabelIndex",
"reverseTooltips",
"enabledFooterLabels",
"labelLookup",
"secondLabelLookup",
"hideRangeRow",
"range"
})
@Generated("jsonschema2pojo")
public class Custom {

@JsonProperty("minvalue")
private String minvalue;
@JsonProperty("maxvalue")
private String maxvalue;
@JsonProperty("title")
private String title;
@JsonProperty("description")
private String description;
@JsonProperty("tooltiplabels")
private Object tooltiplabels;
@JsonProperty("footerLabels")
private Object footerLabels;
@JsonProperty("colourLookup")
private ColourLookup colourLookup;
@JsonProperty("footerLabelIndex")
private Object footerLabelIndex;
@JsonProperty("reverseTooltips")
private Boolean reverseTooltips;
@JsonProperty("enabledFooterLabels")
private Boolean enabledFooterLabels;
@JsonProperty("labelLookup")
private LabelLookup labelLookup;
@JsonProperty("secondLabelLookup")
private SecondLabelLookup secondLabelLookup;
@JsonProperty("hideRangeRow")
private Boolean hideRangeRow;
@JsonProperty("range")
private Object range;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("minvalue")
public String getMinvalue() {
return minvalue;
}

@JsonProperty("minvalue")
public void setMinvalue(String minvalue) {
this.minvalue = minvalue;
}

@JsonProperty("maxvalue")
public String getMaxvalue() {
return maxvalue;
}

@JsonProperty("maxvalue")
public void setMaxvalue(String maxvalue) {
this.maxvalue = maxvalue;
}

@JsonProperty("title")
public String getTitle() {
return title;
}

@JsonProperty("title")
public void setTitle(String title) {
this.title = title;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("tooltiplabels")
public Object getTooltiplabels() {
return tooltiplabels;
}

@JsonProperty("tooltiplabels")
public void setTooltiplabels(Object tooltiplabels) {
this.tooltiplabels = tooltiplabels;
}

@JsonProperty("footerLabels")
public Object getFooterLabels() {
return footerLabels;
}

@JsonProperty("footerLabels")
public void setFooterLabels(Object footerLabels) {
this.footerLabels = footerLabels;
}

@JsonProperty("colourLookup")
public ColourLookup getColourLookup() {
return colourLookup;
}

@JsonProperty("colourLookup")
public void setColourLookup(ColourLookup colourLookup) {
this.colourLookup = colourLookup;
}

@JsonProperty("footerLabelIndex")
public Object getFooterLabelIndex() {
return footerLabelIndex;
}

@JsonProperty("footerLabelIndex")
public void setFooterLabelIndex(Object footerLabelIndex) {
this.footerLabelIndex = footerLabelIndex;
}

@JsonProperty("reverseTooltips")
public Boolean getReverseTooltips() {
return reverseTooltips;
}

@JsonProperty("reverseTooltips")
public void setReverseTooltips(Boolean reverseTooltips) {
this.reverseTooltips = reverseTooltips;
}

@JsonProperty("enabledFooterLabels")
public Boolean getEnabledFooterLabels() {
return enabledFooterLabels;
}

@JsonProperty("enabledFooterLabels")
public void setEnabledFooterLabels(Boolean enabledFooterLabels) {
this.enabledFooterLabels = enabledFooterLabels;
}

@JsonProperty("labelLookup")
public LabelLookup getLabelLookup() {
return labelLookup;
}

@JsonProperty("labelLookup")
public void setLabelLookup(LabelLookup labelLookup) {
this.labelLookup = labelLookup;
}

@JsonProperty("secondLabelLookup")
public SecondLabelLookup getSecondLabelLookup() {
return secondLabelLookup;
}

@JsonProperty("secondLabelLookup")
public void setSecondLabelLookup(SecondLabelLookup secondLabelLookup) {
this.secondLabelLookup = secondLabelLookup;
}

@JsonProperty("hideRangeRow")
public Boolean getHideRangeRow() {
return hideRangeRow;
}

@JsonProperty("hideRangeRow")
public void setHideRangeRow(Boolean hideRangeRow) {
this.hideRangeRow = hideRangeRow;
}

@JsonProperty("range")
public Object getRange() {
return range;
}

@JsonProperty("range")
public void setRange(Object range) {
this.range = range;
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
