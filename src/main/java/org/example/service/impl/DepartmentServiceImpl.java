package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Department;
import org.example.model.UserToDepartment;
import org.example.repository.DepartmentRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserToDepartmentRepository;
import org.example.repository.impl.DepartmentRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.repository.impl.UserToDepartmentRepositoryImpl;
import org.example.service.DepartmentService;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;
import org.example.servlet.mapper.DepartmentDtoMapper;
import org.example.servlet.mapper.impl.DepartmentDtoMapperImpl;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private static final DepartmentRepository departmentRepository = DepartmentRepositoryImpl.getInstance();
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final UserToDepartmentRepository userToDepartmentRepository = UserToDepartmentRepositoryImpl.getInstance();
    private static final DepartmentDtoMapper departmentDtoMapper = DepartmentDtoMapperImpl.getInstance();
    private static DepartmentService instance;


    private DepartmentServiceImpl() {
    }

    public static synchronized DepartmentService getInstance() {
        if (instance == null) {
            instance = new DepartmentServiceImpl();
        }
        return instance;
    }

    private static void chekExistDepartment(Long departmentId) throws NotFoundException {
        if (!departmentRepository.exitsById(departmentId)) {
            throw new NotFoundException("Department not found.");
        }
    }

    @Override
    public DepartmentOutGoingDto save(DepartmentIncomingDto departmentDto) {
        Department department = departmentDtoMapper.map(departmentDto);
        department = departmentRepository.save(department);
        return departmentDtoMapper.map(department);
    }

    @Override
    public void update(DepartmentUpdateDto departmentUpdateDto) throws NotFoundException {
        chekExistDepartment(departmentUpdateDto.getId());
        Department department = departmentDtoMapper.map(departmentUpdateDto);
        departmentRepository.update(department);
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
    public void delete(Long departmentId) throws NotFoundException {
        chekExistDepartment(departmentId);
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public void deleteUserFromDepartment(Long departmentId, Long userId) throws NotFoundException {
        chekExistDepartment(departmentId);
        if (userRepository.exitsById(userId)) {
            UserToDepartment linkUserDepartment = userToDepartmentRepository.findByUserIdAndDepartmentId(userId, departmentId)
                    .orElseThrow(() -> new NotFoundException("Link many to many Not found."));

            userToDepartmentRepository.deleteById(linkUserDepartment.getId());
        } else {
            throw new NotFoundException("User not found.");
        }

    }

    @Override
    public void addUserToDepartment(Long departmentId, Long userId) throws NotFoundException {
        chekExistDepartment(departmentId);
        if (userRepository.exitsById(userId)) {
            UserToDepartment linkUserDepartment = new UserToDepartment(
                    null,
                    userId,
                    departmentId
            );
            userToDepartmentRepository.save(linkUserDepartment);
        } else {
            throw new NotFoundException("User not found.");
        }

    }

}
