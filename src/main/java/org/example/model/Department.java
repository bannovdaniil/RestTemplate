package org.example.model;

import java.util.List;

/**
 * The Department where User work
 *
 * Relation:
 * Many To Many: Department <-> User
 */
public class Department {
    private Long id;
    private String name;
    private List<User> userList;

    public Department() {
    }

    public Department(Long id, String name, List<User> userList) {
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
