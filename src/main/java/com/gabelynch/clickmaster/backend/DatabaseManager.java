package com.gabelynch.clickmaster.backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:database/clickmaster.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        File dbFile = new File("database/clickmaster.db");
        if (!dbFile.exists()) {
            createDatabase();
        }
    }

    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            if (connection != null) {
                System.out.println("Database created successfully.");
                createTables(connection);
            }
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private static void createTables(Connection connection) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS settings ("
                                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + "interval INTEGER, "
                                + "button INTEGER, "
                                + "x INTEGER, "
                                + "y INTEGER);";
        
        try {
            connection.createStatement().execute(createTableSQL);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void saveSettings(int interval, int button, int x, int y) {
        String insertSQL = "INSERT INTO settings (interval, button, x, y) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setInt(1, interval);
            statement.setInt(2, button);
            statement.setInt(3, x);
            statement.setInt(4, y);
            statement.executeUpdate();
            System.out.println("Settings saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    public static void loadLastSettings() {
        String selectSQL = "SELECT * FROM settings ORDER BY id DESC LIMIT 1";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(selectSQL);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Last Saved Settings:");
                System.out.println("Interval: " + rs.getInt("interval"));
                System.out.println("Button: " + rs.getString("button"));
                System.out.println("X: " + rs.getInt("x"));
                System.out.println("Y: " + rs.getInt("y"));
            }
        } catch (SQLException e) {
            System.err.println("Error loading settings: " + e.getMessage());
        }
    }
}
