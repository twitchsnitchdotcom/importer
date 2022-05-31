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
"labelIndex"
})
@Generated("jsonschema2pojo")
public class VerticalLabels {

@JsonProperty("labelIndex")
private List<LabelIndex> labelIndex = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("labelIndex")
public List<LabelIndex> getLabelIndex() {
return labelIndex;
}

@JsonProperty("labelIndex")
public void setLabelIndex(List<LabelIndex> labelIndex) {
this.labelIndex = labelIndex;
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