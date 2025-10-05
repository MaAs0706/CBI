package org.crimefile.ui;

import org.crimefile.dao.ReportDAO;
import org.crimefile.models.Report;
import org.crimefile.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame {

    private User loggedInUser;
    private JTable reportsTable;
    private DefaultTableModel tableModel;
    private JButton viewButton;
    private JButton addButton;
    private ReportDAO reportDAO;

    public UserDashboard(User user) {
        this.loggedInUser = user;
        this.reportDAO = new ReportDAO();
        initUI();
        loadReports();
    }

    private void initUI() {
        setTitle("User Dashboard - " + loggedInUser.getUsername());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Top panel with welcome message ---
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUser.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // --- Table of reports ---
        String[] columns = {"Token", "Title", "Status", "Created At"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table read-only
            }
        };
        reportsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reportsTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom panel with buttons ---
        JPanel bottomPanel = new JPanel();

        viewButton = new JButton("View Report Details");
        viewButton.addActionListener(e -> viewReportDetails());
        bottomPanel.add(viewButton);

        addButton = new JButton("Add New Report");
        addButton.addActionListener(e -> addNewReport());
        bottomPanel.add(addButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- Load user's reports into table ---
    private void loadReports() {
        tableModel.setRowCount(0); // clear previous rows
        List<Report> reports = reportDAO.getReportsByUser(loggedInUser.getId());
        for (Report r : reports) {
            tableModel.addRow(new Object[]{
                    r.getToken(),
                    r.getTitle(),
                    r.getStatus(),
                    r.getCreatedAt()
            });
        }
    }

    // --- View report full details by token ---
    private void viewReportDetails() {
        int selectedRow = reportsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a report", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String token = (String) tableModel.getValueAt(selectedRow, 0);
        Report report = reportDAO.getReportByToken(token);
        if (report != null) {
            JOptionPane.showMessageDialog(this,
                    "Token: " + report.getToken() + "\n" +
                            "Title: " + report.getTitle() + "\n" +
                            "Description: " + report.getDescription() + "\n" +
                            "Status: " + report.getStatus() + "\n" +
                            "Created At: " + report.getCreatedAt(),
                    "Report Details",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Report not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Add a new report ---
    private void addNewReport() {
        JTextField titleField = new JTextField();
        JTextArea descArea = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(descArea);

        Object[] message = {
                "Title:", titleField,
                "Description:", scroll
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Report", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String description = descArea.getText().trim();
            if (!title.isEmpty() && !description.isEmpty()) {
                Report report = reportDAO.addReport(loggedInUser.getId(), title, description);
                if (report != null) {
                    loadReports();
                    JOptionPane.showMessageDialog(this, "Report added successfully!\nToken: " + report.getToken());
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add report", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Title and Description cannot be empty", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
