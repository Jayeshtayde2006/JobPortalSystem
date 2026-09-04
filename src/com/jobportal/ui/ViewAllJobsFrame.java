package com.jobportal.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.jobportal.dao.JobDAO;
import com.jobportal.model.Job;

public class ViewAllJobsFrame extends JFrame {

    private JTable jobTable;

    private List<Job> jobs;

    public ViewAllJobsFrame() {

        setTitle("Admin - View All Jobs");

        setSize(1000, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // ==========================
        // TABLE
        // ==========================

        jobTable = new JTable();

        JScrollPane scrollPane =
                new JScrollPane(jobTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // ==========================
        // REFRESH BUTTON
        // ==========================

        JButton refreshButton =
                new JButton("Refresh");

        add(
                refreshButton,
                BorderLayout.SOUTH
        );

        refreshButton.addActionListener(
                e -> loadJobs()
        );

        loadJobs();

        setVisible(true);
    }

    // ==========================
    // LOAD ALL JOBS
    // ==========================

    private void loadJobs() {

        JobDAO jobDAO =
                new JobDAO();

        jobs = jobDAO.getAllJobs();

        String[] columns = {
                "ID",
                "Employer ID",
                "Job Title",
                "Company",
                "Location",
                "Salary",
                "Job Type",
                "Required Skills"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                );

        for (Job job : jobs) {

            model.addRow(
                    new Object[] {

                            job.getId(),

                            job.getEmployerId(),

                            job.getJobTitle(),

                            job.getCompanyName(),

                            job.getLocation(),

                            job.getSalary(),

                            job.getJobType(),

                            job.getRequiredSkills()
                    }
            );
        }

        jobTable.setModel(model);
    }
}