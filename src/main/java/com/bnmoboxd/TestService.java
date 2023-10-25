package com.bnmoboxd;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class TestService {
    @WebMethod
    public String sayHello() {
        return "Hello world soap taikkkk";
    }
}
