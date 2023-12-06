package com.example.tobi_spring_v1;

import com.example.tobi_spring_v1.dao.CountingConnectionMaker;
import com.example.tobi_spring_v1.dao.CountingDaoFactory;
import com.example.tobi_spring_v1.dao.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CountingConnectionMakerTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Counection counter : " + ccm.getCount());
    }
}
