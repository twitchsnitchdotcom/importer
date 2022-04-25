package com.twitchsnitch.importer.dto;

public class EstateAgentDTO {

    private String url;
    private String licenseNumber;//	765045
    private String licenseeName; //	Christopher Michael Durbin
    private String licenseType;	// Sales Agent
    private String mailingAddress;	//121 Kit Fox Ct Montgomery TX 77316
    private String mailingCounty; //	MONTGOMERY
    private String telephone; //	281-451-2275
    private String email; //	chdurbin(a)gmail.com
    private String licenseStatus; //	Current and Inactive
    private String originalLicenseDate; //	20210712
    private String expirationDate; //	20230731
    private String educationStatus; //	Non-elective CE Requirements Outstanding
    private String mCEStatus; //	No MCE (Mandatory Continuing Education) Requirement
    private String designatedSupervisor;//	Not Designated Supervisor
    private String relatedLicenseNumber; //	528222
    private String relatedLicenseType; //	Corporation Broker
    private String relatedLicenseStartDate; //	20210712
    private String agencyIdentifier;//	1000164296

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRelatedLicenseNumber() {
        return relatedLicenseNumber;
    }

    public void setRelatedLicenseNumber(String relatedLicenseNumber) {
        this.relatedLicenseNumber = relatedLicenseNumber;
    }

    public String getRelatedLicenseType() {
        return relatedLicenseType;
    }

    public void setRelatedLicenseType(String relatedLicenseType) {
        this.relatedLicenseType = relatedLicenseType;
    }

    public String getRelatedLicenseStartDate() {
        return relatedLicenseStartDate;
    }

    public void setRelatedLicenseStartDate(String relatedLicenseStartDate) {
        this.relatedLicenseStartDate = relatedLicenseStartDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseeName() {
        return licenseeName;
    }

    public void setLicenseeName(String licenseeName) {
        this.licenseeName = licenseeName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getMailingCounty() {
        return mailingCounty;
    }

    public void setMailingCounty(String mailingCounty) {
        this.mailingCounty = mailingCounty;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getOriginalLicenseDate() {
        return originalLicenseDate;
    }

    public void setOriginalLicenseDate(String originalLicenseDate) {
        this.originalLicenseDate = originalLicenseDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
    }

    public String getmCEStatus() {
        return mCEStatus;
    }

    public void setmCEStatus(String mCEStatus) {
        this.mCEStatus = mCEStatus;
    }

    public String getDesignatedSupervisor() {
        return designatedSupervisor;
    }

    public void setDesignatedSupervisor(String designatedSupervisor) {
        this.designatedSupervisor = designatedSupervisor;
    }

    public String getAgencyIdentifier() {
        return agencyIdentifier;
    }

    public void setAgencyIdentifier(String agencyIdentifier) {
        this.agencyIdentifier = agencyIdentifier;
    }

    @Override
    public String toString() {
        return "EstateAgent{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", licenseeName='" + licenseeName + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", mailingCounty='" + mailingCounty + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", licenseStatus='" + licenseStatus + '\'' +
                ", originalLicenseDate='" + originalLicenseDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", educationStatus='" + educationStatus + '\'' +
                ", mCEStatus='" + mCEStatus + '\'' +
                ", designatedSupervisor='" + designatedSupervisor + '\'' +
                ", relatedLicenseNumber='" + relatedLicenseNumber + '\'' +
                ", relatedLicenseType='" + relatedLicenseType + '\'' +
                ", relatedLicenseStartDate='" + relatedLicenseStartDate + '\'' +
                ", agencyIdentifier='" + agencyIdentifier + '\'' +
                '}';
    }
}
