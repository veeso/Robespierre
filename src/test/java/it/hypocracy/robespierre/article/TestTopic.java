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
    assertEquals(topic.getId().length(), 36);
    assertEquals(topic.getName(), "test");
    assertEquals(topic.getDescription(), "this is a test topic");
  }

  /**
   * Check if a topic is generated
   */
  @Test
  public void shouldInstantiateTopic() {
    String testId = "995b8b98-2103-4873-8457-c2d7436170c5";
    Topic topic = new Topic(testId, "test", "this is a test topic");
    assertEquals(topic.getId(), testId);
    assertEquals(topic.getName(), "test");
    assertEquals(topic.getDescription(), "this is a test topic");
  }
}
