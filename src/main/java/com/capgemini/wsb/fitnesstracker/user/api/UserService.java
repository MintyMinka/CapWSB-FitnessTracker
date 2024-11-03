package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    // tworzenie nowego użytkownika
    User createUser(User user);

    // usuwanie użytkownika
    void deleteUser(Long id);

    //aktualizowanie użytkownika
    User updateUser(User user);

}
