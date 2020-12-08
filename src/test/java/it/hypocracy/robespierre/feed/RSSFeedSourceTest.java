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

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import it.hypocracy.robespierre.utils.ISO3166;

public class RSSFeedSourceTest {

  @Test
  public void shouldInstantiateRSSFeedSource() {
    ISO3166 country = new ISO3166("IT");
    URI uri = URI.create("http://mynews.com/rss");
    RSSFeedSource source = new RSSFeedSource(uri, country);
    assertEquals(source.getURI(), uri);
    assertEquals(country.toString(), source.getCountry().toString());
  }
  
}
