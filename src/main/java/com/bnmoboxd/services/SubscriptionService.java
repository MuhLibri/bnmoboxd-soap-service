package com.bnmoboxd.services;

import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;
import com.bnmoboxd.struct.Pagination;
import com.bnmoboxd.struct.SubscriptionStatus;

import java.util.List;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService() {
        subscriptionRepository = new SubscriptionRepository();
    }

    public List<Subscription> getSubscriptions(SubscriptionRepository.Filter filter) {
        return subscriptionRepository.getSubscriptions(filter, null);
    }

    public List<Subscription> getSubscriptions(SubscriptionRepository.Filter filter, Pagination pagination) {
        return subscriptionRepository.getSubscriptions(filter, pagination);
    }

    public boolean addSubscription(String curatorUsername, String subscriberUsername, SubscriptionStatus status) {
        return subscriptionRepository.addSubscription(new Subscription(
            curatorUsername,
            subscriberUsername,
            status
        ));
    }

    public boolean updateSubscription(String curatorUsername, String subscriberUsername, SubscriptionStatus status) {
        return subscriptionRepository.updateSubscription(new Subscription(
            curatorUsername,
            subscriberUsername,
            status
        ));
    }
}
