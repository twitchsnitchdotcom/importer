
package com.twitchsnitch.importer.proxy;

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
    "supportsHttps",
    "protocol",
    "ip",
    "port",
    "get",
    "post",
    "cookies",
    "referer",
    "user-agent",
    "anonymityLevel",
    "websites",
    "country",
    "unixTimestampMs",
    "tsChecked",
    "unixTimestamp",
    "curl",
    "ipPort",
    "type",
    "speed",
    "otherProtocols",
    "verifiedSecondsAgo"
})
@Generated("jsonschema2pojo")
public class ProxyDTO {

    @JsonProperty("supportsHttps")
    private Boolean supportsHttps;
    @JsonProperty("protocol")
    private String protocol;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("port")
    private String port;
    @JsonProperty("get")
    private Boolean get;
    @JsonProperty("post")
    private Boolean post;
    @JsonProperty("cookies")
    private Boolean cookies;
    @JsonProperty("referer")
    private Boolean referer;
    @JsonProperty("user-agent")
    private Boolean userAgent;
    @JsonProperty("anonymityLevel")
    private Long anonymityLevel;
    @JsonProperty("websites")
    private Websites websites;
    @JsonProperty("country")
    private String country;
    @JsonProperty("unixTimestampMs")
    private Long unixTimestampMs;
    @JsonProperty("tsChecked")
    private Long tsChecked;
    @JsonProperty("unixTimestamp")
    private Long unixTimestamp;
    @JsonProperty("curl")
    private String curl;
    @JsonProperty("ipPort")
    private String ipPort;
    @JsonProperty("type")
    private String type;
    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("otherProtocols")
    private OtherProtocols otherProtocols;
    @JsonProperty("verifiedSecondsAgo")
    private Long verifiedSecondsAgo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("supportsHttps")
    public Boolean getSupportsHttps() {
        return supportsHttps;
    }

    @JsonProperty("supportsHttps")
    public void setSupportsHttps(Boolean supportsHttps) {
        this.supportsHttps = supportsHttps;
    }

    @JsonProperty("protocol")
    public String getProtocol() {
        return protocol;
    }

    @JsonProperty("protocol")
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonProperty("port")
    public String getPort() {
        return port;
    }

    @JsonProperty("port")
    public void setPort(String port) {
        this.port = port;
    }

    @JsonProperty("get")
    public Boolean getGet() {
        return get;
    }

    @JsonProperty("get")
    public void setGet(Boolean get) {
        this.get = get;
    }

    @JsonProperty("post")
    public Boolean getPost() {
        return post;
    }

    @JsonProperty("post")
    public void setPost(Boolean post) {
        this.post = post;
    }

    @JsonProperty("cookies")
    public Boolean getCookies() {
        return cookies;
    }

    @JsonProperty("cookies")
    public void setCookies(Boolean cookies) {
        this.cookies = cookies;
    }

    @JsonProperty("referer")
    public Boolean getReferer() {
        return referer;
    }

    @JsonProperty("referer")
    public void setReferer(Boolean referer) {
        this.referer = referer;
    }

    @JsonProperty("user-agent")
    public Boolean getUserAgent() {
        return userAgent;
    }

    @JsonProperty("user-agent")
    public void setUserAgent(Boolean userAgent) {
        this.userAgent = userAgent;
    }

    @JsonProperty("anonymityLevel")
    public Long getAnonymityLevel() {
        return anonymityLevel;
    }

    @JsonProperty("anonymityLevel")
    public void setAnonymityLevel(Long anonymityLevel) {
        this.anonymityLevel = anonymityLevel;
    }

    @JsonProperty("websites")
    public Websites getWebsites() {
        return websites;
    }

    @JsonProperty("websites")
    public void setWebsites(Websites websites) {
        this.websites = websites;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("unixTimestampMs")
    public Long getUnixTimestampMs() {
        return unixTimestampMs;
    }

    @JsonProperty("unixTimestampMs")
    public void setUnixTimestampMs(Long unixTimestampMs) {
        this.unixTimestampMs = unixTimestampMs;
    }

    @JsonProperty("tsChecked")
    public Long getTsChecked() {
        return tsChecked;
    }

    @JsonProperty("tsChecked")
    public void setTsChecked(Long tsChecked) {
        this.tsChecked = tsChecked;
    }

    @JsonProperty("unixTimestamp")
    public Long getUnixTimestamp() {
        return unixTimestamp;
    }

    @JsonProperty("unixTimestamp")
    public void setUnixTimestamp(Long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    @JsonProperty("curl")
    public String getCurl() {
        return curl;
    }

    @JsonProperty("curl")
    public void setCurl(String curl) {
        this.curl = curl;
    }

    @JsonProperty("ipPort")
    public String getIpPort() {
        return ipPort;
    }

    @JsonProperty("ipPort")
    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("speed")
    public Double getSpeed() {
        return speed;
    }

    @JsonProperty("speed")
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @JsonProperty("otherProtocols")
    public OtherProtocols getOtherProtocols() {
        return otherProtocols;
    }

    @JsonProperty("otherProtocols")
    public void setOtherProtocols(OtherProtocols otherProtocols) {
        this.otherProtocols = otherProtocols;
    }

    @JsonProperty("verifiedSecondsAgo")
    public Long getVerifiedSecondsAgo() {
        return verifiedSecondsAgo;
    }

    @JsonProperty("verifiedSecondsAgo")
    public void setVerifiedSecondsAgo(Long verifiedSecondsAgo) {
        this.verifiedSecondsAgo = verifiedSecondsAgo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "ProxyDTO{" +
                "supportsHttps=" + supportsHttps +
                ", protocol='" + protocol + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", get=" + get +
                ", post=" + post +
                ", cookies=" + cookies +
                ", referer=" + referer +
                ", userAgent=" + userAgent +
                ", anonymityLevel=" + anonymityLevel +
                ", websites=" + websites +
                ", country='" + country + '\'' +
                ", unixTimestampMs=" + unixTimestampMs +
                ", tsChecked=" + tsChecked +
                ", unixTimestamp=" + unixTimestamp +
                ", curl='" + curl + '\'' +
                ", ipPort='" + ipPort + '\'' +
                ", type='" + type + '\'' +
                ", speed=" + speed +
                ", otherProtocols=" + otherProtocols +
                ", verifiedSecondsAgo=" + verifiedSecondsAgo +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
