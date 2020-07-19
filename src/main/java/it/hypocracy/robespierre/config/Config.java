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

public class Config implements BaseConfig {

  public DatabaseConfig database = null;
  public FeedConfig feed = null;
  public MetadataConfig metadata = null;

  @Override
  public void check() throws BadConfigException {
    if (database == null) {
      throw new BadConfigException("'database' is null");
    }
    if (feed == null) {
      throw new BadConfigException("'feed' is null");
    }
    if (metadata == null) {
      throw new BadConfigException("'metadata' is null");
    }
    database.check();
    feed.check();
    metadata.check();
  }

}
