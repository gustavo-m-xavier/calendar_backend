package com.calendar.CalendarApplication.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Test
    void findByEmail() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void findById() {
    }
}