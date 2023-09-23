package org.example.servlet.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumberOutGoingDto {
    private Long id;
    private String number;
    @JsonProperty("user")
    private UserSmallOutGoingDto userDto;

    public PhoneNumberOutGoingDto() {
    }

    public PhoneNumberOutGoingDto(Long id, String number, UserSmallOutGoingDto userDto) {
        this.id = id;
        this.number = number;
        this.userDto = userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserSmallOutGoingDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserSmallOutGoingDto userDto) {
        this.userDto = userDto;
    }
}
