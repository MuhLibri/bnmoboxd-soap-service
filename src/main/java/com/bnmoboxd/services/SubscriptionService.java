package com.bnmoboxd.services;

import com.bnmoboxd.clients.PhpApi;
import com.bnmoboxd.clients.RestApi;
import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.repositories.SubscriptionRepository;
import com.bnmoboxd.struct.Pagination;
import com.bnmoboxd.struct.SubscriptionStatus;
import com.bnmoboxd.utils.EmailSender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final EmailSender emailSender;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SubscriptionService() {
        subscriptionRepository = new SubscriptionRepository();
        emailSender = new EmailSender();
    }

    public List<Subscription> getSubscriptions(SubscriptionRepository.Filter filter) {
        return subscriptionRepository.getSubscriptions(filter, null);
    }

    public List<Subscription> getSubscriptions(SubscriptionRepository.Filter filter, Pagination pagination) {
        return subscriptionRepository.getSubscriptions(filter, pagination);
    }

    public boolean addSubscription(String curatorUsername, String subscriberUsername, SubscriptionStatus status) {
        sendSubscriptionEmailAsync(curatorUsername, subscriberUsername);
        return subscriptionRepository.addSubscription(curatorUsername, subscriberUsername, status);
    }

    public boolean updateSubscription(String curatorUsername, String subscriberUsername, SubscriptionStatus status) {
        boolean success = subscriptionRepository.updateSubscription(curatorUsername, subscriberUsername, status);
        if(success) PhpApi.updateSubscription(curatorUsername, subscriberUsername, status);
        return success;
    }

    private void sendSubscriptionEmailAsync(String curatorUsername, String subscriberUsername) {
        CompletableFuture.runAsync(() -> sendSubscriptionEmail(curatorUsername, subscriberUsername), executorService);
    }

    private void sendSubscriptionEmail(String curatorUsername, String subscriberUsername) {
        String email = RestApi.getAdminEmail();
        String subject = "[BNMOBOXD] New Subscription Request";
        String message = String.format("A new subscription request received from %s to %s", subscriberUsername, curatorUsername);
        emailSender.sendEmail(email, subject, message);
    }
}
