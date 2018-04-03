package biz.cosee.mockitoexamples.service;

import biz.cosee.mockitoexamples.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRepository {

    public List<User> getUsers() {
        return Arrays.asList(
                new User(1L, "alpha"),
                new User(2L, "beta")
        );
    }
}
