package org.example.service.impl;

import org.example.model.Department;
import org.example.repository.DepartmentRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.impl.DepartmentRepositoryImpl;
import org.example.service.DepartmentService;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;
import org.example.servlet.mapper.DepartmentDtoMapper;
import org.example.servlet.mapper.impl.DepartmentDtoMapperImpl;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private static final DepartmentRepository departmentRepository = DepartmentRepositoryImpl.getInstance();
    private final DepartmentDtoMapper departmentDtoMapper;
    private static DepartmentService instance;


    private DepartmentServiceImpl() {
        this.departmentDtoMapper = new DepartmentDtoMapperImpl();
    }

    public static synchronized DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }

    @Override
    public DepartmentOutGoingDto save(DepartmentIncomingDto departmentDto) {
        Department department = departmentDtoMapper.map(departmentDto);
        department = departmentRepository.save(department);
        return departmentDtoMapper.map(department);
    }

    @Override
    public void update(DepartmentUpdateDto departmentUpdateDto) throws NotFoundException {
        if (departmentRepository.exitsById(departmentUpdateDto.getId())) {
            Department department = departmentDtoMapper.map(departmentUpdateDto);
            departmentRepository.update(department);
        } else {
            throw new NotFoundException("Department not found.");
        }
    }

    @Override
    public DepartmentOutGoingDto findById(Long departmentId) throws NotFoundException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
                new NotFoundException("Department not found."));
        return departmentDtoMapper.map(department);
    }

    @Override
    public List<DepartmentOutGoingDto> findAll() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentDtoMapper.map(departmentList);
    }

    @Override
    public boolean delete(Long departmentId) {
        return departmentRepository.deleteById(departmentId);
    }

}
