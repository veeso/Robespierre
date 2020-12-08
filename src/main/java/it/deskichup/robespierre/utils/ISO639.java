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
