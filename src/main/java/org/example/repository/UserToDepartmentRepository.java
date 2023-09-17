package org.example.repository;

import org.example.model.UserToDepartment;

import java.util.List;

public interface UserToDepartmentRepository extends Repository<UserToDepartment, Long> {
    boolean deleteByUserId(Long userId);

    boolean deleteByDepartmentId(Long departmentId);

    List<UserToDepartment> findAllByUserId(Long userId);

    List<UserToDepartment> findAllByDepartmentId(Long departmentId);
}
