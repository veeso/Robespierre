/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import it.hypocracy.robespierre.config.Config;
import it.hypocracy.robespierre.config.DatabaseConfig;
import it.hypocracy.robespierre.config.FeedSourceConfig;
import it.hypocracy.robespierre.config.MetadataConfig;
import it.hypocracy.robespierre.core.errors.BusyWorkerException;
import it.hypocracy.robespierre.core.jobs.FeedJob;

/**
 * <p>
 * JobDispatcher takes care of dispatching and managing the feed jobs
 * </p>
 */

public class JobDispatcher {

  private final static Logger logger = Logger.getLogger(JobDispatcher.class.getName());

  // Attributes
  private FeedJob[] feedJobs;
  private FeedWorker[] workers;

  public JobDispatcher(Config configuration) {
    // Initialize jobs
    logger.debug("Initializing a new JobDispatcher");
    initFeedJobs(configuration.feed.sources);
    initFeedWorkers(configuration.feed.maxWorkers, configuration.database, configuration.metadata);
  }

  /**
   * <p>
   * Start job dispatcher associated workers
   * </p>
   */
  public void start() {
    logger.info("Starting job dispatcher...");
    // Iterate over workers and start them
    for (FeedWorker w : workers) {
      logger.info("Starting worker " + w.getName() + "...");
      if (w.hasStarted()) {
        logger.error("Worker " + w.getName() + " has already started!");
      } else {
        // Start worker
        w.start();
        logger.info("Worker " + w.getName() + " started!");
      }
    }
    logger.info("Job dispatcher started!");
  }

  /**
   * <p>
   * Process jobs; assign all jobs to execute to the first available work
   * </p>
   */

  public void process() {
    logger.info("Processing jobs...");
    LocalDateTime now = LocalDateTime.now();
    // Iterate over jobs
    for (FeedJob job : feedJobs) {
      final String jobName = job.getUri();
      logger.debug("Checking if job '" + jobName + "' has to be run");
      LocalDateTime nextExecTime = job.getNextExecutionTime();
      if (nextExecTime.isBefore(now)) {
        logger.info("Time to run job '" + jobName + "' has come; looking for a candidate worker...");
        // Check if there's an available worker
        boolean workerFound = false;
        for (FeedWorker worker : workers) {
          if (worker.isAvailable()) {
            // Assign to worker the job
            logger.info("Assigned  '" + jobName + "' to worker '" + worker.getName() + "'");
            try {
              job.runJob(worker);
            } catch (BusyWorkerException e) { // NOTE: this should never happen
              logger.error("Could not assign job '" + jobName + "' to worker '" + worker.getName()
                  + "'; was available one instant ago... trying with another one");
              logger.trace(e);
              continue;
            }
          } else {
            logger.debug("Worker '" + worker.getName() + "' is currently busy");
          }
        }
        if (!workerFound) {
          logger.info("It wasn't possible to assign a worker to job '" + jobName + "'; try later");
        }
      } else {
        logger.debug("It's not time to run job '" + jobName + "' yet; next execution time: " + nextExecTime.toString());
      }
    }
    logger.info("Jobs processed");
  }

  /**
   * <p>
   * Stop job dispatcher and all associated workers
   * </p>
   */

  public void stop() {
    logger.info("Stopping job dispatcher...");
    // Iterate over workers (first call stop for all workers, then later join them)
    for (FeedWorker w : workers) {
      logger.info("Stopping worker " + w.getName() + "...");
      if (w.hasStarted()) {
        w.stop();
        logger.debug("Called 'stop' for worker " + w.getName());
      } else {
        logger.error("Worker " + w.getName() + " has never started");
      }
    }
    // Now call join for each one of them
    for (FeedWorker w : workers) {
      logger.debug("Joining " + w.getName() + " thread");
      if (w.hasStarted()) {
        w.join();
        logger.info("Stopped (and joined) worker " + w.getName());
      }
    }
    this.workers = null;
    logger.info("All workers have been stopped!");
  }

  // @! Private

  /**
   * <p>
   * Initialize and define the list of feed jobs
   * </p>
   * 
   * @param feedSources
   */

  private void initFeedJobs(List<FeedSourceConfig> feedSources) {
    // Iterate over sources
    logger.debug("Initializing " + feedSources.size() + " feed jobs");
    final int sourcesLimit = feedSources.size();
    feedJobs = new FeedJob[sourcesLimit];
    for (int i = 0; i < sourcesLimit; i++) {
      FeedSourceConfig sourceConfig = feedSources.get(i);
      logger.debug("Found new Feed Job; Engine: " + sourceConfig.engine + " URI: " + sourceConfig.uri + "; Country: "
          + sourceConfig.country + "; interval: " + sourceConfig.interval + " minutes");
      try {
        feedJobs[i] = new FeedJob(sourceConfig);
      } catch (IllegalArgumentException e) {
        logger.error("Could not add FeedJob with URI: " + sourceConfig.uri);
        logger.trace(e);
        feedJobs[i] = null;
      }
    }
    logger.info("Prepared " + feedJobs.length + " feed jobs");
  }

  /**
   * <p>
   * Initialize and start feed workers
   * </p>
   * 
   * @param maxWorkers
   * @param dbConfig
   * @param metadataConfig
   */

  private void initFeedWorkers(int maxWorkers, DatabaseConfig dbConfig, MetadataConfig metadataConfig) {
    logger.debug("Initializing " + maxWorkers + " workers");
    workers = new FeedWorker[maxWorkers]; // Don't check; can't be 0 or less
    // Build workers
    for (int i = 0; i < maxWorkers; i++) {
      workers[i] = new FeedWorker(dbConfig, metadataConfig);
      logger.info("Initialized a new worker with name " + workers[i].getName());
    }
    logger.info("Prepared " + workers.length + " feed workers");
  }

}
