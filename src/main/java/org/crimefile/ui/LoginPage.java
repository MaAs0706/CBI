package org.crimefile.ui;

import com.formdev.flatlaf.FlatLightLaf;
import org.crimefile.services.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchRoleButton;
    private JLabel roleLabel;
    private String currentRole = "user"; // default role

    public LoginPage() {
        // Apply FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Crime File Management - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // Center form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Crime File Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        roleLabel = new JLabel("Current Role: USER", SwingConstants.CENTER);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(roleLabel, gbc);

        gbc.gridy++;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 130, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        formPanel.add(loginButton, gbc);

        gbc.gridy++;
        switchRoleButton = new JButton("Switch to Admin Login");
        formPanel.add(switchRoleButton, gbc);

        // Add panels
        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);

        // Event listeners
        addListeners();
    }

    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean success = AuthService.login(username, password, currentRole);

                if (success) {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Login successful as " + currentRole.toUpperCase() + "!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Navigate based on role
                    if (currentRole.equals("admin")) {
                        new AdminDashboard().setVisible(true);
                    } else {
                        new UserDashboard().setVisible(true);
                    }
                    dispose(); // close login window
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid credentials!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        switchRoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRole.equals("user")) {
                    currentRole = "admin";
                    roleLabel.setText("Current Role: ADMIN");
                    switchRoleButton.setText("Switch to User Login");
                } else {
                    currentRole = "user";
                    roleLabel.setText("Current Role: USER");
                    switchRoleButton.setText("Switch to Admin Login");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
