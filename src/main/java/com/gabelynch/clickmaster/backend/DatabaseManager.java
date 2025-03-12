package com.gabelynch.clickmaster.backend;

import java.awt.event.InputEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String DB_URL = "jdbc:sqlite:database/clickmaster.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        File dbFile = new File("database/clickmaster.db");
        if (!dbFile.exists()) {
            createDatabaseDirectory();
            createDatabase();
        }
    }

    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            if (connection != null) {
                logger.info("Created the Database.");
                createTables(connection);
            }
        } catch (SQLException e) {
            logger.error("Error creating database: " + e.getMessage());
        }
    }

    private static void createDatabaseDirectory() {
        File directory = new File("database");
        if (!directory.exists()) {
            directory.mkdirs();
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
            logger.info("Table created or already exists.");
        } catch (SQLException e) {
            logger.error("Error creating table: " + e.getMessage());
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
            logger.info("Settings saved successfully.");
        } catch (SQLException e) {
            logger.error("Error saving settings: " + e.getMessage());
        }
    }

    public static int[] loadLastSettings() {
        String selectSQL = "SELECT * FROM settings ORDER BY id DESC LIMIT 1";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(selectSQL);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
            return new int[] { rs.getInt("interval"), rs.getInt("x"), rs.getInt("y"), rs.getInt("button") };
        }
        } catch (SQLException e) {
            logger.error("Error loading settings: " + e.getMessage());
        }
        
        return new int[] { 100, -1, -1, InputEvent.BUTTON1_DOWN_MASK 
        };
    }
}