
package com.twitchsnitch.importer.opentender.tendersearch;

import java.util.HashMap;
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
    "_lots_awardDecisionDate"
})
@Generated("jsonschema2pojo")
public class Aggregations {

    @JsonProperty("_lots_awardDecisionDate")
    private LotsAwardDecisionDate lotsAwardDecisionDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_lots_awardDecisionDate")
    public LotsAwardDecisionDate getLotsAwardDecisionDate() {
        return lotsAwardDecisionDate;
    }

    @JsonProperty("_lots_awardDecisionDate")
    public void setLotsAwardDecisionDate(LotsAwardDecisionDate lotsAwardDecisionDate) {
        this.lotsAwardDecisionDate = lotsAwardDecisionDate;
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
