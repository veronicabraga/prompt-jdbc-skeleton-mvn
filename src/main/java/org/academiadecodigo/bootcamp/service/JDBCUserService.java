package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;

import java.sql.*;
import java.util.List;

public class JDBCUserService implements UserService{

    private ConnectionManager connectionManager;

    public JDBCUserService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public void add(User user) {
        Statement statement = null;
        try {
            statement = connectionManager.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // create a query
        String query = "INSERT INTO user(username, email, password, firstName, lastName, phone) VALUES ('"
                + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "','" + user.getFirstName() +
                "','" + user.getLastName() + "'," + user.getPhone() + ");";

        // execute the query
        int result = 0;
        try {
            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }
}
