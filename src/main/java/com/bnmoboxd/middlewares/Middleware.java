package com.bnmoboxd.middlewares;

import javax.xml.ws.WebServiceContext;

public interface Middleware {
    boolean execute();
}
