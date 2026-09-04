package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.model.Application;
import com.jobportal.model.User;

public class ViewApplicationsFrame extends JFrame {

    private User employer;
    private JTable applicationTable;
    private DefaultTableModel tableModel;
    private List<Application> applications;

    public ViewApplicationsFrame(User employer) {

        this.employer = employer;

        setTitle("Job Portal - Candidate Applications");
        setSize(1150, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(
                "Candidate Applications",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        JLabel subtitleLabel = new JLabel(
                "Review candidate applications and update application status",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JPanel titlePanel = new JPanel(
                new BorderLayout(5, 5)
        );

        titlePanel.add(
                titleLabel,
                BorderLayout.CENTER
        );

        titlePanel.add(
                subtitleLabel,
                BorderLayout.SOUTH
        );

        headerPanel.add(
                titlePanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Application ID",
                "Candidate Name",
                "Candidate Email",
                "Job Title",
                "Company",
                "Location",
                "Applied Date",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        applicationTable = new JTable(tableModel);

        applicationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        applicationTable.setRowHeight(32);

        applicationTable.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        applicationTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        applicationTable.getTableHeader().setReorderingAllowed(false);

        // Column widths

        applicationTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(90);

        applicationTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        applicationTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(200);

        applicationTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(150);

        applicationTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(140);

        applicationTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(120);

        applicationTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(170);

        applicationTable.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(110);

        JScrollPane scrollPane =
                new JScrollPane(applicationTable);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Applications"
                )
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.CENTER,
                        12,
                        5
                ));

        JButton shortlistButton =
                new JButton("Shortlist");

        JButton rejectButton =
                new JButton("Reject");

        JButton refreshButton =
                new JButton("Refresh");

        JButton closeButton =
                new JButton("Close");

        // Button styling

        JButton[] buttons = {
                shortlistButton,
                rejectButton,
                refreshButton,
                closeButton
        };

        for (JButton button : buttons) {

            button.setFont(
                    new Font("Arial", Font.BOLD, 13)
            );

            button.setFocusPainted(false);

            button.setMargin(
                    new Insets(8, 18, 8, 18)
            );
        }

        buttonPanel.add(shortlistButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        shortlistButton.addActionListener(
                e -> updateStatus("Shortlisted")
        );

        rejectButton.addActionListener(
                e -> updateStatus("Rejected")
        );

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        // =========================
        // DOUBLE CLICK
        // =========================

        applicationTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && applicationTable.getSelectedRow() != -1) {

                            updateStatus("Shortlisted");
                        }
                    }
                }
        );

        // =========================
        // LOAD APPLICATIONS
        // =========================

        loadApplications();

        add(mainPanel);

        setVisible(true);
    }

    // =====================================================
    // LOAD APPLICATIONS
    // =====================================================

    private void loadApplications() {

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        applications =
                applicationDAO.getApplicationsByEmployer(
                        employer.getId()
                );

        displayApplications(applications);

        if (applications.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No candidates have applied yet.",
                    "Applications",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =====================================================
    // DISPLAY APPLICATIONS
    // =====================================================

    private void displayApplications(
            List<Application> applicationList) {

        tableModel.setRowCount(0);

        for (Application application : applicationList) {

            Object[] row = {

                    application.getId(),

                    application.getCandidateName(),

                    application.getCandidateEmail(),

                    application.getJobTitle(),

                    application.getCompanyName(),

                    application.getLocation(),

                    application.getApplicationDate(),

                    application.getStatus()
            };

            tableModel.addRow(row);
        }
    }

    // =====================================================
    // UPDATE APPLICATION STATUS
    // =====================================================

    private void updateStatus(String status) {

        int selectedRow =
                applicationTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an application first!",
                    "Select Application",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Application selectedApplication =
                applications.get(selectedRow);

        int choice =
                JOptionPane.showConfirmDialog(
                        this,

                        "Change application status to "
                                + status
                                + "?\n\n"
                                + "Candidate: "
                                + selectedApplication.getCandidateName()
                                + "\nJob: "
                                + selectedApplication.getJobTitle(),

                        "Confirm Status",

                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        boolean result =
                applicationDAO.updateApplicationStatus(
                        selectedApplication.getId(),
                        employer.getId(),
                        status
                );

        if (result) {

            JOptionPane.showMessageDialog(
                    this,

                    "Application status updated to "
                            + status
                            + "!",

                    "Success",

                    JOptionPane.INFORMATION_MESSAGE
            );

            loadApplications();

        } else {

            JOptionPane.showMessageDialog(
                    this,

                    "Failed to update application status!",

                    "Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}