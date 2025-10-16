package org.acme.model.rest;

public class NativeName {
    private String common;
    private String official;

    public NativeName(String official, String common) {
        this.official = official;
        this.common = common;
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
}
