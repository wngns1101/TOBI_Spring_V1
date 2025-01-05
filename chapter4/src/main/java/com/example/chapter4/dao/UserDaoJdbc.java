package com.example.chapter4.dao;

import com.example.chapter4.domain.User;
import com.example.chapter4.error.DuplicateUserIdException;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 * <p>
 * 1-42 DataSource를 사용하는 UserDao
 */
public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) {
            try {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            } catch (SQLException e) {
                if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                    throw new DuplicateUserIdException(e);
                } else {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 사용자 데이터 추가
    public void add(User user) throws DuplicateKeyException {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    // 사용자 데이터 가져오기
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(
                "select * from users order by id",
                this.userMapper
        );
    }
}