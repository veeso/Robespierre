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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

  /**
   * <p>
   * Convert a java.util.date to a LocalDateTime
   * </p>
   * 
   * @param dt
   * @return LocalDateTime
   */

  public static LocalDateTime dateToLocalDateTime(Date dt) {
    return dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

}
