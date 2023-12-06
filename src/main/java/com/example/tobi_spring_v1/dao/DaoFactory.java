package com.example.tobi_spring_v1.dao;

// 객체의 생성 방법을 결정하고 그로 만들어진 오브젝트를 돌려주는 것을 factory라고 부른다

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration // 애플리케이션 컨텍스트 또는 빈 펙토리가 사용할 설정정보라는 표시
public class DaoFactory {
    @Bean // 오브젝트 생성을 담당하는 ioC용 메소드라는 표시
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
//        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/user");
        dataSource.setUsername("root");
        dataSource.setPassword("wkrwjs4602!");

        return dataSource;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
