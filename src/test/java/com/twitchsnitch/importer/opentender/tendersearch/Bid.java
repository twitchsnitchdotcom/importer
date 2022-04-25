
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
    "isWinning",
    "bidders",
    "isSubcontracted",
    "price",
    "robustPrice",
    "isConsortium",
    "bidId",
    "digiwhistPrice"
})
@Generated("jsonschema2pojo")
public class Bid {

    @JsonProperty("isWinning")
    private Boolean isWinning;
    @JsonProperty("bidders")
    private List<Bidder> bidders = null;
    @JsonProperty("isSubcontracted")
    private Boolean isSubcontracted;
    @JsonProperty("price")
    private Price price;
    @JsonProperty("robustPrice")
    private RobustPrice robustPrice;
    @JsonProperty("isConsortium")
    private Boolean isConsortium;
    @JsonProperty("bidId")
    private String bidId;
    @JsonProperty("digiwhistPrice")
    private DigiwhistPrice digiwhistPrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("isWinning")
    public Boolean getIsWinning() {
        return isWinning;
    }

    @JsonProperty("isWinning")
    public void setIsWinning(Boolean isWinning) {
        this.isWinning = isWinning;
    }

    @JsonProperty("bidders")
    public List<Bidder> getBidders() {
        return bidders;
    }

    @JsonProperty("bidders")
    public void setBidders(List<Bidder> bidders) {
        this.bidders = bidders;
    }

    @JsonProperty("isSubcontracted")
    public Boolean getIsSubcontracted() {
        return isSubcontracted;
    }

    @JsonProperty("isSubcontracted")
    public void setIsSubcontracted(Boolean isSubcontracted) {
        this.isSubcontracted = isSubcontracted;
    }

    @JsonProperty("price")
    public Price getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Price price) {
        this.price = price;
    }

    @JsonProperty("robustPrice")
    public RobustPrice getRobustPrice() {
        return robustPrice;
    }

    @JsonProperty("robustPrice")
    public void setRobustPrice(RobustPrice robustPrice) {
        this.robustPrice = robustPrice;
    }

    @JsonProperty("isConsortium")
    public Boolean getIsConsortium() {
        return isConsortium;
    }

    @JsonProperty("isConsortium")
    public void setIsConsortium(Boolean isConsortium) {
        this.isConsortium = isConsortium;
    }

    @JsonProperty("bidId")
    public String getBidId() {
        return bidId;
    }

    @JsonProperty("bidId")
    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    @JsonProperty("digiwhistPrice")
    public DigiwhistPrice getDigiwhistPrice() {
        return digiwhistPrice;
    }

    @JsonProperty("digiwhistPrice")
    public void setDigiwhistPrice(DigiwhistPrice digiwhistPrice) {
        this.digiwhistPrice = digiwhistPrice;
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
