package org.example.servlet.dto;

public class RoleOutGoingDto {
    private Long id;
    private String name;

    public RoleOutGoingDto() {
    }

    public RoleOutGoingDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
