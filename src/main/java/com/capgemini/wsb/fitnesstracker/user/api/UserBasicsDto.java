package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record UserBasicsDto(@Nullable Long id, String firstName, String lastName) {
}
