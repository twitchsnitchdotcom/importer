
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
    "source",
    "sourceTenderId",
    "machineReadableUrl",
    "dispatchDate",
    "isIncluded",
    "formType",
    "sourceFormType"
})
@Generated("jsonschema2pojo")
public class Publication {

    @JsonProperty("source")
    private String source;
    @JsonProperty("sourceTenderId")
    private String sourceTenderId;
    @JsonProperty("machineReadableUrl")
    private String machineReadableUrl;
    @JsonProperty("dispatchDate")
    private String dispatchDate;
    @JsonProperty("isIncluded")
    private Boolean isIncluded;
    @JsonProperty("formType")
    private String formType;
    @JsonProperty("sourceFormType")
    private String sourceFormType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("sourceTenderId")
    public String getSourceTenderId() {
        return sourceTenderId;
    }

    @JsonProperty("sourceTenderId")
    public void setSourceTenderId(String sourceTenderId) {
        this.sourceTenderId = sourceTenderId;
    }

    @JsonProperty("machineReadableUrl")
    public String getMachineReadableUrl() {
        return machineReadableUrl;
    }

    @JsonProperty("machineReadableUrl")
    public void setMachineReadableUrl(String machineReadableUrl) {
        this.machineReadableUrl = machineReadableUrl;
    }

    @JsonProperty("dispatchDate")
    public String getDispatchDate() {
        return dispatchDate;
    }

    @JsonProperty("dispatchDate")
    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    @JsonProperty("isIncluded")
    public Boolean getIsIncluded() {
        return isIncluded;
    }

    @JsonProperty("isIncluded")
    public void setIsIncluded(Boolean isIncluded) {
        this.isIncluded = isIncluded;
    }

    @JsonProperty("formType")
    public String getFormType() {
        return formType;
    }

    @JsonProperty("formType")
    public void setFormType(String formType) {
        this.formType = formType;
    }

    @JsonProperty("sourceFormType")
    public String getSourceFormType() {
        return sourceFormType;
    }

    @JsonProperty("sourceFormType")
    public void setSourceFormType(String sourceFormType) {
        this.sourceFormType = sourceFormType;
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
