
package com.twitchsnitch.importer.opentender.tendersearch;

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
    "lotTitle",
    "decisionPeriodLength"
})
@Generated("jsonschema2pojo")
public class MetaDataLot {

    @JsonProperty("lotTitle")
    private String lotTitle;
    @JsonProperty("decisionPeriodLength")
    private Long decisionPeriodLength;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("lotTitle")
    public String getLotTitle() {
        return lotTitle;
    }

    @JsonProperty("lotTitle")
    public void setLotTitle(String lotTitle) {
        this.lotTitle = lotTitle;
    }

    @JsonProperty("decisionPeriodLength")
    public Long getDecisionPeriodLength() {
        return decisionPeriodLength;
    }

    @JsonProperty("decisionPeriodLength")
    public void setDecisionPeriodLength(Long decisionPeriodLength) {
        this.decisionPeriodLength = decisionPeriodLength;
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
