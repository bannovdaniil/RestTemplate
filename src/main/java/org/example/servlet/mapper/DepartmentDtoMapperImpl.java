package org.example.servlet.mapper;

import org.example.model.Department;
import org.example.model.User;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.UserOutGoingDto;

import java.util.List;

public class DepartmentDtoMapperImpl implements DepartmentDtoMapper {
    @Override
    public Department map(DepartmentIncomingDto departmentIncomingDto, List<User> userList) {
        return new Department(
                null,
                departmentIncomingDto.getName(),
                userList
        );
    }

    @Override
    public DepartmentOutGoingDto map(Department department, List<UserOutGoingDto> userList) {
        return new DepartmentOutGoingDto(
                department.getId(),
                department.getName(),
                userList
        );
    }
}
