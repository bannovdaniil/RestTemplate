package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.model.User;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.UserOutGoingDto;

public class PhoneNumberDtoMapperImpl implements PhoneNumberDtoMapper {
    @Override
    public PhoneNumber map(PhoneNumberIncomingDto phoneNumberIncomingDto, User user) {
        return new PhoneNumber(
                null,
                phoneNumberIncomingDto.getNumber(),
                user
        );
    }

    @Override
    public PhoneNumberOutGoingDto map(PhoneNumber phoneNumber, UserOutGoingDto user) {
        return new PhoneNumberOutGoingDto(
                phoneNumber.getId(),
                phoneNumber.getNumber(),
                user
        );
    }
}
