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

package it.deskichup.robespierre.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.article.Subject;
import it.deskichup.robespierre.article.Topic;
import it.deskichup.robespierre.feed.Feed;
import it.deskichup.robespierre.feed.RSSFeed;
import it.deskichup.robespierre.meta.exceptions.CacheException;
import it.deskichup.robespierre.meta.exceptions.MetadataReceiverException;
import it.deskichup.robespierre.meta.exceptions.ParserException;
import it.deskichup.robespierre.meta.wikidata.WikiDataReceiver;
import it.deskichup.robespierre.utils.ISO3166;

public class ArticleAssemblerTest {

  private static Feed testFeed;

  @BeforeClass
  public static void prepareFeed() {
    ISO3166 lang = new ISO3166("IT");
    LocalDateTime date = LocalDateTime.of(2020, Month.AUGUST, 2, 16, 26, 32);
    testFeed = new RSSFeed("Alberto Angela è amico dei vegani", "Il paleontologo ha detto di essere pro veganismo",
        URI.create("https://example.com/news/alberto_angela_vegan"), date, lang);
  }

  @Test
  public void shouldAssembleArticle() throws MetadataReceiverException, CacheException, ParserException {
    // Prepare assembler (with WikiDataReceiver)
    ArticleAssembler assembler = new ArticleAssembler(new WikiDataReceiver());
    // Get article
    Article article = assembler.assemble(testFeed);
    // Verify Article
    assertEquals("Alberto Angela è amico dei vegani", article.getTitle());
    assertEquals("Il paleontologo ha detto di essere pro veganismo", article.getBrief());
    assertEquals(2020, article.getDate().getYear());
    assertEquals(Month.AUGUST, article.getDate().getMonth());
    assertEquals(2, article.getDate().getDayOfMonth());
    assertEquals(16, article.getDate().getHour());
    assertEquals(26, article.getDate().getMinute());
    assertEquals(32, article.getDate().getSecond());
    assertEquals(URI.create("https://example.com/news/alberto_angela_vegan"), article.getLink());
    assertEquals("IT", article.getCountry().toString());
    // Verify subjects
    // Verify article subjects
    Iterator<Subject> subjects = article.iterSubjects();
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
    Iterator<Topic> topics = article.iterTopics();
    assertTrue(topics.hasNext());
    Topic topic = topics.next();
    assertEquals("veganismo", topic.getName());
    assertEquals("stile di vita che tende ad escludere ogni forma di sfruttamento animale", topic.getDescription());
    // There shouldn't be any other topic
    assertFalse(topics.hasNext());
  }

}
