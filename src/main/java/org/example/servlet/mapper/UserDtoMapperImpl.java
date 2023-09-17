package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.model.User;
import org.example.servlet.dto.*;

import java.util.List;

public class UserDtoMapperImpl implements UserDtoMapper {
    @Override
    public User map(UserIncomingDto userIncomingDto,
                    Long roleId,
                    List<PhoneNumber> phoneNumberList,
                    List<Long> departmentIdList) {
        return new User(
                null,
                userIncomingDto.getFirstName(),
                userIncomingDto.getLastName(),
                roleId,
                phoneNumberList,
                departmentIdList
        );
    }

    @Override
    public UserOutGoingDto map(User user,
                               RoleOutGoingDto role,
                               List<PhoneNumberOutGoingDto> phoneNumberList,
                               List<DepartmentOutGoingDto> departmentList) {
        return new UserOutGoingDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                role,
                phoneNumberList,
                departmentList
        );
    }
}
