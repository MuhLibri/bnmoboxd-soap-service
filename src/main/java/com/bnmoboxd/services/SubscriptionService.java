package com.bnmoboxd.services;

import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;

import java.util.List;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService() {
        subscriptionRepository = new SubscriptionRepository();
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.getAllSubscriptions();
    }
}
