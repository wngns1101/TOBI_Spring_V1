package com.example.chapter5;

import com.example.chapter5.dao.UserDaoJdbc;
import com.example.chapter5.domain.Level;
import com.example.chapter5.domain.User;
import com.example.chapter5.service.DummyMailSender;
import com.example.chapter5.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.Arrays;
import java.util.List;
import com.example.chapter5.service.DummyMailSender.MockMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.chapter5.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.chapter5.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    private UserDaoJdbc userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;
    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0, "test@test.com"),
                new User("joytouch", "강명성", "P2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "test1@test.com"),
                new User("erwins",  " 신승한 ", "p3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1, "test2@test.com"),
                new User("madnitel", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD,"test3@test.com"),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "test4@test.com")
        );
    }


    @Test
    @DirtiesContext
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

        List<String> requests = mockMailSender.getRequests();
        assertThat(requests).hasSize(2);
        assertThat(requests.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(requests.get(1)).isEqualTo(users.get(3).getEmail());
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithoutLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithoutLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevel.getLevel());
    }

    @Test
    public void upgradeAllOrNothing() {
        UserService testUserService = new UserService.TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setTransactionManager(this.transactionManager);
        testUserService.setMailSender(this.mailSender);

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (Exception e) {

        }
        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevel(User user, Level level) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(level);
    }

    private void checkLevelUpgraded(User user, Boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }
}
