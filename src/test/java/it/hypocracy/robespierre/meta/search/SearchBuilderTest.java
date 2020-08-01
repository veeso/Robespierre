/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SearchBuilderTest {

  private final static String text1 = "Elizabeth Woolridge Grant is known by her stage name Lana Del Rey.";
  private final static String text2 = "Violet Bent Backwards Over The Grass is the debut book by Lana Del Rey, including \"13 longer poems\" and several short pieces.";
  private final static String text3 = "\"Happy\" is a poem by Lana Del Rey";

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
    assertEquals("is", entities[1].getSearch());
    assertEquals("known", entities[2].getSearch());
    assertEquals("by", entities[3].getSearch());
    assertEquals("her", entities[4].getSearch());
    assertEquals("stage", entities[5].getSearch());
    assertEquals("name", entities[6].getSearch());
    assertEquals("Lana Del Rey", entities[7].getSearch());
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
     * for (SearchEntity e : entities) { System.out.println(e.getSearch()); }
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

}
