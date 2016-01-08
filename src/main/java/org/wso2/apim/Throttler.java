package org.wso2.apim;

import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;

/**
 * Created by upekka on 07/01/16.
 */
public class Throttler {
    public static void main(String[] args) throws InterruptedException {

        // Creating Siddhi Manager
        SiddhiManager siddhiManager = new SiddhiManager();

        //Execution plan for APP level throttling
        String executionPlan = "" +
                "define stream RuleStream (tier_name string, starting_ip string, ending_ip string, http_method string , request_count int, unit_time int, else_request_count int, else_unit_time int);" +
                "define stream RequestStream (messageID string, app_key string, api_key string, resource_key string, app_tier string, api_tier string, resource_tier string, ip_address string, http_method string); "+
                "" +
                "@info(name = 'query1') " +
                "from RequestStream#window.time(1 sec) join RuleStream#window.time(1 sec) " +
                "on (RequestStream.app_tier == RuleStream.tier_name and RequestStream.ip_address == RuleStream.starting_ip and RequestStream.http_method == RuleStream.http_method) "+
                "select RequestStream.app_key, RequestStream.app_tier " +
                "insert all events into ProcessingStream;";

        //Generating runtime
        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);

        //Adding callback to retrieve output events from query
        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
            }
        });

        //Retrieving InputHandler to push events into Siddhi
        InputHandler ruleinputHandler = executionPlanRuntime.getInputHandler("RuleStream");
        InputHandler requestinputHandler = executionPlanRuntime.getInputHandler("RequestStream");

        //Starting event processing
        executionPlanRuntime.start();

        //Sending events to Siddhi
        ruleinputHandler.send(new Object[]{"Gold","10.10.10","10.10.11","GET",3,60000});
        ruleinputHandler.send(new Object[]{"Silver","10.10.10","10.10.11","POST",2,60000});
        requestinputHandler.send(new Object[]{"msg1","app1","api1","res1","Gold","Silver","Gold","10.10.10","GET"});
        requestinputHandler.send(new Object[]{"msg2","app2","api2","res2","Silver","Silver","Gold","10.10.11","GET"});
        requestinputHandler.send(new Object[]{"msg3","app3","api3","res3","Gold","Gold","Gold","10.10.10","GET"});

        Thread.sleep(100);

        //Shutting down the runtime
        executionPlanRuntime.shutdown();

        //Shutting down Siddhi
        siddhiManager.shutdown();

    }
}
