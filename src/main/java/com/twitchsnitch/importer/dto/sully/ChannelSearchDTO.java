package com.twitchsnitch.importer.dto.sully;

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
"draw",
"recordsTotal",
"recordsFiltered",
"data",
"progressProps"
})
@Generated("jsonschema2pojo")
public class ChannelSearchDTO {

@JsonProperty("draw")
private Long draw;
@JsonProperty("recordsTotal")
private Long recordsTotal;
@JsonProperty("recordsFiltered")
private Long recordsFiltered;
@JsonProperty("data")
private List<ChannelSearchDatum> data = null;
@JsonProperty("progressProps")
private List<Object> progressProps = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("draw")
public Long getDraw() {
return draw;
}

@JsonProperty("draw")
public void setDraw(Long draw) {
this.draw = draw;
}

@JsonProperty("recordsTotal")
public Long getRecordsTotal() {
return recordsTotal;
}

@JsonProperty("recordsTotal")
public void setRecordsTotal(Long recordsTotal) {
this.recordsTotal = recordsTotal;
}

@JsonProperty("recordsFiltered")
public Long getRecordsFiltered() {
return recordsFiltered;
}

@JsonProperty("recordsFiltered")
public void setRecordsFiltered(Long recordsFiltered) {
this.recordsFiltered = recordsFiltered;
}

@JsonProperty("data")
public List<ChannelSearchDatum> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<ChannelSearchDatum> data) {
this.data = data;
}

@JsonProperty("progressProps")
public List<Object> getProgressProps() {
return progressProps;
}

@JsonProperty("progressProps")
public void setProgressProps(List<Object> progressProps) {
this.progressProps = progressProps;
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

