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

import java.util.LinkedList;
import java.util.ListIterator;

public class SearchBuilder {

  private final static String[] punctuationMarks = { "!", "?", ".", ",", ";", ":", "(", ")", "[", "]", "{", "}", "¿",
      "¡" };

  public SearchBuilder() {

  }

  /**
   * <p>
   * Build search entity starting from text following these rules:
   * 
   * - 0. Is the last word
   * - 1. Any punctuation marks in ['!', '?', ',', ';', '.', ':', '(', ')', '[',
   * ']', '{', '}', '¿', '¡'] represents a QE terminator 
   * - 2. If a word begins with
   * capital letter, the terminator for the QE is the first following word which
   * starts with lowercase letter or according to rule 1 
   * - 3. If a word begins with a lowercase letter, QE will be the word itself
   * - 4. Quoted texts are QE itself
   * 
   * </p>
   * 
   * @param text
   * @return
   */

  public SearchEntity[] buildSearchForSubjectsAndTopics(String[] words) {
    // Instantiate list of Search Entity
    LinkedList<SearchEntity> entities = new LinkedList<>();
    // Prepare states
    LinkedList<String> buffer = new LinkedList<String>(); // Initialize buffer
    SearchState states = new SearchState();
    // Iterate over words
    int idx = 0;
    while (idx < words.length) {
      String word = words[idx];
      // Initialize states
      states.nextState();
      // Check quotes
      if (word.startsWith("\"")) {
        if (word.length() > 1) {
          word = word.substring(1); // Remove quote from word
        }
        states.quoted = true;
      } 
      if (word.endsWith("\"")) {
        word = word.substring(0, word.length() - 1); // Remove quote from word
        states.wasQuoted = true;
        states.quoted = false;
      }
      if (! states.quoted) { // If not quoted
        // Check punctuation mark
        if (endsWithPunctuationMark(word)) {
          // Remove punctuation mark from word
          word = removePunctuationMarkFromWord(word);
          // Set state
          states.endsWithPunctuation = true;
        }
        // Check capital letter
        if (hasCapitalLetter(word) && !isUppercase(word)) {
          // Set state
          states.wordCapital = true;
        } else {
          // Set state
          states.wordCapital = false;
        }
      }
      // @! Finalize states

      // Create QE if...
      if (! states.wasQuoted && ! states.quoted) {
        if (states.lastWordCapital && !states.wordCapital) { // Rule 2 (Subject)
          // First create entitiy
          entities.push(new SearchEntity(bufferToString(buffer)));
          // Reset buffer
          buffer.clear();
          // Reset state
          states.resetState();
          // NOTE: don't push word to buffer and don't increment index; we need to
          // re-iterate over this word (with updated states though)
        } else if (idx + 1 == words.length) { // Rule 0
          // Push word to buffer
          buffer.push(word);
          // First create entitiy
          entities.push(new SearchEntity(bufferToString(buffer)));
          // Reset buffer
          buffer.clear();
          // Reset state
          states.resetState();
          // Increment index
          idx++;
        } else if (states.endsWithPunctuation || idx + 1 == words.length) { // Rule 1
          // Push word to buffer
          buffer.push(word);
          // First create entitiy
          entities.push(new SearchEntity(bufferToString(buffer)));
          // Reset buffer
          buffer.clear();
          // Reset state
          states.resetState();
          // Increment index
          idx++;
        } else if (!states.wordCapital) { // Rule 3 (Topics)
          // Push word to buffer
          buffer.push(word);
          // First create entitiy
          entities.push(new SearchEntity(bufferToString(buffer)));
          // Reset buffer
          buffer.clear();
          // Reset state
          states.resetState();
          // Increment index
          idx++;
        } else { // Out of rule
          // Push word to buffer
          buffer.push(word);
          // Increment index
          idx++;
        }
      }
      // Rule 4 (quotes)
      if (states.wasQuoted && ! states.quoted) {
        // Create entity
        // Push word to buffer
        buffer.push(word);
        // First create entitiy
        entities.push(new SearchEntity(bufferToString(buffer)));
        // Reset buffer
        buffer.clear();
        // Reset state
        states.resetState();
        // Increment index
        idx++;
      } else if (states.quoted) {
        // Push word to buffer
        buffer.push(word);
        // Increment index
        idx++;
      }
    }

    // @! Return entities
    SearchEntity[] entitiesArray = new SearchEntity[entities.size()];
    ListIterator<SearchEntity> it = entities.listIterator(entities.size());
    for (int i = 0; i < entities.size() && it.hasPrevious(); i++) {
      entitiesArray[i] = it.previous();
    }
    return entitiesArray;
  }

  /**
   * <p>
   * Returns whether a certain word ends with a punctuation mark
   * </p>
   * 
   * @param word
   * @return
   */

  private boolean endsWithPunctuationMark(String word) {
    for (String p : punctuationMarks) {
      if (word.endsWith(p)) {
        return true;
      }
    }
    return false;
  }

  /**
   * <p>
   * Returns whether a word is uppercase
   * </p>
   * 
   * @param word
   * @return boolean
   */

  private boolean isUppercase(String word) {
    return word.toUpperCase().equals(word);
  }

  /**
   * <p>
   * Returns whether a certain string begins with capital letter
   * </p>
   * 
   * @param word
   * @return boolean
   */

  private boolean hasCapitalLetter(String word) {
    String first = word.substring(0, 1);
    return first.toUpperCase().equals(first);
  }

  /**
   * <p>
   * Remove punctuation word from end of word
   * </p>
   * 
   * @param word
   * @return string
   */

  private String removePunctuationMarkFromWord(String word) {
    while (endsWithPunctuationMark(word)) {
      word = word.substring(0, word.length() - 1);
    }
    return word;
  }

  /**
   * <p>
   * Join buffer elements into a string
   * </p>
   * 
   * @param buffer
   * @return String
   */

  private String bufferToString(LinkedList<String> buffer) {
    StringBuilder result = new StringBuilder();
    ListIterator<String> it = buffer.listIterator(buffer.size());
    while (it.hasPrevious()) {
      // Push w
      result.append(it.previous());
      // If has next, append ' '
      if (it.hasPrevious()) {
        result.append(' ');
      }
    }
    return result.toString();
  }

}
