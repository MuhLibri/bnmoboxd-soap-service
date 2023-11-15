package com.bnmoboxd.middlewares;
import com.bnmoboxd.core.Config;
import com.bnmoboxd.core.Endpoints;
import com.bnmoboxd.repositories.LogRepository;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class LoggingMiddleware implements Middleware {
    private final SOAPMessageContext context;
    private final LogRepository logRepository;

    public LoggingMiddleware(SOAPMessageContext context) {
        this.context = context;
        this.logRepository = new LogRepository();
    }

    @Override
    public boolean execute() {
        String endpoint = Endpoints.getEndpoint(String.format("com.bnmoboxd.controllers.%s",
            ((QName) context.get(MessageContext.WSDL_INTERFACE)).getLocalPart()
        ));
        String method = ((QName) context.get(MessageContext.WSDL_OPERATION)).getLocalPart();
        Map<String, Object> headers = (Map<String, Object>) context.get(MessageContext.HTTP_REQUEST_HEADERS);
        String apiKey = ((List<String>) headers.get("x-api-key")).get(0);
        String source = "UNAUTHORIZED";
        if (apiKey.equals(Config.get("SOAP_API_KEY_REST"))) {
            source = "REST";
        }
        if (apiKey.equals(Config.get("SOAP_API_KEY_PHP"))) {
            source = "PHP";
        }
        String host = headers.get("Host").toString();
        String client = "[" + source + "] " + host;
        String params = buildParamString(context.getMessage());

        logRepository.addLog(params, endpoint, client, method);

        System.out.printf("[%s] %16s: %-8s %-20s client: %s; %s%n",
            LocalDateTime.now(), endpoint, method, getClass().getSimpleName(), client, params
        );
        return true;
    }

    private String buildParamString(SOAPMessage message) {
        try {
            StringBuilder str = new StringBuilder();

            SOAPBody body = context.getMessage().getSOAPBody();
            NodeList children = body.getFirstChild().getChildNodes();

            for(int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if(str.length() > 0) str.append(' ');
                str.append(String.format("%s: %s;", child.getLocalName(), child.getTextContent()));
            }
            return str.toString();
        } catch(SOAPException e) {
            e.printStackTrace();
            return "";
        }
    }
}
