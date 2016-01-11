package org.wso2.apim.core;

/**
 * Created by upekka on 11/01/16.
 */
public class Throttler {
    private String executionPlan = "FROM RequestStream[app_tier==\"bronze\"] \n" +
            "SELECT messageID,app_key as key, (RequestStream.ip_address == \"10.100.4.125\" and RequestStream.http_method == \"GET\") as isEligible \n" +
            "insert into app_bronzeStream; \n" +
            "@info(name = 'query1') \n" +
            "from app_bronzeStream[isEligible == true]#window.time(60000) \n" +
            "select key, (count(messageID) >= 3) as isThrottled \n" +
            "group by key \n" +
            "insert all events into ResultStream; \n"+
            "@info(name = 'query2') \n" +
            "from app_bronzeStream[isEligible == false]#window.time(60000) \n" +
            "select key, (count(messageID) >= 100) as isThrottled \n" +
            "group by key \n" +
            "insert all events into ResultStream; \n";

    public String addRule(String tier_name, String starting_ip, String ending_ip, String http_method, long request_count, long unit_time, long else_request_count, long else_unit_time){
        /*Execution Plan for new tier*/
        //System.out.println(executionPlan);
        return executionPlan;
    }

    public void doThrottling(){

    }
}
