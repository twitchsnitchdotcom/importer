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
"responsive",
"maintainAspectRatio",
"animation",
"title",
"tooltips",
"legend",
"hover",
"scales",
"verticalLabels"
})
@Generated("jsonschema2pojo")
public class Options {

@JsonProperty("responsive")
private Boolean responsive;
@JsonProperty("maintainAspectRatio")
private Boolean maintainAspectRatio;
@JsonProperty("animation")
private Animation animation;
@JsonProperty("title")
private Title title;
@JsonProperty("tooltips")
private Tooltips tooltips;
@JsonProperty("legend")
private Legend legend;
@JsonProperty("hover")
private Hover hover;
@JsonProperty("scales")
private Scales scales;
@JsonProperty("verticalLabels")
private VerticalLabels verticalLabels;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("responsive")
public Boolean getResponsive() {
return responsive;
}

@JsonProperty("responsive")
public void setResponsive(Boolean responsive) {
this.responsive = responsive;
}

@JsonProperty("maintainAspectRatio")
public Boolean getMaintainAspectRatio() {
return maintainAspectRatio;
}

@JsonProperty("maintainAspectRatio")
public void setMaintainAspectRatio(Boolean maintainAspectRatio) {
this.maintainAspectRatio = maintainAspectRatio;
}

@JsonProperty("animation")
public Animation getAnimation() {
return animation;
}

@JsonProperty("animation")
public void setAnimation(Animation animation) {
this.animation = animation;
}

@JsonProperty("title")
public Title getTitle() {
return title;
}

@JsonProperty("title")
public void setTitle(Title title) {
this.title = title;
}

@JsonProperty("tooltips")
public Tooltips getTooltips() {
return tooltips;
}

@JsonProperty("tooltips")
public void setTooltips(Tooltips tooltips) {
this.tooltips = tooltips;
}

@JsonProperty("legend")
public Legend getLegend() {
return legend;
}

@JsonProperty("legend")
public void setLegend(Legend legend) {
this.legend = legend;
}

@JsonProperty("hover")
public Hover getHover() {
return hover;
}

@JsonProperty("hover")
public void setHover(Hover hover) {
this.hover = hover;
}

@JsonProperty("scales")
public Scales getScales() {
return scales;
}

@JsonProperty("scales")
public void setScales(Scales scales) {
this.scales = scales;
}

@JsonProperty("verticalLabels")
public VerticalLabels getVerticalLabels() {
return verticalLabels;
}

@JsonProperty("verticalLabels")
public void setVerticalLabels(VerticalLabels verticalLabels) {
this.verticalLabels = verticalLabels;
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