
package com.twitchsnitch.importer.opentender.tendersearch;

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
    "processingOrder",
    "name",
    "address",
    "email",
    "contactName",
    "phone",
    "mainActivities",
    "buyerType",
    "id"
})
@Generated("jsonschema2pojo")
public class Buyer {

    @JsonProperty("processingOrder")
    private String processingOrder;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contactName")
    private String contactName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("mainActivities")
    private List<String> mainActivities = null;
    @JsonProperty("buyerType")
    private String buyerType;
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

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("contactName")
    public String getContactName() {
        return contactName;
    }

    @JsonProperty("contactName")
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("mainActivities")
    public List<String> getMainActivities() {
        return mainActivities;
    }

    @JsonProperty("mainActivities")
    public void setMainActivities(List<String> mainActivities) {
        this.mainActivities = mainActivities;
    }

    @JsonProperty("buyerType")
    public String getBuyerType() {
        return buyerType;
    }

    @JsonProperty("buyerType")
    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
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
