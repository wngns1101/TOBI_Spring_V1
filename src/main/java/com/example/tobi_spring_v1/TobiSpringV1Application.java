package com.example.tobi_spring_v1;


import com.example.tobi_spring_v1.dao.DaoFactory;
import com.example.tobi_spring_v1.dao.UserDao;
import com.example.tobi_spring_v1.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class TobiSpringV1Application {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(TobiSpringV1Application.class, args);
//		초난감 상태로 인한 중복 코드 분라 적용
//		UserDao를 추상 클래스로 만들었기 때문에 인스턴스 생성 불가능
//		UserDao dao = new UserDao();

//		4. 다형성 분리로 인한 UserDao 추상 클래스 사용 불가능
//		DUserDao dDao = new DUserDao();

//		Factory 클래스로 main에서 가지고 있던 두 개의 책임을 분리
//		ConnectionMaker connectionMaker = new NConnectionMaker();
//		UserDao nDao = new UserDao(connectionMaker);

		UserDao dDao = new DaoFactory().userDao();
		User nUser = new User();
		nUser.setId("4");
		nUser.setName("5");
		nUser.setPassword("6");

		dDao.add(nUser);

		System.out.println(nUser.getId() + " 등록 성공!");

		User user2 = dDao.get(nUser.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");
	}

}
