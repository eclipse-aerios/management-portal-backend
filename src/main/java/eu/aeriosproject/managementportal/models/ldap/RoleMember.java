package eu.aeriosproject.managementportal.models.ldap;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.stereotype.Repository;

@Repository
public class RoleMember {
    @Attribute(name="memberuid")
    private String memberuid;

    public RoleMember() {
    }

    public RoleMember(String memberuid) {
        this.memberuid = memberuid;
    }

    public String getMemberuid() {
        return memberuid;
    }

    public void setMemberuid(String memberuid) {
        this.memberuid = memberuid;
    }

    @Override
    public String toString() {
        return memberuid;
    }

}
