package com.example.tobi_spring_v1.dao;

// 객체의 생성 방법을 결정하고 그로 만들어진 오브젝트를 돌려주는 것을 factory라고 부른다
public class DaoFactory {
    public UserDao dUserDao() {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
    public UserDao nUserDao() {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
