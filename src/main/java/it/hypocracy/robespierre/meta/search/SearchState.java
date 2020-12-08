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

package it.hypocracy.robespierre.meta.search;

/**
 * SearchState is the machine state used by the SearchBuilder to find targets
 */

public class SearchState {

  public boolean lastWordCapital; // Last word began with capital letter
  public boolean wordCapital;
  public boolean endsWithPunctuation;
  public boolean quoted;
  public boolean wasQuoted;

  public SearchState() {
    this.lastWordCapital = false;
    this.wordCapital = false;
    this.endsWithPunctuation = false;
    this.quoted = false;
    this.wasQuoted = false;
  }

  /**
   * <p>
   * Reset all states
   * </p>
   */

  public void resetState() {
    this.lastWordCapital = false;
    this.wordCapital = false;
    this.endsWithPunctuation = false;
    this.quoted = false;
    this.wasQuoted = false;
  }

  /**
   * <p>
   * Enter in 'next' cycle state
   * </p>
   */

  public void nextState() {
    this.lastWordCapital = this.wordCapital;
    this.wordCapital = false;
    this.endsWithPunctuation = false;
  }

}
