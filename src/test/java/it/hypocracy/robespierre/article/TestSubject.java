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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import it.hypocracy.robespierre.utils.ISO3166;

public class TestSubject {
  /**
   * Check if a Subject is correctly instantiated
   */
  @Test
  public void shouldInstantiateCategoryNew() {
    Category cat = new Category("politician");
    ISO3166 citizenship = new ISO3166("US");
    Subject subject = new Subject("foo bar", LocalDate.of(1960, 5, 14), citizenship, "foo bar is a nice person", "123", cat);
    assertEquals(subject.getId().length(), 36);
    assertEquals(subject.getName(), "foo bar");
    assertEquals(subject.getBiography(), "foo bar is a nice person");
    assertEquals(subject.getBirthdate().getYear(), 1960);
    assertEquals(subject.getRemoteId(), "123");
    assertEquals(subject.getLastUpdate().getHour(), LocalDateTime.now().getHour());
    assertEquals(subject.getCitizenship(), citizenship.toString());
  }

  @Test
  public void shouldInstantiateCategory() {
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Category cat = new Category("politician");
    ISO3166 citizenship = new ISO3166("US");
    Subject subject = new Subject(testId, "foo bar", LocalDate.of(1960, 5, 14), citizenship, "foo bar is a nice person", "123", LocalDateTime.of(2020, 6, 28, 12, 45, 54), cat);
    assertEquals(subject.getId(), testId);
    assertEquals(subject.getName(), "foo bar");
    assertEquals(subject.getBiography(), "foo bar is a nice person");
    assertEquals(subject.getBirthdate().getYear(), 1960);
    assertEquals(subject.getRemoteId(), "123");
    assertEquals(subject.getLastUpdate().getMonth(), Month.JUNE);
    assertEquals(subject.getCitizenship(), citizenship.toString());
  }
}
