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

public class ISO639 {

  private static String[] codes = null;

  private String code;

  /**
   * </p>
   * Instantiate a new ISO639
   * </p>
   * 
   * @param code
   * @throws IllegalArgumentException
   */

  public ISO639(String code) throws IllegalArgumentException {
    // If codes is null, gather codes
    if (codes == null) {
      ISO639.fillCodes();
    }
    if (code == null) {
      throw new IllegalArgumentException("code is null");
    }
    if (checkCode(code)) {
      this.code = code.toLowerCase();
    } else {
      String err = "Invalid code: " + code;
      throw new IllegalArgumentException(err);
    }
  }

  /**
   * <p>
   * Create default ISO639
   * </p>
   */

  public ISO639() {
    // If codes is null, gather codes
    if (codes == null) {
      ISO639.fillCodes();
    }
    // Return default
    this.code = "en"; // English
  }

  /**
   * <p>
   * Convert ISO639 to string
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
    codes = Locale.getISOLanguages();
  }

  /**
   * <p>
   * Check if provided code is a valid ISO639
   * </p>
   * 
   * @param code
   * @return true if is valid
   */

  private boolean checkCode(String code) {
    String check = code.toLowerCase();
    for (String c : codes) {
      if (check.equals(c)) {
        return true;
      }
    }
    return false;
  }

}
