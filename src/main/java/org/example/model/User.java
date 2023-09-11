package org.example.model;

import java.util.List;
import java.util.UUID;

/**
 * User entity
 * <p>
 * Relation:
 * One To Many: User -> PhoneNumber
 * Many To Many: User <-> Department
 * Many To One: User -> Role
 */
public class User {
    private UUID uuid;
    private String firstName;
    private String lastName;

    private Role role;
    private List<PhoneNumber> phoneNumberList;
    private List<Department> departmentList;

    public User() {
    }

    public User(UUID uuid, String firstName, String lastName, Role role, List<PhoneNumber> phoneNumberList, List<Department> departmentList) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumberList = phoneNumberList;
        this.departmentList = departmentList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PhoneNumber> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
