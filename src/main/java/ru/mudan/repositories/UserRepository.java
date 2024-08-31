package ru.mudan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mudan.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);
}
