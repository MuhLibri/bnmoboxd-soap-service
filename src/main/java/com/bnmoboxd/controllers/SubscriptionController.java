package com.bnmoboxd.controllers;

import com.bnmoboxd.repositories.SubscriptionRepository;
import com.bnmoboxd.services.SubscriptionService;
import com.bnmoboxd.struct.Pagination;
import com.bnmoboxd.struct.SubscriptionPage;
import com.bnmoboxd.struct.SubscriptionStatus;

import javax.jws.*;
import javax.xml.bind.annotation.XmlElement;

@WebService
@HandlerChain(file = "SubscriptionHandlers.xml")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController() {
        subscriptionService = new SubscriptionService();
    }

    @WebMethod(operationName = "getAll")
    @WebResult(name = "response")
    public SubscriptionPage getAll(
        @WebParam(name = "page")
        Integer page,

        @WebParam(name = "take")
        Integer take
    ) {
        Pagination pagination = page != null && take != null ? new Pagination(page, take) : null;
        return new SubscriptionPage(subscriptionService.getSubscriptions(null, pagination));
    }

    @WebMethod(operationName = "count")
    @WebResult(name = "response")
    public Integer count(
        @WebParam(name = "curatorUsername")
        @XmlElement(required = true)
        String curatorUsername
    ) {
        return subscriptionService.getSubscriptions(new SubscriptionRepository.Filter(
            curatorUsername,
            null,
            SubscriptionStatus.ACCEPTED
        )).size();

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
        return subscriptionService.addSubscription(
            curatorUsername,
            subscriberUsername,
            SubscriptionStatus.valueOf(status)
        );
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
        try {
            SubscriptionStatus newStatus = SubscriptionStatus.valueOf(status);
            return subscriptionService.updateSubscription(
                curatorUsername,
                subscriberUsername,
                newStatus
            );
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
