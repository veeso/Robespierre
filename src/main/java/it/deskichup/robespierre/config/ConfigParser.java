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

package it.deskichup.robespierre.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import it.deskichup.robespierre.config.exceptions.BadConfigException;

/**
 * ConfigParser is the object which takes care of parsing the configuration file
 */

public class ConfigParser {

  /**
   * <p>
   * Parse JSON configuration file
   * </p>
   * 
   * @param filename
   * @return Config
   * @throws BadConfigException
   */

  public Config parse(String filename) throws BadConfigException, IOException {
    InputStream input = null;
    String json = null;
    try {
      input = new FileInputStream(filename);
      json = readFromInputStream(input);
    } finally {
      if (input != null) {
        input.close();
      }
    }
    // Parse with GSON
    Gson gson = new Gson();
    Config config = null;
    try {
      config = gson.fromJson(json, Config.class);
    } catch (JsonSyntaxException e) {
      throw new BadConfigException(e.getMessage());
    }
    // Verify configuration
    config.check();
    // Return configuration
    return config;
  }

  /**
   * <p>
   * Convert input stream to string
   * </p>
   * 
   * @param inputStream
   * @return String
   * @throws IOException
   */

  private String readFromInputStream(InputStream inputStream) throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }
    }
    return resultStringBuilder.toString();
  }

}
