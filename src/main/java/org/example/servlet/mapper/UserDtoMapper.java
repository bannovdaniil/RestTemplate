package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.model.User;
import org.example.servlet.dto.*;

import java.util.List;

public interface UserDtoMapper {
    User map(UserIncomingDto userIncomingDto,
             Role role,
             List<PhoneNumber> phoneNumberList,
             List<Long> departmentIdList);

    UserOutGoingDto map(User user,
                        RoleOutGoingDto role,
                        List<PhoneNumberOutGoingDto> phoneNumberList,
                        List<DepartmentOutGoingDto> departmentList);
}
