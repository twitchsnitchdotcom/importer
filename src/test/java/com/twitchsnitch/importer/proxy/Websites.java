
package com.twitchsnitch.importer.proxy;

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
    "example",
    "google",
    "amazon",
    "yelp",
    "google_maps"
})
@Generated("jsonschema2pojo")
public class Websites {

    @JsonProperty("example")
    private Boolean example;
    @JsonProperty("google")
    private Boolean google;
    @JsonProperty("amazon")
    private Boolean amazon;
    @JsonProperty("yelp")
    private Boolean yelp;
    @JsonProperty("google_maps")
    private Boolean googleMaps;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("example")
    public Boolean getExample() {
        return example;
    }

    @JsonProperty("example")
    public void setExample(Boolean example) {
        this.example = example;
    }

    @JsonProperty("google")
    public Boolean getGoogle() {
        return google;
    }

    @JsonProperty("google")
    public void setGoogle(Boolean google) {
        this.google = google;
    }

    @JsonProperty("amazon")
    public Boolean getAmazon() {
        return amazon;
    }

    @JsonProperty("amazon")
    public void setAmazon(Boolean amazon) {
        this.amazon = amazon;
    }

    @JsonProperty("yelp")
    public Boolean getYelp() {
        return yelp;
    }

    @JsonProperty("yelp")
    public void setYelp(Boolean yelp) {
        this.yelp = yelp;
    }

    @JsonProperty("google_maps")
    public Boolean getGoogleMaps() {
        return googleMaps;
    }

    @JsonProperty("google_maps")
    public void setGoogleMaps(Boolean googleMaps) {
        this.googleMaps = googleMaps;
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
