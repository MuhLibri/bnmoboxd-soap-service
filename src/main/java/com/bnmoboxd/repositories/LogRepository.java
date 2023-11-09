package com.bnmoboxd.repositories;

import com.bnmoboxd.database.Database;
import com.bnmoboxd.models.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    public List<Log> getAllLogs() {
        /*
        * Could be improved to be tidier
        * The try catch block isn't very nice looking ?
        * */
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM logs";
            List<Log> logs = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String description = resultSet.getString("description");
                    String endpoint = resultSet.getString("endpoint");
                    String clientIp = resultSet.getString("client_ip");
                    String requestTimestamp = resultSet.getString("request_timestamp");
                    String soapRequest = resultSet.getString("soap_request");

                    Log log = new Log(id, description, endpoint, clientIp, requestTimestamp, soapRequest);
                    logs.add(log);
                    /*System.out.println(log.getId());
                    System.out.println(log.getDescription());*/
                }
            }
            return logs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * Function to test the log repository
    * */
    /*public static void main(String[] args){
        try {
            LogRepository logRepository = new LogRepository();
            logRepository.getAllLogs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
