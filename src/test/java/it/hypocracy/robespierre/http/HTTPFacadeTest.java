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
    // Perform test request
    try {
      HTTPResponse response = httpFac.get(URI.create("https://www.example.com"));
      assertEquals(200, response.getStatus());
      assertTrue(response.getStringBody().startsWith("<!doctype html>"));
    } catch (IOException e) {
      e.printStackTrace();
      assert false;
    }
  }

  @Test
  public void shouldSucceedInGetRequestAsBytes() {
    HTTPFacade httpFac = new HTTPFacade();
    // Perform test request
    try {
      HTTPResponse response = httpFac.get(URI.create("https://www.example.com"));
      assertEquals(200, response.getStatus());
      BufferedInputStream stream = response.getBytesBody();
      assertTrue(stream.read() > 0);
    } catch (IOException e) {
      e.printStackTrace();
      assert false;
    }
  }
}
