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
    assertEquals(convertedTime.getDayOfMonth(), 13);
    assertEquals(convertedTime.getMonth(), Month.JULY);
    assertEquals(convertedTime.getYear(), 2020);
    assertEquals(convertedTime.getHour(), 17);
    assertEquals(convertedTime.getMinute(), 32);
  }

}