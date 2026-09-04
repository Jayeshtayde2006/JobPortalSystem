package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.table.DefaultTableModel;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.model.Application;
import com.jobportal.model.User;

public class MyApplicationsFrame extends JFrame {

    private User candidate;

    private JTable applicationTable;

    private DefaultTableModel tableModel;

    private List<Application> applications;

    public MyApplicationsFrame(User candidate) {

        this.candidate = candidate;

        setTitle("Job Portal - My Applications");
        setSize(1000, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // ==========================================
        // MAIN PANEL
        // ==========================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        // ==========================================
        // HEADER
        // ==========================================

        JLabel titleLabel =
                new JLabel(
                        "My Job Applications",
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
                        "Track the status of your applications",
                        JLabel.CENTER
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
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
        // TABLE
        // ==========================================

        String[] columns = {

                "Application ID",
                "Job Title",
                "Company",
                "Location",
                "Applied Date",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        applicationTable =
                new JTable(tableModel);

        applicationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        applicationTable.setRowHeight(32);

        applicationTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        applicationTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        applicationTable.getTableHeader()
                .setReorderingAllowed(false);

        // ==========================================
        // COLUMN WIDTHS
        // ==========================================

        applicationTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(100);

        applicationTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        applicationTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(160);

        applicationTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(130);

        applicationTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(170);

        applicationTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);

        JScrollPane scrollPane =
                new JScrollPane(
                        applicationTable
                );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ==========================================
        // BOTTOM BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                5
                        )
                );

        JButton refreshButton =
                new JButton("Refresh");

        JButton closeButton =
                new JButton("Close");

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                );

        refreshButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);

        refreshButton.setPreferredSize(
                new Dimension(120, 35)
        );

        closeButton.setPreferredSize(
                new Dimension(100, 35)
        );

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                closeButton
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        refreshButton.addActionListener(
                e -> loadApplications()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        // ==========================================
        // LOAD APPLICATIONS
        // ==========================================

        loadApplications();

        add(mainPanel);

        setVisible(true);
    }

    // ==========================================
    // LOAD APPLICATIONS
    // ==========================================

    private void loadApplications() {

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        applications =
                applicationDAO
                        .getApplicationsByCandidate(
                                candidate.getId()
                        );

        displayApplications(applications);

        if (applications.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have not applied for any jobs yet.",
                    "My Applications",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==========================================
    // DISPLAY APPLICATIONS
    // ==========================================

    private void displayApplications(
            List<Application> applicationList) {

        tableModel.setRowCount(0);

        for (Application application
                : applicationList) {

            Object[] row = {

                    application.getId(),

                    application.getJobTitle(),

                    application.getCompanyName(),

                    application.getLocation(),

                    application.getApplicationDate(),

                    application.getStatus()
            };

            tableModel.addRow(row);
        }
    }
}