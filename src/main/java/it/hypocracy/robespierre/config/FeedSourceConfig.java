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
