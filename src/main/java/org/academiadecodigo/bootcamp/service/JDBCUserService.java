package org.academiadecodigo.bootcamp.service;

import org.academiadecodigo.bootcamp.model.User;
import org.academiadecodigo.bootcamp.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.utils.Security;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCUserService implements UserService{

    private ConnectionManager connectionManager;

    public JDBCUserService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean authenticate(String username, String password) {

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connectionManager.getConnection().createStatement();

            String query = "SELECT (username), (password) FROM user WHERE user.username = '" + username +
                    "' AND user.password = '" + Security.getHash(password) + "';";


            resultSet = statement.executeQuery(query);


            if (resultSet.next()) {
                statement.close();
                return true;
            }
            statement.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;


    }


    @Override
    public void add(User user) {
        Statement statement = null;

        try {
            statement = connectionManager.getConnection().createStatement();

            String query = "INSERT INTO user(username, email, password, firstName, lastName, phone) VALUES ('"
                + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword() + "','" + user.getFirstName() +
                "','" + user.getLastName() + "'," + user.getPhone() + ");";

            statement.executeUpdate(query);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User findByName(String username) {

        User user = null;
        try {
            Statement statement = connectionManager.getConnection().createStatement();

            String query = "SELECT * FROM user WHERE user.username = '" + username + "';";

            ResultSet resultSet = statement.executeQuery(query);

            // user exists
            if(resultSet.next()) {

                String usernameValue = resultSet.getString("username");
                String emailValue = resultSet.getString("email");
                String passwordValue = resultSet.getString("password");
                String firstNameValue = resultSet.getString("firstName");
                String lastNameValue = resultSet.getString("lastName");
                String phoneValue = resultSet.getString("phone");

                user = new User(usernameValue, emailValue, passwordValue, firstNameValue, lastNameValue, phoneValue);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;



    }

    @Override
    public List<User> findAll() {
        List<User> users = new LinkedList<>();

        try {
            Statement statement = connectionManager.getConnection().createStatement();

            String query = "SELECT * FROM user;";

            ResultSet resultSet = statement.executeQuery(query);

            // user exists
            while(resultSet.next()) {

                String usernameValue = resultSet.getString("username");
                String emailValue = resultSet.getString("email");
                String passwordValue = resultSet.getString("password");
                String firstNameValue = resultSet.getString("firstName");
                String lastNameValue = resultSet.getString("lastName");
                String phoneValue = resultSet.getString("phone");


                users.add(new User(usernameValue, emailValue, passwordValue, firstNameValue, lastNameValue, phoneValue));
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;

    }

    @Override
    public int count() {
        int result = 0;

        Statement statement = null;
        try {
            statement = connectionManager.getConnection().createStatement();
            String query = "SELECT COUNT(*) FROM user";


            ResultSet resultSet = statement.executeQuery(query);


            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
