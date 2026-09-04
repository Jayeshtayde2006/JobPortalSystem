package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jobportal.model.User;

public class CandidateDashboard extends JFrame {

    private User user;

    public CandidateDashboard(User user) {

        this.user = user;

        setTitle("Job Portal - Candidate Dashboard");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // ==========================================
        // HEADER
        // ==========================================

        JLabel titleLabel =
                new JLabel(
                        "Candidate Dashboard",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " + user.getName(),
                        JLabel.CENTER
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        17
                )
        );

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        headerPanel.add(
                welcomeLabel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        gbc.insets =
                new Insets(
                        6, 20, 6, 20
                );

        // ==========================================
        // BUTTONS
        // ==========================================

        JButton viewJobsButton =
                new JButton(
                        "View Available Jobs"
                );

        JButton searchJobsButton =
                new JButton(
                        "Search Jobs"
                );

        JButton filterJobsButton =
                new JButton(
                        "Filter Jobs"
                );

        JButton appliedJobsButton =
                new JButton(
                        "My Applications"
                );

        JButton profileButton =
                new JButton(
                        "My Profile"
                );

        JButton logoutButton =
                new JButton(
                        "Logout"
                );

        // ==========================================
        // BUTTON FONT
        // ==========================================

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                );

        viewJobsButton.setFont(buttonFont);
        searchJobsButton.setFont(buttonFont);
        filterJobsButton.setFont(buttonFont);
        appliedJobsButton.setFont(buttonFont);
        profileButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        // ==========================================
        // ADD BUTTONS
        // ==========================================

        gbc.gridy = 0;
        buttonPanel.add(
                viewJobsButton,
                gbc
        );

        gbc.gridy = 1;
        buttonPanel.add(
                searchJobsButton,
                gbc
        );

        gbc.gridy = 2;
        buttonPanel.add(
                filterJobsButton,
                gbc
        );

        gbc.gridy = 3;
        buttonPanel.add(
                appliedJobsButton,
                gbc
        );

        gbc.gridy = 4;
        buttonPanel.add(
                profileButton,
                gbc
        );

        gbc.gridy = 5;
        buttonPanel.add(
                logoutButton,
                gbc
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        viewJobsButton.addActionListener(
                e -> new ViewJobsFrame(user)
        );

        searchJobsButton.addActionListener(
                e -> new ViewJobsFrame(user)
        );

        filterJobsButton.addActionListener(
                e -> new ViewJobsFrame(user)
        );

        appliedJobsButton.addActionListener(
                e -> new MyApplicationsFrame(user)
        );

        profileButton.addActionListener(
                e -> new ProfileFrame(user)
        );

        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        // ==========================================
        // DISPLAY
        // ==========================================

        add(mainPanel);

        setVisible(true);
    }
}