package org.wso2.apim.core;

import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;

/**
 * Created by upekka on 11/01/16.
 */
public class Throttler {

    SiddhiManager siddhiManager = new SiddhiManager();
    private static String executionPlan ="define stream RequestStream (messageID string, app_key string, api_key string, resource_key string, app_tier string, api_tier string, resource_tier string, ip_address long, http_method string);";
    public String addRule(String throttleLevel, String tier_name, String starting_ip, String ending_ip, String http_method, long request_count, long unit_time, long else_request_count, long else_unit_time){
        /*Execution Plan for new tier*/
        executionPlan += RuleCreator.getRuleQuery(throttleLevel, tier_name, starting_ip, ending_ip, http_method, request_count, unit_time, else_request_count, else_unit_time);
        return executionPlan;
    }

    public static String getExecutionPlan() {
        return executionPlan;
    }

    public ExecutionPlanRuntime doThrottling(){

        //Generating runtime
        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(getExecutionPlan());
        System.out.println(getExecutionPlan());

        return executionPlanRuntime;

    }
}
