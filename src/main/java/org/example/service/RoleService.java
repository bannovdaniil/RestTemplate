package org.example.service;

import org.example.repository.exception.NotFoundException;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;

import java.util.List;

public interface RoleService {
    RoleOutGoingDto save(RoleIncomingDto role);

    void update(RoleUpdateDto role);

    RoleOutGoingDto findById(Long roleID) throws NotFoundException;

    List<RoleOutGoingDto> findAll();

    boolean delete(Long roleId);
}
