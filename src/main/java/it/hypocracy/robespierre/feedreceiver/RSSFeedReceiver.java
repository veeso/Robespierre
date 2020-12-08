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

package it.hypocracy.robespierre.feedreceiver;

import java.io.IOException;

import com.rometools.rome.io.FeedException;

import it.hypocracy.robespierre.feed.Feed;
import it.hypocracy.robespierre.feed.FeedSource;
import it.hypocracy.robespierre.feedparser.RSSFeedParser;
import it.hypocracy.robespierre.http.HTTPFacade;
import it.hypocracy.robespierre.http.HTTPResponse;

/**
 * The RSSFeedReceiver is the class which takes care of receiving feeds through http and then
 * parse them using the `RSSFeedParser`
 */

public class RSSFeedReceiver implements FeedReceiver {

  private HTTPFacade httpFac;
  private RSSFeedParser parser;

  /**
   * <p>
   * RSS Feed Receiver class constructor
   * </p>
   */

  public RSSFeedReceiver() {
    this.httpFac = new HTTPFacade();
    this.parser = new RSSFeedParser();
  }

  /**
   * @param source
   * @return RSSFeed[]
   * @throws IOException
   * @throws FeedException
   */

  @Override
  public Feed[] fetchFeed(FeedSource source) throws IOException, FeedException {
    HTTPResponse response = this.httpFac.get(source.getURI());
    return this.parser.parse(response.getStringBody(), source.getCountry());
  }

}
