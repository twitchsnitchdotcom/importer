package com.twitchsnitch.importer.dto.sully.games;

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
import com.twitchsnitch.importer.dto.sully.games.GamesDatum;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"draw",
"recordsTotal",
"recordsFiltered",
"data",
"progressProps"
})
@Generated("jsonschema2pojo")
public class GamesTable {
    private Integer days;
    private LinkedHashMap map;
@JsonProperty("draw")
private Long draw;
@JsonProperty("recordsTotal")
private Long recordsTotal;
@JsonProperty("recordsFiltered")
private Long recordsFiltered;
@JsonProperty("data")
private List<GamesDatum> data = null;
@JsonProperty("progressProps")
private List<ProgressProp> progressProps = null;
    private Map<String, Double> progressMap = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("draw")
public Long getDraw() {
return draw;
}

@JsonProperty("draw")
public void setDraw(Long draw) {
this.draw = draw;
}

@JsonProperty("recordsTotal")
public Long getRecordsTotal() {
return recordsTotal;
}

@JsonProperty("recordsTotal")
public void setRecordsTotal(Long recordsTotal) {
this.recordsTotal = recordsTotal;
}

@JsonProperty("recordsFiltered")
public Long getRecordsFiltered() {
return recordsFiltered;
}

@JsonProperty("recordsFiltered")
public void setRecordsFiltered(Long recordsFiltered) {
this.recordsFiltered = recordsFiltered;
}

@JsonProperty("data")
public List<GamesDatum> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<GamesDatum> data) {
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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

    public void setProgressMap(Map<String, Double> progressMap) {
        this.progressMap = progressMap;
    }

    public LinkedHashMap getMap() {
        return map;
    }

    public void setMap(LinkedHashMap map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "GamesTable{" +
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
}