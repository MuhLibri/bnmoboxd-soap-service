package com.bnmoboxd.controllers;

import com.bnmoboxd.middlewares.AuthMiddleware;
import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;
import com.bnmoboxd.services.SubscriptionService;
import com.bnmoboxd.struct.ApiResponse;
import com.bnmoboxd.struct.ResponseStatus;
import com.bnmoboxd.struct.SubscriptionStatus;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.WebServiceContext;
import java.util.List;

@WebService
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final SubscriptionRepository subscriptionRepository;
    @Resource
    private WebServiceContext serviceContext;

    // TODO: Logging
    public SubscriptionController() {
        subscriptionService = new SubscriptionService();
        subscriptionRepository = new SubscriptionRepository();
    }

    @WebMethod(operationName = "getAll")
    @WebResult(name = "response")
    public List<Subscription> getAll() {
        if(new AuthMiddleware(serviceContext).execute()) {
            return subscriptionService.getAllSubscriptions();
        } else {
            return null;
        }
    }

    @WebMethod(operationName = "count")
    @WebResult(name = "response")
    public Integer count(
        @WebParam(name = "curatorUsername")
        @XmlElement(required = true)
        String curatorUsername
    ) {
        if(new AuthMiddleware(serviceContext).execute()) {
            return subscriptionRepository.getAcceptedSubscriptions(curatorUsername).size();
        } else {
            return null;
        }
    }

    @WebMethod(operationName = "add")
    @WebResult(name = "response")
    public Boolean add(
        @WebParam(name = "curatorUsername")
        @XmlElement(required = true)
        String curatorUsername,

        @WebParam(name = "subscriberUsername")
        @XmlElement(required = true)
        String subscriberUsername,

        @WebParam(name = "status")
        @XmlElement(required = true)
        String status
    ) {
        if(new AuthMiddleware(serviceContext).execute()) {
            return subscriptionRepository.addSubscription(new Subscription(curatorUsername, subscriberUsername, SubscriptionStatus.valueOf(status)));
        } else {
            return null;
        }
    }

    @WebMethod(operationName = "update")
    @WebResult(name = "response")
    public Boolean update(
        @WebParam(name = "curatorUsername")
        @XmlElement(required = true)
        String curatorUsername,

        @WebParam(name = "subscriberUsername")
        @XmlElement(required = true)
        String subscriberUsername,

        @WebParam(name = "status")
        @XmlElement(required = true)
        String status
    ) {
        if(new AuthMiddleware(serviceContext).execute()) {
            return subscriptionRepository.updateSubscriptionStatus(new Subscription(curatorUsername, subscriberUsername, SubscriptionStatus.valueOf(status)));
        } else {
            return null;
        }
    }
}
