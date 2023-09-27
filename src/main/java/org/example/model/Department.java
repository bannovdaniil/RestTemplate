package org.example.model;

import org.example.repository.UserToDepartmentRepository;
import org.example.repository.impl.UserToDepartmentRepositoryImpl;

import java.util.List;

/**
 * The Department where User work
 * Relation:
 * Many To Many: Department <-> User
 */
public class Department {
    private static final UserToDepartmentRepository userToDepartmentRepository = UserToDepartmentRepositoryImpl.getInstance();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        if (userList == null) {
            userList = userToDepartmentRepository.findUsersByDepartmentId(this.id);
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
