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

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The HTTPFacade is a façade which makes easier receiving data through HTTP
 */

public class HTTPFacade {

  private final static Logger logger = Logger.getLogger(HTTPFacade.class.getName());

  public HTTPFacade() {
    super();
  }

  /**
   * <p>
   * Perform a GET request on the provided URI
   * </p>
   * 
   * @param uri
   * @return HTTPResponse
   * @throws IOException
   */

  public HTTPResponse get(URI uri) throws IOException{
    Request request = new Request.Builder().url(HttpUrl.get(uri)).build();
    OkHttpClient httpCli = new OkHttpClient();
    logger.trace("GET " + uri);
    Response response = httpCli.newCall(request).execute();
    logger.debug("GET " + uri + " => " + response.code());
    return new HTTPResponse(response.code(), response.body());
  }

}
