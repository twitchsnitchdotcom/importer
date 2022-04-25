
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
    "title",
    "description",
    "isAwarded",
    "areVariantsAccepted",
    "hasOptions",
    "addressOfImplementation",
    "awardDecisionDate",
    "cpvs",
    "fundings",
    "awardCriteria",
    "estimatedPrice",
    "indicators",
    "contractNumber",
    "lotNumber",
    "status",
    "robustEstimatedPrice",
    "bids",
    "bidsCount",
    "electronicBidsCount",
    "smeBidsCount",
    "otherEuMemberStatesCompaniesBidsCount",
    "nonEuMemberStatesCompaniesBidsCount",
    "lotId"
})
@Generated("jsonschema2pojo")
public class Lot {

    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isAwarded")
    private Boolean isAwarded;
    @JsonProperty("areVariantsAccepted")
    private Boolean areVariantsAccepted;
    @JsonProperty("hasOptions")
    private Boolean hasOptions;
    @JsonProperty("addressOfImplementation")
    private AddressOfImplementation addressOfImplementation;
    @JsonProperty("awardDecisionDate")
    private String awardDecisionDate;
    @JsonProperty("cpvs")
    private List<Cpv> cpvs = null;
    @JsonProperty("fundings")
    private List<Funding> fundings = null;
    @JsonProperty("awardCriteria")
    private List<AwardCriterium> awardCriteria = null;
    @JsonProperty("estimatedPrice")
    private Price estimatedPrice;
    @JsonProperty("indicators")
    private List<IndicatorWithClass> indicators = null;
    @JsonProperty("contractNumber")
    private String contractNumber;
    @JsonProperty("lotNumber")
    private Long lotNumber;
    @JsonProperty("status")
    private String status;
    @JsonProperty("robustEstimatedPrice")
    private RobustEstimatedPrice robustEstimatedPrice;
    @JsonProperty("bids")
    private List<Bid> bids = null;
    @JsonProperty("bidsCount")
    private Long bidsCount;
    @JsonProperty("electronicBidsCount")
    private Long electronicBidsCount;
    @JsonProperty("smeBidsCount")
    private Long smeBidsCount;
    @JsonProperty("otherEuMemberStatesCompaniesBidsCount")
    private Long otherEuMemberStatesCompaniesBidsCount;
    @JsonProperty("nonEuMemberStatesCompaniesBidsCount")
    private Long nonEuMemberStatesCompaniesBidsCount;
    @JsonProperty("lotId")
    private String lotId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("isAwarded")
    public Boolean getIsAwarded() {
        return isAwarded;
    }

    @JsonProperty("isAwarded")
    public void setIsAwarded(Boolean isAwarded) {
        this.isAwarded = isAwarded;
    }

    @JsonProperty("areVariantsAccepted")
    public Boolean getAreVariantsAccepted() {
        return areVariantsAccepted;
    }

    @JsonProperty("areVariantsAccepted")
    public void setAreVariantsAccepted(Boolean areVariantsAccepted) {
        this.areVariantsAccepted = areVariantsAccepted;
    }

    @JsonProperty("hasOptions")
    public Boolean getHasOptions() {
        return hasOptions;
    }

    @JsonProperty("hasOptions")
    public void setHasOptions(Boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    @JsonProperty("addressOfImplementation")
    public AddressOfImplementation getAddressOfImplementation() {
        return addressOfImplementation;
    }

    @JsonProperty("addressOfImplementation")
    public void setAddressOfImplementation(AddressOfImplementation addressOfImplementation) {
        this.addressOfImplementation = addressOfImplementation;
    }

    @JsonProperty("awardDecisionDate")
    public String getAwardDecisionDate() {
        return awardDecisionDate;
    }

    @JsonProperty("awardDecisionDate")
    public void setAwardDecisionDate(String awardDecisionDate) {
        this.awardDecisionDate = awardDecisionDate;
    }

    @JsonProperty("cpvs")
    public List<Cpv> getCpvs() {
        return cpvs;
    }

    @JsonProperty("cpvs")
    public void setCpvs(List<Cpv> cpvs) {
        this.cpvs = cpvs;
    }

    @JsonProperty("fundings")
    public List<Funding> getFundings() {
        return fundings;
    }

    @JsonProperty("fundings")
    public void setFundings(List<Funding> fundings) {
        this.fundings = fundings;
    }

    @JsonProperty("awardCriteria")
    public List<AwardCriterium> getAwardCriteria() {
        return awardCriteria;
    }

    @JsonProperty("awardCriteria")
    public void setAwardCriteria(List<AwardCriterium> awardCriteria) {
        this.awardCriteria = awardCriteria;
    }

    @JsonProperty("estimatedPrice")
    public Price getEstimatedPrice() {
        return estimatedPrice;
    }

    @JsonProperty("estimatedPrice")
    public void setEstimatedPrice(Price estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    @JsonProperty("indicators")
    public List<IndicatorWithClass> getIndicators() {
        return indicators;
    }

    @JsonProperty("indicators")
    public void setIndicators(List<IndicatorWithClass> indicators) {
        this.indicators = indicators;
    }

    @JsonProperty("contractNumber")
    public String getContractNumber() {
        return contractNumber;
    }

    @JsonProperty("contractNumber")
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @JsonProperty("lotNumber")
    public Long getLotNumber() {
        return lotNumber;
    }

    @JsonProperty("lotNumber")
    public void setLotNumber(Long lotNumber) {
        this.lotNumber = lotNumber;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("robustEstimatedPrice")
    public RobustEstimatedPrice getRobustEstimatedPrice() {
        return robustEstimatedPrice;
    }

    @JsonProperty("robustEstimatedPrice")
    public void setRobustEstimatedPrice(RobustEstimatedPrice robustEstimatedPrice) {
        this.robustEstimatedPrice = robustEstimatedPrice;
    }

    @JsonProperty("bids")
    public List<Bid> getBids() {
        return bids;
    }

    @JsonProperty("bids")
    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    @JsonProperty("bidsCount")
    public Long getBidsCount() {
        return bidsCount;
    }

    @JsonProperty("bidsCount")
    public void setBidsCount(Long bidsCount) {
        this.bidsCount = bidsCount;
    }

    @JsonProperty("electronicBidsCount")
    public Long getElectronicBidsCount() {
        return electronicBidsCount;
    }

    @JsonProperty("electronicBidsCount")
    public void setElectronicBidsCount(Long electronicBidsCount) {
        this.electronicBidsCount = electronicBidsCount;
    }

    @JsonProperty("smeBidsCount")
    public Long getSmeBidsCount() {
        return smeBidsCount;
    }

    @JsonProperty("smeBidsCount")
    public void setSmeBidsCount(Long smeBidsCount) {
        this.smeBidsCount = smeBidsCount;
    }

    @JsonProperty("otherEuMemberStatesCompaniesBidsCount")
    public Long getOtherEuMemberStatesCompaniesBidsCount() {
        return otherEuMemberStatesCompaniesBidsCount;
    }

    @JsonProperty("otherEuMemberStatesCompaniesBidsCount")
    public void setOtherEuMemberStatesCompaniesBidsCount(Long otherEuMemberStatesCompaniesBidsCount) {
        this.otherEuMemberStatesCompaniesBidsCount = otherEuMemberStatesCompaniesBidsCount;
    }

    @JsonProperty("nonEuMemberStatesCompaniesBidsCount")
    public Long getNonEuMemberStatesCompaniesBidsCount() {
        return nonEuMemberStatesCompaniesBidsCount;
    }

    @JsonProperty("nonEuMemberStatesCompaniesBidsCount")
    public void setNonEuMemberStatesCompaniesBidsCount(Long nonEuMemberStatesCompaniesBidsCount) {
        this.nonEuMemberStatesCompaniesBidsCount = nonEuMemberStatesCompaniesBidsCount;
    }

    @JsonProperty("lotId")
    public String getLotId() {
        return lotId;
    }

    @JsonProperty("lotId")
    public void setLotId(String lotId) {
        this.lotId = lotId;
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
