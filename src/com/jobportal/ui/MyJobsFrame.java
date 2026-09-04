package com.jobportal.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;
import com.jobportal.model.User;

public class MyJobsFrame extends JFrame {

    private User employer;

    private JList<String> jobList;

    private List<Job> jobs;

    public MyJobsFrame(User employer) {

        this.employer = employer;

        setTitle("Job Portal - My Posted Jobs");
        setSize(850, 550);

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
                        "My Posted Jobs",
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
                        "Manage your job postings",
                        JLabel.CENTER
                );

        subtitleLabel.setFont(
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
                subtitleLabel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // JOB LIST
        // ==========================================

        jobList =
                new JList<>();

        jobList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        jobList.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        jobList.setFixedCellHeight(45);

        jobList.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 5, 10
                )
        );

        // Center text vertically/nicely

        DefaultListCellRenderer renderer =
                new DefaultListCellRenderer();

        renderer.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 5, 10
                )
        );

        jobList.setCellRenderer(renderer);

        JScrollPane scrollPane =
                new JScrollPane(jobList);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Your Job Postings"
                )
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ==========================================
        // BUTTON PANEL
        // ==========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                5
                        )
                );

        JButton editButton =
                new JButton(
                        "Edit Selected Job"
                );

        JButton deleteButton =
                new JButton(
                        "Delete Selected Job"
                );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                );

        editButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        refreshButton.setFont(buttonFont);
        closeButton.setFont(buttonFont);

        editButton.setPreferredSize(
                new Dimension(170, 38)
        );

        deleteButton.setPreferredSize(
                new Dimension(180, 38)
        );

        refreshButton.setPreferredSize(
                new Dimension(110, 38)
        );

        closeButton.setPreferredSize(
                new Dimension(100, 38)
        );

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        editButton.addActionListener(
                e -> editSelectedJob()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedJob()
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        // ==========================================
        // DOUBLE CLICK TO EDIT
        // ==========================================

        jobList.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && jobList
                                .getSelectedIndex() != -1) {

                            editSelectedJob();
                        }
                    }
                }
        );

        // ==========================================
        // LOAD JOBS
        // ==========================================

        loadJobs();

        add(mainPanel);

        setVisible(true);
    }

    // ==========================================
    // LOAD EMPLOYER JOBS
    // ==========================================

    private void loadJobs() {

        JobDAO jobDAO =
                new JobDAO();

        jobs =
                jobDAO.getJobsByEmployer(
                        employer.getId()
                );

        String[] jobNames =
                new String[jobs.size()];

        for (int i = 0;
                i < jobs.size();
                i++) {

            Job job =
                    jobs.get(i);

            jobNames[i] =
                    "Job ID: "
                    + job.getId()
                    + "    |    "
                    + job.getJobTitle()
                    + "    |    "
                    + job.getCompanyName()
                    + "    |    "
                    + job.getLocation()
                    + "    |    "
                    + job.getJobType();
        }

        jobList.setListData(jobNames);

        // ==========================================
        // NO JOBS
        // ==========================================

        if (jobs.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have not posted any jobs yet.",
                    "My Posted Jobs",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==========================================
    // EDIT SELECTED JOB
    // ==========================================

    private void editSelectedJob() {

        int index =
                jobList.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first!",
                    "Select Job",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Job selectedJob =
                jobs.get(index);

        new EditJobFrame(
                employer,
                selectedJob,
                this
        );
    }

    // ==========================================
    // DELETE SELECTED JOB
    // ==========================================

    private void deleteSelectedJob() {

        int index =
                jobList.getSelectedIndex();

        if (index == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a job first!",
                    "Select Job",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Job selectedJob =
                jobs.get(index);

        int choice =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to delete this job?\n\n"
                        + "Job Title: "
                        + selectedJob.getJobTitle()
                        + "\nCompany: "
                        + selectedJob.getCompanyName(),

                        "Confirm Delete",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        JobDAO jobDAO =
                new JobDAO();

        boolean result =
                jobDAO.deleteJob(
                        selectedJob.getId(),
                        employer.getId()
                );

        if (result) {

            JOptionPane.showMessageDialog(
                    this,
                    "Job deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadJobs();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete job!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}