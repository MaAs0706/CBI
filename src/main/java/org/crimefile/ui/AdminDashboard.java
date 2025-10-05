package org.crimefile.ui;

import org.crimefile.dao.ReportDAO;
import org.crimefile.dao.UserDAO;
import org.crimefile.models.Report;
import org.crimefile.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private JTable usersTable;
    private JTable reportsTable;
    private DefaultTableModel usersTableModel;
    private DefaultTableModel reportsTableModel;

    private JButton editUserButton;
    private JButton viewReportButton;

    private UserDAO userDAO;
    private ReportDAO reportDAO;

    public AdminDashboard() {
        userDAO = new UserDAO();
        reportDAO = new ReportDAO();

        initUI();
        loadUsers();
        loadReports();
    }

    private void initUI() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Top panel ---
        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // --- Center Panel with Users and Reports ---
        JTabbedPane tabbedPane = new JTabbedPane();

        // Users Tab
        usersTableModel = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable = new JTable(usersTableModel);
        JScrollPane usersScroll = new JScrollPane(usersTable);

        editUserButton = new JButton("Edit Selected User");
        editUserButton.addActionListener(e -> editSelectedUser());
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.add(usersScroll, BorderLayout.CENTER);
        usersPanel.add(editUserButton, BorderLayout.SOUTH);

        tabbedPane.add("Users", usersPanel);

        // Reports Tab
        reportsTableModel = new DefaultTableModel(new String[]{"Token", "Title", "User ID", "Status", "Created At"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportsTable = new JTable(reportsTableModel);
        JScrollPane reportsScroll = new JScrollPane(reportsTable);

        viewReportButton = new JButton("View Report Details");
        viewReportButton.addActionListener(e -> viewSelectedReport());
        JPanel reportsPanel = new JPanel(new BorderLayout());
        reportsPanel.add(reportsScroll, BorderLayout.CENTER);
        reportsPanel.add(viewReportButton, BorderLayout.SOUTH);

        tabbedPane.add("Reports", reportsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // --- Load users into table ---
    private void loadUsers() {
        usersTableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            usersTableModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getRole()});
        }
    }

    // --- Load all reports into table ---
    private void loadReports() {
        reportsTableModel.setRowCount(0);
        List<Report> reports = reportDAO.getAllReports(); // You need to add getAllReports() in ReportDAO
        for (Report r : reports) {
            reportsTableModel.addRow(new Object[]{
                    r.getToken(),
                    r.getTitle(),
                    r.getUserId(),
                    r.getStatus(),
                    r.getCreatedAt()
            });
        }
    }

    // --- Edit user dialog ---
    private void editSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) usersTableModel.getValueAt(selectedRow, 0);
        User user = userDAO.getUserById(userId);

        JTextField usernameField = new JTextField(user.getUsername());
        JTextField roleField = new JTextField(user.getRole());
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Role (admin/user):", roleField,
                "New Password (leave blank to keep):", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newUsername = usernameField.getText().trim();
            String newRole = roleField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            boolean success = userDAO.updateUser(userId, newUsername, newRole, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "User updated successfully!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- View report details ---
    private void viewSelectedReport() {
        int selectedRow = reportsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a report", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String token = (String) reportsTableModel.getValueAt(selectedRow, 0);
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
}
