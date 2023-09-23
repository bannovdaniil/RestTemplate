package org.example.servlet.mapper;

import org.example.model.Department;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;

import java.util.List;

public interface DepartmentDtoMapper {
    Department map(DepartmentIncomingDto departmentIncomingDto);

    DepartmentOutGoingDto map(Department department);

    Department map(DepartmentUpdateDto departmentUpdateDto);

    List<DepartmentOutGoingDto> map(List<Department> departmentList);

    List<Department> mapUpdateList(List<DepartmentUpdateDto> departmentList);
}
