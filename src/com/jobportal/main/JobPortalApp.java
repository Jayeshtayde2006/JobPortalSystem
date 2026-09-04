package com.jobportal.main;

import javax.swing.SwingUtilities;

import com.jobportal.ui.LoginFrame;

public class JobPortalApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}