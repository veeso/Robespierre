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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

public class HTTPFacadeTest {

  @Test
  public void shouldSucceedInGetRequestAsString() {
    HTTPFacade httpFac = new HTTPFacade();
    //Perform test request
    try {
      HTTPResponse response = httpFac.get(URI.create("https://www.example.com"));
      assertEquals(response.getStatus(), 200);
      assertTrue(response.getStringBody().startsWith("<!doctype html>"));
    } catch (IOException e) {
      e.printStackTrace();
      assert false;
    }
  }

  @Test
  public void shouldSucceedInGetRequestAsBytes() {
    HTTPFacade httpFac = new HTTPFacade();
    //Perform test request
    try {
      HTTPResponse response = httpFac.get(URI.create("https://www.example.com"));
      assertEquals(response.getStatus(), 200);
      BufferedInputStream stream = response.getBytesBody();
      assertTrue(stream.read() > 0);
    } catch (IOException e) {
      e.printStackTrace();
      assert false;
    }
  }
}
