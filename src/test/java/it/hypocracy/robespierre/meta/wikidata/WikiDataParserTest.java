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
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.meta.search.SearchTarget;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;
import it.hypocracy.robespierre.utils.ISO3166;

public class WikiDataParserTest {

  private static WbEntity albertoAngela = null;
  private static WbEntity veganism = null;
  private static WbEntity france = null;

  private static Article testArticle = null;

  @BeforeClass
  public static void prerunGetAlbertoAngela() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    ISO3166 country = new ISO3166("IT");
    albertoAngela = cli.getWebEntity("Q514695", country);
  }

  @BeforeClass
  public static void prerunGetVeganism() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    ISO3166 country = new ISO3166("IT");
    veganism = cli.getWebEntity("Q181138", country);
  }

  @BeforeClass
  public static void prerunGetSample() throws MetadataReceiverException {
    WikiDataApiClient cli = new WikiDataApiClient();
    ISO3166 country = new ISO3166("IT");
    france = cli.getWebEntity("Q142", country);
  }

  @BeforeClass
  public static void prerunPrepareArticle() {
    ISO3166 lang = new ISO3166("IT");
    testArticle = new Article("Alberto Angela said veganism is ok", "He said he likes vegetables",
        URI.create("https://example.com/news/alberto_angela_vegan"), LocalDateTime.now(), lang);
  }

  @Test
  public void shouldParseSubject() throws ParserException, MetadataReceiverException {
    WikiDataParser parser = new WikiDataParser();
    // Parse subject
    assertTrue(
        parser.parseWbEntity(albertoAngela, "Q514695", testArticle, new SearchTarget[] { SearchTarget.SUBJECT }));
    // Verify subject
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
  }

  @Test
  public void shouldParseTopic() throws ParserException, MetadataReceiverException {
    WikiDataParser parser = new WikiDataParser();
    // Parse subject
    assertTrue(parser.parseWbEntity(veganism, "Q181138", testArticle, new SearchTarget[] { SearchTarget.TOPIC }));
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
  public void shouldNotReturnAnyChange() throws ParserException, MetadataReceiverException {
    WikiDataParser parser = new WikiDataParser();
    assertFalse(parser.parseWbEntity(france, "Q142", testArticle, new SearchTarget[] { SearchTarget.SUBJECT }));
  }

}
