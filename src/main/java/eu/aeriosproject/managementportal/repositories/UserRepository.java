package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.User;
import org.springframework.ldap.core.DirContextOperations;

import java.util.List;

public interface UserRepository {
    public void addUser(User user);
    void mapToContext(User user, DirContextOperations context);
    public User getUser(String uid);
    public List<User> getUsers();
    public void delete(String uid);
}
