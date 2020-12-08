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
