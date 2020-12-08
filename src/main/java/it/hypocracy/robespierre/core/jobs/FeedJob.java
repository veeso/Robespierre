/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package it.hypocracy.robespierre.core.jobs;

import java.net.URI;

import it.hypocracy.robespierre.config.FeedSourceConfig;
import it.hypocracy.robespierre.core.FeedWorker;
import it.hypocracy.robespierre.core.errors.BusyWorkerException;
import it.hypocracy.robespierre.feed.FeedSource;
import it.hypocracy.robespierre.feed.RSSFeedSource;
import it.hypocracy.robespierre.utils.ISO3166;

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
