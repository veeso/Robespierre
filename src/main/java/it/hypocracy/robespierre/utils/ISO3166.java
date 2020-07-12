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

import java.util.Locale;

public class ISO3166 {

  private static String[] codes = null;

  private String code;

  /**
   * </p>
   * Instantiate a new ISO3166
   * </p>
   * 
   * @param code
   * @throws IllegalArgumentException
   */

  public ISO3166(String code) throws IllegalArgumentException {
    // If codes is null, gather codes
    if (codes == null) {
      ISO3166.fillCodes();
    }
    if (checkCode(code)) {
      this.code = code.toUpperCase();
    } else {
      String err = "Invalid code: " + code;
      throw new IllegalArgumentException(err);
    }
  }

  /**
   * <p>
   * Convert ISO3166 to string
   * </p>
   * 
   * @return String
   */
  @Override
  public String toString() {
    return this.code;
  }

  /**
   * <p>
   * Fill codes array
   * </p>
   */

  private static void fillCodes() {
    codes = Locale.getISOCountries();
  }

  /**
   * <p>
   * Check if provided code is a valid ISO3166
   * </p>
   * 
   * @param code
   * @return true if is valid
   */

  private boolean checkCode(String code) {
    String check = code.toUpperCase();
    for (String c : codes) {
      if (check.equals(c)) {
        return true;
      }
    }
    return false;
  }

}
