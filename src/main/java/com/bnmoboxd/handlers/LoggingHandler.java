package com.bnmoboxd.handlers;

import com.bnmoboxd.middlewares.LoggingMiddleware;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if(!(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)) {
            new LoggingMiddleware(context).execute();
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }
}
