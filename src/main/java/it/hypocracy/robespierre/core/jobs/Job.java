/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core.jobs;

import java.time.LocalDateTime;

/**
 * Job is an interface which represents a Job the JobDispatcher will manage to dispatch to workers.
 * It can be anything, but the important thing about a job is that it can be scheduled
 * @param <T>
 */

public abstract class Job<T> {

  protected int interval; // Seconds
  // Runtime
  LocalDateTime nextExecution = LocalDateTime.now();

  /**
   * <p>
   * Run Job. Once ran the job is re-scheduled
   * </p>
   */

  public abstract void runJob(T ptr) throws Exception;

  /**
   * <p>
   * Return next execution time
   * </p>
   * 
   * @return local date time
   */

  public LocalDateTime getNextExecutionTime() {
    return nextExecution;
  }

  /**
   * <p>
   * reschedule job execution time
   * </p>
   */

  final protected void rescheduleJob() {
    // Set previous execution
    LocalDateTime prevExecution = null;
    prevExecution = LocalDateTime.from(nextExecution);
    // Sum interval to previous execution
    nextExecution = prevExecution.plusSeconds(interval);
  }

}
