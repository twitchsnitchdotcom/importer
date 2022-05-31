
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
    "vat",
    "currency",
    "netAmountEur",
    "currencyNational",
    "netAmountNational"
})
@Generated("jsonschema2pojo")
public class Price {

    @JsonProperty("netAmount")
    private Long netAmount;
    @JsonProperty("vat")
    private Long vat;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("netAmountEur")
    private Long netAmountEur;
    @JsonProperty("currencyNational")
    private String currencyNational;
    @JsonProperty("netAmountNational")
    private Long netAmountNational;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("netAmount")
    public Long getNetAmount() {
        return netAmount;
    }

    @JsonProperty("netAmount")
    public void setNetAmount(Long netAmount) {
        this.netAmount = netAmount;
    }

    @JsonProperty("vat")
    public Long getVat() {
        return vat;
    }

    @JsonProperty("vat")
    public void setVat(Long vat) {
        this.vat = vat;
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
    public Long getNetAmountEur() {
        return netAmountEur;
    }

    @JsonProperty("netAmountEur")
    public void setNetAmountEur(Long netAmountEur) {
        this.netAmountEur = netAmountEur;
    }

    @JsonProperty("currencyNational")
    public String getCurrencyNational() {
        return currencyNational;
    }

    @JsonProperty("currencyNational")
    public void setCurrencyNational(String currencyNational) {
        this.currencyNational = currencyNational;
    }

    @JsonProperty("netAmountNational")
    public Long getNetAmountNational() {
        return netAmountNational;
    }

    @JsonProperty("netAmountNational")
    public void setNetAmountNational(Long netAmountNational) {
        this.netAmountNational = netAmountNational;
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
