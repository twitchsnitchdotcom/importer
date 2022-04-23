package com.twitchsnitch.importer.dto;

public class InsuranceProvider {

    private String nationalProducerNumber;//	18703180
    private String lastName;//	BROWN
    private String firstName;//	HERBERT
    private String mailingAddress;//	6255 Lake Gray Blvd Ste 4JACKSONVILLE FL 32244
    private String activeDate;//	2018-03-09
    private String expireDate;//	2021-09-30
    private String businessPhone;//	7034612878
    private String businessEmail;//	pescobar@geicomarine.com
    private String iowaResident;//	No
    private String insuranceProductsProvided; //	Personal Lines

    public String getNationalProducerNumber() {
        return nationalProducerNumber;
    }

    public void setNationalProducerNumber(String nationalProducerNumber) {
        this.nationalProducerNumber = nationalProducerNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getIowaResident() {
        return iowaResident;
    }

    public void setIowaResident(String iowaResident) {
        this.iowaResident = iowaResident;
    }

    public String getInsuranceProductsProvided() {
        return insuranceProductsProvided;
    }

    public void setInsuranceProductsProvided(String insuranceProductsProvided) {
        this.insuranceProductsProvided = insuranceProductsProvided;
    }

    @Override
    public String toString() {
        return "InsuranceProviderIndividualResult{" +
                "nationalProducerNumber='" + nationalProducerNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", activeDate='" + activeDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", businessPhone='" + businessPhone + '\'' +
                ", businessEmail='" + businessEmail + '\'' +
                ", iowaResident='" + iowaResident + '\'' +
                ", insuranceProductsProvided='" + insuranceProductsProvided + '\'' +
                '}';
    }
}
