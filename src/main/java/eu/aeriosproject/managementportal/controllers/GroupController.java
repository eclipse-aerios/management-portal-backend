package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.models.ldap.Group;
import eu.aeriosproject.managementportal.models.ldap.GroupMember;
import eu.aeriosproject.managementportal.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal-backend/groups")
public class GroupController {
    Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupRepository groupRepo;

    @PostMapping(value = {"", "/"})
    public void addGroup(@RequestBody Group group) {
        groupRepo.addGroup(group);
    }

    @GetMapping(path = "/{cn}")
    public Group getGroup(@PathVariable("cn") String cn) {
        return groupRepo.getGroup(cn);
    }

    @GetMapping(value = {"", "/"})
    public List<Group> getGroup() {
        return groupRepo.getGroups();
    }

    @DeleteMapping(path = "/{cn}")
    public void deleteGroup(@PathVariable("cn") String cn) {
        groupRepo.delete(cn);
    }

    @PutMapping(path = "/{cn}")
    public void putMethodName(@PathVariable("cn") String cn, @RequestBody GroupMember groupmember) {
        logger.info("group: " + cn + " - add member: " + groupmember.getMemberuid());
        groupRepo.addMember(groupRepo.getGroup(cn), groupmember.getMemberuid());
    }

    @DeleteMapping(path = "/{cn}/{memberuid}")
    public void deleteMember(@PathVariable("cn") String cn, @PathVariable("memberuid") String memberuid) {
        groupRepo.deleteMember(cn, memberuid);
    }
}
