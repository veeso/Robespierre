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
