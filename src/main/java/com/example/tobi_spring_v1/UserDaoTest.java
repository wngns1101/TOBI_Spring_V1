package com.example.tobi_spring_v1;

import com.example.tobi_spring_v1.dao.UserDao;
import com.example.tobi_spring_v1.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;
public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao1 = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("user");
        user.setName("dlwngns");
        user.setPassword("java");

        userDao1.add(user);

        System.out.println(user.getId() + "등록 성공!");

        User user1 = userDao1.get(user.getId());
//        System.out.println(user1.getName());
//        System.out.println(user1.getPassword());
//        System.out.println(user1.getId() + "조회 성공");
        if (!user.getName().equals(user1.getName())) {
            System.out.println("테스트 실패 (name)");
        } else if (!user.getPassword().equals(user1.getPassword())) {
            System.out.println("테스트 실패 (password)");
        }else{
            System.out.println("조회 테스트 성공!");
        }

    }

}
