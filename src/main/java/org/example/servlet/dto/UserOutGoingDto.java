package org.example.servlet.dto;

import java.util.List;

public class UserOutGoingDto {
    private Long id;
    private String firstName;
    private String lastName;

    private RoleOutGoingDto role;
    private List<PhoneNumberOutGoingDto> phoneNumberList;
    private List<DepartmentOutGoingDto> departmentList;

    public UserOutGoingDto() {
    }

    public UserOutGoingDto(Long id, String firstName, String lastName, RoleOutGoingDto role, List<PhoneNumberOutGoingDto> phoneNumberList, List<DepartmentOutGoingDto> departmentList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumberList = phoneNumberList;
        this.departmentList = departmentList;
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

    public RoleOutGoingDto getRole() {
        return role;
    }

    public void setRole(RoleOutGoingDto role) {
        this.role = role;
    }

    public List<PhoneNumberOutGoingDto> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumberOutGoingDto> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<DepartmentOutGoingDto> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentOutGoingDto> departmentList) {
        this.departmentList = departmentList;
    }
}
