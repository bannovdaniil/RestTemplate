package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;

import java.util.List;

public interface PhoneNumberDtoMapper {
    PhoneNumber map(PhoneNumberIncomingDto phoneNumberIncomingDto);

    PhoneNumberOutGoingDto map(PhoneNumber phoneNumber);

    List<PhoneNumberOutGoingDto> map(List<PhoneNumber> phoneNumber);

    PhoneNumber map(PhoneNumberUpdateDto phoneNumberIncomingDto);
}
