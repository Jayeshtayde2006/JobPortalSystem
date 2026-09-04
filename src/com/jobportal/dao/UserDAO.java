package com.jobportal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jobportal.db.DBConnection;
import com.jobportal.model.User;

public class UserDAO {

    // ==========================================
    // REGISTER USER
    // ==========================================

    public boolean registerUser(User user) {

        String sql =
                "INSERT INTO users "
                + "(name, email, password, role) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            // Hash password before storing
            String hashedPassword =
                    PasswordUtil.hashPassword(
                            user.getPassword()
                    );

            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, hashedPassword);
            pst.setString(4, user.getRole());

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // LOGIN USER
    // ==========================================

    public User loginUser(
            String email,
            String password) {

        String sql =
                "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String storedPassword =
                        rs.getString("password");

                boolean passwordCorrect = false;

                // ----------------------------------
                // New secure password
                // ----------------------------------

                if (PasswordUtil.isHashed(
                        storedPassword)) {

                    passwordCorrect =
                            PasswordUtil.verifyPassword(
                                    password,
                                    storedPassword
                            );

                }

                // ----------------------------------
                // Old plain-text password
                // ----------------------------------

                else {

                    passwordCorrect =
                            password.equals(
                                    storedPassword
                            );

                    // Automatically upgrade old
                    // password to secure hash
                    if (passwordCorrect) {

                        String newHash =
                                PasswordUtil.hashPassword(
                                        password
                                );

                        updatePassword(
                                rs.getInt("id"),
                                newHash
                        );
                    }
                }

                // ----------------------------------
                // Password incorrect
                // ----------------------------------

                if (!passwordCorrect) {
                    return null;
                }

                // ----------------------------------
                // Create User object
                // ----------------------------------

                User user = new User();

                user.setId(
                        rs.getInt("id")
                );

                user.setName(
                        rs.getString("name")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setPassword(
                        rs.getString("password")
                );

                user.setRole(
                        rs.getString("role")
                );

                user.setPhone(
                        rs.getString("phone")
                );

                user.setEducation(
                        rs.getString("education")
                );

                user.setSkills(
                        rs.getString("skills")
                );

                user.setExperience(
                        rs.getString("experience")
                );

                return user;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    // ==========================================
    // UPDATE PASSWORD
    // ==========================================

    private boolean updatePassword(
            int userId,
            String hashedPassword) {

        String sql =
                "UPDATE users SET password = ? "
                + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, hashedPassword);
            pst.setInt(2, userId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // GET ALL USERS
    // ==========================================

    public List<User> getAllUsers() {

        List<User> users =
                new ArrayList<>();

        String sql =
                "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql);
             ResultSet rs =
                     pst.executeQuery()) {

            while (rs.next()) {

                User user = new User();

                user.setId(
                        rs.getInt("id")
                );

                user.setName(
                        rs.getString("name")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setPassword(
                        rs.getString("password")
                );

                user.setRole(
                        rs.getString("role")
                );

                user.setPhone(
                        rs.getString("phone")
                );

                user.setEducation(
                        rs.getString("education")
                );

                user.setSkills(
                        rs.getString("skills")
                );

                user.setExperience(
                        rs.getString("experience")
                );

                users.add(user);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return users;
    }


    // ==========================================
    // GET TOTAL USERS
    // ==========================================

    public int getTotalUsers() {

        String sql =
                "SELECT COUNT(*) FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql);
             ResultSet rs =
                     pst.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }


    // ==========================================
    // UPDATE PROFILE
    // ==========================================

    public boolean updateProfile(
            int userId,
            String phone,
            String education,
            String skills,
            String experience) {

        String sql =
                "UPDATE users SET "
                + "phone = ?, "
                + "education = ?, "
                + "skills = ?, "
                + "experience = ? "
                + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, phone);
            pst.setString(2, education);
            pst.setString(3, skills);
            pst.setString(4, experience);
            pst.setInt(5, userId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}