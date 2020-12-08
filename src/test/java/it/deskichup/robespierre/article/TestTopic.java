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
