package com.example.chapter3;

import com.example.chapter3.dao.UserDao;
import com.example.chapter3.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
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

        if (!user.getName().equals(user2.getName())) {
            System.out.println("테스트 실패 (name) ");
        }
        else if (!user.getPassword().equals(user2.getPassword())) {
            System.out.println("테스트 실패 (password)");
        }
        else {
            System.out.println("조회 테스트 성공");
        }
    }


}
