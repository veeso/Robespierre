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

package it.hypocracy.robespierre.article;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Unit test for Topic.
 */
public class TestTopic {
  /**
   * Check if a topic and its id are generated
   */
  @Test
  public void shouldInstantiateTopicWithAutoId() {
    Topic topic = new Topic("test", "this is a test topic");
    assertEquals(36, topic.getId().length());
    assertEquals("test", topic.getName());
    assertEquals("this is a test topic", topic.getDescription());
  }

  /**
   * Check if a topic is generated
   */
  @Test
  public void shouldInstantiateTopic() {
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Topic topic = new Topic(testId, "test", "this is a test topic");
    assertEquals(testId, topic.getId());
    assertEquals("test", topic.getName());
    assertEquals("this is a test topic", topic.getDescription());
  }

  @Test
  public void shouldSetId() {
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Topic topic = new Topic(testId, "test", "this is a test topic");
    assertEquals(testId, topic.getId());
    String newId = "d3cd6886-2b03-437f-bacf-73a6ac590dbf";
    topic.setId(newId);
    assertEquals(newId, topic.getId());
  }
}
