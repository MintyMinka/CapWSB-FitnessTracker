package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class UserEmailMapper {

    // zwracanie szczegółów dot. użytkownika: ID + data urodzenia
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(), user.getEmail());
    }

    // zwracanie encji daty urodzenia użytkownika o zadanym wcześniej ID
    User toEmailEntity(UserEmailDto userDto) {
        return new User(null, null, null, userDto.email());
    }
}
