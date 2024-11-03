package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, create is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("User not specified, deleting is not permitted!");
        }
        log.info("Deleting User {}", id);
        userRepository.deleteById(id);

    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // zwracanie użytkownika starszego niż podana data
    @Override
    public List<User> getOlderUser(final LocalDate birthDate) {
        return userRepository.findUserOlderThanDate(birthDate);
    }

    // zwracanie użytkownika, do którego pasuje framgnet emaila z pominięciem wielkosci liter
    @Override
    public List<User> getUserByEmailIgnoreCase(final String email) {
        return userRepository.findUsersByFragmentIgnoreCase(email);
    }

    // aktualizowanie użytkownika
    @Override
    public User updateUser(final User user) {
        log.info("Updating User {}", user);
        if (user.getId() == null) {
            throw new IllegalArgumentException("User not specified, update is not permitted!");
        }
        return userRepository.save(user);
    }
}
