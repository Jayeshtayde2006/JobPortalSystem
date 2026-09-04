package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jobportal.dao.UserDAO;
import com.jobportal.model.User;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    public RegisterFrame() {

        setTitle("Job Portal - Registration");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // ==========================================
        // TITLE
        // ==========================================

        JLabel titleLabel =
                new JLabel(
                        "CREATE ACCOUNT",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Join the Job Portal",
                        JLabel.CENTER
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        titlePanel.add(
                subtitleLabel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // FORM PANEL
        // ==========================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        8, 5, 8, 5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // ==========================================
        // NAME
        // ==========================================

        JLabel nameLabel =
                new JLabel("Full Name:");

        nameField =
                new JTextField();

        nameField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        formPanel.add(
                nameLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        formPanel.add(
                nameField,
                gbc
        );

        // ==========================================
        // EMAIL
        // ==========================================

        JLabel emailLabel =
                new JLabel("Email:");

        emailField =
                new JTextField();

        emailField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                emailLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                emailField,
                gbc
        );

        // ==========================================
        // PASSWORD
        // ==========================================

        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField();

        passwordField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                passwordLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                passwordField,
                gbc
        );

        // ==========================================
        // ROLE
        // ==========================================

        JLabel roleLabel =
                new JLabel("Register As:");

        String[] roles = {
                "CANDIDATE",
                "EMPLOYER"
        };

        roleBox =
                new JComboBox<>(roles);

        roleBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                roleLabel,
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                roleBox,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel();

        JButton registerButton =
                new JButton("Register");

        JButton clearButton =
                new JButton("Clear");

        registerButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        clearButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        registerButton.addActionListener(
                e -> registerUser()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        // Press Enter to Register

        passwordField.addActionListener(
                e -> registerUser()
        );

        add(mainPanel);

        setVisible(true);
    }


    // ==========================================
    // REGISTER USER
    // ==========================================

    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String role =
                roleBox.getSelectedItem().toString();


        // ==========================================
        // EMPTY FIELD VALIDATION
        // ==========================================

        if (name.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // NAME VALIDATION
        // ==========================================

        if (!name.matches("[a-zA-Z ]+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name should contain only letters!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // EMAIL VALIDATION
        // ==========================================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // PASSWORD VALIDATION
        // ==========================================

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==========================================
        // CREATE USER
        // ==========================================

        User user =
                new User(
                        name,
                        email,
                        password,
                        role
                );


        // ==========================================
        // SAVE USER
        // ==========================================

        UserDAO userDAO =
                new UserDAO();

        boolean result =
                userDAO.registerUser(user);


        // ==========================================
        // SUCCESS
        // ==========================================

        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!\n"
                            + "Email may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // CLEAR FIELDS
    // ==========================================

    private void clearFields() {

        nameField.setText("");

        emailField.setText("");

        passwordField.setText("");

        roleBox.setSelectedIndex(0);
    }
}