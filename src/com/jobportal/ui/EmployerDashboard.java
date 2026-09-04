package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jobportal.model.User;

public class EmployerDashboard extends JFrame {

    private User user;

    public EmployerDashboard(User user) {

        this.user = user;

        setTitle("Job Portal - Employer Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JLabel titleLabel = new JLabel(
                "Welcome, " + user.getName(),
                JLabel.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
                new GridLayout(6, 1, 10, 10)
        );

        JButton postJobButton =
                new JButton("Post New Job");

        JButton myJobsButton =
                new JButton("My Posted Jobs");
        
        myJobsButton.addActionListener(e -> {
            new MyJobsFrame(user);
        });

        JButton editJobButton =
                new JButton("Edit Job");

        JButton deleteJobButton =
                new JButton("Delete Job");

        JButton applicationsButton =
                new JButton("View Applications");
        
        applicationsButton.addActionListener(e -> {
            new ViewApplicationsFrame(user);
        });
        

        JButton logoutButton =
                new JButton("Logout");
        
        
        //POST BUTTON
        postJobButton.addActionListener(e -> {
            new PostJobFrame(user);
        });

        buttonPanel.add(postJobButton);
        buttonPanel.add(myJobsButton);
        buttonPanel.add(editJobButton);
        buttonPanel.add(deleteJobButton);
        buttonPanel.add(applicationsButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Logout
        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        setVisible(true);
    }
}