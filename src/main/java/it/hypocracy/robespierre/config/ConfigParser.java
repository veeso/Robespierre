/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import it.hypocracy.robespierre.config.exceptions.BadConfigException;

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
