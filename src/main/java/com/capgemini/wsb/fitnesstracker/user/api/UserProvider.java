package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    // pobieranie użytkownika wg wieku - starszy niż zdefiniowany
    List<User> getOlderUser(LocalDate birthDate);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    // pobieranie użytkownika wg maila bez rozróżniania wielkości liter
    List<User> getUserByEmailIgnoreCase(String email);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<User> findAllUsers();

}
