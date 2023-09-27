package org.example.servlet.dto;

public class DepartmentUpdateDto {
    private Long id;
    private String name;

    public DepartmentUpdateDto() {
    }

    public DepartmentUpdateDto(Long id, String name) {
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
