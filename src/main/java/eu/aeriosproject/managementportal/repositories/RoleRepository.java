package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.Role;
import org.springframework.ldap.core.DirContextOperations;

import java.util.List;

public interface RoleRepository {
    public void addRole(Role role);
    public void addMember(Role role, String memberuid);
    public void deleteMember(String cn, String memberuid);
    void mapToContext(Role role, DirContextOperations context);
    public Role getRole(String cn);
    public List<Role> getRoles();
    public void delete(String cn);
}
