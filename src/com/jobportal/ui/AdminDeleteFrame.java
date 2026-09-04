package com.jobportal.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jobportal.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminDeleteFrame extends JFrame {

    private JTextField userIdField;
    private JTextField jobIdField;

    public AdminDeleteFrame() {

        setTitle("Admin - Delete User / Job");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel userLabel = new JLabel("User ID:");
        userIdField = new JTextField();

        JButton deleteUserButton =
                new JButton("Delete User");

        JLabel jobLabel = new JLabel("Job ID:");
        jobIdField = new JTextField();

        JButton deleteJobButton =
                new JButton("Delete Job");

        JButton closeButton =
                new JButton("Close");

        panel.add(userLabel);
        panel.add(userIdField);

        panel.add(deleteUserButton);
        panel.add(new JLabel(""));

        panel.add(jobLabel);
        panel.add(jobIdField);

        panel.add(deleteJobButton);
        panel.add(new JLabel(""));

        panel.add(closeButton);

        add(panel);

        // Delete User
        deleteUserButton.addActionListener(e -> deleteUser());

        // Delete Job
        deleteJobButton.addActionListener(e -> deleteJob());

        // Close
        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void deleteUser() {

        String userIdText = userIdField.getText().trim();

        if (userIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter User ID!"
            );

            return;
        }

        try {

            int userId = Integer.parseInt(userIdText);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete User ID "
                            + userId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql =
                    "DELETE FROM users WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst =
                         con.prepareStatement(sql)) {

                pst.setInt(1, userId);

                int rows = pst.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User deleted successfully!"
                    );

                    userIdField.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "User ID not found!"
                    );
                }
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric User ID!"
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot delete user.\n"
                            + "The user may have related jobs/applications."
            );
        }
    }

    private void deleteJob() {

        String jobIdText = jobIdField.getText().trim();

        if (jobIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Job ID!"
            );

            return;
        }

        try {

            int jobId = Integer.parseInt(jobIdText);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete Job ID "
                            + jobId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql =
                    "DELETE FROM jobs WHERE id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst =
                         con.prepareStatement(sql)) {

                pst.setInt(1, jobId);

                int rows = pst.executeUpdate();

                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Job deleted successfully!"
                    );

                    jobIdField.setText("");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Job ID not found!"
                    );
                }
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric Job ID!"
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Cannot delete job.\n"
                            + "The job may have existing applications."
            );
        }
    }
}