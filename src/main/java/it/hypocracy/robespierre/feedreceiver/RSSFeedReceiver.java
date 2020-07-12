/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.feedreceiver;

import java.io.IOException;

import it.hypocracy.robespierre.feed.RSSFeed;
import it.hypocracy.robespierre.feed.RSSFeedSource;
import it.hypocracy.robespierre.feedparser.RSSFeedParser;
import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.http.HTTPResponse;

public class RSSFeedReceiver implements FeedReceiver<RSSFeedSource, RSSFeed> {

  private HTTPFacade httpFac;
  private RSSFeedParser parser;

  /**
   * <p>
   * RSS Feed Receiver class constructor
   * </p>
   */

  public RSSFeedReceiver() {
    this.httpFac = new HTTPFacade();
    this.parser = new RSSFeedParser();
  }

  @Override
  public RSSFeed[] fetchFeed(RSSFeedSource source) throws IOException {
    HTTPResponse response = this.httpFac.get(source.getURI());
    return this.parser.parse(response.getStringBody());
  }

}
