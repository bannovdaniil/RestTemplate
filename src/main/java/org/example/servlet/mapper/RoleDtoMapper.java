package org.example.servlet.mapper;

import org.example.model.Role;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;

public interface RoleDtoMapper {
    Role map(RoleIncomingDto roleIncomingDto);

    RoleOutGoingDto map(Role role);
}
