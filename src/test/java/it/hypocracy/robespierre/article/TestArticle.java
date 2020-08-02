/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;

import org.junit.Test;

import it.hypocracy.robespierre.utils.ISO3166;

public class TestArticle {
  
  @Test
  public void shouldInstantiateArticle() {
    ISO3166 country = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), country);
    //Verify constructor
    assertEquals(36, art.getId().length());
    assertEquals("foo", art.getTitle());
    assertEquals("bar", art.getBrief());
    assertEquals(URI.create("http://news.com/all-dead-all-dead.html"), art.getLink());
    assertEquals(LocalDateTime.now().getMonth(), art.getDate().getMonth());
    assertEquals(country.toString(), art.getCountry().toString());

    //Topics + subjects should be empty
    assertTrue(! art.iterSubjects().hasNext());
    assertTrue(! art.iterTopics().hasNext());
  }

  @Test
  public void shouldAddSubjects() {
    ISO3166 country = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), country);
    Occupation cat1 = new Occupation("politician");
    Subject subject1 = new Subject("foo bar", LocalDate.of(1960, 5, 14), new ISO3166("US"), "Footown", "myimg.gif", "foo bar is a nice person", "123", cat1);
    art.addSubject(subject1);
    Occupation cat2 = new Occupation("influencer");
    Subject subject2 = new Subject("mr ping", LocalDate.of(1993, 8, 12), new ISO3166("GB"), "Footown", "myimg.gif", "mr ping has many followers", "444", cat2);
    art.addSubject(subject2);
    assertTrue(art.iterSubjects().hasNext());
    //Iterate
    Iterator<Subject> it = art.iterSubjects();
    int idx = 0;
    while(it.hasNext()) {
      Subject next = it.next();
      if (idx == 0) {
        assertEquals(subject1.getName(), next.getName());
        assertEquals(cat1.getName(), next.occupation.getName());
      } else {
        assertEquals(subject2.getName(), next.getName());
        assertEquals(cat2.getName(), next.occupation.getName());
      }
      idx++;
    }
  }

  @Test
  public void shouldAddTopics() {
    ISO3166 country = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), country);
    Topic topic1 = new Topic("test", "this is a test topic");
    art.addTopic(topic1);
    Topic topic2 = new Topic("test 2", "this is a test topic");
    art.addTopic(topic2);
    assertTrue(art.iterTopics().hasNext());

    //Iterate
    Iterator<Topic> it = art.iterTopics();
    int idx = 0;
    while(it.hasNext()) {
      Topic next = it.next();
      if (idx == 0) {
        assertEquals(topic1.getName(), next.getName());
      } else {
        assertEquals(topic2.getName(), next.getName());
      }
      idx++;
    }
  }

}
