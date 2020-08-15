/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.feedparser;

import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.utils.ISO3166;

/**
 * The FeedParser is the entity which takes care, after receiving the data stream of the feed
 * to parse it and to convert it into a list of Feed
 */

public interface FeedParser {

  /**
   * <p>
   * Parse Feed
   * </p>
   * 
   * @param body
   * @param country
   * @return Feed[]
   * @throws Exception
   */

  public Feed[] parse(String body, ISO3166 country) throws Exception;

}
