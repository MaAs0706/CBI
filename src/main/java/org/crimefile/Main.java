package org.crimefile;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import org.crimefile.ui.HomePage;
import org.crimefile.db.DatabaseSetup;

public class Main {
    public static void main(String[] args) {
        try {
            // Apply FlatDarkLaf theme (modern dark mode)
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize database (create tables if not exists)
        DatabaseSetup.initialize();

        // Launch the homepage
        new HomePage().setVisible(true);
    }
}
