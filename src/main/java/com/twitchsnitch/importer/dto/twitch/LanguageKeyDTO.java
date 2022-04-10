package com.twitchsnitch.importer.dto.twitch;

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
        "en",
        "zh",
        "ja",
        "ko",
        "es",
        "fr",
        "de",
        "it",
        "pt",
        "sv",
        "no",
        "nl",
        "fi",
        "el",
        "ru",
        "tr",
        "cs",
        "hu",
        "ar",
        "bg",
        "th",
        "vi",
        "asl",
        "other",
        "uk",
        "pl",
        "hi",
        "ca",
        "zh-HK",
        "zh-TW",
        "da",
        "id",
        "ms",
        "pt-BR",
        "ro",
        "sk",
        "es-MX"
})
@Generated("jsonschema2pojo")
public class LanguageKeyDTO {

    @JsonProperty("en")
    private String en;
    @JsonProperty("zh")
    private String zh;
    @JsonProperty("ja")
    private String ja;
    @JsonProperty("ko")
    private String ko;
    @JsonProperty("es")
    private String es;
    @JsonProperty("fr")
    private String fr;
    @JsonProperty("de")
    private String de;
    @JsonProperty("it")
    private String it;
    @JsonProperty("pt")
    private String pt;
    @JsonProperty("sv")
    private String sv;
    @JsonProperty("no")
    private String no;
    @JsonProperty("nl")
    private String nl;
    @JsonProperty("fi")
    private String fi;
    @JsonProperty("el")
    private String el;
    @JsonProperty("ru")
    private String ru;
    @JsonProperty("tr")
    private String tr;
    @JsonProperty("cs")
    private String cs;
    @JsonProperty("hu")
    private String hu;
    @JsonProperty("ar")
    private String ar;
    @JsonProperty("bg")
    private String bg;
    @JsonProperty("th")
    private String th;
    @JsonProperty("vi")
    private String vi;
    @JsonProperty("asl")
    private String asl;
    @JsonProperty("other")
    private String other;
    @JsonProperty("uk")
    private String uk;
    @JsonProperty("pl")
    private String pl;
    @JsonProperty("hi")
    private String hi;
    @JsonProperty("ca")
    private String ca;
    @JsonProperty("zh-HK")
    private String zhHK;
    @JsonProperty("zh-TW")
    private String zhTW;
    @JsonProperty("da")
    private String da;
    @JsonProperty("id")
    private String id;
    @JsonProperty("ms")
    private String ms;
    @JsonProperty("pt-BR")
    private String ptBR;
    @JsonProperty("ro")
    private String ro;
    @JsonProperty("sk")
    private String sk;
    @JsonProperty("es-MX")
    private String esMX;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("en")
    public String getEn() {
        return en;
    }

    @JsonProperty("en")
    public void setEn(String en) {
        this.en = en;
    }

    @JsonProperty("zh")
    public String getZh() {
        return zh;
    }

    @JsonProperty("zh")
    public void setZh(String zh) {
        this.zh = zh;
    }

    @JsonProperty("ja")
    public String getJa() {
        return ja;
    }

    @JsonProperty("ja")
    public void setJa(String ja) {
        this.ja = ja;
    }

    @JsonProperty("ko")
    public String getKo() {
        return ko;
    }

    @JsonProperty("ko")
    public void setKo(String ko) {
        this.ko = ko;
    }

    @JsonProperty("es")
    public String getEs() {
        return es;
    }

    @JsonProperty("es")
    public void setEs(String es) {
        this.es = es;
    }

    @JsonProperty("fr")
    public String getFr() {
        return fr;
    }

    @JsonProperty("fr")
    public void setFr(String fr) {
        this.fr = fr;
    }

    @JsonProperty("de")
    public String getDe() {
        return de;
    }

    @JsonProperty("de")
    public void setDe(String de) {
        this.de = de;
    }

    @JsonProperty("it")
    public String getIt() {
        return it;
    }

    @JsonProperty("it")
    public void setIt(String it) {
        this.it = it;
    }

    @JsonProperty("pt")
    public String getPt() {
        return pt;
    }

    @JsonProperty("pt")
    public void setPt(String pt) {
        this.pt = pt;
    }

    @JsonProperty("sv")
    public String getSv() {
        return sv;
    }

    @JsonProperty("sv")
    public void setSv(String sv) {
        this.sv = sv;
    }

    @JsonProperty("no")
    public String getNo() {
        return no;
    }

    @JsonProperty("no")
    public void setNo(String no) {
        this.no = no;
    }

    @JsonProperty("nl")
    public String getNl() {
        return nl;
    }

    @JsonProperty("nl")
    public void setNl(String nl) {
        this.nl = nl;
    }

    @JsonProperty("fi")
    public String getFi() {
        return fi;
    }

    @JsonProperty("fi")
    public void setFi(String fi) {
        this.fi = fi;
    }

    @JsonProperty("el")
    public String getEl() {
        return el;
    }

    @JsonProperty("el")
    public void setEl(String el) {
        this.el = el;
    }

    @JsonProperty("ru")
    public String getRu() {
        return ru;
    }

    @JsonProperty("ru")
    public void setRu(String ru) {
        this.ru = ru;
    }

    @JsonProperty("tr")
    public String getTr() {
        return tr;
    }

    @JsonProperty("tr")
    public void setTr(String tr) {
        this.tr = tr;
    }

    @JsonProperty("cs")
    public String getCs() {
        return cs;
    }

    @JsonProperty("cs")
    public void setCs(String cs) {
        this.cs = cs;
    }

    @JsonProperty("hu")
    public String getHu() {
        return hu;
    }

    @JsonProperty("hu")
    public void setHu(String hu) {
        this.hu = hu;
    }

    @JsonProperty("ar")
    public String getAr() {
        return ar;
    }

    @JsonProperty("ar")
    public void setAr(String ar) {
        this.ar = ar;
    }

    @JsonProperty("bg")
    public String getBg() {
        return bg;
    }

    @JsonProperty("bg")
    public void setBg(String bg) {
        this.bg = bg;
    }

    @JsonProperty("th")
    public String getTh() {
        return th;
    }

    @JsonProperty("th")
    public void setTh(String th) {
        this.th = th;
    }

    @JsonProperty("vi")
    public String getVi() {
        return vi;
    }

    @JsonProperty("vi")
    public void setVi(String vi) {
        this.vi = vi;
    }

    @JsonProperty("asl")
    public String getAsl() {
        return asl;
    }

    @JsonProperty("asl")
    public void setAsl(String asl) {
        this.asl = asl;
    }

    @JsonProperty("other")
    public String getOther() {
        return other;
    }

    @JsonProperty("other")
    public void setOther(String other) {
        this.other = other;
    }

    @JsonProperty("uk")
    public String getUk() {
        return uk;
    }

    @JsonProperty("uk")
    public void setUk(String uk) {
        this.uk = uk;
    }

    @JsonProperty("pl")
    public String getPl() {
        return pl;
    }

    @JsonProperty("pl")
    public void setPl(String pl) {
        this.pl = pl;
    }

    @JsonProperty("hi")
    public String getHi() {
        return hi;
    }

    @JsonProperty("hi")
    public void setHi(String hi) {
        this.hi = hi;
    }

    @JsonProperty("ca")
    public String getCa() {
        return ca;
    }

    @JsonProperty("ca")
    public void setCa(String ca) {
        this.ca = ca;
    }

    @JsonProperty("zh-HK")
    public String getZhHK() {
        return zhHK;
    }

    @JsonProperty("zh-HK")
    public void setZhHK(String zhHK) {
        this.zhHK = zhHK;
    }

    @JsonProperty("zh-TW")
    public String getZhTW() {
        return zhTW;
    }

    @JsonProperty("zh-TW")
    public void setZhTW(String zhTW) {
        this.zhTW = zhTW;
    }

    @JsonProperty("da")
    public String getDa() {
        return da;
    }

    @JsonProperty("da")
    public void setDa(String da) {
        this.da = da;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("ms")
    public String getMs() {
        return ms;
    }

    @JsonProperty("ms")
    public void setMs(String ms) {
        this.ms = ms;
    }

    @JsonProperty("pt-BR")
    public String getPtBR() {
        return ptBR;
    }

    @JsonProperty("pt-BR")
    public void setPtBR(String ptBR) {
        this.ptBR = ptBR;
    }

    @JsonProperty("ro")
    public String getRo() {
        return ro;
    }

    @JsonProperty("ro")
    public void setRo(String ro) {
        this.ro = ro;
    }

    @JsonProperty("sk")
    public String getSk() {
        return sk;
    }

    @JsonProperty("sk")
    public void setSk(String sk) {
        this.sk = sk;
    }

    @JsonProperty("es-MX")
    public String getEsMX() {
        return esMX;
    }

    @JsonProperty("es-MX")
    public void setEsMX(String esMX) {
        this.esMX = esMX;
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