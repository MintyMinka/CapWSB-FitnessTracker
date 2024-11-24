package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ReportService {
    private final TrainingProvider trainingProvider;
    private final EmailSender emailSender;

    public Map<User, List<Training>> groupTrainingListByUser(List<Training> trainingList) {
        return trainingList.stream()
                .collect(Collectors.groupingBy(Training::getUser));
    }

    public void generateReport(YearMonth yearMonth) {
        List<Training> trainingList = trainingProvider.findAllTrainingsByMonth(yearMonth);

        Map<User, List<Training>> groupedTrainingList = groupTrainingListByUser(trainingList);
        String title = "Monthly report " + yearMonth.toString();

        for (Map.Entry<User, List<Training>> entry : groupedTrainingList.entrySet()) {
            String content = createContent(entry.getValue(), entry.getKey());

            EmailDto emailDto = new EmailDto( entry.getKey().getEmail(), title, content);
            emailSender.send(emailDto);
        }
    }

    public String createContent(List<Training> userTreninigList, User user) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Dear ")
                .append(user.getFirstName())
                .append(", this month you have done ")
                .append(userTreninigList.size())
                .append(" workouts!")
                .append("\n")
                .append("Training list:")
                .append("\n");

        for (Training training : userTreninigList) {
            emailContent.append("Start time: ").append(training.getStartTime())
                    .append(", End time: ").append(training.getEndTime())
                    .append(", Activity Type: ").append(training.getActivityType())
                    .append(", Distance: ").append(training.getDistance())
                    .append(", Average Speed: ").append(training.getAverageSpeed())
                    .append("\n");
        }

        return emailContent.toString();
    }
}
