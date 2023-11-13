package com.bnmoboxd.core;

import javax.xml.ws.Endpoint;
import java.util.HashMap;
import java.util.Map;

public class Endpoints {
    private static final String host = "http://localhost:9000";
//    private static final Map<String, Object> endpoint2Executor = new HashMap<>();
    private static final Map<String, String> executor2Endpoint = new HashMap<>();

    public static String getHost() {
        return host;
    }

    public static void publish(String endpointAddress, Object executor) {
//        endpoint2Executor.put(endpointAddress, executor);
        executor2Endpoint.put(executor.getClass().getName(), endpointAddress);

        Endpoint.publish(host + endpointAddress, executor);
    }

    public static String getEndpoint(String executorName) {
        return executor2Endpoint.get(executorName);
    }

    public static String getEndpoint(Object executor) {
        return executor2Endpoint.get(executor.getClass().getName());
    }
}
