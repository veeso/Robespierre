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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class MySqlDateTime {

  /**
   * <p>
   * Convert a MySqlDateTime string to LocalDateTime
   * </p>
   * 
   * @param dateStr
   * @return LocalDateTime
   * @throws DateTimeParseException
   */

  public static LocalDateTime parse(String dateStr) throws DateTimeParseException {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendPattern("uuuu-MM-dd HH:mm:ss")
        .appendPattern("[.SSSSSSSSS][.SSSSSS][.SSS][.SS][.S]")
        // create formatter
        .toFormatter();
    return LocalDateTime.parse(dateStr, formatter);
  }

}
