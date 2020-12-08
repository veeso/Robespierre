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

package it.hypocracy.robespierre.http;

import java.io.BufferedInputStream;
import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * HTTPResponse is the wrapper for HTTP responses
 */

public class HTTPResponse {
  
  private int status;
  private ResponseBody body;

  public HTTPResponse(int status, ResponseBody body) {
    this.status = status;
    this.body = body;
  }

  public int getStatus() {
    return this.status;
  }

  /**
   * <p>
   * Returns Body as String
   * </p>
   * 
   * @return body
   * @throws IOException
   */

  public String getStringBody() throws IOException {
    return this.body.string();
  }

  /**
   * <p>
   * Returns Body as InputStream
   * </p>
   * 
   * @return BufferedInputStream
   * @throws IOException
   */

  public BufferedInputStream getBytesBody() throws IOException {
    return new BufferedInputStream(body.byteStream());
  }

}
