package org.example.servlet.dto;

import java.util.List;

public class DepartmentOutGoingDto {
    private Long id;
    private String name;
    private List<UserOutGoingDto> userList;

    public DepartmentOutGoingDto() {
    }

    public DepartmentOutGoingDto(Long id, String name, List<UserOutGoingDto> userList) {
        this.id = id;
        this.name = name;
        this.userList = userList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserOutGoingDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserOutGoingDto> userList) {
        this.userList = userList;
    }
}
