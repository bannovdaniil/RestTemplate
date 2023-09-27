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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public RoleOutGoingDto getRole() {
        return role;
    }


    public List<PhoneNumberOutGoingDto> getPhoneNumberList() {
        return phoneNumberList;
    }

    public List<DepartmentOutGoingDto> getDepartmentList() {
        return departmentList;
    }

}
