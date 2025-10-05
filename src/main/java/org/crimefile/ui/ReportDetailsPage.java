package org.crimefile.ui;

import javax.swing.*;
import java.awt.*;

public class ReportDetailsPage extends JFrame {

    public ReportDetailsPage(String token) {
        setTitle("Report Details - " + token);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Mock details for now
        JLabel tokenLabel = new JLabel("Token: " + token);
        JLabel titleLabel = new JLabel("Title: Example Report");
        JLabel descLabel = new JLabel("<html>Description: This is a mock report detail fetched using the token.<br>More info will come from DB later.</html>");
        JLabel dateLabel = new JLabel("Date: 2025-10-03");

        panel.add(tokenLabel);
        panel.add(titleLabel);
        panel.add(descLabel);
        panel.add(dateLabel);

        add(panel);
    }
}
