
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
    "doc_count",
    "buckets"
})
@Generated("jsonschema2pojo")
public class LotsAwardDecisionDate {

    @JsonProperty("doc_count")
    private Long docCount;
    @JsonProperty("buckets")
    private List<Bucket> buckets = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("doc_count")
    public Long getDocCount() {
        return docCount;
    }

    @JsonProperty("doc_count")
    public void setDocCount(Long docCount) {
        this.docCount = docCount;
    }

    @JsonProperty("buckets")
    public List<Bucket> getBuckets() {
        return buckets;
    }

    @JsonProperty("buckets")
    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
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
