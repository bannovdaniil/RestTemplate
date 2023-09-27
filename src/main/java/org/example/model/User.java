package org.example.model;

import org.example.repository.PhoneNumberRepository;
import org.example.repository.UserToDepartmentRepository;
import org.example.repository.impl.PhoneNumberRepositoryImpl;
import org.example.repository.impl.UserToDepartmentRepositoryImpl;

import java.util.List;

/**
 * User entity
 * <p>
 * Relation:
 * One To Many: User -> PhoneNumber
 * Many To Many: User <-> Department
 * Many To One: User -> Role
 */
public class User {
    private static final PhoneNumberRepository phoneNumberRepository = PhoneNumberRepositoryImpl.getInstance();
    private static final UserToDepartmentRepository userToDepartmentRepository = UserToDepartmentRepositoryImpl.getInstance();
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private List<PhoneNumber> phoneNumberList;
    private List<Department> departmentList;

    public User() {
    }

    public User(Long id, String firstName, String lastName, Role role, List<PhoneNumber> phoneNumberList, List<Department> departmentList) {
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

    public List<PhoneNumber> getPhoneNumberList() {
        if (phoneNumberList == null) {
            this.phoneNumberList = phoneNumberRepository.findAllByUserId(this.id);
        }
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    public List<Department> getDepartmentList() {
        if (departmentList == null) {
            departmentList = userToDepartmentRepository.findDepartmentsByUserId(this.id);
        }
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
