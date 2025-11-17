package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.Group;
import eu.aeriosproject.managementportal.models.ldap.Role;
import eu.aeriosproject.managementportal.models.ldap.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Name;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Repository
public class UserRepositoryImpl implements UserRepository{
    Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    public static String BASE_DN = "ou=users,dc=example,dc=org";
    public static String BASE_DN_ROLE = "ou=roles,dc=example,dc=org";
    public static String BASE_DN_GROUP = "ou=groups,dc=example,dc=org";

    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Pattern ldapEncryptPattern;

    @Override
    public void addUser(User user) {
        try {
            DirContextAdapter context = new DirContextAdapter(buildDn(user));
            mapToContext(user, context);
            ldapTemplate.bind(context);
        } catch (NameAlreadyBoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exists", e);
        }
    }

    @Override
    public void mapToContext(User user, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[] { "top", "posixAccount", "inetOrgPerson" });
        context.setAttributeValue("cn", user.getCn());
        context.setAttributeValue("sn", user.getSn());
        context.setAttributeValue("uid", user.getUid());
        context.setAttributeValue("mail", user.getMail());
        context.setAttributeValue("uidnumber", user.getUidnumber());
        context.setAttributeValue("gidnumber", user.getGidnumber());
        context.setAttributeValue("homedirectory", user.getHomedirectory());
        context.setAttributeValue("givenname", user.getGivenName());
        String hashedPassword = "{CRYPT}" + passwordEncoder.encode(user.getUserPassword());
        context.setAttributeValue("userPassword", hashedPassword);
    }

    private Name buildDn(User user) {
        return LdapNameBuilder.newInstance(BASE_DN)
                .add("cn", user.getCn())
                .build();
    }

    private Name buildDnRole(Role role) {
        return LdapNameBuilder.newInstance(BASE_DN_ROLE)
                .add("cn", role.getCn())
                .build();
    }

    private Name buildDnGroup(Group group) {
        return LdapNameBuilder.newInstance(BASE_DN_GROUP)
                .add("cn", group.getCn())
                .build();
    }

    @Override
    public User getUser(String uid) {
        try {
            User user = ldapTemplate.findOne(query().base(BASE_DN).where("uid").is(uid), User.class);
            if (user != null){
                String password = user.getUserPassword();
                if (password != null) {
                    String[] asciiValues = password.split(",");
                    StringBuilder result = new StringBuilder();
                    for (String value : asciiValues) {
                        result.append((char) Integer.parseInt(value.trim()));
                    }
                    // If the password has been alreay hashed, return the original LDAP value (e.g. {MD5}...)
                    if (ldapEncryptPattern.matcher(result.toString()).matches()) user.setUserPassword(result.toString());
                    // else hash it using BCrypt algorithm
                    else user.setUserPassword(passwordEncoder.encode(result.toString()));
                }
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = ldapTemplate.find(query().base(BASE_DN).where("uid").isPresent(), User.class);
        for (User user : users) {
            if (user != null && user.getUserPassword() != null) {
                // hide passwords (even hashed) in users listing
                user.setUserPassword("hashed passwords are hidden for security");
                /*String password = user.getUserPassword();
                String[] asciiValues = password.split(",");
                StringBuilder result = new StringBuilder();
                for (String value : asciiValues) {
                    result.append((char) Integer.parseInt(value.trim()));
                }
                if (ldapEncryptPattern.matcher(result.toString()).matches()) user.setUserPassword(result.toString());
                else user.setUserPassword(passwordEncoder.encode(result.toString()));*/
            }
        }
        return users;
    }

    @Override
    public void delete(String uid) {
        logger.info("Deleting user: " + uid);
        // Delete user from all roles
        List<Role> tempRoles = ldapTemplate.find(query().base(BASE_DN_ROLE).where("cn").isPresent(), Role.class);
        for (Role tempRole : tempRoles) {
            if (tempRole.getMemberuid().contains(uid)) {
                logger.debug("Role \"" + tempRole.getCn() + "\" contains this member: \"" + uid + "\"");
                deleteMemberFromRole(tempRole.getCn(), uid);
            }
        }
        // Delete user from all groups
        List<Group> tempGroups = ldapTemplate.find(query().base(BASE_DN_GROUP).where("cn").isPresent(), Group.class);
        for (Group tempGroup : tempGroups) {
            if (tempGroup.getMemberuid().contains(uid)) {
                logger.debug("Group \"" + tempGroup.getCn() + "\" contains this member: \"" + uid + "\"");
                deleteMemberFromGroup(tempGroup.getCn(), uid);
            }
        }
        // Delete user
        try {
            User user = ldapTemplate.findOne(query().base(BASE_DN).where("uid").is(uid), User.class);
            ldapTemplate.unbind(user.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

    public void deleteMemberFromRole(String cn, String memberuid) {
        try {
            Role role = ldapTemplate.findOne(query().base(BASE_DN_ROLE).where("cn").is(cn), Role.class);
            logger.debug("Removing user \"" + memberuid + "\" from role \"" + role.getCn() + "\"");
            Name dn = buildDnRole(role);
            DirContextOperations context = ldapTemplate.lookupContext(dn);
            context.removeAttributeValue("memberuid", memberuid);
            ldapTemplate.modifyAttributes(context);

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found", e);
        }
    }

    public void deleteMemberFromGroup(String cn, String memberuid) {
        try {
            Group group = ldapTemplate.findOne(query().base(BASE_DN_GROUP).where("cn").is(cn), Group.class);
            logger.debug("Removing user \"" + memberuid + "\" from group \"" + group.getCn() + "\"");
            Name dn = buildDnGroup(group);
            DirContextOperations context = ldapTemplate.lookupContext(dn);
            context.removeAttributeValue("memberuid", memberuid);
            ldapTemplate.modifyAttributes(context);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group Not Found", e);
        }
    }


}
