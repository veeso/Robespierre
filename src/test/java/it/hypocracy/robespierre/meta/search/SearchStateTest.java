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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class SearchStateTest {
  
  @Test
  public void shouldSetStates() {
    SearchState states = new SearchState();
    assertFalse(states.endsWithPunctuation);
    assertFalse(states.lastWordCapital);
    assertFalse(states.wordCapital);
    // Set true
    states.endsWithPunctuation = true;
    states.wordCapital = true;
    states.nextState();
    assertTrue(states.lastWordCapital);
    assertFalse(states.endsWithPunctuation);
    assertFalse(states.wordCapital);
    // Reset states
    states.endsWithPunctuation = true;
    states.wordCapital = true;
    states.lastWordCapital = true;
    states.resetState();
    assertFalse(states.endsWithPunctuation);
    assertFalse(states.lastWordCapital);
    assertFalse(states.wordCapital);
  }

}
