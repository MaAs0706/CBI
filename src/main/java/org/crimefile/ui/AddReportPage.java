package org.crimefile.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

public class AddReportPage extends JFrame {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton submitButton;

    public AddReportPage() {
        setTitle("Add New Report");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Report Title:");
        titleField = new JTextField(20);

        JLabel descLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(8, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        submitButton = new JButton("Submit Report");

        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.add(titleLabel);
        form.add(titleField);
        form.add(descLabel);
        form.add(scrollPane);
        form.add(submitButton);

        panel.add(form, BorderLayout.CENTER);
        add(panel);

        submitButton.addActionListener(this::handleSubmit);
    }

    private void handleSubmit(ActionEvent e) {
        String title = titleField.getText().trim();
        String desc = descriptionArea.getText().trim();

        if (title.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate mock token
        String token = "CRF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        JOptionPane.showMessageDialog(this,
                "Report submitted successfully!\nYour Token: " + token,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
