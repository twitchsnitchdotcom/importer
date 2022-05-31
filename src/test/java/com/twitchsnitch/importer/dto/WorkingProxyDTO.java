package com.twitchsnitch.importer.dto;

public class WorkingProxyDTO {

    public WorkingProxyDTO(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    private String ip;
    private Integer port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
