package org.example.servlet.dto;

import java.util.List;

public class UserIncomingDto {
    private String firstName;
    private String lastName;

    private RoleIncomingDto role;
    private List<PhoneNumberIncomingDto> phoneNumberList;
    private List<DepartmentIncomingDto> departmentList;

    public UserIncomingDto() {
    }

    public UserIncomingDto(String firstName, String lastName, RoleIncomingDto role, List<PhoneNumberIncomingDto> phoneNumberList, List<DepartmentIncomingDto> departmentList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumberList = phoneNumberList;
        this.departmentList = departmentList;
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

    public RoleIncomingDto getRole() {
        return role;
    }

    public void setRole(RoleIncomingDto role) {
        this.role = role;
    }

    public List<PhoneNumberIncomingDto> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumberIncomingDto> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<DepartmentIncomingDto> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentIncomingDto> departmentList) {
        this.departmentList = departmentList;
    }
}

