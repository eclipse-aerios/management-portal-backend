package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.models.ldap.Role;
import eu.aeriosproject.managementportal.models.ldap.RoleMember;
import eu.aeriosproject.managementportal.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/portal-backend/roles")
public class RoleController {
    Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    RoleRepository roleRepo;

    @PostMapping(value = {"", "/"})
    public void addRole(@RequestBody Role role) {
        logger.debug("role: " + role.getCn());
        roleRepo.addRole(role);
    }

    @GetMapping(path = "/{cn}")
    public Role getRole(@PathVariable("cn") String cn) {
        return roleRepo.getRole(cn);
    }

    @GetMapping(value = {"", "/"})
    public List<Role> getRole() {
        return roleRepo.getRoles();
    }

    @DeleteMapping(path = "/{cn}")
    public void deleteRole(@PathVariable("cn") String cn) {
        roleRepo.delete(cn);
    }

    @PutMapping(path = "/{cn}")
    public void putMethodName(@PathVariable("cn") String cn, @RequestBody RoleMember rolemember) {
        logger.info("role: " + cn + " - add member: " + rolemember.getMemberuid());
        roleRepo.addMember(roleRepo.getRole(cn), rolemember.getMemberuid());
    }

    @DeleteMapping(path = "/{cn}/{memberuid}")
    public void deleteMember(@PathVariable("cn") String cn, @PathVariable("memberuid") String memberuid) {
        roleRepo.deleteMember(cn, memberuid);
    }
}