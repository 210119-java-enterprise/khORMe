package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

import java.lang.annotation.Retention;
import java.util.Objects;

/**
 * Object that contains all available data pertaining to a user
 */
@Table(name="user")
public class AppUser {
    @PrimaryKey(name="id")
    private int id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="username")
    private String username;
    @Column(name="pw")
    private String password;
    @Column(name="registration_date")
    private String registrationDate;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="email")
    private String email;
    @ForeignKey(name="roleId")
    private int roleId;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private UserRole userRole;

    public AppUser() {
        super();
    }

    /**
     * constructor that duplicates an existing AppUser object
     * @param copy user to be Duplicated
     */
    public AppUser(AppUser copy) {//TODO build on this
        this.id = copy.id;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.username = copy.username;
        this.password = copy.password;
        //this.userRole = copy.userRole;
    }

    /** constructor */
    public AppUser(String firstName, String lastName, String username, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
    /** constructor */
    public AppUser(int id, String firstName, String lastName, String username, String password, UserRole userRole) {
        this(firstName, lastName, username, password);
        this.id = id;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }



    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(password, appUser.password) &&
                userRole == appUser.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, userRole);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }

}
