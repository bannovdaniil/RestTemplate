package org.example.model;

/**
 * The Phone Number of User
 * Relation:
 * One To One: PhoneNumber - User
 */
public class PhoneNumber {
    private Long id;
    private String number;
    private Long userId;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id, String number, Long userId) {
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

    @Override
    public String toString() {
        return "PhoneNumber{" +
               "id=" + id +
               ", number='" + number + '\'' +
               ", userId=" + userId +
               '}';
    }
}
