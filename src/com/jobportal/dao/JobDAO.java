package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.db.DBConnection;
import com.jobportal.model.Job;

public class JobDAO {

    // =========================
    // ADD JOB
    // =========================
    public boolean addJob(Job job) {

        String sql = "INSERT INTO jobs "
                + "(employer_id, job_title, company_name, location, "
                + "salary, job_type, description, required_skills) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, job.getEmployerId());
            pst.setString(2, job.getJobTitle());
            pst.setString(3, job.getCompanyName());
            pst.setString(4, job.getLocation());
            pst.setString(5, job.getSalary());
            pst.setString(6, job.getJobType());
            pst.setString(7, job.getDescription());
            pst.setString(8, job.getRequiredSkills());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // =========================
    // GET EMPLOYER JOBS
    // =========================
    public List<Job> getJobsByEmployer(int employerId) {

        List<Job> jobs = new ArrayList<>();

        String sql = "SELECT * FROM jobs WHERE employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, employerId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("id"),
                        rs.getInt("employer_id"),
                        rs.getString("job_title"),
                        rs.getString("company_name"),
                        rs.getString("location"),
                        rs.getString("salary"),
                        rs.getString("job_type"),
                        rs.getString("description"),
                        rs.getString("required_skills")
                );

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }


    // =========================
    // UPDATE JOB
    // =========================
    public boolean updateJob(Job job) {

        String sql =
                "UPDATE jobs SET "
                + "job_title = ?, "
                + "company_name = ?, "
                + "location = ?, "
                + "salary = ?, "
                + "job_type = ?, "
                + "description = ?, "
                + "required_skills = ? "
                + "WHERE id = ? AND employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, job.getJobTitle());
            pst.setString(2, job.getCompanyName());
            pst.setString(3, job.getLocation());
            pst.setString(4, job.getSalary());
            pst.setString(5, job.getJobType());
            pst.setString(6, job.getDescription());
            pst.setString(7, job.getRequiredSkills());

            pst.setInt(8, job.getId());
            pst.setInt(9, job.getEmployerId());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // DELETE JOB
    // =========================
    public boolean deleteJob(int jobId, int employerId) {

        String sql = "DELETE FROM jobs "
                + "WHERE id=? AND employer_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, jobId);
            pst.setInt(2, employerId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public List<Job> searchJobs(String keyword) {

        List<Job> jobs = new ArrayList<>();

        String sql =
                "SELECT * FROM jobs "
                + "WHERE job_title LIKE ? "
                + "OR company_name LIKE ? "
                + "OR required_skills LIKE ? "
                + "OR location LIKE ? "
                + "ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            String search =
                    "%" + keyword + "%";

            pst.setString(1, search);
            pst.setString(2, search);
            pst.setString(3, search);
            pst.setString(4, search);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("id"),
                        rs.getInt("employer_id"),
                        rs.getString("job_title"),
                        rs.getString("company_name"),
                        rs.getString("location"),
                        rs.getString("salary"),
                        rs.getString("job_type"),
                        rs.getString("description"),
                        rs.getString("required_skills")
                );

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }


    public List<Job> filterJobs(
            String location,
            String jobType) {

        List<Job> jobs = new ArrayList<>();

        String sql =
                "SELECT * FROM jobs "
                + "WHERE location LIKE ? "
                + "AND job_type LIKE ? "
                + "ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(
                    1,
                    "%" + location + "%"
            );

            pst.setString(
                    2,
                    "%" + jobType + "%"
            );

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("id"),
                        rs.getInt("employer_id"),
                        rs.getString("job_title"),
                        rs.getString("company_name"),
                        rs.getString("location"),
                        rs.getString("salary"),
                        rs.getString("job_type"),
                        rs.getString("description"),
                        rs.getString("required_skills")
                );

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }
    
 // Get All Jobs
    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = "SELECT * FROM jobs";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("id"),
                        rs.getInt("employer_id"),
                        rs.getString("job_title"),
                        rs.getString("company_name"),
                        rs.getString("location"),
                        rs.getString("salary"),
                        rs.getString("job_type"),
                        rs.getString("description"),
                        rs.getString("required_skills")
                );

                jobs.add(job);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jobs;
    }
    
 // ==========================================
 // GET TOTAL JOBS
 // ==========================================

 public int getTotalJobs() {

     String sql = "SELECT COUNT(*) FROM jobs";

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