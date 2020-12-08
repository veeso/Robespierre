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

package it.deskichup.robespierre.core.jobs;

import java.net.URI;

import it.deskichup.robespierre.config.FeedSourceConfig;
import it.deskichup.robespierre.core.FeedWorker;
import it.deskichup.robespierre.core.errors.BusyWorkerException;
import it.deskichup.robespierre.feed.FeedSource;
import it.deskichup.robespierre.feed.RSSFeedSource;
import it.deskichup.robespierre.utils.ISO3166;

/**
 * FeedJob is a Job which must fetch the remote feed and store the articles into the database
 */

public class FeedJob extends Job<FeedWorker> {

  FeedSource source;

  /**
   * <p>
   * Instantiatiates a new FeedJob
   * </p>
   * 
   * @param sourceConfig
   * @throws IllegalArgumentException
   */

  public FeedJob(FeedSourceConfig sourceConfig) throws IllegalArgumentException {
    this.interval = sourceConfig.interval;
    switch (sourceConfig.engine) {
      case "rss":
        this.source = new RSSFeedSource(URI.create(sourceConfig.uri), new ISO3166(sourceConfig.country));
        break;
      default:
        throw new IllegalArgumentException("Unsupported engine " + sourceConfig.engine);
    }
  }

  /**
   * <p>
   * Run job on provided feed worker
   * </p>
   * 
   * @throws BusyWorkerException
   */
  @Override
  public void runJob(FeedWorker ptr) throws BusyWorkerException {
    // Assign job to feed worker
    ptr.assignNewJob(source);
    // Re-schedule job
    this.rescheduleJob();
  }

  /**
   * <p>
   * Get uri
   * </p>
   * 
   * @return uri
   */

  public String getUri() {
    return this.source.getURI().toString();
  }

}
