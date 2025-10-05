package org.crimefile.services;

public class AuthService {

    public static boolean login(String username, String password, String role) {
        // Temporary dummy logic for testing
        if (role.equals("admin") && username.equals("admin") && password.equals("admin123")) {
            return true;
        } else if (role.equals("user") && username.equals("user") && password.equals("user123")) {
            return true;
        }
        return false;
    }
}
