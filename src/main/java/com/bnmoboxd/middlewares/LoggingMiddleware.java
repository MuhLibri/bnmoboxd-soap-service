package com.bnmoboxd.middlewares;

import com.bnmoboxd.struct.Pair;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import com.sun.xml.internal.ws.server.WSEndpointImpl;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class LoggingMiddleware implements Middleware {
    private final WebServiceContext serviceContext;
    private final Pair<String, Object>[] params;

    @SafeVarargs
    public LoggingMiddleware(WebServiceContext serviceContext, Pair<String, Object>... params) {
        this.serviceContext = serviceContext;
        this.params = params;
    }

    @Override
    public boolean execute() {
        // TODO: Do this the better way: call this from a Handler?
        MessageContext msgContext = serviceContext.getMessageContext();
        Map<String, Object> headers = (Map<String, Object>) msgContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            String endpoint = (String) getParam("endpoint");
            String method = msgContext.get(MessageContext.WSDL_OPERATION).toString();
            method = method.substring(method.indexOf('}') + 1);

            StringBuilder paramsStr = new StringBuilder();
            for(Pair<String, Object> param : params) {
                if(paramsStr.length() > 0) paramsStr.append(';');
                paramsStr.append(String.format(" %s: %s", param.getFirst(), param.getSecond()));
            }

            System.out.printf("[%s %8s]%s%n", LocalDateTime.now(), method, paramsStr);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Object getParam(String key) {
        for(Pair<String, Object> param : params) {
            if(param.getFirst().equals(key)) return param.getSecond();
        }
        return null;
    }
}
