package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findAllTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> findAllFinishedTrainingsAfterTime(Date afterTime) {
        return trainingRepository.findByEndTimeGreaterThan(afterTime);
    }

    @Override
    public List<Training> findAllTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public Training createTraining(Training training) {

        log.info("Creating training {}", training);

        if (training.getUser() == null || training.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID must be provided.");
        }
        User user = userRepository.findById(training.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(training.getUser().getId()));
        training.setUser(user);

        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long id, Training training) {

        log.info("Updating training with ID: {}", id);

        return trainingRepository.findById(id)
                .map(existingTraining -> {
                    // wszystkie pola uzupełnić nowymi wartościami
                    existingTraining.setStartTime(training.getStartTime());
                    existingTraining.setEndTime(training.getEndTime());
                    existingTraining.setActivityType(training.getActivityType());
                    existingTraining.setDistance(training.getDistance());
                    existingTraining.setAverageSpeed(training.getAverageSpeed());

                    return trainingRepository.save(existingTraining); // zapis do bazy
                })
                .orElseThrow(() -> new IllegalArgumentException("Training with ID " + id + " not found"));
    }

    @Override
    public Training partiallyUpdateTraining(Long id, Map<String, Object> update) {

        log.info("Partially updating training with ID: {}", id);

        // szukanie istniejący trening w bazie danych
        return trainingRepository.findById(id)
                .map(existingTraining -> {
                    update.forEach((field, value) -> {
                        switch (field) {
                            case "userId" -> existingTraining.setUser((User) value);
                            case "startTime" -> existingTraining.setStartTime((Date) value);
                            case "endTime" -> existingTraining.setEndTime((Date) value);
                            case "activityType" -> existingTraining.setActivityType(ActivityType.valueOf((String) value));
                            case "distance" -> existingTraining.setDistance((Double) value);
                            case "averageSpeed" -> existingTraining.setAverageSpeed((Double) value);
                            default -> throw new IllegalArgumentException("Unknown field: " + field);
                        }
                    });
                    return trainingRepository.save(existingTraining);
                })
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }
}
