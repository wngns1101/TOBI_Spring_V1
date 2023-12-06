package com.example.tobi_spring_v1;

import com.example.tobi_spring_v1.dao.DaoFactory;
import com.example.tobi_spring_v1.dao.UserDao;
import com.example.tobi_spring_v1.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao userDao1 = context.getBean("userDao", UserDao.class);
//        UserDao userDao2 = context.getBean("userDao", UserDao.class);

        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao1 = context.getBean("userDao", UserDao.class);
        System.out.println(userDao1);
        User user = userDao1.get("1");
        System.out.println(user.getId());
    }
}
