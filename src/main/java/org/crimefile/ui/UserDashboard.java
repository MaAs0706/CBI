package org.crimefile.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends JFrame {

    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField tokenSearchField;
    private JButton addReportButton;
    private JButton fetchByTokenButton;
    private JButton logoutButton;

    public UserDashboard() {
        setTitle("Crime File System - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));

        // --- Header ---
        JLabel titleLabel = new JLabel("Welcome, User 👮‍♂️", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columnNames = {"Token Number", "Title", "Date Submitted"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        reportTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(reportTable);

        // Load dummy reports
        loadReports();

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Controls ---
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        tokenSearchField = new JTextField(15);
        tokenSearchField.setToolTipText("Enter token number");
        fetchByTokenButton = new JButton("Fetch by Token");
        addReportButton = new JButton("Add New Report");
        logoutButton = new JButton("Logout");

        gbc.gridx = 0;
        bottomPanel.add(tokenSearchField, gbc);

        gbc.gridx = 1;
        bottomPanel.add(fetchByTokenButton, gbc);

        gbc.gridx = 2;
        bottomPanel.add(addReportButton, gbc);

        gbc.gridx = 3;
        bottomPanel.add(logoutButton, gbc);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // --- Button Actions ---
        fetchByTokenButton.addActionListener(this::handleFetchByToken);
        addReportButton.addActionListener(this::handleAddReport);
        logoutButton.addActionListener(e -> handleLogout());
    }

    // Temporary mock data for now
    private void loadReports() {
        List<String[]> mockReports = new ArrayList<>();
        mockReports.add(new String[]{"CRF-1728312034159", "Robbery in Market Street", "2025-10-03"});
        mockReports.add(new String[]{"CRF-1728312037120", "Missing Vehicle", "2025-10-04"});

        for (String[] row : mockReports) {
            tableModel.addRow(row);
        }
    }

    private void handleFetchByToken(ActionEvent e) {
        String token = tokenSearchField.getText().trim();
        if (token.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a token number.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Open detail page (mock)
        new ReportDetailsPage(token).setVisible(true);
    }

    private void handleAddReport(ActionEvent e) {
        new AddReportPage().setVisible(true);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginPage().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDashboard().setVisible(true));
    }
}
