package com.twitchsnitch.importer.dto;

public class EntitySearchResult {

    private String fullName;
    private String url;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "InsuranceProviderSearchResult{" +
                "fullName='" + fullName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
