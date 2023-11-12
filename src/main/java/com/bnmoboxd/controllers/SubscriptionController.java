package com.bnmoboxd.controllers;

import com.bnmoboxd.middlewares.AuthMiddleware;
import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;
import com.bnmoboxd.services.SubscriptionService;
import com.bnmoboxd.struct.Pagination;
import com.bnmoboxd.struct.SubscriptionStatus;

import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.WebServiceContext;
import java.util.List;

@WebService
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    @Resource
    private WebServiceContext serviceContext;

    // TODO: Logging
    public SubscriptionController() {
        subscriptionService = new SubscriptionService();
    }

    @WebMethod(operationName = "getAll")
    @WebResult(name = "response")
    public List<Subscription> getAll(
        @WebParam(name = "page")
        Integer page,

        @WebParam(name = "take")
        Integer take
    ) {
        if(new AuthMiddleware(serviceContext).execute()) {
            Pagination pagination = page != null && take != null ? new Pagination(page, take) : null;
            return subscriptionService.getSubscriptions(null, pagination);
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
        if(new AuthMiddleware(serviceContext).execute() && curatorUsername != null) {
            return subscriptionService.getSubscriptions(new SubscriptionRepository.Filter(
                curatorUsername,
                null,
                SubscriptionStatus.ACCEPTED
            )).size();
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
        if(new AuthMiddleware(serviceContext).execute()
            && curatorUsername != null
            && subscriberUsername != null
            && status != null
        ) {
            return subscriptionService.addSubscription(
                curatorUsername,
                subscriberUsername,
                SubscriptionStatus.valueOf(status)
            );
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
        if(new AuthMiddleware(serviceContext).execute()
            && curatorUsername != null
            && subscriberUsername != null
            && status != null
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
                return null;
            }
        } else {
            return null;
        }
    }
}
