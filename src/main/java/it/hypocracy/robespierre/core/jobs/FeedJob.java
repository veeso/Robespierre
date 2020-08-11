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

import java.net.URI;

import it.hypocracy.robespierre.config.FeedSourceConfig;
import it.hypocracy.robespierre.core.FeedWorker;
import it.hypocracy.robespierre.core.errors.BusyWorkerException;
import it.hypocracy.robespierre.feed.FeedSource;
import it.hypocracy.robespierre.feed.RSSFeedSource;
import it.hypocracy.robespierre.utils.ISO3166;

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
