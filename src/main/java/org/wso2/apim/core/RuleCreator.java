package org.wso2.apim.core;

import org.wso2.apim.model.ThrottleLevel;

/**
 * Created by upekka on 11/01/16.
 */
public class RuleCreator {

    private static String ruleQueryTemplate = "FROM RequestStream[$THROTTLELEVEL_tier==\"$TIER\"] \n" +
            "SELECT messageID,$THROTTLELEVEL_key as key, (RequestStream.ip_address >= $STARTING_IP_ADDRESS and RequestStream.ip_address <= $ENDING_IP_ADDRESS and RequestStream.http_method == \"$HTTP_METHOD\") as isEligible \n" +
            "insert into $THROTTLELEVEL_$TIERStream; \n" +
            "@info(name = 'query1_$THROTTLELEVEL_$TIER') \n" +
            "from $THROTTLELEVEL_$TIERStream[isEligible == true]#window.time($UNIT_TIME) \n" +
            "select key, (count(messageID) >= $REQUEST_COUNT) as isThrottled \n" +
            "group by key \n" +
            "insert all events into ResultStream; \n"+
            "@info(name = 'query2_$THROTTLELEVEL_$TIER') \n" +
            "from $THROTTLELEVEL_$TIERStream[isEligible == false]#window.time($ELSE_UNIT_TIME) \n" +
            "select key, (count(messageID) >= $ELSE_REQUEST_COUNT) as isThrottled \n" +
            "group by key \n" +
            "insert all events into ResultStream; \n";


    public static String getRuleQuery(String throttleLevel, String tier_name, String starting_ip, String ending_ip, String http_method, long request_count, long unit_time, long else_request_count, long else_unit_time) {
        String queryTemplate = getRuleQueryTemplate();

        StringBuilder builder = new StringBuilder();

            String query = queryTemplate.replace("$TIER", tier_name);
            query = query.replace("$THROTTLELEVEL", throttleLevel);
            query = query.replace("$STARTING_IP_ADDRESS", Long.toString(setIPAddress(starting_ip)));
            query = query.replace("$ENDING_IP_ADDRESS", Long.toString(setIPAddress(ending_ip)));
            query = query.replace("$HTTP_METHOD", http_method);
            query = query.replace("$REQUEST_COUNT", Long.toString(request_count));
            query = query.replace("$UNIT_TIME", Long.toString(unit_time));
            query = query.replace("$ELSE_REQUEST_COUNT", Long.toString(else_request_count));
            query = query.replace("$ELSE_UNIT_TIME", Long.toString(else_unit_time));
            builder.append(query);

        builder.append("\n");
    return builder.toString();
}

    public static String getRuleQueryTemplate() {
        return ruleQueryTemplate;
    }

    public static long setIPAddress(String IPAddress) {
        long IPAddressinLong = 0;
        if(IPAddress!=null){
        //convert ipaddress into a long
            String[] ipAddressArray = IPAddress.split("\\."); //split by "." and add to an array

            for(int i=0; i< ipAddressArray.length;i++){
                int power = 3 -i;
                long ip = Long.parseLong(ipAddressArray[i]); //parse to long
                IPAddressinLong += ip*Math.pow(256,power);
            }

        }
        return IPAddressinLong;
    }




}
