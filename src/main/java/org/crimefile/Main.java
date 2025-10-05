package org.crimefile;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import org.crimefile.ui.LoginPage;

public class Main {
    public static void main(String[] args) {
        try {
            // Apply FlatDarkLaf theme (modern dark mode)
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the login page
        new LoginPage().setVisible(true);
    }
}
