package com.example.chapter2.dao;

import com.example.chapter2.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 *
 * 1-42 DataSource를 사용하는 UserDao
 */
public class UserDao {
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 사용자 데이터 추가
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        //2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2,  user.getName());
        ps.setString(3, user.getPassword());

        // 3. 만들어진 Statement를 실행한다.
        ps.executeUpdate();

        // 4. 작업중에 생성된 Connection, Statement, ResultSet 리소스를 닫아 준다.
        ps.close();
        c.close();
    }

    // 사용자 데이터 가져오기
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        // 2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE id = ?");
        ps.setString(1, id);

        // 3. 만들어진 Statement를 실행한다.
        // 실행 결과를 ResultSet으로 받아서 정보를 저장할  오브젝트에 옮긴다.
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteAll() throws SQLException{
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users");

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

}