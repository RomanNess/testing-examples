package biz.cosee.mockitoexamples;

import biz.cosee.mockitoexamples.api.UserController;
import biz.cosee.mockitoexamples.model.User;
import biz.cosee.mockitoexamples.service.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql(scripts = {"classpath:users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"classpath:clean-up.sql"},
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                config = @SqlConfig(errorMode = SqlConfig.ErrorMode.IGNORE_FAILED_DROPS))
})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllUsers() {
        List<User> users = userController.getUsers();

        assertThat(users).contains(
                new User(1L, "alpha"),
                new User(2L, "beta"));
    }

    @Test
    public void saveNewUser() {
        assertThat(userRepository.findByUsername("newguy")).isNull();

        User user = new User(null, "newguy");
        userController.saveUser(user);

        User newUser = userRepository.findByUsername("newguy");
        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isNotNull().isGreaterThanOrEqualTo(3L); // depends on number of saved users
    }

    @Test
    @Transactional(readOnly = true) // fixme why is this even necessary?
    public void getUser() {
        User user = userController.getUser(2L);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getUsername()).isEqualTo("beta");
    }
}