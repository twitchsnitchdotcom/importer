
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
    "hits",
    "aggregations",
    "sortBy"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("hits")
    private Hits hits;

    @JsonProperty("aggregations")
    private Aggregations aggregations;

    @JsonProperty("sortBy")
    private SortBy sortBy;

    @JsonProperty("hits")
    public Hits getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(Hits hits) {
        this.hits = hits;
    }

    @JsonProperty("aggregations")
    public Aggregations getAggregations() {
        return aggregations;
    }

    @JsonProperty("aggregations")
    public void setAggregations(Aggregations aggregations) {
        this.aggregations = aggregations;
    }

    @JsonProperty("sortBy")
    public SortBy getSortBy() {
        return sortBy;
    }

    @JsonProperty("sortBy")
    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }


}
