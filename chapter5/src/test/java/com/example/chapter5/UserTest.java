package com.example.chapter5;

import com.example.chapter5.domain.Level;
import com.example.chapter5.domain.User;
import com.example.chapter5.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User user;

    @BeforeEach
    public void setup() {
        this.user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for(Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();

        assertThrows(IllegalStateException.class, () -> {
            for(Level level : levels) {
                if (level.nextLevel() != null) continue;
                user.setLevel(level);
                user.upgradeLevel();
            }
        });
    }
}
