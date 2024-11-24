package com.capgemini.wsb.fitnesstracker.training.internal;

import jakarta.annotation.Nullable;

import java.util.Date;

public record TrainingDtoUID(@Nullable Long id, Long userId, Date startTime, Date endTime, ActivityType activityType,
                             double distance, double averageSpeed) {
}
