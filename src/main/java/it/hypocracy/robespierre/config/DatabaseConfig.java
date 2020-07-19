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

public class DatabaseConfig implements BaseConfig {

  public String engine = null;
  public String uri = null;
  public String user = null;
  public String password = null;

  @Override
  public void check() throws BadConfigException {
    if (engine == null) {
      throw new BadConfigException("database 'engine' is null");
    }
    // Check parameters based on engine
    if (engine.equals("mariadb")) {
      if (uri == null) {
        throw new BadConfigException("database 'uri' is null");
      }
      if (user == null) {
        throw new BadConfigException("database 'user' is null");
      }
    } else {
      throw new BadConfigException("Unknown database engine");
    }
  }

}
