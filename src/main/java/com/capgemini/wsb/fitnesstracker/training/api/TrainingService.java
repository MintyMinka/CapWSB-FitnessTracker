package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.Map;

public interface TrainingService {

    // dodawanie nowego treningu
    Training createTraining(Training training);

    // edycja istniejącego treningu
    Training updateTraining(Long id, Training training);

    // częściowa edycja istniejącego treningu
    Training partiallyUpdateTraining(Long id, Map<String, Object> update);
}
