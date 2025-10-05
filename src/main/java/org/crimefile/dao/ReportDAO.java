package org.crimefile.dao;

import org.crimefile.db.DatabaseConnection;
import org.crimefile.models.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportDAO {

    private Connection conn;

    public ReportDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // Fetch all reports by user
    public List<Report> getReportsByUser(int userId) {
        List<Report> reports = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reports WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }

    // Fetch single report by token
    public Report getReportByToken(String token) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reports WHERE token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Report(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add new report
    public Report addReport(int userId, String title, String description) {
        try {
            String token = generateToken();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO reports (token, user_id, title, description, status) VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, token);
            stmt.setInt(2, userId);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, "Pending");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return null;

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                return new Report(id, token, userId, title, description, "Pending", new Timestamp(System.currentTimeMillis()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Fetch all reports
    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reports");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }


    // Generate a unique token for each report
    private String generateToken() {
        return UUID.randomUUID().toString().substring(0, 8); // 8-character token
    }
}
