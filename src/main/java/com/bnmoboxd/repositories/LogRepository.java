package com.bnmoboxd.repositories;

import com.bnmoboxd.database.Database;
import com.bnmoboxd.models.Log;
import com.bnmoboxd.struct.SubscriptionStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    public List<Log> getAllLogs() {
        try(Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM logs";
            List<Log> logs = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String endpoint = resultSet.getString("endpoint");
                    String clientIp = resultSet.getString("client_ip");
                    String requestTimestamp = resultSet.getString("request_timestamp");
                    String soapRequest = resultSet.getString("soap_request");

                    Log log = new Log(id, description, endpoint, clientIp, requestTimestamp, soapRequest);
                    logs.add(log);
                }
            }
            return logs;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addLog(String description, String endpoint, String clientIp, String soapRequest) {
        try(Connection connection = Database.getConnection()) {
            String query = "INSERT INTO logs(description, endpoint, client_ip, soap_request) VALUES (?, ?, ?, ?)";

            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, description);
                statement.setString(2, endpoint);
                statement.setString(3, clientIp);
                statement.setString(4, soapRequest);

                int rowCount = statement.executeUpdate();
                return rowCount > 0;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
