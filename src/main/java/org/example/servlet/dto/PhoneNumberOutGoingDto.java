package org.example.servlet.dto;


public class PhoneNumberOutGoingDto {
    private Long id;
    private String number;
    private UserOutGoingDto user;

    public PhoneNumberOutGoingDto() {
    }

    public PhoneNumberOutGoingDto(Long id, String number, UserOutGoingDto user) {
        this.id = id;
        this.number = number;
        this.user = user;
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

    public UserOutGoingDto getUser() {
        return user;
    }

    public void setUser(UserOutGoingDto user) {
        this.user = user;
    }
}
