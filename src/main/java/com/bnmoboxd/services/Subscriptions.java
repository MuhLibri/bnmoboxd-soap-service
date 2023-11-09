package com.bnmoboxd.services;

import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class Subscriptions {
    private final SubscriptionRepository subscriptions = new SubscriptionRepository();

    @WebMethod(operationName = "getAll")
    @WebResult(name = "result")
    public Subscription[] getAll() {
        return subscriptions.getAllSubscriptions().toArray(new Subscription[0]);
    }
}
