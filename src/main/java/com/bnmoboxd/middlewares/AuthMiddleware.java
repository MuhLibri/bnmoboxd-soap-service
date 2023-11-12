package com.bnmoboxd.middlewares;

import com.bnmoboxd.config.Config;
import lombok.AllArgsConstructor;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@AllArgsConstructor
public class AuthMiddleware implements Middleware {
    private final WebServiceContext serviceContext;

    @Override
    public boolean execute() {
        MessageContext msgContext = serviceContext.getMessageContext();
        Map<String, Object> headers = (Map<String, Object>) msgContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            String apiKey = ((List<String>) headers.get("x-api-key")).get(0);
            return apiKey != null && apiKey.equals(Config.get("SOAP_API_KEY"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
