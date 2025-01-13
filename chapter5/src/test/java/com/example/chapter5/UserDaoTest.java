package com.example.chapter5;

import com.example.chapter5.dao.UserDao;
import com.example.chapter5.dao.UserDaoJdbc;
import com.example.chapter5.domain.Level;
import com.example.chapter5.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(locations = "/applicationContext.xml")
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        this.userDao = this.context.getBean("userDao", UserDaoJdbc.class);

        this.user1 = new User("gyumee", "kk", "jj", Level.BASIC, 1, 0, "wngns0812@gmail.com");
        this.user2 = new User("leegw700", "cc", "ee", Level.BASIC, 1, 0, "test@gmail.com");
        this.user3 = new User("bumjin", "dd", "ff", Level.BASIC, 1, 0, "test1@gmail.com");

        System.out.println(this.context);
        System.out.println(this);
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);

        assertThat(userDao.getCount()).isEqualTo(2);

        User userGet1 = userDao.get(user1.getId());
        checkSameUser(user1, userGet1);

        User userGet2 = userDao.get(user2.getId());
        checkSameUser(user2, userGet2);
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    public void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.get("unknown_id");
        });
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        List<User> users0 = userDao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);

        for (User user : users3) {
            System.out.println(user.getId());
        }

        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));

    }

    @Test
    public void duplicateKey() {
        assertThrows(DuplicateKeyException.class, () -> {
            userDao.deleteAll();
            userDao.add(user1);
            userDao.add(user1);
        });
    }

    @Test
    public void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlEx)).isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    public void update() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user2);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        user1.setEmail("update@gmail.com");
        userDao.update(user1);

        User user1Update = userDao.get(user1.getId());
        checkSameUser(user1, user1Update);
        User user2same = userDao.get(user2.getId());
        checkSameUser(user2, user2same);

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}
