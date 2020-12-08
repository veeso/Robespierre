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
