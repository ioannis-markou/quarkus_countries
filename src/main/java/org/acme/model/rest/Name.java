package org.acme.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Name {
    private String common;
    @JsonProperty("official")
    private String official;
    @JsonProperty("nativeName")
    private Map<String, NativeName> nativeName;

    public Name(String common, String official,Map<String,NativeName>  nativeNames) {
        this.common = common;
        this.official = official;
        this.nativeName = nativeNames;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOfficial() {
        return official;
    }

    public void setOfficial(String official) {
        this.official = official;
    }

    public Map<String,NativeName>  getNativeNames() {
        return nativeName;
    }

    public void setNativeNames(Map<String,NativeName>  nativeNames) {
        this.nativeName = nativeNames;
    }
}
