/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * MIT License
 * 
 * Copyright (c) 2020 Christian Visintin
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.deskichup.robespierre.feedreceiver;

import java.io.IOException;

import com.rometools.rome.io.FeedException;

import it.deskichup.robespierre.feed.Feed;
import it.deskichup.robespierre.feed.FeedSource;
import it.deskichup.robespierre.feedparser.RSSFeedParser;
import it.deskichup.robespierre.http.HTTPFacade;
import it.deskichup.robespierre.http.HTTPResponse;

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
