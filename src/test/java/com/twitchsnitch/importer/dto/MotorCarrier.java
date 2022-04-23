package com.twitchsnitch.importer.dto;

public class MotorCarrier {

    private String dOTNumber;//	3665722
    private String legalName; //	WATCH DOG TOWING RECOVERY LLC
    private String physicalAddress;//	2598 Beech St Morrow  GA 30260 US
    private String mailingAddress;//	2598 Beech St Morrow  GA 30260-3203 US
    private String telephone;//	(770) 256-1249
    private String email;	//watchdogtowingrecovery(a)gmail.com
    private String carrierOperation; //	Intrastate Non-Hazmat
    private String placardableHazardousMaterialsThreshold;//	N
    private String passengercarrierThreshold; //	N
    private String mCS150FiledDate; //2021-06-25 (Motor Carrier Identification Report)
    private String oversightState;//	GA
    private String numberOfPowerUnits;//	1
    private String numberOfDrivers;//	4
    private String addDate;//	2021-06-25

    public String getdOTNumber() {
        return dOTNumber;
    }

    public void setdOTNumber(String dOTNumber) {
        this.dOTNumber = dOTNumber;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
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

    public String getCarrierOperation() {
        return carrierOperation;
    }

    public void setCarrierOperation(String carrierOperation) {
        this.carrierOperation = carrierOperation;
    }

    public String getPlacardableHazardousMaterialsThreshold() {
        return placardableHazardousMaterialsThreshold;
    }

    public void setPlacardableHazardousMaterialsThreshold(String placardableHazardousMaterialsThreshold) {
        this.placardableHazardousMaterialsThreshold = placardableHazardousMaterialsThreshold;
    }

    public String getPassengercarrierThreshold() {
        return passengercarrierThreshold;
    }

    public void setPassengercarrierThreshold(String passengercarrierThreshold) {
        this.passengercarrierThreshold = passengercarrierThreshold;
    }

    public String getmCS150FiledDate() {
        return mCS150FiledDate;
    }

    public void setmCS150FiledDate(String mCS150FiledDate) {
        this.mCS150FiledDate = mCS150FiledDate;
    }

    public String getOversightState() {
        return oversightState;
    }

    public void setOversightState(String oversightState) {
        this.oversightState = oversightState;
    }

    public String getNumberOfPowerUnits() {
        return numberOfPowerUnits;
    }

    public void setNumberOfPowerUnits(String numberOfPowerUnits) {
        this.numberOfPowerUnits = numberOfPowerUnits;
    }

    public String getNumberOfDrivers() {
        return numberOfDrivers;
    }

    public void setNumberOfDrivers(String numberOfDrivers) {
        this.numberOfDrivers = numberOfDrivers;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "MotorCarrier{" +
                "dOTNumber='" + dOTNumber + '\'' +
                ", legalName='" + legalName + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", carrierOperation='" + carrierOperation + '\'' +
                ", placardableHazardousMaterialsThreshold='" + placardableHazardousMaterialsThreshold + '\'' +
                ", passengercarrierThreshold='" + passengercarrierThreshold + '\'' +
                ", mCS150FiledDate='" + mCS150FiledDate + '\'' +
                ", oversightState='" + oversightState + '\'' +
                ", numberOfPowerUnits='" + numberOfPowerUnits + '\'' +
                ", numberOfDrivers='" + numberOfDrivers + '\'' +
                ", addDate='" + addDate + '\'' +
                '}';
    }
}
