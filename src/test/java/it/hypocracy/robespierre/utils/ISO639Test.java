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
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ISO639Test {
  
  @Test
  public void shouldInstantiateISO639() {
    String code = "it";
    ISO639 iso = new ISO639(code);
    assertEquals(code, iso.toString());
  }

  @Test
  public void shouldInstantiateISO3166UpperCase() {
    String code = "FR";
    ISO639 iso = new ISO639(code);
    assertEquals(code.toLowerCase(), iso.toString());
  }

  @Test
  public void shouldFailISO3166() {
    String code = "XX";
    assertThrows(IllegalArgumentException.class, () -> new ISO639(code));
  }

}
