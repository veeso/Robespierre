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

package it.deskichup.robespierre.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

  @Test
  public void shouldConvertDateToLocalDateTime() {
    @SuppressWarnings("deprecation")
    Date testDate = new Date(120, 6, 13, 17, 32);
    LocalDateTime convertedTime = DateUtils.dateToLocalDateTime(testDate);
    assertEquals(13, convertedTime.getDayOfMonth());
    assertEquals(Month.JULY, convertedTime.getMonth());
    assertEquals(2020, convertedTime.getYear());
    assertEquals(17, convertedTime.getHour());
    assertEquals(32, convertedTime.getMinute());
  }

}
