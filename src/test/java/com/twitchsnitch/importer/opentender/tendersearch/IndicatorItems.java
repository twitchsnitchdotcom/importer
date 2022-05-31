
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
    "INTEGRITY_SINGLE_BID",
    "ADMINISTRATIVE_CENTRALIZED_PROCUREMENT",
    "INTEGRITY_DECISION_PERIOD",
    "ADMINISTRATIVE_COVERED_BY_GPA",
    "ADMINISTRATIVE_ELECTRONIC_AUCTION",
    "ADMINISTRATIVE_FRAMEWORK_AGREEMENT",
    "ADMINISTRATIVE_ENGLISH_AS_FOREIGN_LANGUAGE",
    "INTEGRITY_CALL_FOR_TENDER_PUBLICATION",
    "INTEGRITY_PROCEDURE_TYPE",
    "ADMINISTRATIVE_NOTICE_AND_AWARD_DISCREPANCIES",
    "TRANSPARENCY_BIDDER_NAME_MISSING",
    "TRANSPARENCY_VALUE_MISSING",
    "TRANSPARENCY_MISSING_ELIGIBLE_BID_LANGUAGES",
    "TRANSPARENCY_MISSING_ADDRESS_OF_IMPLEMENTATION_NUTS",
    "TRANSPARENCY_MISSING_OR_INCOMPLETE_CPVS",
    "TRANSPARENCY_MISSING_OR_INCOMPLETE_DURATION_INFO",
    "TRANSPARENCY_MISSING_SELECTION_METHOD",
    "TRANSPARENCY_MISSING_SUBCONTRACTED_INFO",
    "TRANSPARENCY_MISSING_OR_INCOMPLETE_FUNDINGS_INFO"
})
@Generated("jsonschema2pojo")
public class IndicatorItems {

    @JsonProperty("INTEGRITY_SINGLE_BID")
    private Long integritySingleBid;
    @JsonProperty("ADMINISTRATIVE_CENTRALIZED_PROCUREMENT")
    private Long administrativeCentralizedProcurement;
    @JsonProperty("INTEGRITY_DECISION_PERIOD")
    private Long integrityDecisionPeriod;
    @JsonProperty("ADMINISTRATIVE_COVERED_BY_GPA")
    private Long administrativeCoveredByGpa;
    @JsonProperty("ADMINISTRATIVE_ELECTRONIC_AUCTION")
    private Long administrativeElectronicAuction;
    @JsonProperty("ADMINISTRATIVE_FRAMEWORK_AGREEMENT")
    private Long administrativeFrameworkAgreement;
    @JsonProperty("ADMINISTRATIVE_ENGLISH_AS_FOREIGN_LANGUAGE")
    private Long administrativeEnglishAsForeignLanguage;
    @JsonProperty("INTEGRITY_CALL_FOR_TENDER_PUBLICATION")
    private Long integrityCallForTenderPublication;
    @JsonProperty("INTEGRITY_PROCEDURE_TYPE")
    private Long integrityProcedureType;
    @JsonProperty("ADMINISTRATIVE_NOTICE_AND_AWARD_DISCREPANCIES")
    private Double administrativeNoticeAndAwardDiscrepancies;
    @JsonProperty("TRANSPARENCY_BIDDER_NAME_MISSING")
    private Long transparencyBidderNameMissing;
    @JsonProperty("TRANSPARENCY_VALUE_MISSING")
    private Long transparencyValueMissing;
    @JsonProperty("TRANSPARENCY_MISSING_ELIGIBLE_BID_LANGUAGES")
    private Long transparencyMissingEligibleBidLanguages;
    @JsonProperty("TRANSPARENCY_MISSING_ADDRESS_OF_IMPLEMENTATION_NUTS")
    private Long transparencyMissingAddressOfImplementationNuts;
    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_CPVS")
    private Long transparencyMissingOrIncompleteCpvs;
    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_DURATION_INFO")
    private Long transparencyMissingOrIncompleteDurationInfo;
    @JsonProperty("TRANSPARENCY_MISSING_SELECTION_METHOD")
    private Long transparencyMissingSelectionMethod;
    @JsonProperty("TRANSPARENCY_MISSING_SUBCONTRACTED_INFO")
    private Long transparencyMissingSubcontractedInfo;
    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_FUNDINGS_INFO")
    private Long transparencyMissingOrIncompleteFundingsInfo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("INTEGRITY_SINGLE_BID")
    public Long getIntegritySingleBid() {
        return integritySingleBid;
    }

    @JsonProperty("INTEGRITY_SINGLE_BID")
    public void setIntegritySingleBid(Long integritySingleBid) {
        this.integritySingleBid = integritySingleBid;
    }

    @JsonProperty("ADMINISTRATIVE_CENTRALIZED_PROCUREMENT")
    public Long getAdministrativeCentralizedProcurement() {
        return administrativeCentralizedProcurement;
    }

    @JsonProperty("ADMINISTRATIVE_CENTRALIZED_PROCUREMENT")
    public void setAdministrativeCentralizedProcurement(Long administrativeCentralizedProcurement) {
        this.administrativeCentralizedProcurement = administrativeCentralizedProcurement;
    }

    @JsonProperty("INTEGRITY_DECISION_PERIOD")
    public Long getIntegrityDecisionPeriod() {
        return integrityDecisionPeriod;
    }

    @JsonProperty("INTEGRITY_DECISION_PERIOD")
    public void setIntegrityDecisionPeriod(Long integrityDecisionPeriod) {
        this.integrityDecisionPeriod = integrityDecisionPeriod;
    }

    @JsonProperty("ADMINISTRATIVE_COVERED_BY_GPA")
    public Long getAdministrativeCoveredByGpa() {
        return administrativeCoveredByGpa;
    }

    @JsonProperty("ADMINISTRATIVE_COVERED_BY_GPA")
    public void setAdministrativeCoveredByGpa(Long administrativeCoveredByGpa) {
        this.administrativeCoveredByGpa = administrativeCoveredByGpa;
    }

    @JsonProperty("ADMINISTRATIVE_ELECTRONIC_AUCTION")
    public Long getAdministrativeElectronicAuction() {
        return administrativeElectronicAuction;
    }

    @JsonProperty("ADMINISTRATIVE_ELECTRONIC_AUCTION")
    public void setAdministrativeElectronicAuction(Long administrativeElectronicAuction) {
        this.administrativeElectronicAuction = administrativeElectronicAuction;
    }

    @JsonProperty("ADMINISTRATIVE_FRAMEWORK_AGREEMENT")
    public Long getAdministrativeFrameworkAgreement() {
        return administrativeFrameworkAgreement;
    }

    @JsonProperty("ADMINISTRATIVE_FRAMEWORK_AGREEMENT")
    public void setAdministrativeFrameworkAgreement(Long administrativeFrameworkAgreement) {
        this.administrativeFrameworkAgreement = administrativeFrameworkAgreement;
    }

    @JsonProperty("ADMINISTRATIVE_ENGLISH_AS_FOREIGN_LANGUAGE")
    public Long getAdministrativeEnglishAsForeignLanguage() {
        return administrativeEnglishAsForeignLanguage;
    }

    @JsonProperty("ADMINISTRATIVE_ENGLISH_AS_FOREIGN_LANGUAGE")
    public void setAdministrativeEnglishAsForeignLanguage(Long administrativeEnglishAsForeignLanguage) {
        this.administrativeEnglishAsForeignLanguage = administrativeEnglishAsForeignLanguage;
    }

    @JsonProperty("INTEGRITY_CALL_FOR_TENDER_PUBLICATION")
    public Long getIntegrityCallForTenderPublication() {
        return integrityCallForTenderPublication;
    }

    @JsonProperty("INTEGRITY_CALL_FOR_TENDER_PUBLICATION")
    public void setIntegrityCallForTenderPublication(Long integrityCallForTenderPublication) {
        this.integrityCallForTenderPublication = integrityCallForTenderPublication;
    }

    @JsonProperty("INTEGRITY_PROCEDURE_TYPE")
    public Long getIntegrityProcedureType() {
        return integrityProcedureType;
    }

    @JsonProperty("INTEGRITY_PROCEDURE_TYPE")
    public void setIntegrityProcedureType(Long integrityProcedureType) {
        this.integrityProcedureType = integrityProcedureType;
    }

    @JsonProperty("ADMINISTRATIVE_NOTICE_AND_AWARD_DISCREPANCIES")
    public Double getAdministrativeNoticeAndAwardDiscrepancies() {
        return administrativeNoticeAndAwardDiscrepancies;
    }

    @JsonProperty("ADMINISTRATIVE_NOTICE_AND_AWARD_DISCREPANCIES")
    public void setAdministrativeNoticeAndAwardDiscrepancies(Double administrativeNoticeAndAwardDiscrepancies) {
        this.administrativeNoticeAndAwardDiscrepancies = administrativeNoticeAndAwardDiscrepancies;
    }

    @JsonProperty("TRANSPARENCY_BIDDER_NAME_MISSING")
    public Long getTransparencyBidderNameMissing() {
        return transparencyBidderNameMissing;
    }

    @JsonProperty("TRANSPARENCY_BIDDER_NAME_MISSING")
    public void setTransparencyBidderNameMissing(Long transparencyBidderNameMissing) {
        this.transparencyBidderNameMissing = transparencyBidderNameMissing;
    }

    @JsonProperty("TRANSPARENCY_VALUE_MISSING")
    public Long getTransparencyValueMissing() {
        return transparencyValueMissing;
    }

    @JsonProperty("TRANSPARENCY_VALUE_MISSING")
    public void setTransparencyValueMissing(Long transparencyValueMissing) {
        this.transparencyValueMissing = transparencyValueMissing;
    }

    @JsonProperty("TRANSPARENCY_MISSING_ELIGIBLE_BID_LANGUAGES")
    public Long getTransparencyMissingEligibleBidLanguages() {
        return transparencyMissingEligibleBidLanguages;
    }

    @JsonProperty("TRANSPARENCY_MISSING_ELIGIBLE_BID_LANGUAGES")
    public void setTransparencyMissingEligibleBidLanguages(Long transparencyMissingEligibleBidLanguages) {
        this.transparencyMissingEligibleBidLanguages = transparencyMissingEligibleBidLanguages;
    }

    @JsonProperty("TRANSPARENCY_MISSING_ADDRESS_OF_IMPLEMENTATION_NUTS")
    public Long getTransparencyMissingAddressOfImplementationNuts() {
        return transparencyMissingAddressOfImplementationNuts;
    }

    @JsonProperty("TRANSPARENCY_MISSING_ADDRESS_OF_IMPLEMENTATION_NUTS")
    public void setTransparencyMissingAddressOfImplementationNuts(Long transparencyMissingAddressOfImplementationNuts) {
        this.transparencyMissingAddressOfImplementationNuts = transparencyMissingAddressOfImplementationNuts;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_CPVS")
    public Long getTransparencyMissingOrIncompleteCpvs() {
        return transparencyMissingOrIncompleteCpvs;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_CPVS")
    public void setTransparencyMissingOrIncompleteCpvs(Long transparencyMissingOrIncompleteCpvs) {
        this.transparencyMissingOrIncompleteCpvs = transparencyMissingOrIncompleteCpvs;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_DURATION_INFO")
    public Long getTransparencyMissingOrIncompleteDurationInfo() {
        return transparencyMissingOrIncompleteDurationInfo;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_DURATION_INFO")
    public void setTransparencyMissingOrIncompleteDurationInfo(Long transparencyMissingOrIncompleteDurationInfo) {
        this.transparencyMissingOrIncompleteDurationInfo = transparencyMissingOrIncompleteDurationInfo;
    }

    @JsonProperty("TRANSPARENCY_MISSING_SELECTION_METHOD")
    public Long getTransparencyMissingSelectionMethod() {
        return transparencyMissingSelectionMethod;
    }

    @JsonProperty("TRANSPARENCY_MISSING_SELECTION_METHOD")
    public void setTransparencyMissingSelectionMethod(Long transparencyMissingSelectionMethod) {
        this.transparencyMissingSelectionMethod = transparencyMissingSelectionMethod;
    }

    @JsonProperty("TRANSPARENCY_MISSING_SUBCONTRACTED_INFO")
    public Long getTransparencyMissingSubcontractedInfo() {
        return transparencyMissingSubcontractedInfo;
    }

    @JsonProperty("TRANSPARENCY_MISSING_SUBCONTRACTED_INFO")
    public void setTransparencyMissingSubcontractedInfo(Long transparencyMissingSubcontractedInfo) {
        this.transparencyMissingSubcontractedInfo = transparencyMissingSubcontractedInfo;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_FUNDINGS_INFO")
    public Long getTransparencyMissingOrIncompleteFundingsInfo() {
        return transparencyMissingOrIncompleteFundingsInfo;
    }

    @JsonProperty("TRANSPARENCY_MISSING_OR_INCOMPLETE_FUNDINGS_INFO")
    public void setTransparencyMissingOrIncompleteFundingsInfo(Long transparencyMissingOrIncompleteFundingsInfo) {
        this.transparencyMissingOrIncompleteFundingsInfo = transparencyMissingOrIncompleteFundingsInfo;
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
