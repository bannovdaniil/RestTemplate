package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.PhoneNumber;
import org.example.repository.PhoneNumberRepository;
import org.example.repository.impl.PhoneNumberRepositoryImpl;
import org.example.service.PhoneNumberService;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;
import org.example.servlet.mapper.PhoneNumberDtoMapper;
import org.example.servlet.mapper.impl.PhoneNumberDtoMapperImpl;

import java.util.List;

public class PhoneNumberServiceImpl implements PhoneNumberService {
    private final PhoneNumberDtoMapper phoneNumberDtoMapper = PhoneNumberDtoMapperImpl.getInstance();
    private static PhoneNumberService instance;
    private final PhoneNumberRepository phoneNumberRepository = PhoneNumberRepositoryImpl.getInstance();


    private PhoneNumberServiceImpl() {
    }

    public static synchronized PhoneNumberService getInstance() {
        if (instance == null) {
            instance = new PhoneNumberServiceImpl();
        }
        return instance;
    }

    @Override
    public PhoneNumberOutGoingDto save(PhoneNumberIncomingDto phoneNumberDto) {
        PhoneNumber phoneNumber = phoneNumberDtoMapper.map(phoneNumberDto);
        phoneNumber = phoneNumberRepository.save(phoneNumber);
        return phoneNumberDtoMapper.map(phoneNumber);
    }

    @Override
    public void update(PhoneNumberUpdateDto phoneNumberUpdateDto) throws NotFoundException {
        if (phoneNumberRepository.exitsById(phoneNumberUpdateDto.getId())) {
            PhoneNumber phoneNumber = phoneNumberDtoMapper.map(phoneNumberUpdateDto);
            phoneNumberRepository.update(phoneNumber);
        } else {
            throw new NotFoundException("PhoneNumber not found.");
        }
    }

    @Override
    public PhoneNumberOutGoingDto findById(Long phoneNumberId) throws NotFoundException {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(phoneNumberId).orElseThrow(() ->
                new NotFoundException("PhoneNumber not found."));
        return phoneNumberDtoMapper.map(phoneNumber);
    }

    @Override
    public List<PhoneNumberOutGoingDto> findAll() {
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAll();
        return phoneNumberDtoMapper.map(phoneNumberList);
    }

    @Override
    public boolean delete(Long phoneNumberId) {
        return phoneNumberRepository.deleteById(phoneNumberId);
    }

}
