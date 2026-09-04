package com.jobportal.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/job_portal";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            System.getenv("JOB_PORTAL_DB_PASSWORD");

    public static Connection getConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return connection;
    }
}