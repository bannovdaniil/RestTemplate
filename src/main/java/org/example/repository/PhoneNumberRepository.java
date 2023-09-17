package org.example.repository;

import org.example.model.PhoneNumber;

import java.util.List;

public interface PhoneNumberRepository extends Repository<PhoneNumber, Long> {
    List<PhoneNumber> findAllByUserId(Long userId);

    boolean deleteByUserId(Long userId);

    boolean existsByNumber(String number);
}
