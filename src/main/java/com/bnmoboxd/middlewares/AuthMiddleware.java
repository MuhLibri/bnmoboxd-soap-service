package com.bnmoboxd.middlewares;

import com.bnmoboxd.core.Config;
import com.bnmoboxd.core.Endpoints;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import lombok.AllArgsConstructor;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@AllArgsConstructor
public class AuthMiddleware implements Middleware {
    private final SOAPMessageContext context;

    @Override
    public boolean execute() {
        Map<String, Object> headers = (Map<String, Object>) context.get(MessageContext.HTTP_REQUEST_HEADERS);

        try {
            String apiKey = ((List<String>) headers.get("x-api-key")).get(0);
            System.out.println(apiKey);
            boolean auth = apiKey != null && apiKey.equals(Config.get("SOAP_API_KEY"));

            String endpoint = Endpoints.getEndpoint(String.format("com.bnmoboxd.controllers.%s",
                ((QName) context.get(MessageContext.WSDL_INTERFACE)).getLocalPart()
            ));
            String method = ((QName) context.get(MessageContext.WSDL_OPERATION)).getLocalPart();
            String host = headers.get("Host").toString();

            System.out.printf("[%s] %16s: %-8s %-20s client: %s; auth: %s;%n",
                LocalDateTime.now(), endpoint, method, getClass().getSimpleName(), host, auth
            );
            return auth;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
