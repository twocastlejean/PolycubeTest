package kr.co.polycube.backendtest.config.batch;

import kr.co.polycube.backendtest.service.LottoBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final LottoBatchService lottoBatchService;

    @Bean
    public Job lottoJob() {
        return new JobBuilder("lottoJob", jobRepository)
                .start(lottoStep())
                .build();
    }

    @Bean
    public Step lottoStep() {
        return new StepBuilder("lottosStep", jobRepository)
                .tasklet(lottoTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet lottoTasklet() {
        return (contribution, chunkContext) -> {
            lottoBatchService.checkWinners();
            return RepeatStatus.FINISHED;
        };
    }
}
