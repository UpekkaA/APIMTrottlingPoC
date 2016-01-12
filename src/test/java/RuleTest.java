import org.wso2.apim.core.Throttler;
import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

/**
 * Created by upekka on 11/01/16.
 */
public class RuleTest {


    public static void main(String[] args) throws InterruptedException {
        SiddhiManager siddhiManager = new SiddhiManager();
        Throttler throttler=new Throttler();

        //String executionPlan ="define stream RequestStream (messageID string, app_key string, api_key string, resource_key string, app_tier string, api_tier string, resource_tier string, ip_address string, http_method string); ";
        throttler.addRule("app","bronze","10.100.4.125","10.100.4.129","GET",3,60000,100,60000);
        throttler.addRule("api","silver","10.100.4.125","10.100.4.129","GET",4,60000,100,60000);
        throttler.addRule("resource","gold","10.100.4.125","10.100.4.129","GET",5,60000,100,60000);

       ExecutionPlanRuntime executionPlanRuntime = throttler.doThrottling();

        //Adding callback to retrieve output events from query - APP level
        executionPlanRuntime.addCallback("query1_app_bronze", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });

        executionPlanRuntime.addCallback("query2_app_bronze", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });


        //Adding callback to retrieve output events from query - API level
        executionPlanRuntime.addCallback("query1_api_silver", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });

        executionPlanRuntime.addCallback("query2_api_silver", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });

        //Adding callback to retrieve output events from query - Resource level
        executionPlanRuntime.addCallback("query1_resource_gold", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });

        executionPlanRuntime.addCallback("query2_resource_gold", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(inEvents);
            }
        });

        //Retrieving InputHandler to push events into Siddhi
        InputHandler requestinputHandler = executionPlanRuntime.getInputHandler("RequestStream");

        //Starting event processing
        executionPlanRuntime.start();

        //Sending events to Siddhi
        requestinputHandler.send(new Object[]{"msg1","app1","api1","res1","bronze","silver","gold",174326909l,"GET"});
        requestinputHandler.send(new Object[]{"msg2","app1","api1","res1","bronze","silver","gold",174326909l,"GET"});
        requestinputHandler.send(new Object[]{"msg3","app1","api1","res1","bronze","silver","gold",174326909l,"GET"});
        requestinputHandler.send(new Object[]{"msg4","app2","api1","res1","bronze","silver","gold",174326909l,"GET"});
        requestinputHandler.send(new Object[]{"msg5","app3","api2","res1","bronze","gold","gold",174326909l,"GET"});
        requestinputHandler.send(new Object[]{"msg6","app4","api3","res2","bronze","silver","gold",174326909l,"GET"});

        Thread.sleep(100);

        //Shutting down the runtime
        executionPlanRuntime.shutdown();

        //Shutting down Siddhi
        siddhiManager.shutdown();


    }
}
