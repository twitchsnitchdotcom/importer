package com.twitchsnitch.importer.dto.sully;

public class HomePageElementDTO {

    private Integer rowNumber;
    private String image;
    private String name;
    private String key;
    private String value;
    private Double percentageChange;
    private String valueChange;

    @Override
    public String toString() {
        return "HomePageElementDTO{" +
                "rowNumber=" + rowNumber +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", percentageChange=" + percentageChange +
                ", valueChange='" + valueChange + '\'' +
                '}';
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(Double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getValueChange() {
        return valueChange;
    }

    public void setValueChange(String valueChange) {
        this.valueChange = valueChange;
    }
}
