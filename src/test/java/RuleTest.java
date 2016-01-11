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

        String ruleQuery = throttler.addRule("bronze","10.100.4.125","10.100.4.129","GET",3,60000,1,60000);
        String executionPlan ="define stream RequestStream (messageID string, app_key string, api_key string, resource_key string, app_tier string, api_tier string, resource_tier string, ip_address string, http_method string); "+ ruleQuery;
        //Generating runtime
        ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);

        //Adding callback to retrieve output events from query
        executionPlanRuntime.addCallback("query1", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
            }
        });

        executionPlanRuntime.addCallback("query2", new QueryCallback() {
            @Override
            public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
                EventPrinter.print(timeStamp, inEvents, removeEvents);
            }
        });

        //Retrieving InputHandler to push events into Siddhi
        InputHandler requestinputHandler = executionPlanRuntime.getInputHandler("RequestStream");

        //Starting event processing
        executionPlanRuntime.start();

        //Sending events to Siddhi
        requestinputHandler.send(new Object[]{"msg1","app1","api1","res1","bronze","Silver","Gold","10.100.4.125","GET"});
        requestinputHandler.send(new Object[]{"msg2","app1","api1","res1","bronze","Silver","Gold","10.100.4.125","GET"});
        requestinputHandler.send(new Object[]{"msg3","app2","api1","res1","bronze","Silver","Gold","10.100.4.125","GET"});
        requestinputHandler.send(new Object[]{"msg4","app2","api2","res2","bronze","Silver","Gold","10.10.12","GET"});
        requestinputHandler.send(new Object[]{"msg5","app3","api3","res3","bronze","Gold","Gold","10.10.10","GET"});
        requestinputHandler.send(new Object[]{"msg6","app1","api1","res1","bronze","Silver","Gold","10.100.4.125","GET"});

        Thread.sleep(100);

        //Shutting down the runtime
        executionPlanRuntime.shutdown();

        //Shutting down Siddhi
        siddhiManager.shutdown();


    }
}
