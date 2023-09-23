package org.example.servlet.dto;

import org.example.model.Role;

public class UserIncomingDto {
    private String firstName;
    private String lastName;

    private Role role;

    public UserIncomingDto() {
    }

    public UserIncomingDto(String firstName, String lastName, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
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
}

