package org.example.service.impl;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.repository.exception.NotFoundException;
import org.example.repository.impl.RoleRepositoryImpl;
import org.example.service.RoleService;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;
import org.example.servlet.mapper.RoleDtoMapper;
import org.example.servlet.mapper.RoleDtoMapperImpl;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository = RoleRepositoryImpl.getInstance();
    private final RoleDtoMapper roleDtoMapper;
    private static RoleService instance;


    private RoleServiceImpl() {
        this.roleDtoMapper = new RoleDtoMapperImpl();
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
    public void update(RoleUpdateDto roleUpdateDto) {
        Role role = roleDtoMapper.map(roleUpdateDto);
        role = roleRepository.save(role);
        roleDtoMapper.map(role);
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
    public boolean delete(Long roleId) {
        return roleRepository.deleteById(roleId);
    }

}
