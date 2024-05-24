package com.lotto.web.config.batch;

import com.lotto.web.batch.SchedulerTasks;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Configuration
public class BatchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final SchedulerTasks schedulerTasks;


    @Bean
    public Tasklet updateExtractionsStatusTasklet() {
        return (contribution, chunkContext) -> {
            schedulerTasks.setExtractionsAsWaiting();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step updateExtractionsStatusStep() {
        return stepBuilderFactory.get("updateExtractionsStatusStep")
                .tasklet(updateExtractionsStatusTasklet())
                .build();
    }
    @Bean
    public Job updateExtractionsStatusJob() {
        return jobBuilderFactory.get("updateExtractionsStatusJob")
                .start(updateExtractionsStatusStep())
                .build();
    }

    @Scheduled(cron = "0 35 20 * * SAT")
    public void updateExtractionsLauncher() throws Exception {
        jobLauncher.run(updateExtractionsStatusJob(), new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }

}
