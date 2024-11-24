package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserId(Long userId);

    List<Training> findByActivityType(ActivityType activityType);

    List<Training> findByEndTimeGreaterThan(Date endTime);

    List<Training> findByStartTimeBetween(Date startDate, Date endDate);
}
