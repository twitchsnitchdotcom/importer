package com.twitchsnitch.importer.dto;

public class LawyerDTO {

    private String url;
    private String wSBANumber; //59130
    private String fullName; //	Allyson Boney Evans
    private String lastName; //	Evans
    private String firstName;//	Allyson Boney
    private String address; //	13516 N Park Ave N Seattle  WA 98133-7427 United States
    private String phone;	//(512) 820-0631
    private String email;	//aboneyevans(a)gmail.com
    private String licenseType; //	Lawyer
    private String admitDate; //	2022-03-04
    private String licenseStatus; //	Active
    private String eligibleToPractice; //	Yes

    private PracticeInformationDTO practiceInformationDTO;

    private CompanyPracticeDTO companyPracticeDTO;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PracticeInformationDTO getPracticeInformationDTO() {
        return practiceInformationDTO;
    }

    public void setPracticeInformationDTO(PracticeInformationDTO practiceInformationDTO) {
        this.practiceInformationDTO = practiceInformationDTO;
    }

    public CompanyPracticeDTO getCompanyPracticeDTO() {
        return companyPracticeDTO;
    }

    public void setCompanyPracticeDTO(CompanyPracticeDTO companyPracticeDTO) {
        this.companyPracticeDTO = companyPracticeDTO;
    }

    public CompanyPracticeDTO getCompanyPractice() {
        return companyPracticeDTO;
    }

    public void setCompanyPractice(CompanyPracticeDTO companyPracticeDTO) {
        this.companyPracticeDTO = companyPracticeDTO;
    }

    public PracticeInformationDTO getPracticeInformation() {
        return practiceInformationDTO;
    }

    public void setPracticeInformation(PracticeInformationDTO practiceInformationDTO) {
        this.practiceInformationDTO = practiceInformationDTO;
    }

    public String getwSBANumber() {
        return wSBANumber;
    }

    public void setwSBANumber(String wSBANumber) {
        this.wSBANumber = wSBANumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public String getEligibleToPractice() {
        return eligibleToPractice;
    }

    public void setEligibleToPractice(String eligibleToPractice) {
        this.eligibleToPractice = eligibleToPractice;
    }

    @Override
    public String toString() {
        return "Lawyer{" +
                "wSBANumber='" + wSBANumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", admitDate='" + admitDate + '\'' +
                ", licenseStatus='" + licenseStatus + '\'' +
                ", eligibleToPractice='" + eligibleToPractice + '\'' +
                ", practiceInformation=" + practiceInformationDTO.toString() +
                '}';
    }
}
