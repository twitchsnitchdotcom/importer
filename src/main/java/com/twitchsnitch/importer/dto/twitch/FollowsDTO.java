package com.twitchsnitch.importer.dto.twitch;

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
"total",
"data",
"pagination"
})
@Generated("jsonschema2pojo")
public class FollowsDTO {

    private String url;
    private Map map;
@JsonProperty("total")
private Long total;
@JsonProperty("data")
private List<FollowersDatum> data = null;
@JsonProperty("pagination")
private Pagination pagination;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("total")
public Long getTotal() {
return total;
}

@JsonProperty("total")
public void setTotal(Long total) {
this.total = total;
}

@JsonProperty("data")
public List<FollowersDatum> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<FollowersDatum> data) {
this.data = data;
}

@JsonProperty("pagination")
public Pagination getPagination() {
return pagination;
}

@JsonProperty("pagination")
public void setPagination(Pagination pagination) {
this.pagination = pagination;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}