package com.calendar.CalendarApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calendar.CalendarApplication.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findById(int id);
}
