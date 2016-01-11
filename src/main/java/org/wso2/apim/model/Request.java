package org.wso2.apim.model;

/**
 * Created by upekka on 11/01/16.
 */
public class Request {
    private String app_key;
    private String api_key;
    private String resource_key;

    private String app_tier;
    private String api_tier;
    private String resource_tier;

    private String ip_address;
    private String http_method;

    public Request(String app_key, String api_key, String resource_key, String app_tier, String api_tier, String resource_tier, String ip_address, String http_method) {
        this.app_key = app_key;
        this.api_key = api_key;
        this.resource_key = resource_key;
        this.app_tier = app_tier;
        this.api_tier = api_tier;
        this.resource_tier = resource_tier;
        this.ip_address = ip_address;
        this.http_method = http_method;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getResource_key() {
        return resource_key;
    }

    public void setResource_key(String resource_key) {
        this.resource_key = resource_key;
    }

    public String getApp_tier() {
        return app_tier;
    }

    public void setApp_tier(String app_tier) {
        this.app_tier = app_tier;
    }

    public String getApi_tier() {
        return api_tier;
    }

    public void setApi_tier(String api_tier) {
        this.api_tier = api_tier;
    }

    public String getResource_tier() {
        return resource_tier;
    }

    public void setResource_tier(String resource_tier) {
        this.resource_tier = resource_tier;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }
}
