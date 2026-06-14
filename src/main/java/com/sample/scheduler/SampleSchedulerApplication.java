package com.sample.scheduler;

import com.common.scheduler.annotation.EnableCustomScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCustomScheduler
public class SampleSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleSchedulerApplication.class, args);
    }
}
