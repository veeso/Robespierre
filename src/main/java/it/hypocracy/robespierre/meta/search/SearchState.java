/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
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
