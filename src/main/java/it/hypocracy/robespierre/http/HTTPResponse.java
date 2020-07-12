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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

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
