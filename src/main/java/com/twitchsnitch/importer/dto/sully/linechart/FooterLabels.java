package com.twitchsnitch.importer.dto.sully.linechart;

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
"222",
"8375",
"47738",
"74446",
"81530"
})
@Generated("jsonschema2pojo")
public class FooterLabels {

@JsonProperty("222")
private String _222;
@JsonProperty("8375")
private String _8375;
@JsonProperty("47738")
private String _47738;
@JsonProperty("74446")
private String _74446;
@JsonProperty("81530")
private String _81530;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("222")
public String get222() {
return _222;
}

@JsonProperty("222")
public void set222(String _222) {
this._222 = _222;
}

@JsonProperty("8375")
public String get8375() {
return _8375;
}

@JsonProperty("8375")
public void set8375(String _8375) {
this._8375 = _8375;
}

@JsonProperty("47738")
public String get47738() {
return _47738;
}

@JsonProperty("47738")
public void set47738(String _47738) {
this._47738 = _47738;
}

@JsonProperty("74446")
public String get74446() {
return _74446;
}

@JsonProperty("74446")
public void set74446(String _74446) {
this._74446 = _74446;
}

@JsonProperty("81530")
public String get81530() {
return _81530;
}

@JsonProperty("81530")
public void set81530(String _81530) {
this._81530 = _81530;
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
