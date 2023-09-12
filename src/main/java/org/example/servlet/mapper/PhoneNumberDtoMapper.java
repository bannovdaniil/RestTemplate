package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.model.User;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.UserOutGoingDto;

public interface PhoneNumberDtoMapper {
    public PhoneNumber map(PhoneNumberIncomingDto phoneNumberIncomingDto, User user);

    public PhoneNumberOutGoingDto map(PhoneNumber phoneNumber, UserOutGoingDto user);
}
