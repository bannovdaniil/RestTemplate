package org.example.servlet.mapper;

import org.example.model.Role;
import org.example.servlet.dto.RoleIncomingDto;
import org.example.servlet.dto.RoleOutGoingDto;
import org.example.servlet.dto.RoleUpdateDto;

import java.util.List;

public class RoleDtoMapperImpl implements RoleDtoMapper {
    @Override
    public Role map(RoleIncomingDto roleIncomingDto) {
        return new Role(
                null,
                roleIncomingDto.getName()
        );
    }

    @Override
    public Role map(RoleUpdateDto roleUpdateDto) {
        return new Role(
                roleUpdateDto.getId(),
                roleUpdateDto.getName());
    }

    @Override
    public RoleOutGoingDto map(Role role) {
        return new RoleOutGoingDto(
                role.getId(),
                role.getName()
        );
    }

    @Override
    public List<RoleOutGoingDto> map(List<Role> roleList) {
        return roleList.stream().map(this::map).toList();
    }
}
