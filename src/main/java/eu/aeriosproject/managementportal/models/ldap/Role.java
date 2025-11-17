package eu.aeriosproject.managementportal.models.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.Formatter;
import java.util.List;

@Entry(
        objectClasses = { "posixGroup", "top" },
        base = "ou=roles,dc=example,dc=org")

public class Role implements Comparable<Role>{
    @Id
    @JsonIgnore
    private Name id;

    @Attribute(name="cn")
    @NonNull
    private String cn;

    @Attribute(name="gidNumber")
    @NonNull
    private String gidnumber;

    @Attribute(name="memberUid")
    private List<String> memberuid;
/*
    @Attribute(name="memberUid")
    private String homedirectory;
*/

    public Role() {
    }

    public Role(Name id, @NonNull String cn, @NonNull String gidnumber, List<String> memberuid) {
        this.id = id;
        this.cn = cn;
        this.gidnumber = gidnumber;
        this.memberuid = memberuid;
    }

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    @NonNull
    public String getCn() {
        return cn;
    }

    public void setCn(@NonNull String cn) {
        this.cn = cn;
    }

    @NonNull
    public String getGidnumber() {
        return gidnumber;
    }

    public void setGidnumber(@NonNull String gidnumber) {
        this.gidnumber = gidnumber;
    }

    public List<String> getMemberuid() {
        return memberuid;
    }

    public void setMemberuid(List<String> memberuid) {
        this.memberuid = memberuid;
    }

    @Override
    public int compareTo(Role o) {
        boolean allAttributesEqual = getCn().equals(o.getCn());
        return  allAttributesEqual ? 0 : 1;
    }

    @SuppressWarnings("resource")
    public String toString() {
        return new Formatter().format("{\"cn\": \"%s\", \"gidNumber\": \"%s\", \"memberUid\": \"%s\"}",
                cn, gidnumber, memberuid).toString();
    }



}
