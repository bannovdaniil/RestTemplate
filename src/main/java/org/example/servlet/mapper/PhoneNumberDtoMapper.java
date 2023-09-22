package org.example.servlet.mapper;

import org.example.model.PhoneNumber;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;

import java.util.List;

public interface PhoneNumberDtoMapper {
    public PhoneNumber map(PhoneNumberIncomingDto phoneNumberIncomingDto);

    public PhoneNumberOutGoingDto map(PhoneNumber phoneNumber);

    public List<PhoneNumberOutGoingDto> map(List<PhoneNumber> phoneNumber);

    public PhoneNumber map(PhoneNumberUpdateDto phoneNumberIncomingDto);
}
