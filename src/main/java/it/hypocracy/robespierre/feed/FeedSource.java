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

package it.hypocracy.robespierre.feed;

import java.net.URI;

import it.hypocracy.robespierre.utils.ISO3166;

/**
 * FeedSource is the base class which represents a FeedSource
 */

public class FeedSource {

  protected URI uri;
  protected ISO3166 country;

  public FeedSource(URI uri, ISO3166 country) {
    this.uri = uri;
    this.country = country;
  }

  /**
   * <p>
   * Returns the URI to access the Feed
   * </p>
   * 
   * @return URI
   */

  public URI getURI() {
    return this.uri;
  }

  /**
   * <p>
   * Returns the feed source country code (ISO3166)
   * </p>
   * 
   * @return
   */

  public ISO3166 getCountry() {
    return this.country;
  }

}
