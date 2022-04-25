
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
    "street",
    "city",
    "postcode",
    "nuts",
    "country",
    "url",
    "ot"
})
@Generated("jsonschema2pojo")
public class Address {

    @JsonProperty("street")
    private String street;
    @JsonProperty("city")
    private String city;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("nuts")
    private List<String> nuts = null;
    @JsonProperty("country")
    private String country;
    @JsonProperty("url")
    private String url;
    @JsonProperty("ot")
    private Ot ot;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @JsonProperty("nuts")
    public List<String> getNuts() {
        return nuts;
    }

    @JsonProperty("nuts")
    public void setNuts(List<String> nuts) {
        this.nuts = nuts;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("ot")
    public Ot getOt() {
        return ot;
    }

    @JsonProperty("ot")
    public void setOt(Ot ot) {
        this.ot = ot;
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
