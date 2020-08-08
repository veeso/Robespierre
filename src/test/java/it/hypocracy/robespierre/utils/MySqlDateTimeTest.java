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

public class MySqlDateTimeTest {
  
  @Test
  public void shouldParseMySqlDateTime() {
    String dateStr = "2020-08-08 15:31:47.0";
    LocalDateTime tm = MySqlDateTime.toLocalDateTime(dateStr);
    assertEquals(2020, tm.getYear());
    assertEquals(Month.AUGUST, tm.getMonth());
    assertEquals(8, tm.getDayOfMonth());
    assertEquals(15, tm.getHour());
    assertEquals(31, tm.getMinute());
    assertEquals(47, tm.getSecond());
  }

  @Test
  public void shouldFailParsingMySqlDateTime() {
    String dateStr = "+1896-25T04:30:10Z";
    assertThrows(DateTimeParseException.class, () -> ISO8601.toLocalDateTime(dateStr));
  }

}
