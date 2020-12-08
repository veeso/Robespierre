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

package it.hypocracy.robespierre.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Occupation;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.core.errors.DupedRecordException;
import it.hypocracy.robespierre.database.driver.MariaFacade;
import it.hypocracy.robespierre.database.query.SelectQuery;
import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;
import it.hypocracy.robespierre.utils.ISO3166;

public class FeedDatabaseTest {

  // Queries
  private static String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS feed_database_test;";
  private static String useDatabaseQuery = "USE feed_database_test;";

  private static String createOccupationQuery = "CREATE TABLE IF NOT EXISTS occupation (id CHAR(36) NOT NULL PRIMARY KEY,it CHAR(128),en CHAR(128),fr CHAR(128));";
  private static String createBiographyQuery = "CREATE TABLE IF NOT EXISTS biography (id CHAR(36) NOT NULL PRIMARY KEY,it MEDIUMTEXT,en MEDIUMTEXT,fr MEDIUMTEXT);";
  private static String createSubjectQuery = "CREATE TABLE IF NOT EXISTS subject (id CHAR(36) NOT NULL PRIMARY KEY,name VARCHAR(128) NOT NULL,birthdate DATE,citizenship VARCHAR(2),birthplace VARCHAR(64),image VARCHAR(512),remote_id VARCHAR(256),last_update DATETIME NOT NULL,bio CHAR(36) NOT NULL,occupation CHAR(36) NOT NULL,CONSTRAINT `subject_bio_fk` FOREIGN KEY (bio) REFERENCES biography (id),CONSTRAINT `subject_occupation_fk` FOREIGN KEY (occupation) REFERENCES occupation (id));";
  private static String createTopicDataQuery = "CREATE TABLE IF NOT EXISTS topic_data (id CHAR(36) NOT NULL PRIMARY KEY,name_it CHAR(64),desc_it MEDIUMTEXT,name_en CHAR(64),desc_en MEDIUMTEXT,name_fr CHAR(64),desc_fr MEDIUMTEXT);";
  private static String createTopicQuery = "CREATE TABLE IF NOT EXISTS topic (id CHAR(36) NOT NULL PRIMARY KEY,topic_data_id CHAR(36) NOT NULL,CONSTRAINT `topic_data_fk` FOREIGN KEY (topic_data_id) REFERENCES topic_data (id));";
  private static String createArticleQuery = "CREATE TABLE IF NOT EXISTS article (id CHAR(36) NOT NULL PRIMARY KEY,title VARCHAR(128) NOT NULL,brief MEDIUMTEXT NOT NULL,link VARCHAR(128) NOT NULL,date DATETIME NOT NULL,country VARCHAR(2) NOT NULL);";
  private static String createArticleTopicQuery = "CREATE TABLE IF NOT EXISTS article_topic (id CHAR(36) NOT NULL PRIMARY KEY,article_id CHAR(36) NOT NULL,topic_id CHAR(36) NOT NULL,CONSTRAINT `article_topic_fk` FOREIGN KEY (article_id) REFERENCES article (id),CONSTRAINT `topic_fk` FOREIGN KEY (topic_id) REFERENCES topic (id));";
  private static String createArticleSubjectQuery = "CREATE TABLE IF NOT EXISTS article_subject (id CHAR(36) NOT NULL PRIMARY KEY,article_id CHAR(36) NOT NULL,subject_id CHAR(36) NOT NULL,CONSTRAINT `article_subject_fk` FOREIGN KEY (article_id) REFERENCES article (id),CONSTRAINT `subject_fk` FOREIGN KEY (subject_id) REFERENCES subject (id));";
  private static String createMetadataBlacklistQuery = "CREATE TABLE IF NOT EXISTS metadata_blacklist (id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,word MEDIUMTEXT NOT NULL,language VARCHAR(2) NOT NULL,commit_date DATETIME NOT NULL);";

  private static String dropDatabaseQuery = "DROP DATABASE feed_database_test;";

  private static String baseUrl = "jdbc:mariadb://localhost:3306/";
  private static String databaseName = "feed_database_test";
  private static String dbUrl = baseUrl + databaseName;

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
    db.performFreeform(createArticleQuery);
    db.performFreeform(createArticleTopicQuery);
    db.performFreeform(createArticleSubjectQuery);
    db.performFreeform(createMetadataBlacklistQuery);
    // Commit
    db.commit();
    db.disconnect();
  }

  // @! Tests

  @Test
  public void shouldSuccedsInOperatingWithDatabase() throws SQLException, DupedRecordException {
    MariaFacade dbFacade = new MariaFacade(dbUrl, "root", null);
    FeedDatabase db = new FeedDatabase(dbFacade);
    // @! Insert article
    // Prepare subjects
    Occupation occupation1 = new Occupation("paleontologo");
    Subject subject1 = new Subject("alberto angela", LocalDate.of(1962, Month.APRIL, 8), new ISO3166("IT"), "paris",
        "https://upload.wikimedia.org/wikipedia/commons/1/1e/Alberto_Angela.jpg", "paleontologo italiano", "Q514695",
        occupation1);
    Occupation occupation2 = new Occupation("conduttore televisivo");
    Subject subject2 = new Subject("gerry scotti", LocalDate.of(1956, Month.AUGUST, 7), new ISO3166("IT"),
        "miradolo terme", "https://upload.wikimedia.org/wikipedia/commons/9/9d/Gerry_Scotti.jpg",
        "conduttore televisivo, conduttore radiofonico, attore ed ex politico italiano", "Q363498", occupation2);
    // Prepare topic
    Topic topic = new Topic("europeismo", "ideologia politica");
    // Build Article
    LocalDateTime publishDate = LocalDateTime.now();
    publishDate = publishDate.withNano(0);
    Article article = new Article("titolo articolo", "testo dell'articolo",
        URI.create("https://giornaletto/alberto%20angela%20europeista"), publishDate, new ISO3166("it"));
    article.addSubject(subject1);
    article.addSubject(subject2);
    article.addTopic(topic);
    // Insert article
    db.commitArticle(article);
    // Verify inserted records
    dbFacade.connect();
    try {
      // Verify article itself
      SelectQuery query = new SelectQuery("article");
      ArrayList<Map<String, String>> rows = dbFacade.select(query);
      // There should be one record
      assertEquals(1, rows.size());
      Map<String, String> row = rows.get(0);
      // Verify row
      cmpArticleWithRow(article, row);
      // Verify subjects
      query = new SelectQuery("subject");
      rows = dbFacade.select(query);
      // There should be two record
      assertEquals(2, rows.size());
      // Verify first subject
      // Get test subjects (sometimes rows are inverted)
      row = rows.get(0);
      Subject testSubject1 = subject1.getId().equals(row.get("id")) ? subject1 : subject2;
      Subject testSubject2 = testSubject1 == subject1 ? subject2 : subject1;
      cmpSubjectWithRow(dbFacade, testSubject1, row);
      // Verify second subject
      row = rows.get(1);
      cmpSubjectWithRow(dbFacade, testSubject2, row);
    } finally {
      dbFacade.disconnect();
    }

    // @! Duped article
    LocalDateTime publishDate2 = LocalDateTime.now();
    Article article2 = new Article("titolo articolo", "testo dell'articolo",
        URI.create("https://giornaletto/alberto%20angela%20europeista"), publishDate2, new ISO3166("it"));
    assertThrows(DupedRecordException.class, () -> db.commitArticle(article2));

    // @! Search subjects
    Subject[] subjects = db.searchSubject("angela", 180, "it");
    // Verify subject
    assertEquals(1, subjects.length);
    Subject albertoAngela = subjects[0];
    cmpSubjects(subject1, albertoAngela);
    // @! Search topics
    Topic[] topics = db.searchTopic("europeismo", "it");
    assertEquals(1, topics.length);
    Topic europeismo = topics[0];
    cmpTopics(topic, europeismo);
    // @! Update subject and topic
    Occupation occupationUpdated = new Occupation("imperatore di Roma");
    Subject subject1Updated = new Subject(subject1.getId(), "alberto angela", LocalDate.of(1962, Month.APRIL, 8),
        new ISO3166("IT"), "paris",
        "https://upload.wikimedia.org/wikipedia/commons/1/1e/Alberto_Angela_In_Spiaggia.jpg",
        "paleontologo italiano ed imperatore di Roma", "Q514695", LocalDateTime.now(), occupationUpdated);
    Topic updatedTopic = new Topic("europeismo",
        "ideologia politica volta a favorire l'integrazione dei popoli e degli Stati d'Europa in un ordinamento sovranazionale più vasto che si estenda su tutto il continente");
    publishDate = LocalDateTime.now();
    Article article3 = new Article("Alberto Angela diventa imperatore di Roma", "testo dell'articolo",
        URI.create("https://giornaletto/alberto%20angela%20europeista"), publishDate, new ISO3166("it"));
    article3.addSubject(subject1Updated);
    article3.addTopic(updatedTopic);
    // Commit article
    db.commitArticle(article3);
    // Verify inserted records
    dbFacade.connect();
    try {
      // Verify article itself
      SelectQuery query = new SelectQuery(new String[] { "*" }, new String[] { "article" },
          new Clause("id", escapeString(article3.getId()), ClauseOperator.EQUAL));
      ArrayList<Map<String, String>> rows = dbFacade.select(query);
      // There should be one record
      assertEquals(1, rows.size());
      Map<String, String> row = rows.get(0);
      // Verify row
      cmpArticleWithRow(article3, row);
      // Verify subjects
      query = new SelectQuery(new String[] { "*" }, new String[] { "subject" },
          new Clause("id", escapeString(subject1Updated.getId()), ClauseOperator.EQUAL));
      rows = dbFacade.select(query);
      // There should be once record
      assertEquals(1, rows.size());
      // Verify first subject
      // Get test subjects (sometimes rows are inverted)
      row = rows.get(0);
      cmpSubjectWithRow(dbFacade, subject1Updated, row);
      // Check topic data
      query = new SelectQuery(new String[] { "*" }, new String[] { "topic_data" },
          new Clause("id", escapeString(topic.getDescriptionId()), ClauseOperator.EQUAL));
      rows = dbFacade.select(query);
      assertEquals(1, rows.size());
      row = rows.get(0);
      assertEquals(updatedTopic.getName(), row.get("name_it"));
      assertEquals(updatedTopic.getDescription(), row.get("desc_it"));
    } finally {
      dbFacade.disconnect();
    }

    // @! Blacklist
    // Check if 'cane' doesn't exist
    assertFalse(db.searchBlacklist("cane", 180, "it"));
    // insert a word
    db.commitBlacklistWord("cane", "it");
    // Verify cane exists
    assertTrue(db.searchBlacklist("cane", 180, "it"));
    // That's fine, but cane mustn't be true for french for example
    assertFalse(db.searchBlacklist("cane", 180, "fr"));
    // Update word
    db.commitBlacklistWord("cane", "it");
  }

  // @! Test methods

  private void cmpArticleWithRow(Article lvalue, Map<String, String> rvalue) {
    assertEquals(lvalue.getId(), rvalue.get("id"));
    assertEquals(lvalue.getTitle(), rvalue.get("title"));
    assertEquals(lvalue.getBrief(), rvalue.get("brief"));
    assertEquals(lvalue.getLink().toString(), rvalue.get("link"));
    // assertEquals(lvalue.getDate().toString(),
    // MySqlDateTime.parse(rvalue.get("date")).toString());
    assertEquals(lvalue.getCountry().toString(), rvalue.get("country"));
  }

  private void cmpSubjectWithRow(MariaFacade dbFacade, Subject lvalue, Map<String, String> rvalue) throws SQLException {
    assertEquals(lvalue.getId(), rvalue.get("id"));
    assertEquals(lvalue.getName(), rvalue.get("name"));
    assertEquals(lvalue.getBirthdate().toString(), rvalue.get("birthdate"));
    assertEquals(lvalue.getCitizenship().toString(), rvalue.get("citizenship"));
    assertEquals(lvalue.getBirthplace(), rvalue.get("birthplace"));
    assertEquals(lvalue.getImageUri(), rvalue.get("image"));
    assertEquals(lvalue.getRemoteId(), rvalue.get("remote_id"));
    assertEquals(36, rvalue.get("bio").length());
    assertEquals(36, rvalue.get("occupation").length());
    final String bioId = rvalue.get("bio");
    final String occupationId = rvalue.get("occupation");
    // Check bio of first subject
    String[] tables = new String[] { "biography" };
    String[] fields = new String[] { "it" };
    SelectQuery query = new SelectQuery(fields, tables, new Clause("id", escapeString(bioId), ClauseOperator.EQUAL));
    ArrayList<Map<String, String>> rows = dbFacade.select(query);
    assertEquals(1, rows.size());
    Map<String, String> row = rows.get(0);
    assertEquals(lvalue.biography.getBrief(), row.get("it"));
    // Check occupation of first subject
    tables = new String[] { "occupation" };
    fields = new String[] { "it" };
    query = new SelectQuery(fields, tables, new Clause("id", escapeString(occupationId), ClauseOperator.EQUAL));
    rows = dbFacade.select(query);
    assertEquals(1, rows.size());
    row = rows.get(0);
    assertEquals(lvalue.occupation.getName(), row.get("it"));
  }

  private void cmpSubjects(Subject lvalue, Subject rvalue) {
    assertEquals(lvalue.getId(), rvalue.getId());
    assertEquals(lvalue.getName(), rvalue.getName());
    assertEquals(lvalue.getBirthdate(), rvalue.getBirthdate());
    assertEquals(lvalue.getCitizenship().toString(), rvalue.getCitizenship().toString());
    assertEquals(lvalue.getBirthplace(), rvalue.getBirthplace());
    assertEquals(lvalue.getImageUri(), rvalue.getImageUri());
    // assertEquallvaluet1.getLastUpdate(), albertoAngela.getLastUpdate());
    assertEquals(lvalue.getRemoteId(), rvalue.getRemoteId());
    assertEquals(lvalue.biography.getId(), rvalue.biography.getId());
    assertEquals(lvalue.biography.getBrief(), rvalue.biography.getBrief());
    assertEquals(lvalue.occupation.getId(), rvalue.occupation.getId());
    assertEquals(lvalue.occupation.getName(), rvalue.occupation.getName());
  }

  private void cmpTopics(Topic lvalue, Topic rvalue) {
    assertEquals(lvalue.getId(), rvalue.getId());
    assertEquals(lvalue.getDescriptionId(), rvalue.getDescriptionId());
    assertEquals(lvalue.getName(), rvalue.getName());
    assertEquals(lvalue.getDescription(), rvalue.getDescription());
  }

  // @! Run as final!

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

  private String escapeString(String val) {
    StringBuilder escaped = new StringBuilder();
    escaped.append("\"");
    escaped.append(val);
    escaped.append("\"");
    return escaped.toString();
  }

}
