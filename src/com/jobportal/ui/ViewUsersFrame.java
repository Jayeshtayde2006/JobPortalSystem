package com.jobportal.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jobportal.dao.UserDAO;
import com.jobportal.model.User;

public class ViewUsersFrame extends JFrame {

    private JTable userTable;
    private List<User> users;

    public ViewUsersFrame() {

        setTitle("Admin - View All Users");

        setSize(700, 450);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // ==========================
        // TABLE
        // ==========================

        String[] columns = {
                "ID",
                "Name",
                "Email",
                "Role"
        };

        userTable = new JTable();

        JScrollPane scrollPane =
                new JScrollPane(userTable);

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
                e -> loadUsers()
        );

        loadUsers();

        setVisible(true);
    }

    private void loadUsers() {

        UserDAO userDAO =
                new UserDAO();

        users = userDAO.getAllUsers();

        String[][] data =
                new String[users.size()][4];

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            data[i][0] =
                    String.valueOf(user.getId());

            data[i][1] =
                    user.getName();

            data[i][2] =
                    user.getEmail();

            data[i][3] =
                    user.getRole();
        }

        String[] columns = {
                "ID",
                "Name",
                "Email",
                "Role"
        };

        userTable.setModel(
                new javax.swing.table.DefaultTableModel(
                        data,
                        columns
                )
        );
    }
}