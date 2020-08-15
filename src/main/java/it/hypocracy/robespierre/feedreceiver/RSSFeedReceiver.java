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

import com.rometools.rome.io.FeedException;

import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.feed.FeedSource;
import it.hypocracy.robespierre.feedparser.RSSFeedParser;
import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.http.HTTPResponse;

/**
 * The RSSFeedReceiver is the class which takes care of receiving feeds through http and then
 * parse them using the `RSSFeedParser`
 */

public class RSSFeedReceiver implements FeedReceiver {

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

  /**
   * @param source
   * @return RSSFeed[]
   * @throws IOException
   * @throws FeedException
   */

  @Override
  public Feed[] fetchFeed(FeedSource source) throws IOException, FeedException {
    HTTPResponse response = this.httpFac.get(source.getURI());
    return this.parser.parse(response.getStringBody(), source.getCountry());
  }

}
