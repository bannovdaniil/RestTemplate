package org.example.servlet.mapper;

import org.example.model.Department;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.UserOutGoingDto;

import java.util.List;

public class DepartmentDtoMapperImpl implements DepartmentDtoMapper {
    @Override
    public Department map(DepartmentIncomingDto departmentIncomingDto, List<Long> userIdList) {
        return new Department(
                null,
                departmentIncomingDto.getName(),
                userIdList
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
