package com.bnmoboxd;

import com.bnmoboxd.services.Subscriptions;

import javax.xml.ws.*;

public class Main {
    public static void main(String[] args){
        try {
            Endpoint.publish("http://localhost:9000/subscription", new Subscriptions());
            System.out.println("Server created at localhost:9000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}