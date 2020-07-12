/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.feed;

import java.net.URI;

import it.hypocracy.robespierre.utils.ISO3166;

public interface FeedSource {
  
  /**
   * <p>
   * Returns the URI to access the Feed
   * </p>
   * 
   * @return URI
   */

  public URI getURI();

  /**
   * <p>
   * Returns the feed source country code (ISO3166)
   * </p>
   * @return
   */

  public ISO3166 getCountry();

}
