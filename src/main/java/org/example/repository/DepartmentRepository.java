package org.example.repository;

import org.example.model.Department;

public interface DepartmentRepository extends Repository<Department, Long> {
    boolean exitsById(Long departmentId);
}
