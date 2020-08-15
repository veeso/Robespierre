/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.config;

import it.hypocracy.robespierre.config.exceptions.BadConfigException;

public class MetadataCacheConfig implements BaseConfig {

  public int duration = 0; // Days
  public boolean withBlacklist = false;

  @Override
  public void check() throws BadConfigException {
    if (duration <= 0) {
      throw new BadConfigException("metadata cache 'duration' is invalid");
    }
  }

}
