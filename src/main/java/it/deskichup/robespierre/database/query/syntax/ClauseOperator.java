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

package it.deskichup.robespierre.database.query.syntax;

/**
 * This enum describes the operators for where statements
 */

public enum ClauseOperator {

  EQUAL, NOT_EQUAL, LIKE, BIGGER_THAN, BIGGER_EQUAL, LESS_THAN, LESS_EQUAL;

  /**
   * <p>
   * Convert operator to String
   * </p>
   * 
   * @return String
   */

  public String toString() {
    switch (this) {
      case EQUAL:
        return "=";
      case NOT_EQUAL:
        return "!=";
      case LIKE:
        return "LIKE";
      case BIGGER_EQUAL:
        return ">=";
      case BIGGER_THAN:
        return ">";
      case LESS_EQUAL:
        return "<=";
      case LESS_THAN:
        return "<";
    }
    return null;
  }

}
