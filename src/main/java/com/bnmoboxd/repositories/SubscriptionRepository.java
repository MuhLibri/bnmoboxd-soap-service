package com.bnmoboxd.repositories;

import com.bnmoboxd.database.Database;
import com.bnmoboxd.models.Subscription;
import com.bnmoboxd.struct.SubscriptionStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepository {
    public List<Subscription> getAllSubscriptions() {
        try(Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM subscriptions";
            List<Subscription> subscriptions = new ArrayList<>();

            try(PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    String curatorUsername = resultSet.getString("curator_username");
                    String subscriberUsername = resultSet.getString("subscriber_username");
                    String status = resultSet.getString("status");

                    Subscription subscription = new Subscription(curatorUsername, subscriberUsername, SubscriptionStatus.valueOf(status));
                    subscriptions.add(subscription);
                }
            }
            return subscriptions;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Subscription> getAcceptedSubscriptions(String subCuratorUsername) {
        try(Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM subscriptions WHERE curator_username = ? AND status = 'ACCEPTED'";
            List<Subscription> subscriptions = new ArrayList<>();

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, subCuratorUsername);
                try(ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {
                        String curatorUsername = resultSet.getString("curator_username");
                        String subscriberUsername = resultSet.getString("subscriber_username");
                        String status = resultSet.getString("status");

                        Subscription subscription = new Subscription(curatorUsername, subscriberUsername, SubscriptionStatus.valueOf(status));
                        subscriptions.add(subscription);
                    }
                }
                return subscriptions;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSubscription(Subscription subscription) {
        try(Connection connection = Database.getConnection()) {
            String query = "INSERT INTO subscriptions VALUES (?, ?, ?)";

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, subscription.getCuratorUsername());
                statement.setString(2, subscription.getSubscriberUsername());
                statement.setString(3, subscription.getStatus().toString());

                // Will fail if a subscription already exists
                int rowCount = statement.executeUpdate();
                return rowCount > 0;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSubscriptionStatus(Subscription subscription) {
        try(Connection connection = Database.getConnection()) {
            String query = "UPDATE subscriptions SET status = ? WHERE curator_username = ? AND subscriber_username = ?";

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, subscription.getStatus().toString());
                statement.setString(2, subscription.getCuratorUsername());
                statement.setString(3, subscription.getSubscriberUsername());

                int rowCount = statement.executeUpdate();
                return rowCount > 0;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
