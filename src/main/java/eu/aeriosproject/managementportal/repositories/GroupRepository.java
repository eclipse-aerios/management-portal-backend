package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.Group;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository {
    public void addGroup(Group group);
    public void addMember(Group group, String memberuid);
    public void deleteMember(String cn, String memberuid);
    void mapToContext(Group group, DirContextOperations context);
    public Group getGroup(String cn);
    public List<Group> getGroups();
    public void delete(String cn);
}
