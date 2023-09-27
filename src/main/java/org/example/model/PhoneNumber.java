package org.example.model;

import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;

/**
 * The Phone Number of User
 * Lazy User getter from Repository.
 * Relation:
 * One To One: PhoneNumber - User
 */
public class PhoneNumber {
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        if (user != null && user.getId() > 0 && user.getFirstName() == null) {
            this.user = userRepository.findById(user.getId()).orElse(user);
        } else if (user != null && user.getId() == 0) {
            this.user = null;
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}