package com.bnmoboxd.middlewares;

import com.bnmoboxd.config.Config;
import com.bnmoboxd.controllers.SubscriptionController;
import com.sun.xml.internal.ws.api.message.HeaderList;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import com.sun.xml.internal.ws.developer.WSBindingProvider;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class AuthMiddleware implements Middleware {
    private final WebServiceContext serviceContext;

    public AuthMiddleware(WebServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    @Override
    public boolean execute() {
        MessageContext msgContext = serviceContext.getMessageContext();
//        HeaderList headers = (HeaderList) msgContext.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        Map<String, Object> headers = (Map<String, Object>) msgContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            String apiKey = ((List<String>) headers.get("x-api-key")).get(0);
            return apiKey != null && (
                apiKey.equals(Config.get("REST_API_KEY")) ||
                apiKey.equals(Config.get("PHP_API_KEY"))
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
