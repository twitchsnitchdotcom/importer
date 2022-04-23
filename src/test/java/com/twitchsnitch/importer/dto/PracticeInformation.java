package com.twitchsnitch.importer.dto;

public class PracticeInformation {

    private String firmSize; //Not Specified
    private String practiceAreas;//	None Specified
    private String otherLanguagesSpoken;//	None Specified
    private String privatePractice; //	No
    private String committees;//	None

    public String getFirmSize() {
        return firmSize;
    }

    public void setFirmSize(String firmSize) {
        this.firmSize = firmSize;
    }

    public String getPracticeAreas() {
        return practiceAreas;
    }

    public void setPracticeAreas(String practiceAreas) {
        this.practiceAreas = practiceAreas;
    }

    public String getOtherLanguagesSpoken() {
        return otherLanguagesSpoken;
    }

    public void setOtherLanguagesSpoken(String otherLanguagesSpoken) {
        this.otherLanguagesSpoken = otherLanguagesSpoken;
    }

    public String getPrivatePractice() {
        return privatePractice;
    }

    public void setPrivatePractice(String privatePractice) {
        this.privatePractice = privatePractice;
    }

    public String getCommittees() {
        return committees;
    }

    public void setCommittees(String committees) {
        this.committees = committees;
    }

    @Override
    public String toString() {
        return "PracticeInformation{" +
                "firmSize='" + firmSize + '\'' +
                ", practiceAreas='" + practiceAreas + '\'' +
                ", otherLanguagesSpoken='" + otherLanguagesSpoken + '\'' +
                ", privatePractice='" + privatePractice + '\'' +
                ", committees='" + committees + '\'' +
                '}';
    }
}
