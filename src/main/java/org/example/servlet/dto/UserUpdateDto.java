package org.example.servlet.dto;

import java.util.List;

public class UserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private RoleUpdateDto role;
    private List<PhoneNumberUpdateDto> phoneNumberList;
    private List<DepartmentUpdateDto> departmentList;

    public UserUpdateDto() {
    }

    public UserUpdateDto(Long id, String firstName, String lastName, RoleUpdateDto role, List<PhoneNumberUpdateDto> phoneNumberList, List<DepartmentUpdateDto> departmentList) {
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

    public RoleUpdateDto getRole() {
        return role;
    }

    public void setRole(RoleUpdateDto role) {
        this.role = role;
    }

    public List<PhoneNumberUpdateDto> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumberUpdateDto> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<DepartmentUpdateDto> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentUpdateDto> departmentList) {
        this.departmentList = departmentList;
    }
}

