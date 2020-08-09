/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Occupation;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.database.driver.MariaFacade;
import it.hypocracy.robespierre.database.query.InsertQuery;
import it.hypocracy.robespierre.meta.cache.MariaCacheProvider;
import it.hypocracy.robespierre.meta.exceptions.CacheException;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.utils.ISO3166;

public class WikiDataReceiverTest {

  private static Article testArticle = null;
  private static Article testArticleWithCache = null;
  private static Subject cachedSubject = null;

  // Maria Db Cache provider stuff
  private static String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS metadata_database_test;";
  private static String useDatabaseQuery = "USE metadata_database_test;";

  private static String createOccupationQuery = "CREATE TABLE IF NOT EXISTS occupation (id CHAR(36) NOT NULL PRIMARY KEY,it CHAR(128),en CHAR(128),fr CHAR(128));";
  private static String createBiographyQuery = "CREATE TABLE IF NOT EXISTS biography (id CHAR(36) NOT NULL PRIMARY KEY,it MEDIUMTEXT,en MEDIUMTEXT,fr MEDIUMTEXT);";
  private static String createSubjectQuery = "CREATE TABLE IF NOT EXISTS subject (id CHAR(36) NOT NULL PRIMARY KEY,name VARCHAR(128) NOT NULL,birthdate DATE,citizenship VARCHAR(2),birthplace VARCHAR(64),image VARCHAR(512),remote_id VARCHAR(256),last_update DATETIME NOT NULL,bio CHAR(36) NOT NULL,occupation CHAR(36) NOT NULL,CONSTRAINT `subject_bio_fk` FOREIGN KEY (bio) REFERENCES biography (id),CONSTRAINT `subject_occupation_fk` FOREIGN KEY (occupation) REFERENCES occupation (id));";
  private static String createTopicDataQuery = "CREATE TABLE IF NOT EXISTS topic_data (id CHAR(36) NOT NULL PRIMARY KEY,name_it CHAR(64),desc_it MEDIUMTEXT,name_en CHAR(64),desc_en MEDIUMTEXT,name_fr CHAR(64),desc_fr MEDIUMTEXT);";
  private static String createTopicQuery = "CREATE TABLE IF NOT EXISTS topic (id CHAR(36) NOT NULL PRIMARY KEY,topic_data_id CHAR(36) NOT NULL,CONSTRAINT `topic_data_fk` FOREIGN KEY (topic_data_id) REFERENCES topic_data (id));";

  private static String dropDatabaseQuery = "DROP DATABASE metadata_database_test;";

  private static String baseUrl = "jdbc:mariadb://localhost:3306/";
  private static String databaseName = "metadata_database_test";
  private static String dbUrl = baseUrl + databaseName;

  @BeforeClass
  public static void prerunInitArticle() {
    ISO3166 lang = new ISO3166("IT");
    testArticle = new Article("Alberto Angela è amico dei vegani", "Il paleontologo ha detto di essere pro veganismo",
        URI.create("https://example.com/news/alberto_angela_vegan"), LocalDateTime.now(), lang);
    testArticleWithCache = new Article("Alberto Angela e Gerry Scotti sono amici dei vegani",
        "I due conduttori hanno detto di essere pro veganismo",
        URI.create("https://example.com/news/alberto_angela_e_gerry_scotti_vegan"), LocalDateTime.now(), lang);
  }

  @BeforeClass
  public static void shouldInitializeDatabase() throws SQLException {
    MariaFacade db = new MariaFacade(baseUrl, "root", null);
    // Open database
    db.connect();
    // Create database
    db.performFreeform(createDatabaseQuery);
    db.performFreeform(useDatabaseQuery);
    // Create tables
    db.performFreeform(createOccupationQuery);
    db.performFreeform(createBiographyQuery);
    db.performFreeform(createSubjectQuery);
    db.performFreeform(createTopicDataQuery);
    db.performFreeform(createTopicQuery);
    // Commit
    db.commit();
    db.disconnect();
    // Insert subject and topic
    db = new MariaFacade(dbUrl, "root", null);

    db.connect();
    try {
      // Insert Gerry Scotti
      Occupation occupation = new Occupation("conduttore televisivo");
      Subject gerry = new Subject("gerry scotti", LocalDate.of(1956, Month.AUGUST, 7), new ISO3166("IT"),
          "miradolo terme", "https://upload.wikimedia.org/wikipedia/commons/9/9d/Gerry_Scotti.jpg",
          "conduttore televisivo, conduttore radiofonico, attore ed ex politico italiano", "Q363498", occupation);
      String[] values = new String[] { escapeString(gerry.getId()), escapeString(gerry.getName()),
          escapeString(gerry.getBirthdate().toString()), escapeString(gerry.getCitizenship().toString()),
          escapeString(gerry.getBirthplace()), escapeString(gerry.getImageUri()), escapeString(gerry.getRemoteId()),
          escapeString(LocalDateTime.now().toString()), escapeString(gerry.biography.getId()),
          escapeString(gerry.occupation.getId()) };
      // Insert biography
      InsertQuery query = new InsertQuery("biography", new String[] { "id", "it" },
          new String[] { escapeString(gerry.biography.getId()), escapeString(gerry.biography.getBrief()) });
      db.insert(query);
      // Insert occupation
      query = new InsertQuery("occupation", new String[] { "id", "it" },
          new String[] { escapeString(gerry.occupation.getId()), escapeString(gerry.occupation.getName()) });
      db.insert(query);
      // Insert subject
      query = new InsertQuery("subject", new String[] { "id", "name", "birthdate", "citizenship", "birthplace", "image",
          "remote_id", "last_update", "bio", "occupation" }, values);
      db.insert(query);
      // Insert veganismo
      Topic veganism = new Topic("veganismo",
          "stile di vita che tende ad escludere ogni forma di sfruttamento animale");
      query = new InsertQuery("topic_data", new String[] { "id", "name_it", "desc_it" },
          new String[] { escapeString(veganism.getDescriptionId()), escapeString(veganism.getName()),
              escapeString(veganism.getDescription()) });
      db.insert(query);
      query = new InsertQuery("topic", new String[] { "id", "topic_data_id" },
          new String[] { escapeString(veganism.getId()), escapeString(veganism.getDescriptionId()) });
      db.insert(query);
      // Commit
      db.commit();
      cachedSubject = gerry;
    } finally {
      db.disconnect();
    }
  }

  @Test
  public void shouldReceiveMetadataForArticleWoutCache()
      throws MetadataReceiverException, CacheException, ParserException {
    WikiDataReceiver recv = new WikiDataReceiver();
    // Get metadta
    recv.fetchMetadata(testArticle);
    // Verify article subjects
    Iterator<Subject> subjects = testArticle.iterSubjects();
    assertTrue(subjects.hasNext());
    Subject subject = subjects.next();
    assertEquals("alberto angela", subject.getName());
    assertEquals("paleontologo, divulgatore scientifico e giornalista italiano", subject.biography.getBrief());
    assertEquals("IT", subject.getCitizenship().toString());
    assertEquals(1962, subject.getBirthdate().getYear());
    assertEquals(Month.APRIL, subject.getBirthdate().getMonth());
    assertEquals(8, subject.getBirthdate().getDayOfMonth());
    assertEquals("Q514695", subject.getRemoteId());
    assertEquals("https://upload.wikimedia.org/wikipedia/commons/1/1e/Alberto_Angela.jpg", subject.getImageUri());
    assertEquals("paleontologo", subject.occupation.getName());
    assertEquals("paris", subject.getBirthplace());
    // No other subjects
    assertFalse(subjects.hasNext());
    // Verify topic
    Iterator<Topic> topics = testArticle.iterTopics();
    assertTrue(topics.hasNext());
    Topic topic = topics.next();
    assertEquals("veganismo", topic.getName());
    assertEquals("stile di vita che tende ad escludere ogni forma di sfruttamento animale", topic.getDescription());
    // There shouldn't be any other topic
    assertFalse(topics.hasNext());
  }

  @Test
  public void shouldReceiveMetadataWithCache() throws MetadataReceiverException, CacheException, ParserException {
    // Create cache
    WikiDataReceiver recv = new WikiDataReceiver(new MariaCacheProvider(dbUrl, "root", null, 180));
    // Get metadta
    recv.fetchMetadata(testArticleWithCache);
    // Verify article subjects
    Iterator<Subject> subjects = testArticleWithCache.iterSubjects();
    assertTrue(subjects.hasNext());
    Subject subject = subjects.next();
    assertEquals("alberto angela", subject.getName());
    assertEquals("paleontologo, divulgatore scientifico e giornalista italiano", subject.biography.getBrief());
    assertEquals("IT", subject.getCitizenship().toString());
    assertEquals(1962, subject.getBirthdate().getYear());
    assertEquals(Month.APRIL, subject.getBirthdate().getMonth());
    assertEquals(8, subject.getBirthdate().getDayOfMonth());
    assertEquals("Q514695", subject.getRemoteId());
    assertEquals("https://upload.wikimedia.org/wikipedia/commons/1/1e/Alberto_Angela.jpg", subject.getImageUri());
    assertEquals("paleontologo", subject.occupation.getName());
    assertEquals("paris", subject.getBirthplace());
    // There should be Gerry Scotti too
    assertTrue(subjects.hasNext());
    subject = subjects.next();
    assertEquals(cachedSubject.getId(), subject.getId());
    assertEquals(cachedSubject.getName(), subject.getName());
    assertEquals(cachedSubject.biography.getBrief(), subject.biography.getBrief());
    assertEquals(cachedSubject.getCitizenship().toString(), subject.getCitizenship().toString());
    assertEquals(cachedSubject.getBirthdate(), subject.getBirthdate());
    assertEquals(cachedSubject.getRemoteId(), subject.getRemoteId());
    assertEquals(cachedSubject.getImageUri(), subject.getImageUri());
    assertEquals(cachedSubject.occupation.getName(), subject.occupation.getName());
    assertEquals(cachedSubject.getBirthplace(), subject.getBirthplace());
    // Verify topic
    Iterator<Topic> topics = testArticleWithCache.iterTopics();
    assertTrue(topics.hasNext());
    Topic topic = topics.next();
    assertEquals("veganismo", topic.getName());
    assertEquals("stile di vita che tende ad escludere ogni forma di sfruttamento animale", topic.getDescription());
    // There shouldn't be any other topic
    assertFalse(topics.hasNext());
  }

  @AfterClass
  public static void shouldDropTable() throws SQLException {
    // Drop database
    MariaFacade db = new MariaFacade(baseUrl, "root", null);
    // Open database
    db.connect();
    // Drop
    db.performFreeform(dropDatabaseQuery);
    // Commit
    db.commit();
    db.disconnect();
  }

  private static String escapeString(String val) {
    StringBuilder escaped = new StringBuilder();
    escaped.append("\"");
    escaped.append(val);
    escaped.append("\"");
    return escaped.toString();
  }

}
