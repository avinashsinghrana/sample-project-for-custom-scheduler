package com.sample.scheduler.controller;

import com.common.scheduler.entity.JobEntity;
import com.common.scheduler.repository.JobEntityRepository;
import com.common.scheduler.service.DatabaseSchedulerSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobSyncController {

    private final JobEntityRepository jobEntityRepository;
    private final DatabaseSchedulerSyncService databaseSchedulerSyncService;

    public JobSyncController(JobEntityRepository jobEntityRepository, DatabaseSchedulerSyncService databaseSchedulerSyncService) {
        this.jobEntityRepository = jobEntityRepository;
        this.databaseSchedulerSyncService = databaseSchedulerSyncService;
    }

    /**
     * Helper endpoint to instantly activate all jobs in the database and push them to Quartz.
     */
    @PostMapping("/activate-all")
    public ResponseEntity<String> activateAllJobs() {
        // 1. Update database manually
        List<JobEntity> allJobs = jobEntityRepository.findAll();
        for (JobEntity job : allJobs) {
            job.setStatus("ACTIVE");
            jobEntityRepository.save(job);
        }

        // 2. Trigger the sync to Quartz
        databaseSchedulerSyncService.syncDatabaseJobsWithQuartz();

        return ResponseEntity.ok("Successfully updated " + allJobs.size() + " jobs to ACTIVE and synced with Quartz.");
    }
}
