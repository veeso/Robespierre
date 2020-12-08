/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.hypocracy.robespierre.core.jobs;

import java.time.LocalDateTime;

/**
 * Job is an interface which represents a Job the JobDispatcher will manage to dispatch to workers.
 * It can be anything, but the important thing about a job is that it can be scheduled
 * @param <T>
 */

public abstract class Job<T> {

  protected int interval; // Minutes
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
    // Sum interval to previous execution
    nextExecution = nextExecution.plusMinutes(interval);
  }

}
