package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final UserBasicsMapper userBasicsMapper;

    private final UserEmailMapper emailMapper;

    //pobieranie wszystkich informacji o wszystkich użytkownikach
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    // pobieranie informacji podstawowych o wszystkich użytkownikach
    @GetMapping("/basics")
    public List<UserBasicsDto> getAllBasicsAboutUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userBasicsMapper::toBasicsDto)
                .toList();
    }

    // pobieranie informacji podstawowych użytkownika o podanym przez nas ID
    @GetMapping("/{id}")
    public UserDto getUser (@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User with ID: "
                        + id + " doesn't exist yet."));
    }

    // pobieranie użytkowników starszych niż podana w żądaniu data
    @GetMapping("/older/{birthdate}")
    public List<UserDto> getOlderUsers(@PathVariable LocalDate birthdate) {
        return userService.getOlderUser(birthdate)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    // pobieranie użytkownika po mailu - np. http://localhost:1111/v1/users/email?email=taylor
    // pokaże użytkownika z mailem "Ethan.Taylor@domain.com"
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailIgnoreCase(email)
                .stream()
                .map(emailMapper::toEmailDto)
                .toList();
    }



    // dodanie użytkownika
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // TODO: saveUser with Service and return User
        // Demonstracja how to use @RequestBody
        try {
            User user = userMapper.toEntity(userDto);
            userService.createUser(user);
            System.out.println("User with e-mail: " + userDto.email() + " passed to the request");
        } catch (Exception e){
            throw new IllegalArgumentException("Could not create user with email: " +
                    userDto.email() + " with exception " + e.getMessage());
        }

        return null;
    }

    // usuwanie użytkownika o podanym id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not delete user with ID: "
                    + id + " with exception " + e.getMessage());
        }
    }
}