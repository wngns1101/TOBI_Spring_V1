package com.example.chapter1;

import java.sql.SQLException;

import com.example.chapter1.dao.UserDao;
import com.example.chapter1.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class UserDaoTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        /*ApplicationContext context =
				new AnnotationConfigApplicationContext(DaoFactory.class);*/
        // XML을 이용한 애플리케이션 컨텍스트
        ApplicationContext context =
                new GenericXmlApplicationContext("classpath:applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공!");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
