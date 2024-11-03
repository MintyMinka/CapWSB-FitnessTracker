package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class UserBasicsMapper {

    UserBasicsDto toBasicsDto(User user) {
        return new UserBasicsDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    User toBasicsEntity(UserBasicsDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                null,
                null);
    }
}
