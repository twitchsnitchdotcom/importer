
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
    "key",
    "doc_count"
})
@Generated("jsonschema2pojo")
public class Bucket {

    @JsonProperty("key")
    private Long key;
    @JsonProperty("doc_count")
    private Long docCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("key")
    public Long getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(Long key) {
        this.key = key;
    }

    @JsonProperty("doc_count")
    public Long getDocCount() {
        return docCount;
    }

    @JsonProperty("doc_count")
    public void setDocCount(Long docCount) {
        this.docCount = docCount;
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
