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
 * Unit test for WbProperty.
 */
public class TestCategory {
  /**
   * Check if a category is correctly instantiated
   */
  @Test
  public void shouldInstantiateWbProperty() {
    Occupation cat = new Occupation("politician");
    assertEquals("politician", cat.getName());
  }

}
