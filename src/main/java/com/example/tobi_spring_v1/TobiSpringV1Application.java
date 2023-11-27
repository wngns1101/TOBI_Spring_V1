package com.example.tobi_spring_v1;


import com.example.tobi_spring_v1.dao.UserDao;
import com.example.tobi_spring_v1.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class TobiSpringV1Application {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(TobiSpringV1Application.class, args);

		UserDao dao = new UserDao();

		User user = new User();
		user.setId("키득");
		user.setName("나야");
		user.setPassword("나라고");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공!");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");
	}

}
