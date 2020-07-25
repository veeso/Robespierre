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
    ISO3166 language = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), language);
    //Verify constructor
    assertEquals(art.getId().length(), 36);
    assertEquals(art.getTitle(), "foo");
    assertEquals(art.getBrief(), "bar");
    assertEquals(art.getLink(), URI.create("http://news.com/all-dead-all-dead.html"));
    assertEquals(art.getDate().getMonth(), LocalDateTime.now().getMonth());
    assertEquals(art.getLanguage().toString(), language.toString());

    //Topics + subjects should be empty
    assertTrue(! art.iterSubjects().hasNext());
    assertTrue(! art.iterTopics().hasNext());
  }

  @Test
  public void shouldAddSubjects() {
    ISO3166 language = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), language);
    Category cat1 = new Category("politician");
    Subject subject1 = new Subject("foo bar", LocalDate.of(1960, 5, 14), new ISO3166("US"), "foo bar is a nice person", "123", cat1);
    art.addSubject(subject1);
    Category cat2 = new Category("influencer");
    Subject subject2 = new Subject("mr ping", LocalDate.of(1993, 8, 12), new ISO3166("GB"), "mr ping has many followers", "444", cat2);
    art.addSubject(subject2);
    assertTrue(art.iterSubjects().hasNext());
    //Iterate
    Iterator<Subject> it = art.iterSubjects();
    int idx = 0;
    while(it.hasNext()) {
      Subject next = it.next();
      if (idx == 0) {
        assertEquals(next.getName(), subject1.getName());
        assertEquals(next.category.getName(), cat1.getName());
      } else {
        assertEquals(next.getName(), subject2.getName());
        assertEquals(next.category.getName(), cat2.getName());
      }
      idx++;
    }
  }

  @Test
  public void shouldAddTopics() {
    ISO3166 language = new ISO3166("US");
    Article art = new Article("foo", "bar", URI.create("http://news.com/all-dead-all-dead.html"), LocalDateTime.now(), language);
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
        assertEquals(next.getName(), topic1.getName());
      } else {
        assertEquals(next.getName(), topic2.getName());
      }
      idx++;
    }
  }

}
