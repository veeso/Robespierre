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

package it.deskichup.robespierre.http;

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
