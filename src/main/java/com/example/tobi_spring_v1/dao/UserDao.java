package com.example.tobi_spring_v1.dao;

import com.example.tobi_spring_v1.domain.User;

import java.sql.*;

public abstract class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
//      초 난감 상태
//      1. 중복 코드 분리
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("insert into user(id, name, password) values (?, ?, ?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
//      초 난감 상태
//      1. 중복 코드 분리
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("select * from user where id = ?");

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        ps.close();
        c.close();

        return user;
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
    public class DUserDao extends UserDao {

        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");

            return c;
        }
    }

    public class NUserDao extends UserDao {
        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");

            return c;
        }
    }
}


