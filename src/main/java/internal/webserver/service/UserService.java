package internal.webserver.service;

import internal.repository.implementation.UserRepositoryImpl;
import internal.repository.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(String name, String password) {
        userRepository.saveUser(ApplicationUser.builder().name(name).password(password).build());
    }

    public void removeUser(String name) {
        userRepository.deleteUserByName(name);
    }

    public void removeAllUsers() {
        userRepository.deleteAll();
    }
}
