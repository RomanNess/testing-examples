package biz.cosee.mockitoexamples;

import biz.cosee.mockitoexamples.api.UserController;
import biz.cosee.mockitoexamples.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void getAllUsers() {
        List<User> users = userController.getUsers();

        assertThat(users).contains(
                new User(1L, "alpha"),
                new User(2L, "beta"));
    }

}