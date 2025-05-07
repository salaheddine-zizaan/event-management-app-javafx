package org.example.eventmanagement.Controller;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "/db.properties";

    public static Connection getConnection() throws SQLException {
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new SQLException("Failed to load database configuration", e);
        }
    }
}