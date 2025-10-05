package org.crimefile.db;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initialize() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            // --- Create users table ---
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    role ENUM('admin', 'user') NOT NULL
                );
            """;
            stmt.execute(createUsersTable);

            // --- Insert default admin if not exists ---
            String insertAdmin = """
                INSERT IGNORE INTO users (id, username, password, role)
                VALUES (1, 'admin', 'admin123', 'admin');
            """;
            stmt.execute(insertAdmin);

            // --- Create reports table ---
            String createReportsTable = """
                CREATE TABLE IF NOT EXISTS reports (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    token VARCHAR(50) NOT NULL UNIQUE,
                    user_id INT NOT NULL,
                    title VARCHAR(255) NOT NULL,
                    description TEXT NOT NULL,
                    status VARCHAR(50) DEFAULT 'Pending',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                );
            """;
            stmt.execute(createReportsTable);

            System.out.println("Database setup completed!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to setup database");
        }
    }
}
