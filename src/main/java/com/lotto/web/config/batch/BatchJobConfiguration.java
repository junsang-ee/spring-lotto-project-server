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


    /**
     * Tasklet
     * updateExtractionsStatusTasklet - schedulerTask:setExtractionsAsWaiting
     * matchExtractionsTasklet -
     */
    @Bean
    public Tasklet updateExtractionsStatusTasklet() {
        return (contribution, chunkContext) -> {
            schedulerTasks.setExtractionsAsWaiting();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet matchExtractionsTasklet() {
        return (contribution, chunkContext) -> {
            schedulerTasks.setExtractionsAsWaiting();
            return RepeatStatus.FINISHED;
        };
    }


    /**
     * Step
     * updateExtractionsStatusStep - updateExtractionsStatusTasklet
     * matchExtractionsStep - updateExtractionsStatusTasklet
     */
    @Bean
    public Step updateExtractionsStatusStep() {
        return stepBuilderFactory.get("updateExtractionsStatusStep")
                .tasklet(updateExtractionsStatusTasklet())
                .build();
    }

    @Bean
    public Step matchExtractionsStep() {
        return stepBuilderFactory.get("matchExtractionsStep")
                .tasklet(matchExtractionsTasklet())
                .build();
    }

    /**
     * Job
     * updateExtractionsStatusJob - updateExtractionsStatusStep
     * matchExtractionsJob - matchExtractionsStep
     */
    @Bean
    public Job updateExtractionsStatusJob() {
        return jobBuilderFactory.get("updateExtractionsStatusJob")
                .start(updateExtractionsStatusStep())
                .build();
    }

    @Bean
    public Job matchExtractionsJob() {
        return jobBuilderFactory.get("matchExtractionsJob")
                .start(matchExtractionsStep())
                .build();
    }

    /**
     * Launcher
     * updateExtractionsStatusLauncher - updateExtractionsStatusJob
     * matchExtractionsLauncher - matchExtractionsJob
     */
    @Scheduled(cron = "0 35 20 * * SAT")
    public void updateExtractionsStatusLauncher() throws Exception {
        jobLauncher.run(updateExtractionsStatusJob(), new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }

    @Scheduled(cron = "0 0 21 * * SAT")
    public void matchExtractionsLauncher() throws Exception {
        jobLauncher.run(matchExtractionsJob(), new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }

}
