package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getAllTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.findAllTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto).
                toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findAllTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto).
                toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getAllFinishedTrainingsAfterTime(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        return trainingService.findAllFinishedTrainingsAfterTime(afterTime)
                .stream()
                .map(trainingMapper::toDto).
                toList();
    }
}
