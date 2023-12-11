package com.example.tobi_spring_v1.dao;

import com.example.tobi_spring_v1.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

/*
    4. 다형성 분리
    다형성 분리로 인한 생성자의 매개변수로 인스턴스를 생성하기 때문에
    인터페이스 사용 불가능
    public abstract class UserDao {
 */
public class UserDao {
    /*
        3. 인터페이스 분리
        확장성의 문제로 인한 클래스 분리를 사용하지 않고 인터페이스 사용
        private SimpleConnectionMaker simpleConnectionMaker;
    */
    private DataSource dataSource;
    private ConnectionMaker connectionMaker; // 읽기전용의 정보이기 때문에 인스턴스 변수로 사용 가능

//    public UserDao(ConnectionMaker connectionMaker) {
////      3. 인터페이스 분리
////      확장성의 문제로 인한 클래스 분리를 사용하지 않고 인터페이스 사용
////      simpleConnectionMaker = new SimpleConnectionMaker();
//
////      4. 다형성 분리
//        this.connectionMaker = connectionMaker;
//    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
//      초난감 상태
//      1. 중복 코드 분리
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");

//      2. 클래스 분리
//      클래스 분리로 인한 상속받은 getConnection 메서드 사용 불가
//      Connection c = getConnection();

//      3. 인터페이스 분리
//      확장성의 문제로 인한 클래스 분리를 사용하지 않고 인터페이스 사용
//        Connection c = SimpleConnectionMaker.makeConnection();

//        Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into user(id, name, password) values (?, ?, ?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
//      초난감 상태
//      1. 중복 코드 분리
//      UserDao에 추상 메서드 getConnection()을 생성하고 상속을 받아서 Override를 했기 때문에 아래 코드는 불필요하다.
//      Class.forName("com.mysql.cj.jdbc.Driver");
//      Connection c = DriverManager.getConnection("jdbc:mysql://localhost/user", "root", "Wkrwjs4602!");

//      2. 클래스 분리
//      Connection 정보를 읽어오는 메서드를 클래스로 분리했기 때문에 getConnection 메서드는 사용 불가
//      Connection c = getConnection();

//      3. 인터페이스 분리
//      확장성의 문제로 인한 클래스 분리를 사용하지 않고 인터페이스 사용
//      Connection c = new SimpleConnectionMaker().makeNewConnection();

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from user where id = ?");

        ps.setString(1, id);

        ResultSet resultSet = ps.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        resultSet.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteAll() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from user");

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from user");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

//    2. 클래스 분리
//    상속의 단점을 보완하기 위해 SimpleConnectionMaker 클래스를 사용함으로서 getConnection 메서드는 사용 불가
//    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}


