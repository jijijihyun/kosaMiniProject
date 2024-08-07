package com.kosa.libaraySystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/kosaMiniProject";
    private static final String JDBC_ID = "kosa";
    private static final String JDBC_PASSWORD = "kosa123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_ID, JDBC_PASSWORD);
    }
}
