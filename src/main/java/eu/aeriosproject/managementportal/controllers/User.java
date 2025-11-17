package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping(path = "/portal-backend/users")
public class User {
    Logger logger = LoggerFactory.getLogger(User.class);
    @Autowired
    UserRepository userRepo;

    @PostMapping(value = {"", "/"})
    public void addUser(@RequestBody eu.aeriosproject.managementportal.models.ldap.User user) {
        logger.info("user: " + user.getSn() + " " + user.getCn() + " " + user.getUid());
        userRepo.addUser(user);
    }

    @GetMapping(path = "/{uid}")
    public eu.aeriosproject.managementportal.models.ldap.User getUser(@PathVariable("uid") String uid) {
        return userRepo.getUser(uid);
    }

    @GetMapping(value = {"", "/"})
    public List<eu.aeriosproject.managementportal.models.ldap.User> getUser() {
        return userRepo.getUsers();
    }

    @DeleteMapping(path = "/{uid}")
    public void deleteUser(@PathVariable("uid") String uid) {
        userRepo.delete(uid);
    }
}
