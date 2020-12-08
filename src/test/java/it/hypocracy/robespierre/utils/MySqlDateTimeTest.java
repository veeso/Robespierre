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

public class MySqlDateTimeTest {
  
  @Test
  public void shouldParseMySqlDateTime() {
    String dateStr = "2020-08-08 15:31:47";
    LocalDateTime tm = MySqlDateTime.parse(dateStr);
    assertEquals(2020, tm.getYear());
    assertEquals(Month.AUGUST, tm.getMonth());
    assertEquals(8, tm.getDayOfMonth());
    assertEquals(15, tm.getHour());
    assertEquals(31, tm.getMinute());
    assertEquals(47, tm.getSecond());
  }

  @Test
  public void shouldParseMySqlDateTimeWithMillis() {
    String dateStr = "2020-08-08 15:31:47.321767";
    LocalDateTime tm = MySqlDateTime.parse(dateStr);
    assertEquals(2020, tm.getYear());
    assertEquals(Month.AUGUST, tm.getMonth());
    assertEquals(8, tm.getDayOfMonth());
    assertEquals(15, tm.getHour());
    assertEquals(31, tm.getMinute());
    assertEquals(47, tm.getSecond());
    assertEquals(321767000, tm.getNano());
  }

  @Test
  public void shouldFailParsingMySqlDateTime() {
    String dateStr = "+1896-25T04:30:10Z";
    assertThrows(DateTimeParseException.class, () -> MySqlDateTime.parse(dateStr));
  }

}
