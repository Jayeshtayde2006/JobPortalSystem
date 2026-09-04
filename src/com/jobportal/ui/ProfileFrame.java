package com.jobportal.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jobportal.dao.UserDAO;
import com.jobportal.model.User;

public class ProfileFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField educationField;
    private JTextField skillsField;
    private JTextField experienceField;

    private User user;

    public ProfileFrame(User user) {

        this.user = user;

        setTitle("My Profile");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        // Name
        panel.add(new JLabel("Name:"));
        nameField = new JTextField(user.getName());
        nameField.setEditable(false);
        panel.add(nameField);

        // Email
        panel.add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        emailField.setEditable(false);
        panel.add(emailField);

        // Phone
        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField(
                user.getPhone() == null ? "" : user.getPhone()
        );
        panel.add(phoneField);

        // Education
        panel.add(new JLabel("Education:"));
        educationField = new JTextField(
                user.getEducation() == null ? "" : user.getEducation()
        );
        panel.add(educationField);

        // Skills
        panel.add(new JLabel("Skills:"));
        skillsField = new JTextField(
                user.getSkills() == null ? "" : user.getSkills()
        );
        panel.add(skillsField);

        // Experience
        panel.add(new JLabel("Experience:"));
        experienceField = new JTextField(
                user.getExperience() == null ? "" : user.getExperience()
        );
        panel.add(experienceField);

        // Update button
        JButton updateButton = new JButton("Update Profile");
        panel.add(updateButton);

        // Close button
        JButton closeButton = new JButton("Close");
        panel.add(closeButton);

        add(panel);

        // Update Profile
        updateButton.addActionListener(e -> updateProfile());

        // Close
        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void updateProfile() {

        String phone = phoneField.getText().trim();
        String education = educationField.getText().trim();
        String skills = skillsField.getText().trim();
        String experience = experienceField.getText().trim();

        // ==========================================
        // EMPTY FIELD VALIDATION
        // ==========================================

        if (phone.isEmpty()
                || education.isEmpty()
                || skills.isEmpty()
                || experience.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all profile fields!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // PHONE VALIDATION
        // ==========================================

        if (!phone.matches("[0-9]{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // EDUCATION VALIDATION
        // ==========================================

        if (education.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid education details!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // SKILLS VALIDATION
        // ==========================================

        if (skills.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid skills!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // EXPERIENCE VALIDATION
        // ==========================================

        if (experience.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid experience details!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // ==========================================
        // UPDATE DATABASE
        // ==========================================

        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.updateProfile(
                user.getId(),
                phone,
                education,
                skills,
                experience
        );

        if (success) {

            // Update current User object
            user.setPhone(phone);
            user.setEducation(education);
            user.setSkills(skills);
            user.setExperience(experience);

            JOptionPane.showMessageDialog(
                    this,
                    "Profile Updated Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to Update Profile!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}