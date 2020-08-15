/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
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
