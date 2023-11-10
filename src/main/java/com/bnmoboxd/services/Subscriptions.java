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

    @WebMethod(operationName = "add")
    @WebResult(name = "success")
    public boolean add(
            @WebParam(name = "curator_id")
            @XmlElement(required = true)
            int curatorId,

            @WebParam(name = "subscriber_id")
            @XmlElement(required = true)
            int subscriberId,

            @WebParam(name = "status")
            @XmlElement(required = true)
            String status
    ) {
        // TODO: Check if the corresponding user IDs exist
        return subscriptions.addSubscription(new Subscription(curatorId, subscriberId, status));
    }
}
