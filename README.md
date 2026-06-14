# Sample Project for Custom Quartz Scheduler

This is a demonstration Spring Boot application that integrates with the `scheduler-common` library to show off dynamic, multi-timezone, configurable job scheduling using Quartz and MySQL.

## Features Demonstrated

1. **Auto-Configuration**: Seamlessly hooks up to the custom Quartz engine using `@EnableAutoConfiguration` logic from the common jar.
2. **Database Integration (MySQL)**: Automatically initializes `custom_job_registry` and `custom_trigger_registry` tables using JPA alongside Quartz's internal cluster tables.
3. **Multi-Timezone Scheduling**: Configures 6 distinct logical groups (G1 through G6, UK, US, India) mapped to varying timezones within `application.properties`.
4. **Selective Triggering**: Uses `@CustomScheduled(allowedGroups = {...})` to isolate jobs to specific geographic locations or shifts.
5. **Parallelism Control**: Demonstrates jobs configured with `parallelism = true` (allowing overlapping executions) vs `parallelism = false` (enforcing strict cluster-wide singleton execution).
6. **Embedded UI Dashboard**: Configured with `custom.scheduler.ui.enabled=true` to demonstrate the zero-dependency embedded dashboard for real-time monitoring and toggling.

## Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **MySQL 8.x** running locally on port 3306 with credentials:
  - Username: `root`
  - Password: `root`
  *(If your credentials differ, update them in `src/main/resources/application.properties`)*
- Ensure the `scheduler-common` jar has been built and installed into your local Maven `.m2` repository using `mvn clean install` from the common jar's directory.

## Getting Started

1. **Start the Application**:
   Navigate to the root directory of this sample project and run:
   ```bash
   mvn spring-boot:run
   ```
   Upon startup, the application will automatically create the `sample_scheduler_db` database and populate the job registry with all methods annotated with `@CustomScheduled`. Jobs are inserted in an **INACTIVE** state by default.

2. **Access the Embedded UI Dashboard**:
   Open your browser and navigate to:
   ```
   http://localhost:8080/custom-scheduler/ui
   ```
   Here, you can monitor execution metrics (last run, next run, status) across all timezones.

3. **Activate the Jobs**:
   You can either click the **Enable** button on the UI dashboard, or use the provided REST endpoint to mass-activate jobs:
   ```bash
   curl -X POST http://localhost:8080/api/jobs/activate-all
   ```
   Watch your terminal console to see Quartz execute the methods exactly at the intervals and timezones specified!

## Repository Configuration
To link this sample project with its GitHub repository, use:
```bash
git remote add origin https://github.com/avinashsinghrana/sample-project-for-custom-scheduler.git
```
