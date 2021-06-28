package toby_spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby_spring.dao.UserDao;
import toby_spring.domain.Level;
import toby_spring.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static toby_spring.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static toby_spring.service.UserService.MIN_RECCOMEND_FOR_GOLD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
class UserServiceTest {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    List<User> users;

    @Test
    public void startsWithTest() {
        String pattern = "";
        String methodName = "upgradeLevels";
        assertThat(methodName.startsWith(pattern)).isTrue();
    }

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "bumjin", "p1", Level.BASIC,MIN_LOGCOUNT_FOR_SILVER-1,0),
                new User("joytouch", "joytouch", "p2", Level.BASIC,MIN_LOGCOUNT_FOR_SILVER,0),
                new User("erwins", "erwins", "p3", Level.SILVER,60,MIN_RECCOMEND_FOR_GOLD-1),
                new User("madnite1", "madnite1", "p4", Level.SILVER,60,MIN_RECCOMEND_FOR_GOLD),
                new User("green", "green", "p5", Level.GOLD,100,Integer.MAX_VALUE)
        );
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }




    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();
//        checkLevel(users.get(0),Level.BASIC);
//        checkLevel(users.get(1),Level.SILVER);
//        checkLevel(users.get(2),Level.SILVER);
//        checkLevel(users.get(3),Level.GOLD);
//        checkLevel(users.get(4),Level.GOLD);

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

    }
    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded)
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        else
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    public void bean() {
        assertThat(this.userService).isNotNull();
    }


}