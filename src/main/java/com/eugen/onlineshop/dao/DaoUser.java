package com.eugen.onlineshop.dao;

import com.eugen.onlineshop.entity.User;
import com.eugen.onlineshop.jdbc.JdbcConnector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoUser implements DaoUserInetrface{

    private final JdbcConnector jdbcConnector;

    public DaoUser(JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public User getUserByName(String name){
        try(Connection userConnection = jdbcConnector.getConnection()){
            PreparedStatement getUsers = userConnection.prepareStatement("SELECT * FROM users WHERE NAME = ?;");
            getUsers.setString(1, String.valueOf(name));

            try(ResultSet resultUsers = getUsers.executeQuery()){
                User user = null;
               while (resultUsers.next()) {
                    user = new User(resultUsers.getInt("id"), resultUsers.getString("name"), resultUsers.getString("password"));
                }
                return user;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUser(String name, String password) {
        try(Connection userConnection = jdbcConnector.getConnection()){
            PreparedStatement getUsers = userConnection.prepareStatement("insert into USERS(id, name, password) values (?, ?, ?)");
            getUsers.setString(1, String.valueOf(getNewUserId()+1));
            getUsers.setString(2, name);
            getUsers.setString(3, password);
            getUsers.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getNewUserId (){
        int newId;
        try (Connection connection = jdbcConnector.getConnection();
             PreparedStatement newIdStatement = connection.prepareStatement("select max(id) from USERS")) {
            newIdStatement.execute();
            try (ResultSet resultSet = newIdStatement.getResultSet()) {
                if (resultSet.next()) {
                    newId = resultSet.getInt(1);
                } else {
                    newId = 1;
                }

                return newId;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
