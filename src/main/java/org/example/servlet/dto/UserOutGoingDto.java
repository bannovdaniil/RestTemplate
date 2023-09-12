package org.example.servlet.dto;

import java.util.List;
import java.util.UUID;

public class UserOutGoingDto {
    private UUID uuid;
    private String firstName;
    private String lastName;

    private RoleOutGoingDto role;
    private List<PhoneNumberOutGoingDto> phoneNumberList;
    private List<DepartmentOutGoingDto> departmentList;

    public UserOutGoingDto() {
    }

    public UserOutGoingDto(UUID uuid, String firstName, String lastName, RoleOutGoingDto role, List<PhoneNumberOutGoingDto> phoneNumberList, List<DepartmentOutGoingDto> departmentList) {
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
