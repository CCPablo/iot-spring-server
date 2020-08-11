package internal.webserver.rest;


import internal.repository.model.ApplicationUser;
import internal.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/v1/api/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/users")
    public List<ApplicationUser> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@RequestParam String name, @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userService.addUser(name, encodedPassword);
    }

    @PutMapping(value = "/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@RequestParam String name) {
        userService.removeUser(name);
    }

    @PutMapping(value = "/remove/all")
    @ResponseStatus(HttpStatus.OK)
    public void removeAllUsers() {
        userService.removeAllUsers();
    }
}
