package eu.aeriosproject.managementportal.models.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.Formatter;

@Entry(
        objectClasses = { "inetOrgPerson", "posixAccount", "top" },
        base = "ou=users,dc=example,dc=org")
public class User implements Comparable<User>{
    @Id
    @JsonIgnore
    private Name id;

    @Attribute(name="uid")
    @NonNull
    private String uid;

    @Attribute(name="cn")
    private String cn;

    @Attribute(name="sn")
    private String sn;

    @Attribute(name="mail")
    private String mail;

    @Attribute(name="uidNumber")
    private String uidnumber;

    @Attribute(name="gidNumber")
    private String gidnumber;

    @Attribute(name="homeDirectory")
    @JsonProperty("homeDirectory")
    private String homedirectory;

    @Attribute(name="givenName")
    private String givenName;

    @Attribute(name="userPassword")
    private String userPassword;


    public User() {
    }

    public User(Name id, @NonNull String uid, String cn, String sn, String mail, String uidnumber, String gidnumber, String homedirectory, String givenName, String userPassword) {
        this.id = id;
        this.uid = uid;
        this.cn = cn;
        this.sn = sn;
        this.mail = mail;
        this.uidnumber = uidnumber;
        this.gidnumber = gidnumber;
        this.homedirectory = homedirectory;
        this.givenName = givenName;
        this.userPassword = userPassword;
    }

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUidnumber() {
        return uidnumber;
    }

    public void setUidnumber(String uidnumber) {
        this.uidnumber = uidnumber;
    }

    public String getGidnumber() {
        return gidnumber;
    }

    public void setGidnumber(String gidnumber) {
        this.gidnumber = gidnumber;
    }

    public String getHomedirectory() {
        return homedirectory;
    }

    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public int compareTo(User o) {
        boolean allAttributesEqual = getUid().equals(o.getUid())
                && getCn().equals(o.getCn())
                && getSn().equals(o.getSn());
        return  allAttributesEqual ? 0 : 1;
    }

    @SuppressWarnings("resource")
    public String toString() {
        return new Formatter().format("{\"cn\": \"%s\", \"sn\": \"%s\", \"uid\": \"%s\", \"mail\": \"%s\", \"uidNumber\": \"%s\", \"gidNumber\": \"%s\", \"homeDirectory\": \"%s\", \"givenName\": \"%s\", \"userPassword\": \"{CLEAR}%s\"}",
                cn, sn, uid, mail, uidnumber, gidnumber, homedirectory, givenName, userPassword).toString();
    }

}
