package com.bnmoboxd.repositories;

import com.bnmoboxd.database.Database;
import com.bnmoboxd.models.Subscription;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepository {
    public List<Subscription> getAllSubscriptions() {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM subscriptions";
            List<Subscription> subscriptions = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int curatorId = resultSet.getInt("curator_id");
                    int subscriberId = resultSet.getInt("subscriber_id");
                    String status = resultSet.getString("status");

                    Subscription subscription = new Subscription(curatorId, subscriberId, status);
                    subscriptions.add(subscription);
                    /*System.out.println(subscription.getCuratorId());
                    System.out.println(subscription.getSubscriberId());
                    System.out.println(subscription.getStatus());*/
                }
            }
            return subscriptions;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Subscription> getAcceptedSubscriptions(int subCuratorId) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM subscriptions WHERE curator_id = ? AND status = 'ACCEPTED'";
            List<Subscription> subscriptions = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, subCuratorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int curatorId = resultSet.getInt("curator_id");
                        int subscriberId = resultSet.getInt("subscriber_id");
                        String status = resultSet.getString("status");

                        Subscription subscription = new Subscription(curatorId, subscriberId, status);
                        subscriptions.add(subscription);
                    }
                }
                return subscriptions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSubscription(Subscription subscription) {
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO subscriptions VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, subscription.getCuratorId());
                statement.setInt(2, subscription.getSubscriberId());
                statement.setString(3, subscription.getStatus());

                // Will fail if a subscription already exists
                int rowCount = statement.executeUpdate();
                return rowCount > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public static void main(String[] args) {
        try {
            SubscriptionRepository rep = new SubscriptionRepository();
            rep.getAllSubscriptions();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
}
