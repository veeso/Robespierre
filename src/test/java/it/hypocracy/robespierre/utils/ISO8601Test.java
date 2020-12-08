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
    LocalDateTime tm = ISO8601.parse(dateStr);
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
    LocalDateTime tm = ISO8601.parse(dateStr);
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
    assertThrows(DateTimeParseException.class, () -> ISO8601.parse(dateStr));
  }

}
