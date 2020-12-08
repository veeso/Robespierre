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

public class FeedSourceConfig implements BaseConfig {

  public String uri;
  public String country;
  public String engine;
  public int interval = 0; // Minutes


  @Override
  public void check() throws BadConfigException {
    if (uri == null) {
      throw new BadConfigException("feed source 'uri' is null");
    }
    if (country == null) {
      throw new BadConfigException("feed source 'country' is null");
    }
    if (engine == null) {
      throw new BadConfigException("feed source 'engine' is null");
    }
    if (!engine.equals("rss")) {
      throw new BadConfigException("Unknown feed engine");
    }
    if (interval <= 0) {
      throw new BadConfigException("feed source 'interval' is invalid");
    }
  }

}
