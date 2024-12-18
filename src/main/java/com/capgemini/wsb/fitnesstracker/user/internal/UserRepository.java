package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    // wyszukiwanie użytkownika po fragmencie maila niezależnie od wielkości liter
    default List<User> findUsersByFragmentIgnoreCase(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    // wyszukiwanie użytkownika starszego od podanej daty urodzenia
    default List<User> findUserOlderThanDate(LocalDate birthDate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(birthDate))
                .toList();
    }
}
