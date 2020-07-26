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

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;

import org.junit.Test;

public class ISO8601Test {
  
  @Test
  public void shouldParseIso8601AcBc() {
    String dateStr = "+1896-09-25T04:30:10Z";
    LocalDateTime tm = ISO8601.toLocalDateTime(dateStr);
    assertEquals(1896, tm.getYear());
    assertEquals(Month.SEPTEMBER, tm.getMonth());
    assertEquals(25, tm.getDayOfMonth());
    assertEquals(4, tm.getHour());
    assertEquals(30, tm.getMinute());
    assertEquals(10, tm.getSecond());
  }

  @Test
  public void shouldParseIso8601WithTimezone() {
    String dateStr = "1896-09-25T04:30:10+0200";
    LocalDateTime tm = ISO8601.toLocalDateTime(dateStr);
    assertEquals(1896, tm.getYear());
    assertEquals(Month.SEPTEMBER, tm.getMonth());
    assertEquals(25, tm.getDayOfMonth());
    assertEquals(4, tm.getHour());
    assertEquals(30, tm.getMinute());
    assertEquals(10, tm.getSecond());
  }

  @Test
  public void shouldFailParsingIso8601() {
    String dateStr = "+1896-25T04:30:10Z";
    assertThrows(DateTimeParseException.class, () -> ISO8601.toLocalDateTime(dateStr));
  }

}
