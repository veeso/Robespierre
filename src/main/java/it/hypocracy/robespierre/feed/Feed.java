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
import java.time.LocalDateTime;

public interface Feed {

  /**
   * <p>
   * Returns Feed Title
   * </p>
   * 
   * @return title
   */

  public String getTitle();

  /**
   * <p>
   * Returns Feed Brief
   * </p>
   * 
   * @return brief
   */

  public String getBrief();

  /**
   * <p>
   * return feed link
   * </p>
   * 
   * @return link
   */

  public URI getLink();

  /**
   * <p>
   * Returns the publication date time
   * </p>
   * 
   * @return LocalDateTime
   */

  public LocalDateTime getPublicationDatetime();

}
