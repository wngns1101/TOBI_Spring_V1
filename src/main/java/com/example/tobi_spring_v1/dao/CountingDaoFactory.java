//package com.example.tobi_spring_v1.dao;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CountingDaoFactory {
//
//    @Bean
//    public UserDao countingUserDao() {
//        return new UserDao();
//    }
//
//    @Bean
//    public ConnectionMaker countingConnectionMaker() {
//        return new CountingConnectionMaker(realConnectionMaker());
//    }
//
//    @Bean
//    public ConnectionMaker realConnectionMaker() {
//        return new DConnectionMaker();
//    }
//}
