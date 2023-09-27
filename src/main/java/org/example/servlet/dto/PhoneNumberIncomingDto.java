package org.example.servlet.dto;


public class PhoneNumberIncomingDto {
    private String number;

    public PhoneNumberIncomingDto() {
    }

    public PhoneNumberIncomingDto(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
