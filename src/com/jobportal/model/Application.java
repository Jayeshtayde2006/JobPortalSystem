package com.jobportal.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.db.DBConnection;

public class Application {

    private int id;
    private int jobId;
    private int candidateId;

    private String candidateName;
    private String candidateEmail;


    private String jobTitle;
    private String companyName;
    private String location;

    private Timestamp applicationDate;
    private String status;

    public Application() {
    }

    public Application(
            int id,
            int jobId,
            int candidateId,
            String jobTitle,
            String companyName,
            String location,
            Timestamp applicationDate,
            String status) {

        this.id = id;
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
 // Get All Applications
    public List<Application> getAllApplications() {

        List<Application> applications = new ArrayList<>();

        String sql = "SELECT * FROM applications";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Application application = new Application();

                application.setId(rs.getInt("id"));
                application.setJobId(rs.getInt("job_id"));
                application.setCandidateId(rs.getInt("candidate_id"));
                application.setApplicationDate(
                        rs.getTimestamp("application_date")
                );
                application.setStatus(
                        rs.getString("status")
                );

                applications.add(application);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }
    
    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.candidateEmail = candidateEmail;
    }
    
}