package com.twitchsnitch.importer.dto.sully;


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
"displaytext",
"value",
"description",
"itemtype",
"siteurl",
"boxart"
})
@Generated("jsonschema2pojo")
public class SearchDTO {

    private Map map;

@JsonProperty("displaytext")
private String displaytext;
@JsonProperty("value")
private Long value;
@JsonProperty("description")
private String description;
@JsonProperty("itemtype")
private Long itemtype;
@JsonProperty("siteurl")
private String siteurl;
@JsonProperty("boxart")
private String boxart;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("displaytext")
public String getDisplaytext() {
return displaytext;
}

@JsonProperty("displaytext")
public void setDisplaytext(String displaytext) {
this.displaytext = displaytext;
}

@JsonProperty("value")
public Long getValue() {
return value;
}

@JsonProperty("value")
public void setValue(Long value) {
this.value = value;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("itemtype")
public Long getItemtype() {
return itemtype;
}

@JsonProperty("itemtype")
public void setItemtype(Long itemtype) {
this.itemtype = itemtype;
}

@JsonProperty("siteurl")
public String getSiteurl() {
return siteurl;
}

@JsonProperty("siteurl")
public void setSiteurl(String siteurl) {
this.siteurl = siteurl;
}

@JsonProperty("boxart")
public String getBoxart() {
return boxart;
}

@JsonProperty("boxart")
public void setBoxart(String boxart) {
this.boxart = boxart;
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
}