package org.example.servlet.mapper;

import org.example.model.Role;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;

import java.util.List;

public interface RoleDtoMapper {
    Role map(RoleIncomingDto roleIncomingDto);

    Role map(RoleUpdateDto roleUpdateDto);

    RoleOutGoingDto map(Role role);

    List<RoleOutGoingDto> map(List<Role> roleList);
}
