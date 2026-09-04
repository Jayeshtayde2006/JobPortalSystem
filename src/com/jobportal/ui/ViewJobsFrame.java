package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;
import com.jobportal.model.User;

public class ViewJobsFrame extends JFrame {

    private User candidate;

    private JTable jobTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> locationBox;
    private JComboBox<String> jobTypeBox;

    private List<Job> jobs;

    public ViewJobsFrame(User candidate) {

        this.candidate = candidate;

        setTitle("Job Portal - Available Jobs");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

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
                        "Available Jobs",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.add(
                titleLabel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // SEARCH AND FILTER PANEL
        // ==========================================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                8
                        )
                );

        searchPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Search & Filter Jobs"
                )
        );

        JLabel searchLabel =
                new JLabel("Search:");

        searchField =
                new JTextField(15);

        searchField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JButton searchButton =
                new JButton("Search");

        JLabel locationLabel =
                new JLabel("Location:");

        String[] locations = {
                "All",
                "Mumbai",
                "Pune",
                "Nashik",
                "Nagpur",
                "Bangalore",
                "Hyderabad",
                "Delhi"
        };

        locationBox =
                new JComboBox<>(locations);

        JLabel typeLabel =
                new JLabel("Job Type:");

        String[] types = {
                "All",
                "Full Time",
                "Part Time",
                "Internship",
                "Contract"
        };

        jobTypeBox =
                new JComboBox<>(types);

        JButton filterButton =
                new JButton("Filter");

        JButton refreshButton =
                new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchPanel.add(locationLabel);
        searchPanel.add(locationBox);

        searchPanel.add(typeLabel);
        searchPanel.add(jobTypeBox);

        searchPanel.add(filterButton);
        searchPanel.add(refreshButton);

        // ==========================================
        // CENTER PANEL
        // ==========================================

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // JOB TABLE
        // ==========================================

        String[] columns = {
                "ID",
                "Job Title",
                "Company",
                "Location",
                "Salary",
                "Job Type",
                "Required Skills"
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

        jobTable =
                new JTable(tableModel);

        jobTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        jobTable.setRowHeight(32);

        jobTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        jobTable.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        jobTable.getTableHeader()
                .setReorderingAllowed(false);

        // Column widths

        jobTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        jobTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        jobTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);

        jobTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        jobTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        jobTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);

        jobTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(250);

        JScrollPane scrollPane =
                new JScrollPane(jobTable);

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // BOTTOM BUTTON PANEL
        // ==========================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                5
                        )
                );

        JButton viewDetailsButton =
                new JButton("View Details");

        JButton applyButton =
                new JButton("Apply Job");

        JButton closeButton =
                new JButton("Close");

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                );

        viewDetailsButton.setFont(buttonFont);
        applyButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);

        viewDetailsButton.setPreferredSize(
                new Dimension(140, 35)
        );

        applyButton.setPreferredSize(
                new Dimension(140, 35)
        );

        closeButton.setPreferredSize(
                new Dimension(100, 35)
        );

        bottomPanel.add(
                viewDetailsButton
        );

        bottomPanel.add(
                applyButton
        );

        bottomPanel.add(
                closeButton
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        searchButton.addActionListener(
                e -> searchJobs()
        );

        filterButton.addActionListener(
                e -> filterJobs()
        );

        refreshButton.addActionListener(
                e -> {
                    searchField.setText("");
                    locationBox.setSelectedIndex(0);
                    jobTypeBox.setSelectedIndex(0);
                    loadAllJobs();
                }
        );

        viewDetailsButton.addActionListener(
                e -> viewJobDetails()
        );

        applyButton.addActionListener(
                e -> applyForSelectedJob()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        // Press Enter to search

        searchField.addActionListener(
                e -> searchJobs()
        );

        // ==========================================
        // LOAD JOBS
        // ==========================================

        loadAllJobs();

        add(mainPanel);

        setVisible(true);
    }

    // ==========================================
    // LOAD ALL JOBS
    // ==========================================

    private void loadAllJobs() {

        JobDAO jobDAO =
                new JobDAO();

        jobs =
                jobDAO.getAllJobs();

        displayJobs(jobs);
    }

    // ==========================================
    // SEARCH JOBS
    // ==========================================

    private void searchJobs() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadAllJobs();

            return;
        }

        JobDAO jobDAO =
                new JobDAO();

        jobs =
                jobDAO.searchJobs(keyword);

        displayJobs(jobs);

        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No jobs found for: " + keyword,
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==========================================
    // FILTER JOBS
    // ==========================================

    private void filterJobs() {

        String location =
                locationBox
                        .getSelectedItem()
                        .toString();

        String jobType =
                jobTypeBox
                        .getSelectedItem()
                        .toString();

        if (location.equals("All")) {
            location = "";
        }

        if (jobType.equals("All")) {
            jobType = "";
        }

        JobDAO jobDAO =
                new JobDAO();

        jobs =
                jobDAO.filterJobs(
                        location,
                        jobType
                );

        displayJobs(jobs);

        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No jobs found for selected filters.",
                    "Filter Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==========================================
    // DISPLAY JOBS
    // ==========================================

    private void displayJobs(
            List<Job> jobList) {

        tableModel.setRowCount(0);

        for (Job job : jobList) {

            Object[] row = {

                    job.getId(),

                    job.getJobTitle(),

                    job.getCompanyName(),

                    job.getLocation(),

                    job.getSalary(),

                    job.getJobType(),

                    job.getRequiredSkills()
            };

            tableModel.addRow(row);
        }
    }

    // ==========================================
    // VIEW JOB DETAILS
    // ==========================================

    private void viewJobDetails() {

        int selectedRow =
                jobTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first!",
                    "Select Job",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Job selectedJob =
                jobs.get(selectedRow);

        String details =
                "JOB DETAILS\n\n"

                + "Job Title: "
                + selectedJob.getJobTitle()

                + "\n\nCompany: "
                + selectedJob.getCompanyName()

                + "\n\nLocation: "
                + selectedJob.getLocation()

                + "\n\nSalary: "
                + selectedJob.getSalary()

                + "\n\nJob Type: "
                + selectedJob.getJobType()

                + "\n\nRequired Skills: "
                + selectedJob.getRequiredSkills()

                + "\n\nDescription:\n"
                + selectedJob.getDescription();

        JOptionPane.showMessageDialog(
                this,
                details,
                "Job Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================================
    // APPLY FOR JOB
    // ==========================================

    private void applyForSelectedJob() {

        int selectedRow =
                jobTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first!",
                    "Select Job",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Job selectedJob =
                jobs.get(selectedRow);

        if (!candidate.getRole()
                .equalsIgnoreCase("CANDIDATE")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only candidates can apply for jobs!",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to apply for this job?\n\n"
                        + "Job: "
                        + selectedJob.getJobTitle()
                        + "\nCompany: "
                        + selectedJob.getCompanyName()
                        + "\nLocation: "
                        + selectedJob.getLocation(),

                        "Confirm Application",

                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        boolean result =
                applicationDAO.applyForJob(
                        selectedJob.getId(),
                        candidate.getId()
                );

        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Application submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "You have already applied for this job!",
                    "Application Failed",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}