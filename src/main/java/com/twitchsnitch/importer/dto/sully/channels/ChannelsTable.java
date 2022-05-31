package com.twitchsnitch.importer.dto.sully.channels;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.twitchsnitch.importer.dto.sully.ProgressProp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "draw",
        "recordsTotal",
        "recordsFiltered",
        "data",
        "progressProps"
})
@Generated("jsonschema2pojo")
public class ChannelsTable {

    private LinkedHashMap map;
    private Integer days;
    @JsonProperty("draw")
    private Integer draw;
    @JsonProperty("recordsTotal")
    private Integer recordsTotal;
    @JsonProperty("recordsFiltered")
    private Integer recordsFiltered;
    @JsonProperty("data")
    private List<ChannelDatum> data = null;
    @JsonProperty("progressProps")
    private List<ProgressProp> progressProps = null;
    private Map<String, Double> progressMap = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("draw")
    public Integer getDraw() {
        return draw;
    }

    @JsonProperty("draw")
    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    @JsonProperty("recordsTotal")
    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    @JsonProperty("recordsTotal")
    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    @JsonProperty("recordsFiltered")
    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    @JsonProperty("recordsFiltered")
    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    @JsonProperty("data")
    public List<ChannelDatum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<ChannelDatum> data) {
        this.data = data;
    }

    @JsonProperty("progressProps")
    public List<ProgressProp> getProgressProps() {
        return progressProps;
    }

    @JsonProperty("progressProps")
    public void setProgressProps(List<ProgressProp> progressProps) {
        this.progressProps = progressProps;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Map<String, Double> getProgressMap() {
        if(this.progressMap == null){
            Map<String, Double> mapValues = new HashMap<>();
            for (ProgressProp prop : this.progressProps) {
                mapValues.put(prop.getKey(), prop.getValue());
            }
            this.progressMap = mapValues;
        }
            return this.progressMap;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public void setProgressMap(Map<String, Double> progressMap) {
        this.progressMap = progressMap;
    }

    @Override
    public String toString() {
        return "ChannelsTable{" +
                "days=" + days +
                ", draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                ", progressProps=" + progressProps +
                ", progressMap=" + progressMap +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public LinkedHashMap getMap() {
        return map;
    }

    public void setMap(LinkedHashMap map) {
        this.map = map;
    }
}



