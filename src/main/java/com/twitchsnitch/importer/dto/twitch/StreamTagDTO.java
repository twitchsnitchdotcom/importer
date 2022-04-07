package com.twitchsnitch.importer.dto.twitch;

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
"tag_id",
"is_auto",
"localization_names",
"localization_descriptions"
})
@Generated("jsonschema2pojo")
public class StreamTagDTO {

@JsonProperty("tag_id")
private String tagId;
@JsonProperty("is_auto")
private Boolean isAuto;
@JsonProperty("localization_names")
private LocalizationNames localizationNames;
@JsonProperty("localization_descriptions")
private LocalizationDescriptions localizationDescriptions;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("tag_id")
public String getTagId() {
return tagId;
}

@JsonProperty("tag_id")
public void setTagId(String tagId) {
this.tagId = tagId;
}

@JsonProperty("is_auto")
public Boolean getIsAuto() {
return isAuto;
}

@JsonProperty("is_auto")
public void setIsAuto(Boolean isAuto) {
this.isAuto = isAuto;
}

@JsonProperty("localization_names")
public LocalizationNames getLocalizationNames() {
return localizationNames;
}

@JsonProperty("localization_names")
public void setLocalizationNames(LocalizationNames localizationNames) {
this.localizationNames = localizationNames;
}

@JsonProperty("localization_descriptions")
public LocalizationDescriptions getLocalizationDescriptions() {
return localizationDescriptions;
}

@JsonProperty("localization_descriptions")
public void setLocalizationDescriptions(LocalizationDescriptions localizationDescriptions) {
this.localizationDescriptions = localizationDescriptions;
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
