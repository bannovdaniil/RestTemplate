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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public RoleUpdateDto getRole() {
        return role;
    }

    public List<PhoneNumberUpdateDto> getPhoneNumberList() {
        return phoneNumberList;
    }

    public List<DepartmentUpdateDto> getDepartmentList() {
        return departmentList;
    }

}

