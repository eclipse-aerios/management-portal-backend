package eu.aeriosproject.managementportal.models.ldap;

import org.springframework.ldap.odm.annotations.Attribute;

public class GroupMember {
    public GroupMember(){}

    @Attribute(name="memberuid")
    private String memberuid;

    public String getMemberuid() {
        return memberuid;
    }

    public void setMemberuid(String memberuid) {
        this.memberuid = memberuid;
    }

    public String toString() {
        return memberuid;
    }
}
