
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
    "INTEGRITY",
    "ADMINISTRATIVE",
    "TRANSPARENCY",
    "TENDER"
})
@Generated("jsonschema2pojo")
public class ScoreIndicator {

    @JsonProperty("INTEGRITY")
    private Long integrity;
    @JsonProperty("ADMINISTRATIVE")
    private Double administrative;
    @JsonProperty("TRANSPARENCY")
    private Double transparency;
    @JsonProperty("TENDER")
    private Double tender;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("INTEGRITY")
    public Long getIntegrity() {
        return integrity;
    }

    @JsonProperty("INTEGRITY")
    public void setIntegrity(Long integrity) {
        this.integrity = integrity;
    }

    @JsonProperty("ADMINISTRATIVE")
    public Double getAdministrative() {
        return administrative;
    }

    @JsonProperty("ADMINISTRATIVE")
    public void setAdministrative(Double administrative) {
        this.administrative = administrative;
    }

    @JsonProperty("TRANSPARENCY")
    public Double getTransparency() {
        return transparency;
    }

    @JsonProperty("TRANSPARENCY")
    public void setTransparency(Double transparency) {
        this.transparency = transparency;
    }

    @JsonProperty("TENDER")
    public Double getTender() {
        return tender;
    }

    @JsonProperty("TENDER")
    public void setTender(Double tender) {
        this.tender = tender;
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
