package org.example.repository;

import org.example.model.User;

import java.util.UUID;

public interface UserEntityRepository extends UserRepository<User, UUID> {
}
