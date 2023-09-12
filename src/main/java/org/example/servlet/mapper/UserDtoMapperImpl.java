package org.example.servlet.mapper;

import org.example.model.Department;
import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.model.User;
import org.example.servlet.dto.*;

import java.util.List;

public class UserDtoMapperImpl implements UserDtoMapper {
    @Override
    public User map(UserIncomingDto userIncomingDto,
                    Role role,
                    List<PhoneNumber> phoneNumberList,
                    List<Department> departmentList) {
        return new User(
                null,
                userIncomingDto.getFirstName(),
                userIncomingDto.getLastName(),
                role,
                phoneNumberList,
                departmentList
        );
    }

    @Override
    public UserOutGoingDto map(User user,
                               RoleOutGoingDto role,
                               List<PhoneNumberOutGoingDto> phoneNumberList,
                               List<DepartmentOutGoingDto> departmentList) {
        return new UserOutGoingDto(
                user.getUuid(),
                user.getFirstName(),
                user.getLastName(),
                role,
                phoneNumberList,
                departmentList
        );
    }
}
