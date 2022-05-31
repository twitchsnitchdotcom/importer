
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
    "netAmount",
    "currency",
    "netAmountEur"
})
@Generated("jsonschema2pojo")
public class DigiwhistPrice {

    @JsonProperty("netAmount")
    private Double netAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("netAmountEur")
    private Double netAmountEur;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("netAmount")
    public Double getNetAmount() {
        return netAmount;
    }

    @JsonProperty("netAmount")
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("netAmountEur")
    public Double getNetAmountEur() {
        return netAmountEur;
    }

    @JsonProperty("netAmountEur")
    public void setNetAmountEur(Double netAmountEur) {
        this.netAmountEur = netAmountEur;
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
