package biz.cosee.mockitoexamples.service;

import biz.cosee.mockitoexamples.external.ExternalUserService;
import biz.cosee.mockitoexamples.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ExternalUserService externalUserService;

    @Autowired
    public UserService(UserRepository userRepository, ExternalUserService externalUserService) {
        this.userRepository = userRepository;
        this.externalUserService = externalUserService;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.getOne(userId);
    }


    public List<User> getExternalUsers() {
        return externalUserService.getUsers();
    }
}
