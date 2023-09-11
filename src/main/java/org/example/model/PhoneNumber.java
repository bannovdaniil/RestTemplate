package org.example.model;

/**
 * The Phone Number of User
 *
 * Relation:
 * One To One: PhoneNumber - User
 */
public class PhoneNumber {
    private Long id;
    private String number;
    private User user;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id, String number, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
