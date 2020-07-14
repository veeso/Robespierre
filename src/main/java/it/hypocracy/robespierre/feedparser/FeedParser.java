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

import it.hypocracy.robespierre.utils.ISO3166;

public interface FeedParser<T> {

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

  public T[] parse(String body, ISO3166 country) throws Exception;

}
