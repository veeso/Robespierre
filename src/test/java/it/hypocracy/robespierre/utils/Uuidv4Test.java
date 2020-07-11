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
