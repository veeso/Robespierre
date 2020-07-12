/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for UUIDv4.
 */
public class Uuidv4Test {
  /**
   * Check if a uuidv4 is generated
   */
  @Test
  public void shouldGenerateUUID() {
    //UUIDv4 has length 36
    assertEquals(new Uuidv4().getUUIDv4().length(), 36);
  }
}
