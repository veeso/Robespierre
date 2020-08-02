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

import java.util.Iterator;
import java.util.List;

import it.hypocracy.robespierre.config.exceptions.BadConfigException;

public class FeedConfig implements BaseConfig {

  public List<FeedSourceConfig> sources;

  @Override
  public void check() throws BadConfigException {
    // Iterate over sources
    Iterator<FeedSourceConfig> it = sources.iterator();
    while (it.hasNext()) {
      FeedSourceConfig source = it.next();
      // Verify source
      source.check();
    }
  }

}
