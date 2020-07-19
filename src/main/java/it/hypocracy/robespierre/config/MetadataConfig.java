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

public class MetadataConfig implements BaseConfig {

  public String engine;

  @Override
  public void check() throws BadConfigException {
    if (engine == null) {
      throw new BadConfigException("metadata 'engine' is null");
    }
    if (!engine.equals("wikidata")) {
      throw new BadConfigException("Unknown metadata engine");
    }
  }

}
