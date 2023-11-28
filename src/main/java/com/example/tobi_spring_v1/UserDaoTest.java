package com.example.tobi_spring_v1;

import com.example.tobi_spring_v1.dao.ConnectionMaker;
import com.example.tobi_spring_v1.dao.DConnectionMaker;
import com.example.tobi_spring_v1.dao.DaoFactory;
import com.example.tobi_spring_v1.dao.UserDao;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao userDao = new DaoFactory().dUserDao();
    }
}
