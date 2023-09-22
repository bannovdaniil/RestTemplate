package org.example.service;

import org.example.repository.exception.NotFoundException;
import org.example.servlet.dto.*;

import java.util.List;

public interface PhoneNumberService {
    PhoneNumberOutGoingDto save(PhoneNumberIncomingDto phoneNumber);

    void update(PhoneNumberUpdateDto phoneNumber) throws NotFoundException;

    PhoneNumberOutGoingDto findById(Long phoneNumberID) throws NotFoundException;

    List<PhoneNumberOutGoingDto> findAll();

    boolean delete(Long phoneNumberId);
}
