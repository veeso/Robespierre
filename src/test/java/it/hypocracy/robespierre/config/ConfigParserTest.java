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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import it.hypocracy.robespierre.config.exceptions.BadConfigException;

public class ConfigParserTest {

  public static String configTest = "{\"database\":{\"engine\":\"mariadb\",\"uri\":\"jdbc:mariadb://localhost:3306/robespierre\",\"user\":\"root\",\"password\":null},\"feed\":{\"maxWorkers\":16,\"sources\":[{\"uri\":\"http://example.com/rss\",\"country\":\"IT\",\"engine\":\"rss\",\"interval\":360},{\"uri\":\"http://news.com/rss\",\"country\":\"EN\",\"engine\":\"rss\",\"interval\":240}]},\"metadata\":{\"engine\":\"wikidata\",\"cache\":{\"duration\":180,\"withBlacklist\":true}}}";
  public static String filePath = null;
  public static String badConfigTest = "{\"database\":{\"engine\":\"nodb\",\"uri\":\"jdbc:mariadb://localhost:3306/robespierre\",\"user\":\"root\",\"password\":null},\"feed\":{\"sources\":[{\"uri\":\"http://example.com/rss\",\"country\":\"IT\",\"engine\":\"rss\",\"interval\":360},{\"uri\":\"http://news.com/rss\",\"country\":\"EN\",\"engine\":\"rss\",\"interval\":240}]},\"metadata\":{\"engine\":\"wikidata\",\"cache\":{\"duration\":180,\"withBlacklist\":true}}}";
  public static String badFilePath = null;

  @BeforeClass
  public static void createConfiguration() throws IOException {
    File file = File.createTempFile("temp", null);
    filePath = file.getAbsolutePath();
    file.deleteOnExit();
    // Write configuration
    FileWriter fw = new FileWriter(filePath);
    fw.write(configTest);
    fw.write("\n");
    fw.close();
  }

  @BeforeClass
  public static void createBadConfiguration() throws IOException {
    File file = File.createTempFile("temp", null);
    badFilePath = file.getAbsolutePath();
    file.deleteOnExit();
    // Write configuration
    FileWriter fw = new FileWriter(badFilePath);
    fw.write(badConfigTest);
    fw.write("\n");
    fw.close();
  }

  @Test
  public void shouldParseConfiguration() throws BadConfigException, IOException {
    // Create config parser
    ConfigParser parser = new ConfigParser();
    // Parse configuration
    Config config = parser.parse(filePath);
    // Verify configuration parameter
    // Database
    assertEquals("mariadb", config.database.engine);
    assertEquals("jdbc:mariadb://localhost:3306/robespierre", config.database.uri);
    assertEquals("root", config.database.user);
    assertTrue(config.database.password == null);
    // Feed
    assertEquals(16, config.feed.maxWorkers);
    assertEquals(2, config.feed.sources.size());
    assertEquals("http://example.com/rss", config.feed.sources.get(0).uri);
    assertEquals("IT", config.feed.sources.get(0).country);
    assertEquals("rss", config.feed.sources.get(0).engine);
    assertEquals(360, config.feed.sources.get(0).interval);
    assertEquals("http://news.com/rss", config.feed.sources.get(1).uri);
    assertEquals("EN", config.feed.sources.get(1).country);
    assertEquals("rss", config.feed.sources.get(1).engine);
    assertEquals(240, config.feed.sources.get(1).interval);
    // Metadata
    assertEquals("wikidata", config.metadata.engine);
    assertEquals(180, config.metadata.cache.duration);
    assertEquals(true, config.metadata.cache.withBlacklist);
  }

  @Test
  public void shouldFailReadingConfiguration() {
    // Create config parser
    ConfigParser parser = new ConfigParser();
    // Parse configuration
    assertThrows(IOException.class, () -> parser.parse("/pippo.json"));
  }

  @Test
  public void shouldFailParsingConfiguration() {
    // Create config parser
    ConfigParser parser = new ConfigParser();
    // Parse configuration
    assertThrows(BadConfigException.class, () -> parser.parse(badFilePath));
  }

}
