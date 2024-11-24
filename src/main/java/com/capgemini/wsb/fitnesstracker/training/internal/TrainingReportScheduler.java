package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@RequiredArgsConstructor
class TrainingReportScheduler {
    private final ReportService reportService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleMonthlyReports() {
        YearMonth yearMonth = YearMonth.now();
        reportService.generateReport(yearMonth);
    }
}
