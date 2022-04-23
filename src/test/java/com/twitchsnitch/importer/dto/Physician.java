package com.twitchsnitch.importer.dto;

public class Physician {

    private String nPI; //	1245797539 Nation Provider Identifier (NPI) National Plan and Provider Enumeration System (NPPES)
    private String pACID; //	4082949383 PECOS Associate Control (PAC) ID Medicare Provider Enrollment and Chain/Ownership System (PECOS)
    private String professionalEnrollmentID; //	I20220103001685
    private String fullName; //	JAMES MICHAEL HOZALSKI
    private String gender; //	M
    private String medicalSchool; //	OHIO UNIVERSITY, COLLEGE OF OSTEOPATHIC MEDICINE
    private String graduationYear; //	2019
    private String primarySpecialty; //	EMERGENCY MEDICINE
    private String organizationLegalName; //	EMERGENCY MEDICINE SPECIALISTS INC
    private String groupPracticePACID; //	7315851359
    private String numberOfGroupPracticeMembers; //	111
    private String address; //3535 Pentagon Blvd Beavercreek OH 45431-1705
    private String telephone; //	9377024500
    private String professionalAcceptsMedicareAssignment; //	Y - Accepts Medicare approved amount as payment in full
    private String groupAcceptsMedicareAssignment; //	Y - Accepts Medicare approved amount as payment in full

    public String getnPI() {
        return nPI;
    }

    public void setnPI(String nPI) {
        this.nPI = nPI;
    }

    public String getpACID() {
        return pACID;
    }

    public void setpACID(String pACID) {
        this.pACID = pACID;
    }

    public String getProfessionalEnrollmentID() {
        return professionalEnrollmentID;
    }

    public void setProfessionalEnrollmentID(String professionalEnrollmentID) {
        this.professionalEnrollmentID = professionalEnrollmentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMedicalSchool() {
        return medicalSchool;
    }

    public void setMedicalSchool(String medicalSchool) {
        this.medicalSchool = medicalSchool;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getPrimarySpecialty() {
        return primarySpecialty;
    }

    public void setPrimarySpecialty(String primarySpecialty) {
        this.primarySpecialty = primarySpecialty;
    }

    public String getOrganizationLegalName() {
        return organizationLegalName;
    }

    public void setOrganizationLegalName(String organizationLegalName) {
        this.organizationLegalName = organizationLegalName;
    }

    public String getGroupPracticePACID() {
        return groupPracticePACID;
    }

    public void setGroupPracticePACID(String groupPracticePACID) {
        this.groupPracticePACID = groupPracticePACID;
    }

    public String getNumberOfGroupPracticeMembers() {
        return numberOfGroupPracticeMembers;
    }

    public void setNumberOfGroupPracticeMembers(String numberOfGroupPracticeMembers) {
        this.numberOfGroupPracticeMembers = numberOfGroupPracticeMembers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfessionalAcceptsMedicareAssignment() {
        return professionalAcceptsMedicareAssignment;
    }

    public void setProfessionalAcceptsMedicareAssignment(String professionalAcceptsMedicareAssignment) {
        this.professionalAcceptsMedicareAssignment = professionalAcceptsMedicareAssignment;
    }

    public String getGroupAcceptsMedicareAssignment() {
        return groupAcceptsMedicareAssignment;
    }

    public void setGroupAcceptsMedicareAssignment(String groupAcceptsMedicareAssignment) {
        this.groupAcceptsMedicareAssignment = groupAcceptsMedicareAssignment;
    }

    @Override
    public String toString() {
        return "Physician{" +
                "nPI='" + nPI + '\'' +
                ", pACID='" + pACID + '\'' +
                ", professionalEnrollmentID='" + professionalEnrollmentID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", medicalSchool='" + medicalSchool + '\'' +
                ", graduationYear='" + graduationYear + '\'' +
                ", primarySpecialty='" + primarySpecialty + '\'' +
                ", organizationLegalName='" + organizationLegalName + '\'' +
                ", groupPracticePACID='" + groupPracticePACID + '\'' +
                ", numberOfGroupPracticeMembers='" + numberOfGroupPracticeMembers + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", professionalAcceptsMedicareAssignment='" + professionalAcceptsMedicareAssignment + '\'' +
                ", groupAcceptsMedicareAssignment='" + groupAcceptsMedicareAssignment + '\'' +
                '}';
    }
}
