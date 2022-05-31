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
"responsive",
"animation",
"legend",
"valueformat"
})
@Generated("jsonschema2pojo")
public class Options {

@JsonProperty("responsive")
private Boolean responsive;
@JsonProperty("animation")
private Animation animation;
@JsonProperty("legend")
private Legend legend;
@JsonProperty("valueformat")
private String valueformat;
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

@JsonProperty("animation")
public Animation getAnimation() {
return animation;
}

@JsonProperty("animation")
public void setAnimation(Animation animation) {
this.animation = animation;
}

@JsonProperty("legend")
public Legend getLegend() {
return legend;
}

@JsonProperty("legend")
public void setLegend(Legend legend) {
this.legend = legend;
}

@JsonProperty("valueformat")
public String getValueformat() {
return valueformat;
}

@JsonProperty("valueformat")
public void setValueformat(String valueformat) {
this.valueformat = valueformat;
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