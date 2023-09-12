package org.example.servlet.mapper;

import org.example.model.Department;
import org.example.model.User;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.UserOutGoingDto;

import java.util.List;

public interface DepartmentDtoMapper {
    public Department map(DepartmentIncomingDto departmentIncomingDto, List<User> userList);

    public DepartmentOutGoingDto map(Department department, List<UserOutGoingDto> userList);
}
