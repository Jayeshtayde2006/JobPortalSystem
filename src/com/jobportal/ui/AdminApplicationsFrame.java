package com.jobportal.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.jobportal.dao.ApplicationDAO;
import com.jobportal.model.Application;

public class AdminApplicationsFrame extends JFrame {

    private JTable applicationTable;
    private List<Application> applications;

    public AdminApplicationsFrame() {

        setTitle("Admin - All Applications");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        applicationTable = new JTable();

        JScrollPane scrollPane =
                new JScrollPane(applicationTable);

        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton =
                new JButton("Refresh");

        add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadApplications());

        loadApplications();

        setVisible(true);
    }

    private void loadApplications() {

        ApplicationDAO applicationDAO =
                new ApplicationDAO();

        applications =
                applicationDAO.getAllApplications();

        String[] columns = {
        	    "ID",
        	    "Candidate Name",
        	    "Candidate Email",
        	    "Job Title",
        	    "Company",
        	    "Location",
        	    "Application Date",
        	    "Status"
        	};

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        for (Application application : applications) {

        	model.addRow(
        	        new Object[] {
        	                application.getId(),
        	                application.getCandidateName(),
        	                application.getCandidateEmail(),
        	                application.getJobTitle(),
        	                application.getCompanyName(),
        	                application.getLocation(),
        	                application.getApplicationDate(),
        	                application.getStatus()
        	        }
        	);
        }

        applicationTable.setModel(model);
    }
}