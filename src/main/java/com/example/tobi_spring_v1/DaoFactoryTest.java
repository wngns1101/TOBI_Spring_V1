package com.example.tobi_spring_v1;

import com.example.tobi_spring_v1.dao.DaoFactory;
import com.example.tobi_spring_v1.dao.UserDao;

import java.sql.SQLException;

public class DaoFactoryTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.UserDao();
        UserDao dao2 = factory.UserDao();
        System.out.println(dao1);
        System.out.println(dao2);
    }
}
