package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import jakarta.annotation.Nullable;

import java.util.Date;

record TrainingDto(@Nullable Long Id, UserDto user, Date startTime, Date endTime, ActivityType activityType,
                   double distance, double averageSpeed) {
}
