package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Name;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Repository
public class GroupRepositoryImpl implements GroupRepository{
    Logger logger = LoggerFactory.getLogger(GroupRepositoryImpl.class);
    public static String BASE_DN = "ou=groups,dc=example,dc=org";
    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    UserRepository userRepo;

    @Override
    public void addGroup(Group group) {
        try {
            DirContextAdapter context = new DirContextAdapter(buildDn(group));
            mapToContext(group, context);
            ldapTemplate.bind(context);
        } catch (NameAlreadyBoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Group Already Exists", e);
        }
    }

    @Override
    public void addMember(Group group, String memberuid) {
        try {
            if (userRepo.getUser(memberuid) != null){
                // Remove user from all roles
                List<Group> tempGroups = ldapTemplate.find(query().base(BASE_DN).where("cn").isPresent(), Group.class);
                for (Group tempGroup : tempGroups) {
                    if (tempGroup.getMemberuid().contains(memberuid)){
                        logger.debug("Group \"" + tempGroup.getCn() + "\" contains this member: \"" + memberuid + "\"");
                        deleteMember(tempGroup.getCn(), memberuid);
                    }
                }
                // Add user to the new group
                Name dn = buildDn(group);
                DirContextOperations context = ldapTemplate.lookupContext(dn);
                context.addAttributeValue("memberuid", memberuid);
                ldapTemplate.modifyAttributes(context);
            }
        } catch (NameAlreadyBoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Group Already Exists", e);
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    @Override
    public void deleteMember(String cn, String memberuid) {
        try {
            Group group = ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Group.class);
            logger.info("Removing user \"" + memberuid + "\" from group \"" + group.getCn() + "\"");
            Name dn = buildDn(group);
            DirContextOperations context = ldapTemplate.lookupContext(dn);
            context.removeAttributeValue("memberuid", memberuid);
            ldapTemplate.modifyAttributes(context);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group Not Found", e);
        }
    }

    @Override
    public void mapToContext(Group group, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[] {"top", "posixGroup" });
        context.setAttributeValue("cn", group.getCn());
        context.setAttributeValue("gidnumber", group.getGidnumber());
    }

    private Name buildDn(Group group) {
        return LdapNameBuilder.newInstance(BASE_DN).add("cn", group.getCn()).build();
    }


    @Override
    public Group getGroup(String cn) {
        try {
            return ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Group.class);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group Not Found", e);
        }
    }

    @Override
    public List<Group> getGroups() {
        return ldapTemplate.find(query().base(BASE_DN).where("cn").isPresent(), Group.class);
    }

    @Override
    public void delete(String cn) {
        try {
            Group group = ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Group.class);
            ldapTemplate.unbind(buildDn(group));
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group Not Found", e);
        }
    }
}
