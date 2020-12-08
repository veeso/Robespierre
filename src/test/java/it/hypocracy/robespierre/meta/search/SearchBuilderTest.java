/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) 2020 Christian Visintin - christian.visintin1997@gmail.com
 *
 * This file is part of "Robespierre"
 *
 * Robespierre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robespierre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robespierre.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package it.hypocracy.robespierre.meta.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SearchBuilderTest {

  private final static String text1 = "Elizabeth Woolridge Grant is known by her stage name Lana Del Rey.";
  private final static String text2 = "Violet Bent Backwards Over The Grass is the debut book by Lana Del Rey, including \"13 longer poems\" and several short pieces.";
  private final static String text3 = "\"Happy\" is a poem by Lana Del Rey";
  private final static String text4 = "Maximilien Robespierre said «the secret of freedom lies in educating people, whereas the secret of tyranny is in keeping them ignorant»";

  @Test
  public void shouldBuildSearchEntitiesCase1() {
    SearchBuilder builder = new SearchBuilder();
    // Split text by words
    String[] words = text1.split("\\s+");
    assertEquals(12, words.length);
    SearchEntity[] entities = builder.buildSearchForSubjectsAndTopics(words);
    // Check entities expected ["Elizabeth Woolridge Grant", "is", "known", "by",
    // "her", "stage", "name", "Lana Del Rey"]
    // Check length
    assertEquals(8, entities.length);
    // Check search
    assertEquals("Elizabeth Woolridge Grant", entities[0].getSearch());
    assertEquals(SearchTarget.SUBJECT, entities[0].getTarget());
    assertEquals("is", entities[1].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[1].getTarget());
    assertEquals("known", entities[2].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[2].getTarget());
    assertEquals("by", entities[3].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[3].getTarget());
    assertEquals("her", entities[4].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[4].getTarget());
    assertEquals("stage", entities[5].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[5].getTarget());
    assertEquals("name", entities[6].getSearch());
    assertEquals(SearchTarget.TOPIC, entities[6].getTarget());
    assertEquals("Lana Del Rey", entities[7].getSearch());
    assertEquals(SearchTarget.SUBJECT, entities[7].getTarget());
  }

  @Test
  public void shouldBuildSearchEntitiesCase2() {
    SearchBuilder builder = new SearchBuilder();
    // Split text by words
    String[] words = text2.split("\\s+");
    assertEquals(22, words.length);
    SearchEntity[] entities = builder.buildSearchForSubjectsAndTopics(words);
    // Check entities expected ["Violet Bent Backwards Over The Grass", "is", "the",
    // "debut", "book", "by", "Lana Del Rey", "including", "13 longer poems", "and",
    // "several", "short", "pieces"]
    /*
     * for (SearchEntity e : entities) { System.out.println(e.getSearch()); }
     */
    // Check length
    assertEquals(13, entities.length);
    // Check search
    assertEquals("Violet Bent Backwards Over The Grass", entities[0].getSearch());
    assertEquals("is", entities[1].getSearch());
    assertEquals("the", entities[2].getSearch());
    assertEquals("debut", entities[3].getSearch());
    assertEquals("book", entities[4].getSearch());
    assertEquals("by", entities[5].getSearch());
    assertEquals("Lana Del Rey", entities[6].getSearch());
    assertEquals("including", entities[7].getSearch());
    assertEquals("13 longer poems", entities[8].getSearch());
    assertEquals("and", entities[9].getSearch());
    assertEquals("several", entities[10].getSearch());
    assertEquals("short", entities[11].getSearch());
    assertEquals("pieces", entities[12].getSearch());
  }

  @Test
  public void shouldBuildSearchEntitiesCase3() {
    SearchBuilder builder = new SearchBuilder();
    // Split text by words
    String[] words = text3.split("\\s+");
    assertEquals(8, words.length);
    SearchEntity[] entities = builder.buildSearchForSubjectsAndTopics(words);
    // Check entities expected ["Happy", "is", "a", "poem", "by", "Lana Del Rey"]
    /*
    for (SearchEntity e : entities) { System.out.println(e.getSearch()); }
    */ 

    // Check length
    assertEquals(6, entities.length);
    // Check search
    assertEquals("Happy", entities[0].getSearch());
    assertEquals("is", entities[1].getSearch());
    assertEquals("a", entities[2].getSearch());
    assertEquals("poem", entities[3].getSearch());
    assertEquals("by", entities[4].getSearch());
    assertEquals("Lana Del Rey", entities[5].getSearch());
  }
  
  @Test
  public void shouldBuildSearchEntitiesCase4() {
    SearchBuilder builder = new SearchBuilder();
    // Split text by words
    String[] words = text4.split("\\s+");
    assertEquals(21, words.length);
    SearchEntity[] entities = builder.buildSearchForSubjectsAndTopics(words);
    // Maximilien Robespierre said «the secret of freedom lies in educating people, whereas the secret of tyranny is in keeping them ignorant»
    // Check entities expected ["Maximilien Robespierre", "said", "the", "secret", "of", "freedom", "lies", "in", "educating", "people", "whereas", "the", "secret", "of", "tyranny", "is", "in", "keeping", "them", "ignorant"]
    /*
    for (SearchEntity e : entities) { System.out.println(e.getSearch()); }
    */ 

    // Check length
    assertEquals(20, entities.length);
    // Check search
    assertEquals("Maximilien Robespierre", entities[0].getSearch());
    assertEquals("said", entities[1].getSearch());
    assertEquals("the", entities[2].getSearch());
    assertEquals("secret", entities[3].getSearch());
    assertEquals("of", entities[4].getSearch());
    assertEquals("freedom", entities[5].getSearch());
    assertEquals("lies", entities[6].getSearch());
    assertEquals("in", entities[7].getSearch());
    assertEquals("educating", entities[8].getSearch());
    assertEquals("people", entities[9].getSearch());
    assertEquals("whereas", entities[10].getSearch());
    assertEquals("the", entities[11].getSearch());
    assertEquals("secret", entities[12].getSearch());
    assertEquals("of", entities[13].getSearch());
    assertEquals("tyranny", entities[14].getSearch());
    assertEquals("is", entities[15].getSearch());
    assertEquals("in", entities[16].getSearch());
    assertEquals("keeping", entities[17].getSearch());
    assertEquals("them", entities[18].getSearch());
    assertEquals("ignorant", entities[19].getSearch());
  }

}
