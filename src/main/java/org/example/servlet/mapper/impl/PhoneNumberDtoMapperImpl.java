package org.example.servlet.mapper.impl;

import org.example.model.PhoneNumber;
import org.example.model.User;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;
import org.example.servlet.dto.UserSmallOutGoingDto;
import org.example.servlet.mapper.PhoneNumberDtoMapper;

import java.util.List;

public class PhoneNumberDtoMapperImpl implements PhoneNumberDtoMapper {
    private static PhoneNumberDtoMapper instance;

    private PhoneNumberDtoMapperImpl() {
    }

    public static synchronized PhoneNumberDtoMapper getInstance() {
        if (instance == null) {
            instance = new PhoneNumberDtoMapperImpl();
        }
        return instance;
    }

    @Override
    public PhoneNumber map(PhoneNumberIncomingDto phoneDto) {
        return new PhoneNumber(
                null,
                phoneDto.getNumber(),
                null
        );
    }

    @Override
    public PhoneNumberOutGoingDto map(PhoneNumber phoneNumber) {
        return new PhoneNumberOutGoingDto(
                phoneNumber.getId(),
                phoneNumber.getNumber(),
                phoneNumber.getUser() == null ?
                        null :
                        new UserSmallOutGoingDto(
                                phoneNumber.getUser().getId(),
                                phoneNumber.getUser().getFirstName(),
                                phoneNumber.getUser().getLastName()
                        )
        );
    }

    @Override
    public List<PhoneNumberOutGoingDto> map(List<PhoneNumber> phoneNumberList) {
        return phoneNumberList.stream().map(this::map).toList();
    }

    @Override
    public List<PhoneNumber> mapUpdateList(List<PhoneNumberUpdateDto> phoneNumberUpdateList) {
        return phoneNumberUpdateList.stream().map(this::map).toList();
    }

    @Override
    public PhoneNumber map(PhoneNumberUpdateDto phoneDto) {
        return new PhoneNumber(
                phoneDto.getId(),
                phoneDto.getNumber(),
                new User(
                        phoneDto.getUserId(),
                        null,
                        null,
                        null,
                        List.of(),
                        List.of()
                )
        );
    }

}
