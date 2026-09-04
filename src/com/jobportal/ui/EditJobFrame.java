package com.jobportal.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;
import com.jobportal.model.User;

public class EditJobFrame extends JFrame {

    private User employer;
    private Job job;
    private MyJobsFrame myJobsFrame;

    private JTextField jobTitleField;
    private JTextField companyNameField;
    private JTextField locationField;
    private JTextField salaryField;
    private JComboBox<String> jobTypeBox;
    private JTextField skillsField;
    private JTextArea descriptionArea;

    public EditJobFrame(
            User employer,
            Job job,
            MyJobsFrame myJobsFrame) {

        this.employer = employer;
        this.job = job;
        this.myJobsFrame = myJobsFrame;

        setTitle("Edit Job");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(
                new GridLayout(8, 2, 10, 10)
        );

        add(new JLabel("Job Title:"));

        jobTitleField = new JTextField(
                job.getJobTitle()
        );

        add(jobTitleField);


        add(new JLabel("Company Name:"));

        companyNameField = new JTextField(
                job.getCompanyName()
        );

        add(companyNameField);


        add(new JLabel("Location:"));

        locationField = new JTextField(
                job.getLocation()
        );

        add(locationField);


        add(new JLabel("Salary:"));

        salaryField = new JTextField(
                job.getSalary()
        );

        add(salaryField);


        add(new JLabel("Job Type:"));

        String[] types = {
                "Full Time",
                "Part Time",
                "Internship",
                "Contract"
        };

        jobTypeBox =
                new JComboBox<>(types);

        jobTypeBox.setSelectedItem(
                job.getJobType()
        );

        add(jobTypeBox);


        add(new JLabel("Required Skills:"));

        skillsField = new JTextField(
                job.getRequiredSkills()
        );

        add(skillsField);


        add(new JLabel("Description:"));

        descriptionArea =
                new JTextArea(
                        job.getDescription()
                );

        add(descriptionArea);


        JButton updateButton =
                new JButton("Update Job");

        JButton cancelButton =
                new JButton("Cancel");

        add(updateButton);
        add(cancelButton);


        updateButton.addActionListener(
                e -> updateJob()
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }


    private void updateJob() {

        String jobTitle = jobTitleField.getText().trim();
        String companyName = companyNameField.getText().trim();
        String location = locationField.getText().trim();
        String salary = salaryField.getText().trim();
        String jobType = jobTypeBox.getSelectedItem().toString();
        String skills = skillsField.getText().trim();
        String description = descriptionArea.getText().trim();

        // ==========================================
        // EMPTY FIELD VALIDATION
        // ==========================================

        if (jobTitle.isEmpty()
                || companyName.isEmpty()
                || location.isEmpty()
                || salary.isEmpty()
                || skills.isEmpty()
                || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // SALARY VALIDATION
        // ==========================================

        try {

            double salaryValue = Double.parseDouble(
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
        // JOB TITLE VALIDATION
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

        // ==========================================
        // COMPANY VALIDATION
        // ==========================================

        if (companyName.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid company name!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // DESCRIPTION VALIDATION
        // ==========================================

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
        // UPDATE JOB OBJECT
        // ==========================================

        job.setJobTitle(jobTitle);
        job.setCompanyName(companyName);
        job.setLocation(location);
        job.setSalary(salary);
        job.setJobType(jobType);
        job.setRequiredSkills(skills);
        job.setDescription(description);

        // ==========================================
        // UPDATE DATABASE
        // ==========================================

        JobDAO jobDAO = new JobDAO();

        boolean result = jobDAO.updateJob(job);

        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            myJobsFrame.dispose();
            new MyJobsFrame(employer);
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update job!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}