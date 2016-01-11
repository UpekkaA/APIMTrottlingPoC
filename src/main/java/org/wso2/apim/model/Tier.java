package org.wso2.apim.model;

/**
 * Created by upekka on 11/01/16.
 */
public class Tier {
    private String tier_name;
    private String starting_ip;
    private String ending_ip;
    private String  http_method;
    private long request_count;
    private long unit_time;
    private long else_request_count;
    private long else_unit_time;

    public Tier(String tier_name, String starting_ip, String ending_ip, String http_method, long request_count, long unit_time, long else_request_count, long else_unit_time) {
        this.tier_name = tier_name;
        this.starting_ip = starting_ip;
        this.ending_ip = ending_ip;
        this.http_method = http_method;
        this.request_count = request_count;
        this.unit_time = unit_time;
        this.else_request_count = else_request_count;
        this.else_unit_time = else_unit_time;
    }

    public String getTier_name() {
        return tier_name;
    }

    public void setTier_name(String tier_name) {
        this.tier_name = tier_name;
    }

    public String getStarting_ip() {
        return starting_ip;
    }

    public void setStarting_ip(String starting_ip) {
        this.starting_ip = starting_ip;
    }

    public String getEnding_ip() {
        return ending_ip;
    }

    public void setEnding_ip(String ending_ip) {
        this.ending_ip = ending_ip;
    }

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    public long getRequest_count() {
        return request_count;
    }

    public void setRequest_count(long request_count) {
        this.request_count = request_count;
    }

    public long getUnit_time() {
        return unit_time;
    }

    public void setUnit_time(long unit_time) {
        this.unit_time = unit_time;
    }

    public long getElse_request_count() {
        return else_request_count;
    }

    public void setElse_request_count(long else_request_count) {
        this.else_request_count = else_request_count;
    }

    public long getElse_unit_time() {
        return else_unit_time;
    }

    public void setElse_unit_time(long else_unit_time) {
        this.else_unit_time = else_unit_time;
    }
}
