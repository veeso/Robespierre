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
