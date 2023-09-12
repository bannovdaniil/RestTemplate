package org.example.servlet.dto;

public class RoleIncomingDto {
    private String name;

    public RoleIncomingDto() {
    }

    public RoleIncomingDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
