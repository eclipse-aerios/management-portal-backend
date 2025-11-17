package eu.aeriosproject.managementportal.repositories;

import eu.aeriosproject.managementportal.models.ldap.Role;
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
public class RoleRepositoryImpl implements RoleRepository{
    Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    public static String BASE_DN = "ou=roles,dc=example,dc=org";
    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    UserRepository userRepo;

    @Override
    public void addRole(Role role) {
        try {
            DirContextAdapter context = new DirContextAdapter(buildDn(role));
            mapToContext(role, context);
            ldapTemplate.bind(context);
        } catch (NameAlreadyBoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role Already Exists", e);
        }
    }

    @Override
    public void addMember(Role role, String memberuid) {
        try {
            if (userRepo.getUser(memberuid) != null) {
                // Remove user from all roles
                List<Role> tempRoles = ldapTemplate.find(query().base(BASE_DN).where("cn").isPresent(), Role.class);
                for (Role tempRole : tempRoles) {
                    if (tempRole.getMemberuid().contains(memberuid)){
                        logger.debug("Role \"" + tempRole.getCn() + "\" contains this member: \"" + memberuid + "\"");
                        deleteMember(tempRole.getCn(), memberuid);
                    }
                }
                // Add user to new role
                Name dn = buildDn(role);
                DirContextOperations context = ldapTemplate.lookupContext(dn);
                context.addAttributeValue("memberuid", memberuid);
                ldapTemplate.modifyAttributes(context);
            }
        } catch (NameAlreadyBoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role Already Exists", e);
        }
    }

    @Override
    public void deleteMember(String cn, String memberuid) {
        try {
            Role role = ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Role.class);
            logger.info("Removing user \"" + memberuid + "\" from role \"" + role.getCn() + "\"");
            Name dn = buildDn(role);
            DirContextOperations context = ldapTemplate.lookupContext(dn);
            context.removeAttributeValue("memberuid", memberuid);
            ldapTemplate.modifyAttributes(context);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found", e);
        }
    }

    @Override
    public void mapToContext(Role role, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[] {"top", "posixGroup" });
        context.setAttributeValue("cn", role.getCn());
        context.setAttributeValue("gidnumber", role.getGidnumber());
    }

    @Override
    public Role getRole(String cn) {
        try {
            return ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Role.class);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found", e);
        }
    }

    @Override
    public List<Role> getRoles() {
        return ldapTemplate.find(query().base(BASE_DN).where("cn").isPresent(), Role.class);
    }

    @Override
    public void delete(String cn) {
        try {
            Role role = ldapTemplate.findOne(query().base(BASE_DN).where("cn").is(cn), Role.class);
            ldapTemplate.unbind(role.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found", e);
        }
    }

    private Name buildDn(Role role) {
        return LdapNameBuilder.newInstance(BASE_DN).add("cn", role.getCn()).build();
    }
}
