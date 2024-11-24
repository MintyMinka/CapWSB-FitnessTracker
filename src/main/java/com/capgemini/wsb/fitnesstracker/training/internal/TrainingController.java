package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    private final TrainingService trainingServiceAPI;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto addNewTraining(@RequestBody TrainingDto training) {
        Training trainingEntity = trainingMapper.toEntity(training);
        Training savedTraining = trainingServiceAPI.createTraining(trainingEntity);
        return trainingMapper.toDto(savedTraining);
    }


    @PutMapping("/{id}")
    public TrainingDto updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training correctedTraining = trainingServiceAPI.updateTraining(id, trainingMapper.toEntity(trainingDto));
        return trainingMapper.toDto(correctedTraining);
    }

    @PatchMapping("/{id}")
    public TrainingDto partiallyUpdateTraining(@PathVariable Long id, @RequestBody Map<String, Object> update) {
        Training correctedTraining = trainingServiceAPI.partiallyUpdateTraining(id, update);
        return trainingMapper.toDto(correctedTraining);
    }

}
