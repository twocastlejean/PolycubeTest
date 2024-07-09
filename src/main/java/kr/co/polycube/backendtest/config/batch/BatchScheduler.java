package kr.co.polycube.backendtest.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job lottoJob;

    @Scheduled(cron = "0 0 0 * * 0")
    public void runLottoBatch() {
        try {
            jobLauncher.run(lottoJob, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
