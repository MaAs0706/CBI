package org.crimefile.ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {

    private JButton loginButton;
    private JButton exitButton;

    public HomePage() {
        // Apply FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Crime File Management System");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main panel with padding
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));

        // --- Header with logo and title ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(panel.getBackground());

        // Optional logo (replace with your icon path)
        // JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/icons/logo.png")));
        // header.add(logo, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Crime File System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));
        header.add(titleLabel, BorderLayout.CENTER);

        panel.add(header, BorderLayout.NORTH);

        // --- Info text ---
        JTextArea infoArea = new JTextArea(
                "Welcome to the Crime File Management System!\n\n" +
                        "• Users can submit and view crime reports.\n" +
                        "• Admins can manage users and view all reports.\n\n" +
                        "Click 'Login' to get started."
        );
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoArea.setEditable(false);
        infoArea.setBackground(panel.getBackground());
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setMargin(new Insets(20, 20, 20, 20));

        panel.add(infoArea, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(panel.getBackground());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        loginButton = new JButton("Login");
        styleButton(loginButton, new Color(60, 130, 250));

        exitButton = new JButton("Exit");
        styleButton(exitButton, new Color(200, 50, 50));

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // --- Button actions ---
        loginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));
    }

    // Helper to style buttons
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage().setVisible(true));
    }
}
