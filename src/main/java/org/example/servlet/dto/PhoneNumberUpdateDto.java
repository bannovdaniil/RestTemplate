package org.example.servlet.dto;


public class PhoneNumberUpdateDto {
    private Long id;
    private String number;
    private Long userId;

    public PhoneNumberUpdateDto() {
    }

    public PhoneNumberUpdateDto(Long id, String number, Long userId) {
        this.id = id;
        this.number = number;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
