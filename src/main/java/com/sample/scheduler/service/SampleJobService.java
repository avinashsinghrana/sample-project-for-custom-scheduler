package com.sample.scheduler.service;

import com.common.scheduler.annotation.CustomScheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SampleJobService {

    // --- SCHEDULER 1 ---
    // Target only 3 groups (G1, G2, G3)
    // Non-concurrent (parallelism = false)
    @CustomScheduled(
        jobName = "scheduler1",
        cron = "0 0 12 * * ?", // Runs at 12 PM
        parallelism = false,
        allowedGroups = {"G1", "G2", "G3"}
    )
    public void scheduler1() {
        System.out.println("[" + LocalDateTime.now() + "] Scheduler 1 executing strictly one-at-a-time.");
    }

    // --- SCHEDULER 2 ---
    // Target 6 groups (G1 through G6)
    // Concurrent (parallelism = true)
    @CustomScheduled(
        jobName = "scheduler2",
        cron = "0 0 9 * * ?", // Runs at 9 AM
        parallelism = true,
        allowedGroups = {"G1", "G2", "G3", "G4", "G5", "G6"}
    )
    public void scheduler2() {
        System.out.println("[" + LocalDateTime.now() + "] Scheduler 2 executing concurrently.");
    }

    // --- SCHEDULER 3 ---
    // Default fallback: Target ALL globally defined groups automatically.
    // Cron runs every minute just for testing purposes so you can see it easily!
    @CustomScheduled(
        jobName = "scheduler3",
        cron = "0 * * * * ?" // Runs every minute
    )
    public void scheduler3() {
        System.out.println("[" + LocalDateTime.now() + "] Scheduler 3 executing default logic for all groups.");
    }

    // --- SCHEDULER 4: Different Crons, Same Timezone ---
    // Targets INDIA_MORNING (9 AM) and INDIA_NIGHT (9 PM)
    @CustomScheduled(
        jobName = "differentCronsJob",
        cron = "0 0 0 * * ?", // This gets completely overridden by the group properties!
        allowedGroups = {"INDIA_MORNING", "INDIA_NIGHT"}
    )
    public void differentCronsJob() {
        System.out.println("[" + LocalDateTime.now() + "] Executing Job 4: Different Crons, Same Timezone (India).");
    }

    // --- SCHEDULER 5: Same Cron, Different Timezone ---
    // Runs at exactly 3:30 PM in both New York and London!
    @CustomScheduled(
        jobName = "sameCronJob",
        cron = "0 30 15 * * ?", // Runs at 3:30 PM
        allowedGroups = {"US_SHIFT", "UK_SHIFT"}
    )
    public void sameCronJob() {
        System.out.println("[" + LocalDateTime.now() + "] Executing Job 5: Same Cron (3:30 PM), Different Timezones.");
    }
}
