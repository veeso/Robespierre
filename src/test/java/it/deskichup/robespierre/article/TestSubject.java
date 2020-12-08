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

package it.deskichup.robespierre.article;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import it.deskichup.robespierre.utils.ISO3166;

public class TestSubject {
  /**
   * Check if a Subject is correctly instantiated
   */
  @Test
  public void shouldInstantiateCategoryNew() {
    Occupation cat = new Occupation("politician");
    ISO3166 citizenship = new ISO3166("US");
    Subject subject = new Subject("foo bar", LocalDate.of(1960, 5, 14), citizenship, "footown", "img.gif", "foo bar is a nice person", "123", cat);
    assertEquals(36, subject.getId().length());
    assertEquals("foo bar", subject.getName());
    assertEquals("foo bar is a nice person", subject.biography.getBrief());
    assertEquals(36, subject.biography.getId().length());
    assertEquals(1960, subject.getBirthdate().getYear());
    assertEquals("123", subject.getRemoteId());
    assertEquals("footown", subject.getBirthplace());
    assertEquals("img.gif", subject.getImageUri());
    assertEquals(LocalDateTime.now().getHour(), subject.getLastUpdate().getHour());
    assertEquals(citizenship.toString(), subject.getCitizenship().toString());
    assertEquals("politician", subject.occupation.getName());
    assertEquals(36, subject.occupation.getId().length());
  }

  @Test
  public void shouldInstantiateCategory() {
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Occupation cat = new Occupation("politician");
    ISO3166 citizenship = new ISO3166("US");
    Subject subject = new Subject(testId, "foo bar", LocalDate.of(1960, 5, 14), citizenship, "footown", "img.gif", "foo bar is a nice person", "123", LocalDateTime.of(2020, 6, 28, 12, 45, 54), cat);
    assertEquals(testId, subject.getId());
    assertEquals("foo bar", subject.getName());
    assertEquals("foo bar is a nice person", subject.biography.getBrief());
    assertEquals(1960, subject.getBirthdate().getYear());
    assertEquals("123", subject.getRemoteId());
    assertEquals(Month.JUNE, subject.getLastUpdate().getMonth());
    assertEquals(citizenship.toString(), subject.getCitizenship().toString());
    assertEquals("politician", subject.occupation.getName());
  }

  @Test
  public void shouldSetId() {
    Occupation cat = new Occupation("politician");
    ISO3166 citizenship = new ISO3166("US");
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Subject subject = new Subject(testId, "foo bar", LocalDate.of(1960, 5, 14), citizenship, "footown", "img.gif", "foo bar is a nice person", "123", LocalDateTime.of(2020, 6, 28, 12, 45, 54), cat);
    assertEquals(testId, subject.getId());
    String newId = "0a3e7b1d-9cd5-46e3-8609-1bddcbd78f54";
    subject.setId(newId);
    assertEquals(newId, subject.getId());
  }
}
