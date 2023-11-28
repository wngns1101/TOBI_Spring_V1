package com.example.tobi_spring_v1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
4. 다형성 분리로 인한 UserDao 인터페이스 사용 불가
public class DUserDao extends UserDao {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");

        return c;
    }
}
*/