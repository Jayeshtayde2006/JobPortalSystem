package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jobportal.dao.UserDAO;
import com.jobportal.dao.JobDAO;
import com.jobportal.dao.ApplicationDAO;

import com.jobportal.model.User;

public class AdminDashboard extends JFrame {

    private User admin;
    private JLabel usersLabel;
    private JLabel jobsLabel;
    private JLabel applicationsLabel;
    private void loadStatistics() {

        UserDAO userDAO = new UserDAO();
        JobDAO jobDAO = new JobDAO();
        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        int totalUsers =
                userDAO.getTotalUsers();

        int totalJobs =
                jobDAO.getTotalJobs();

        int totalApplications =
                applicationDAO.getTotalApplications();

        usersLabel.setText(
                "Users: " + totalUsers
        );

        jobsLabel.setText(
                "Jobs: " + totalJobs
        );

        applicationsLabel.setText(
                "Applications: " + totalApplications
        );
    }

    public AdminDashboard(User admin) {

        this.admin = admin;

        setTitle("Job Portal - Admin Dashboard");

        setSize(600, 500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // ==========================
        // TITLE
        // ==========================

        JLabel titleLabel =
                new JLabel(
                        "Welcome Admin, "
                        + admin.getName(),
                        JLabel.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );
        
        JPanel statsPanel = new JPanel();

        statsPanel.setLayout(new GridLayout(1, 3, 15, 15));

        usersLabel = new JLabel("Users: 0", JLabel.CENTER);
        jobsLabel = new JLabel("Jobs: 0", JLabel.CENTER);
        applicationsLabel =
                new JLabel("Applications: 0", JLabel.CENTER);

        statsPanel.add(usersLabel);
        statsPanel.add(jobsLabel);
        statsPanel.add(applicationsLabel);

        add(statsPanel, BorderLayout.NORTH);

        // ==========================
        // BUTTON PANEL
        // ==========================

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setLayout(
                new GridLayout(
                        5,
                        1,
                        10,
                        10
                )
        );

        JButton usersButton =
                new JButton("View All Users");
        
        usersButton.addActionListener(e -> {
            new ViewUsersFrame();
        });

        JButton jobsButton =
                new JButton("View All Jobs");
        
        jobsButton.addActionListener(e -> {
            new ViewAllJobsFrame();
        });

        JButton applicationsButton =
                new JButton("View All Applications");
        
        applicationsButton.addActionListener(e -> {
            new AdminApplicationsFrame();
        });
        
        JButton deleteButton =
                new JButton("Delete User / Job");
        deleteButton.addActionListener(e -> {
            new AdminDeleteFrame();
        });   
        
        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(usersButton);
        buttonPanel.add(jobsButton);
        buttonPanel.add(applicationsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // ==========================
        // LOGOUT
        // ==========================

        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();

        });
        loadStatistics();
        
       

        setVisible(true);
    }
}