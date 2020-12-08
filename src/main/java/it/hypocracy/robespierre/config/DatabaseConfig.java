/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
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
