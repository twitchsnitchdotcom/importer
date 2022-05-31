
package com.twitchsnitch.importer.opentender.tendersearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "country",
    "scores",
    "indicator",
    "cpv",
    "date",
    "score",
    "cpv_name"
})
@Generated("jsonschema2pojo")
public class OtDetailed {

    @JsonProperty("country")
    private String country;
    @JsonProperty("scores")
    private List<Score> scores = null;
    @JsonProperty("indicator")
    private IndicatorItems indicator;
    @JsonProperty("cpv")
    private String cpv;
    @JsonProperty("date")
    private String date;
    @JsonProperty("score")
    private Score score;
    @JsonProperty("cpv_name")
    private String cpvName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("scores")
    public List<Score> getScores() {
        return scores;
    }

    @JsonProperty("scores")
    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @JsonProperty("indicator")
    public IndicatorItems getIndicator() {
        return indicator;
    }

    @JsonProperty("indicator")
    public void setIndicator(IndicatorItems indicator) {
        this.indicator = indicator;
    }

    @JsonProperty("cpv")
    public String getCpv() {
        return cpv;
    }

    @JsonProperty("cpv")
    public void setCpv(String cpv) {
        this.cpv = cpv;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("score")
    public Score getScore() {
        return score;
    }

    @JsonProperty("score")
    public void setScore(Score score) {
        this.score = score;
    }

    @JsonProperty("cpv_name")
    public String getCpvName() {
        return cpvName;
    }

    @JsonProperty("cpv_name")
    public void setCpvName(String cpvName) {
        this.cpvName = cpvName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
