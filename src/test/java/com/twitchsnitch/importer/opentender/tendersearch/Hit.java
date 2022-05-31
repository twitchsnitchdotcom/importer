
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
    "created",
    "modified",
    "metaData",
    "persistentId",
    "processingOrder",
    "title",
    "description",
    "isCoveredByGpa",
    "isFrameworkAgreement",
    "isDps",
    "cpvs",
    "isElectronicAuction",
    "estimatedPrice",
    "indicators",
    "procedureType",
    "nationalProcedureType",
    "supplyType",
    "bidDeadline",
    "isCentralProcurement",
    "isJointProcurement",
    "buyers",
    "publications",
    "hasLots",
    "lots",
    "documents",
    "personalRequirements",
    "economicRequirements",
    "technicalRequirements",
    "appealBodyName",
    "finalPrice",
    "eligibleBidLanguages",
    "isEInvoiceAccepted",
    "country",
    "groupId",
    "digiwhistPrice",
    "id",
    "ot"
})
@Generated("jsonschema2pojo")
public class Hit {

    @JsonProperty("created")
    private String created;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("metaData")
    private MetaData metaData;
    @JsonProperty("persistentId")
    private String persistentId;
    @JsonProperty("processingOrder")
    private String processingOrder;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isCoveredByGpa")
    private Boolean isCoveredByGpa;
    @JsonProperty("isFrameworkAgreement")
    private Boolean isFrameworkAgreement;
    @JsonProperty("isDps")
    private Boolean isDps;
    @JsonProperty("cpvs")
    private List<Cpv> cpvs = null;
    @JsonProperty("isElectronicAuction")
    private Boolean isElectronicAuction;
    @JsonProperty("estimatedPrice")
    private Price estimatedPrice;
    @JsonProperty("indicators")
    private List<Indicator> indicators = null;
    @JsonProperty("procedureType")
    private String procedureType;
    @JsonProperty("nationalProcedureType")
    private String nationalProcedureType;
    @JsonProperty("supplyType")
    private String supplyType;
    @JsonProperty("bidDeadline")
    private String bidDeadline;
    @JsonProperty("isCentralProcurement")
    private Boolean isCentralProcurement;
    @JsonProperty("isJointProcurement")
    private Boolean isJointProcurement;
    @JsonProperty("buyers")
    private List<Buyer> buyers = null;
    @JsonProperty("publications")
    private List<Publication> publications = null;
    @JsonProperty("hasLots")
    private Boolean hasLots;
    @JsonProperty("lots")
    private List<Lot> lots = null;
    @JsonProperty("documents")
    private List<Document> documents = null;
    @JsonProperty("personalRequirements")
    private String personalRequirements;
    @JsonProperty("economicRequirements")
    private String economicRequirements;
    @JsonProperty("technicalRequirements")
    private String technicalRequirements;
    @JsonProperty("appealBodyName")
    private String appealBodyName;
    @JsonProperty("finalPrice")
    private Price finalPrice;
    @JsonProperty("eligibleBidLanguages")
    private List<String> eligibleBidLanguages = null;
    @JsonProperty("isEInvoiceAccepted")
    private Boolean isEInvoiceAccepted;
    @JsonProperty("country")
    private String country;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("ot")
    private OtDetailed ot;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    @JsonProperty("metaData")
    public MetaData getMetaData() {
        return metaData;
    }

    @JsonProperty("metaData")
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    @JsonProperty("persistentId")
    public String getPersistentId() {
        return persistentId;
    }

    @JsonProperty("persistentId")
    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    @JsonProperty("processingOrder")
    public String getProcessingOrder() {
        return processingOrder;
    }

    @JsonProperty("processingOrder")
    public void setProcessingOrder(String processingOrder) {
        this.processingOrder = processingOrder;
    }

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

    @JsonProperty("isCoveredByGpa")
    public Boolean getIsCoveredByGpa() {
        return isCoveredByGpa;
    }

    @JsonProperty("isCoveredByGpa")
    public void setIsCoveredByGpa(Boolean isCoveredByGpa) {
        this.isCoveredByGpa = isCoveredByGpa;
    }

    @JsonProperty("isFrameworkAgreement")
    public Boolean getIsFrameworkAgreement() {
        return isFrameworkAgreement;
    }

    @JsonProperty("isFrameworkAgreement")
    public void setIsFrameworkAgreement(Boolean isFrameworkAgreement) {
        this.isFrameworkAgreement = isFrameworkAgreement;
    }

    @JsonProperty("isDps")
    public Boolean getIsDps() {
        return isDps;
    }

    @JsonProperty("isDps")
    public void setIsDps(Boolean isDps) {
        this.isDps = isDps;
    }

    @JsonProperty("cpvs")
    public List<Cpv> getCpvs() {
        return cpvs;
    }

    @JsonProperty("cpvs")
    public void setCpvs(List<Cpv> cpvs) {
        this.cpvs = cpvs;
    }

    @JsonProperty("isElectronicAuction")
    public Boolean getIsElectronicAuction() {
        return isElectronicAuction;
    }

    @JsonProperty("isElectronicAuction")
    public void setIsElectronicAuction(Boolean isElectronicAuction) {
        this.isElectronicAuction = isElectronicAuction;
    }

    @JsonProperty("indicators")
    public List<Indicator> getIndicators() {
        return indicators;
    }

    @JsonProperty("indicators")
    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    @JsonProperty("procedureType")
    public String getProcedureType() {
        return procedureType;
    }

    @JsonProperty("procedureType")
    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    @JsonProperty("nationalProcedureType")
    public String getNationalProcedureType() {
        return nationalProcedureType;
    }

    @JsonProperty("nationalProcedureType")
    public void setNationalProcedureType(String nationalProcedureType) {
        this.nationalProcedureType = nationalProcedureType;
    }

    @JsonProperty("supplyType")
    public String getSupplyType() {
        return supplyType;
    }

    @JsonProperty("supplyType")
    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    @JsonProperty("bidDeadline")
    public String getBidDeadline() {
        return bidDeadline;
    }

    @JsonProperty("bidDeadline")
    public void setBidDeadline(String bidDeadline) {
        this.bidDeadline = bidDeadline;
    }

    @JsonProperty("isCentralProcurement")
    public Boolean getIsCentralProcurement() {
        return isCentralProcurement;
    }

    @JsonProperty("isCentralProcurement")
    public void setIsCentralProcurement(Boolean isCentralProcurement) {
        this.isCentralProcurement = isCentralProcurement;
    }

    @JsonProperty("isJointProcurement")
    public Boolean getIsJointProcurement() {
        return isJointProcurement;
    }

    @JsonProperty("isJointProcurement")
    public void setIsJointProcurement(Boolean isJointProcurement) {
        this.isJointProcurement = isJointProcurement;
    }

    @JsonProperty("buyers")
    public List<Buyer> getBuyers() {
        return buyers;
    }

    @JsonProperty("buyers")
    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
    }

    @JsonProperty("publications")
    public List<Publication> getPublications() {
        return publications;
    }

    @JsonProperty("publications")
    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    @JsonProperty("hasLots")
    public Boolean getHasLots() {
        return hasLots;
    }

    @JsonProperty("hasLots")
    public void setHasLots(Boolean hasLots) {
        this.hasLots = hasLots;
    }

    @JsonProperty("lots")
    public List<Lot> getLots() {
        return lots;
    }

    @JsonProperty("lots")
    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    @JsonProperty("documents")
    public List<Document> getDocuments() {
        return documents;
    }

    @JsonProperty("documents")
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    @JsonProperty("personalRequirements")
    public String getPersonalRequirements() {
        return personalRequirements;
    }

    @JsonProperty("personalRequirements")
    public void setPersonalRequirements(String personalRequirements) {
        this.personalRequirements = personalRequirements;
    }

    @JsonProperty("economicRequirements")
    public String getEconomicRequirements() {
        return economicRequirements;
    }

    @JsonProperty("economicRequirements")
    public void setEconomicRequirements(String economicRequirements) {
        this.economicRequirements = economicRequirements;
    }

    @JsonProperty("technicalRequirements")
    public String getTechnicalRequirements() {
        return technicalRequirements;
    }

    @JsonProperty("technicalRequirements")
    public void setTechnicalRequirements(String technicalRequirements) {
        this.technicalRequirements = technicalRequirements;
    }

    @JsonProperty("appealBodyName")
    public String getAppealBodyName() {
        return appealBodyName;
    }

    @JsonProperty("appealBodyName")
    public void setAppealBodyName(String appealBodyName) {
        this.appealBodyName = appealBodyName;
    }

    @JsonProperty("finalPrice")
    public Price getFinalPrice() {
        return finalPrice;
    }

    @JsonProperty("finalPrice")
    public void setFinalPrice(Price finalPrice) {
        this.finalPrice = finalPrice;
    }

    @JsonProperty("eligibleBidLanguages")
    public List<String> getEligibleBidLanguages() {
        return eligibleBidLanguages;
    }

    @JsonProperty("eligibleBidLanguages")
    public void setEligibleBidLanguages(List<String> eligibleBidLanguages) {
        this.eligibleBidLanguages = eligibleBidLanguages;
    }

    @JsonProperty("isEInvoiceAccepted")
    public Boolean getIsEInvoiceAccepted() {
        return isEInvoiceAccepted;
    }

    @JsonProperty("isEInvoiceAccepted")
    public void setIsEInvoiceAccepted(Boolean isEInvoiceAccepted) {
        this.isEInvoiceAccepted = isEInvoiceAccepted;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("ot")
    public OtDetailed getOt() {
        return ot;
    }

    @JsonProperty("ot")
    public void setOt(OtDetailed ot) {
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
