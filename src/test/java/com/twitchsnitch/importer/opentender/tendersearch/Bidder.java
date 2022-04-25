
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
    "processingOrder",
    "name",
    "address",
    "phone",
    "isSme",
    "id"
})
@Generated("jsonschema2pojo")
public class Bidder {

    @JsonProperty("processingOrder")
    private String processingOrder;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("isSme")
    private Boolean isSme;
    @JsonProperty("id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("processingOrder")
    public String getProcessingOrder() {
        return processingOrder;
    }

    @JsonProperty("processingOrder")
    public void setProcessingOrder(String processingOrder) {
        this.processingOrder = processingOrder;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("isSme")
    public Boolean getIsSme() {
        return isSme;
    }

    @JsonProperty("isSme")
    public void setIsSme(Boolean isSme) {
        this.isSme = isSme;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
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
