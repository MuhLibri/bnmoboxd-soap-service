package com.bnmoboxd;

import com.bnmoboxd.controllers.SubscriptionController;
import com.bnmoboxd.core.Endpoints;

public class Main {
    public static void main(String[] args){
        try {
            Endpoints.publish("/subscription", new SubscriptionController());
            System.out.printf("Server created at %s%n", Endpoints.getHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}