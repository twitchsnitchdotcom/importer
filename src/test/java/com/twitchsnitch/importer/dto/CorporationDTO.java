package com.twitchsnitch.importer.dto;

public class CorporationDTO {

    private String url;
    private String UBI; //	604890412 Unified Business Identifier (UBI)
    private String businessName; //	SMALL TIME CONSTRUCTION LLC
    private String physicalAddress; //	2205 Reservation Rd Oak Harbor WA 98277-7417 USA
    private String mailingAddress; //	2205 Reservation Rd Oak Harbor WA 98277-7417 USA
    private String type; //	PROFIT - WA LIMITED LIABILITY COMPANY
    private String category; //	Limited Liability Regular
    private String recordStatus; // 	Active Pending
    private String incorporationState; //	WASHINGTON
    private String incorporationDate; //	2022-06-01
    private String expirationDate; //	2023-06-30
    private String duration; //	PERPETUAL
    private String email; //smalltimeconstructionllc(a)gmail.com

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUBI() {
        return UBI;
    }

    public void setUBI(String UBI) {
        this.UBI = UBI;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getIncorporationState() {
        return incorporationState;
    }

    public void setIncorporationState(String incorporationState) {
        this.incorporationState = incorporationState;
    }

    public String getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
