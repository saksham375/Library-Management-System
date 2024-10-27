package com.library.util; // Package declaration for utility classes in the library system

import java.sql.Connection; // Importing the Connection interface for managing database connections
import java.sql.DriverManager; // Importing DriverManager for establishing a connection to the database
import java.sql.SQLException; // Importing SQLException for handling SQL-related exceptions

public class DatabaseConnection {
    // Constants for database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/library_management"; // Database URL
    private static final String USER = "root"; // Database user
    private static final String PASSWORD = "5560"; // Database password

    private static Connection connection = null; // Static variable to hold the database connection

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        // Check if the connection is null or closed
        if (connection == null || connection.isClosed()) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish the connection to the database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                // Throw SQLException if the JDBC driver class is not found
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }
        }
        return connection; // Return the established connection
    }

    // Method to close the database connection
    public static void closeConnection() {
        // Check if the connection is not null
        if (connection != null) {
            try {
                // Close the connection
                connection.close();
            } catch (SQLException e) {
                // Print stack trace if closing the connection fails
                e.printStackTrace();
            }
        }
    }
}
