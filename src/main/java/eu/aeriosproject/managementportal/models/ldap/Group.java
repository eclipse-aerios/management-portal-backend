package eu.aeriosproject.managementportal.models.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import java.util.Formatter;
import java.util.List;
import javax.naming.Name;

@Entry(
        objectClasses = { "posixGroup", "top" },
        base = "ou=groups,dc=example,dc=org")
public class Group implements Comparable<Group>{
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

    public Group(){}

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
    public int compareTo(Group o) {
        boolean allAttributesEqual = getCn().equals(o.getCn());
        return  allAttributesEqual ? 0 : 1;
    }

    @SuppressWarnings("resource")
    public String toString() {
        return new Formatter().format("{\"cn\": \"%s\", \"gidNumber\": \"%s\", \"memberUid\": \"%s\"}",
                cn, gidnumber, memberuid).toString();
    }

}
