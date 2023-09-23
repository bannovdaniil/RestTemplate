package org.example.servlet.mapper.impl;

import org.example.model.Department;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;
import org.example.servlet.dto.UserSmallOutGoingDto;
import org.example.servlet.mapper.DepartmentDtoMapper;

import java.util.List;

public class DepartmentDtoMapperImpl implements DepartmentDtoMapper {
    private static DepartmentDtoMapper instance;

    private DepartmentDtoMapperImpl() {
    }

    public static synchronized DepartmentDtoMapper getInstance() {
        if (instance == null) {
            instance = new DepartmentDtoMapperImpl();
        }
        return instance;
    }

    @Override
    public Department map(DepartmentIncomingDto dto) {
        return new Department(
                null,
                dto.getName(),
                null
        );
    }

    @Override
    public DepartmentOutGoingDto map(Department department) {
        List<UserSmallOutGoingDto> userList = department.getUserList()
                .stream().map(user -> new UserSmallOutGoingDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName()
                )).toList();

        return new DepartmentOutGoingDto(
                department.getId(),
                department.getName(),
                userList
        );
    }

    @Override
    public Department map(DepartmentUpdateDto updateDto) {
        return new Department(
                updateDto.getId(),
                updateDto.getName(),
                null
        );
    }

    @Override
    public List<DepartmentOutGoingDto> map(List<Department> departmentList) {
        return departmentList.stream().map(this::map).toList();
    }

    @Override
    public List<Department> mapUpdateList(List<DepartmentUpdateDto> departmentList) {
        return departmentList.stream().map(this::map).toList();
    }
}
