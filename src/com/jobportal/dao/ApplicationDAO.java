package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.db.DBConnection;
import com.jobportal.model.Application;

public class ApplicationDAO {

    // ==========================================
    // APPLY FOR JOB
    // ==========================================

    public boolean applyForJob(
            int jobId,
            int candidateId) {

        // Check whether candidate already applied
        String checkSql =
                "SELECT id FROM applications "
                + "WHERE job_id = ? "
                + "AND candidate_id = ?";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement checkPst =
                     con.prepareStatement(checkSql)) {

            checkPst.setInt(1, jobId);
            checkPst.setInt(2, candidateId);

            ResultSet rs =
                    checkPst.executeQuery();

            if (rs.next()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Insert application
        String sql =
                "INSERT INTO applications "
                + "(job_id, candidate_id, status) "
                + "VALUES (?, ?, 'Applied')";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setInt(1, jobId);
            pst.setInt(2, candidateId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // GET CANDIDATE APPLICATIONS
    // ==========================================

    public List<Application> getApplicationsByCandidate(
            int candidateId) {

        List<Application> applications =
                new ArrayList<>();

        String sql =
                "SELECT "
                + "a.id, "
                + "a.job_id, "
                + "a.candidate_id, "
                + "j.job_title, "
                + "j.company_name, "
                + "j.location, "
                + "a.application_date, "
                + "a.status "
                + "FROM applications a "
                + "JOIN jobs j "
                + "ON a.job_id = j.id "
                + "WHERE a.candidate_id = ? "
                + "ORDER BY a.application_date DESC";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setInt(1, candidateId);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Application application =
                        new Application(
                                rs.getInt("id"),
                                rs.getInt("job_id"),
                                rs.getInt("candidate_id"),
                                rs.getString("job_title"),
                                rs.getString("company_name"),
                                rs.getString("location"),
                                rs.getTimestamp("application_date"),
                                rs.getString("status")
                        );

                applications.add(application);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }
    //=====================================
    //applications for employer's jobs
    //==================================
    
    public List<Application> getApplicationsByEmployer(int employerId) {

        List<Application> applications = new ArrayList<>();

        String sql =
                "SELECT "
                + "a.id, "
                + "a.job_id, "
                + "a.candidate_id, "
                + "u.name AS candidate_name, "
                + "u.email AS candidate_email, "
                + "j.job_title, "
                + "j.company_name, "
                + "j.location, "
                + "a.application_date, "
                + "a.status "
                + "FROM applications a "
                + "JOIN jobs j ON a.job_id = j.id "
                + "JOIN users u ON a.candidate_id = u.id "
                + "WHERE j.employer_id = ? "
                + "ORDER BY a.application_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employerId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Application application = new Application(
                        rs.getInt("id"),
                        rs.getInt("job_id"),
                        rs.getInt("candidate_id"),
                        rs.getString("job_title"),
                        rs.getString("company_name"),
                        rs.getString("location"),
                        rs.getTimestamp("application_date"),
                        rs.getString("status")
                );

                application.setCandidateName(
                        rs.getString("candidate_name")
                );

                application.setCandidateEmail(
                        rs.getString("candidate_email")
                );

                applications.add(application);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }
    
    // ==========================================
    // Update application status
    // ==========================================
   
    public boolean updateApplicationStatus(
            int applicationId,
            int employerId,
            String status) {

        String sql =
                "UPDATE applications a "
                + "JOIN jobs j ON a.job_id = j.id "
                + "SET a.status = ? "
                + "WHERE a.id = ? "
                + "AND j.employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, status);
            pst.setInt(2, applicationId);
            pst.setInt(3, employerId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    
 // ==========================================
 // GET ALL APPLICATIONS FOR ADMIN
 // ==========================================

 public List<Application> getAllApplications() {

     List<Application> applications = new ArrayList<>();

     String sql =
             "SELECT "
             + "a.id, "
             + "a.job_id, "
             + "a.candidate_id, "
             + "u.name AS candidate_name, "
             + "u.email AS candidate_email, "
             + "j.job_title, "
             + "j.company_name, "
             + "j.location, "
             + "a.application_date, "
             + "a.status "
             + "FROM applications a "
             + "JOIN jobs j ON a.job_id = j.id "
             + "JOIN users u ON a.candidate_id = u.id "
             + "ORDER BY a.application_date DESC";

     try (Connection con = DBConnection.getConnection();
          PreparedStatement pst = con.prepareStatement(sql);
          ResultSet rs = pst.executeQuery()) {

         while (rs.next()) {

             Application application =
                     new Application(
                             rs.getInt("id"),
                             rs.getInt("job_id"),
                             rs.getInt("candidate_id"),
                             rs.getString("job_title"),
                             rs.getString("company_name"),
                             rs.getString("location"),
                             rs.getTimestamp("application_date"),
                             rs.getString("status")
                     );

             application.setCandidateName(
                     rs.getString("candidate_name")
             );

             application.setCandidateEmail(
                     rs.getString("candidate_email")
             );

             applications.add(application);
         }

     } catch (Exception e) {
         e.printStackTrace();
     }

     return applications;
 }
 
//==========================================
//GET TOTAL APPLICATIONS
//==========================================

public int getTotalApplications() {

  String sql = "SELECT COUNT(*) FROM applications";

  try (Connection con = DBConnection.getConnection();
       PreparedStatement pst = con.prepareStatement(sql);
       ResultSet rs = pst.executeQuery()) {

      if (rs.next()) {
          return rs.getInt(1);
      }

  } catch (Exception e) {
      e.printStackTrace();
  }

  return 0;
}
    

}