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

import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.feed.FeedSource;

/**
 * FeedReceiver represents the entity which takes care of fetching the source and
 * receiving feeds from it
 */

public interface FeedReceiver {
  
  /**
   * <p>
   * Fetch feed source
   * </p>
   * 
   * @param source
   * @return Feed
   * @throws Exception
   */
  public Feed[] fetchFeed(FeedSource source) throws Exception;

}
