package org.example.model;

import java.util.List;

/**
 * The Department where User work
 * Relation:
 * Many To Many: Department <-> User
 */
public class Department {
    private Long id;
    private String name;
    private List<Long> userIdList;

    public Department() {
    }

    public Department(Long id, String name, List<Long> userIdList) {
        this.id = id;
        this.name = name;
        this.userIdList = userIdList;
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

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public String toString() {
        return "Department{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", userIdList=" + userIdList +
               '}';
    }
}
