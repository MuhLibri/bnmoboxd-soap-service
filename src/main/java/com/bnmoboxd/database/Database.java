package com.bnmoboxd.database;

import com.bnmoboxd.core.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String MYSQL_DATABASE;
    private static final String MYSQL_HOST;
    private static final int MYSQL_PORT;
    private static final String MYSQL_USER;
    private static final String MYSQL_PASSWORD;

    static {
        MYSQL_DATABASE = Config.get("MYSQL_DATABASE");
        MYSQL_HOST = Config.get("MYSQL_HOST");
        MYSQL_PORT = Integer.parseInt(Config.get("MYSQL_PORT"));
        MYSQL_USER = Config.get("MYSQL_USER");
        MYSQL_PASSWORD = Config.get("MYSQL_PASSWORD");
    }

    public static Connection getConnection() throws SQLException {
        String dbUrl = String.format("jdbc:mysql://%s:%d/%s" , MYSQL_HOST, MYSQL_PORT, MYSQL_DATABASE);
        /*System.out.println(dbUrl);
        System.out.println(MYSQL_USER);
        System.out.println(MYSQL_PASSWORD);*/
        return DriverManager.getConnection(dbUrl, MYSQL_USER, MYSQL_PASSWORD);
    }
}
