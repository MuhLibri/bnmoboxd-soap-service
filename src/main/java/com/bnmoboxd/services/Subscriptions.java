package com.bnmoboxd.services;

import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService
public class Subscriptions {
    private final SubscriptionRepository subscriptions = new SubscriptionRepository();

    @WebMethod(operationName = "getAll")
    @WebResult(name = "result")
    public Subscription[] getAll() {
        return subscriptions.getAllSubscriptions().toArray(new Subscription[0]);
    }

    @WebMethod(operationName = "count")
    @WebResult(name = "result")
    public int count(
            @WebParam(name = "curator_id")
            @XmlElement(required = true)
            int curatorId
    ) {
        return subscriptions.getAcceptedSubscriptions(curatorId).size();
    }
}
