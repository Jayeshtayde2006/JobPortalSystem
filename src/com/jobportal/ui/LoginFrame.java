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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jobportal.dao.UserDAO;
import com.jobportal.model.User;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Job Portal - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==============================
        // MAIN PANEL
        // ==============================

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // ==============================
        // TITLE
        // ==============================

        JLabel titleLabel =
                new JLabel(
                        "JOB PORTAL",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Login to your account",
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

        // ==============================
        // FORM PANEL
        // ==============================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10, 5, 10, 5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // Email

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
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        formPanel.add(
                emailLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        formPanel.add(
                emailField,
                gbc
        );

        // Password

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
        gbc.gridy = 1;
        gbc.weightx = 0.3;

        formPanel.add(
                passwordLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 0.7;

        formPanel.add(
                passwordField,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // ==============================
        // BUTTON PANEL
        // ==============================

        JPanel buttonPanel =
                new JPanel();

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        loginButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        registerButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==============================
        // BUTTON ACTIONS
        // ==============================

        loginButton.addActionListener(
                e -> loginUser()
        );

        registerButton.addActionListener(
                e -> new RegisterFrame()
        );

        // Press Enter to Login

        passwordField.addActionListener(
                e -> loginUser()
        );

        add(mainPanel);

        setVisible(true);
    }


    // ==========================================
    // LOGIN USER
    // ==========================================

    private void loginUser() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        // ==============================
        // EMPTY FIELD VALIDATION
        // ==============================

        if (email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ==============================
        // EMAIL VALIDATION
        // ==============================

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


        // ==============================
        // LOGIN VERIFICATION
        // ==============================

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.loginUser(
                        email,
                        password
                );


        // ==============================
        // LOGIN SUCCESS
        // ==============================

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!\nWelcome "
                            + user.getName(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            System.out.println(
                    "Logged in User: "
                            + user.getName()
            );

            System.out.println(
                    "Role: "
                            + user.getRole()
            );


            // ==============================
            // ROLE-BASED DASHBOARD
            // ==============================

            if (user.getRole()
                    .equalsIgnoreCase("CANDIDATE")) {

                dispose();

                new CandidateDashboard(user);

            } else if (user.getRole()
                    .equalsIgnoreCase("EMPLOYER")) {

                dispose();

                new EmployerDashboard(user);

            } else if (user.getRole()
                    .equalsIgnoreCase("ADMIN")) {

                dispose();

                new AdminDashboard(user);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid user role!",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}