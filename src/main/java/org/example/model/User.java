package org.example.model;

import java.util.List;

/**
 * User entity
 * <p>
 * Relation:
 * One To Many: User -> PhoneNumber
 * Many To Many: User <-> Department
 * Many To One: User -> Role
 */
public class User {
    private Long id;
    private String firstName;
    private String lastName;

    private Long roleId;
    private List<PhoneNumber> phoneNumberList;
    private List<Long> departmentIdList;

    public User() {
    }

    public User(Long id, String firstName, String lastName, Long roleId, List<PhoneNumber> phoneNumberList, List<Long> departmentIdList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.phoneNumberList = phoneNumberList;
        this.departmentIdList = departmentIdList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<PhoneNumber> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<Long> getDepartmentIdList() {
        return departmentIdList;
    }

    public void setDepartmentIdList(List<Long> departmentIdList) {
        this.departmentIdList = departmentIdList;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", roleId=" + roleId +
               ", phoneNumberList=" + phoneNumberList +
               ", departmentIdList=" + departmentIdList +
               '}';
    }
}
