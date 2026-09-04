package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;
import com.jobportal.model.User;

public class PostJobFrame extends JFrame {

    private User employer;

    private JTextField jobTitleField;
    private JTextField companyNameField;
    private JTextField locationField;
    private JTextField salaryField;
    private JComboBox<String> jobTypeBox;
    private JTextArea descriptionArea;
    private JTextField skillsField;

    public PostJobFrame(User employer) {

        this.employer = employer;

        setTitle("Job Portal - Post New Job");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Job Title
        panel.add(new JLabel("Job Title:"));
        jobTitleField = new JTextField();
        panel.add(jobTitleField);

        // Company Name
        panel.add(new JLabel("Company Name:"));
        companyNameField = new JTextField();
        panel.add(companyNameField);

        // Location
        panel.add(new JLabel("Location:"));
        locationField = new JTextField();
        panel.add(locationField);

        // Salary
        panel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        panel.add(salaryField);

        // Job Type
        panel.add(new JLabel("Job Type:"));

        String[] jobTypes = {
                "Full Time",
                "Part Time",
                "Internship",
                "Contract"
        };

        jobTypeBox = new JComboBox<>(jobTypes);
        panel.add(jobTypeBox);

        // Required Skills
        panel.add(new JLabel("Required Skills:"));
        skillsField = new JTextField();
        panel.add(skillsField);

        // Description
        panel.add(new JLabel("Description:"));

        descriptionArea = new JTextArea(5, 20);
        JScrollPane scrollPane =
                new JScrollPane(descriptionArea);

        panel.add(scrollPane);

        // Buttons
        JButton postButton = new JButton("Post Job");
        JButton clearButton = new JButton("Clear");

        panel.add(postButton);
        panel.add(clearButton);

        add(panel, BorderLayout.CENTER);

        // Post button
        postButton.addActionListener(e -> postJob());

        // Clear button
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void postJob() {

        String jobTitle = jobTitleField.getText().trim();
        String companyName = companyNameField.getText().trim();
        String location = locationField.getText().trim();
        String salary = salaryField.getText().trim();

        String jobType =
                jobTypeBox.getSelectedItem().toString();

        String description =
                descriptionArea.getText().trim();

        String skills =
                skillsField.getText().trim();

     // ==========================================
     // VALIDATION
     // ==========================================

     // Job Title
     if (jobTitle.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter the job title!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         jobTitleField.requestFocus();
         return;
     }

     // Company Name
     if (companyName.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter the company name!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         companyNameField.requestFocus();
         return;
     }

     // Location
     if (location.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter the job location!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         locationField.requestFocus();
         return;
     }

     // Salary
     if (salary.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter the salary!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         salaryField.requestFocus();
         return;
     }

     // Salary should contain only numbers
     try {

         Double.parseDouble(salary);

     } catch (NumberFormatException e) {

         JOptionPane.showMessageDialog(
                 this,
                 "Salary must contain a valid number!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         salaryField.requestFocus();
         return;
     }

     // Required Skills
     if (skills.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter required skills!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         skillsField.requestFocus();
         return;
     }

     // Description
     if (description.isEmpty()) {

         JOptionPane.showMessageDialog(
                 this,
                 "Please enter the job description!",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
         );

         descriptionArea.requestFocus();
         return;
     }

        // ==========================================
        // SALARY VALIDATION
        // ==========================================

        try {

            double salaryValue =
                    Double.parseDouble(
                            salary.replaceAll("[^0-9.]", "")
                    );

            if (salaryValue <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salary must be greater than 0!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid salary!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // MINIMUM LENGTH VALIDATION
        // ==========================================

        if (jobTitle.length() < 3) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job title must contain at least 3 characters!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (companyName.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid company name!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (description.length() < 10) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job description must contain at least 10 characters!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // CREATE JOB OBJECT
        // ==========================================

        Job job = new Job(
                employer.getId(),
                jobTitle,
                companyName,
                location,
                salary,
                jobType,
                description,
                skills
        );

        // ==========================================
        // SAVE JOB
        // ==========================================

        JobDAO jobDAO = new JobDAO();

        boolean result = jobDAO.addJob(job);

        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job Posted Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to post job!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    } 
    private void clearFields() {

        jobTitleField.setText("");
        companyNameField.setText("");
        locationField.setText("");
        salaryField.setText("");
        skillsField.setText("");
        descriptionArea.setText("");
        jobTypeBox.setSelectedIndex(0);
    }
    }
