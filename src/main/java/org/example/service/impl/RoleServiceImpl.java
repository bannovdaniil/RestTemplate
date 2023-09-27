package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.repository.impl.RoleRepositoryImpl;
import org.example.service.RoleService;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;
import org.example.servlet.mapper.RoleDtoMapper;
import org.example.servlet.mapper.impl.RoleDtoMapperImpl;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private static RoleService instance;
    private final RoleDtoMapper roleDtoMapper = RoleDtoMapperImpl.getInstance();


    private RoleServiceImpl() {
    }

    public static synchronized RoleService getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    @Override
    public RoleOutGoingDto save(RoleIncomingDto roleDto) {
        Role role = roleDtoMapper.map(roleDto);
        role = roleRepository.save(role);
        return roleDtoMapper.map(role);
    }

    @Override
    public void update(RoleUpdateDto roleUpdateDto) throws NotFoundException {
        checkRoleExist(roleUpdateDto.getId());
        Role role = roleDtoMapper.map(roleUpdateDto);
        roleRepository.update(role);
    }

    @Override
    public RoleOutGoingDto findById(Long roleId) throws NotFoundException {
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("Role not found."));
        return roleDtoMapper.map(role);
    }

    @Override
    public List<RoleOutGoingDto> findAll() {
        List<Role> roleList = roleRepository.findAll();
        return roleDtoMapper.map(roleList);
    }

    @Override
    public boolean delete(Long roleId) throws NotFoundException {
        checkRoleExist(roleId);
        return roleRepository.deleteById(roleId);
    }

    private void checkRoleExist(Long roleId) throws NotFoundException {
        if (!roleRepository.exitsById(roleId)) {
            throw new NotFoundException("Role not found.");
        }
    }
}
