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

package it.hypocracy.robespierre.article;

import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.Uuidv4;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A subjects represents a human associated to an article. It contains all the information
 * described in the Hypocracy database.
 */

public class Subject {

  private String id; // UUIDv4
  protected String name;
  protected LocalDate birthdate;
  protected ISO3166 citizenship; // 2 chars or null
  protected String birthplace; // City or null
  protected String imageUri; // Can be null
  public SubjectBio biography;
  private String remoteId; // Access id for metadata receiver
  private LocalDateTime lastUpdate; // Last information update
  public Occupation occupation;

  /**
   * <p>
   * Instantiates a new Subject
   * </p>
   * 
   * @param id
   * @param name
   * @param birthdate
   * @param citizenship
   * @param birthplace
   * @param imageUri
   * @param bio
   * @param remoteId
   * @param lastUpdate
   * @param occupation
   */

  public Subject(String id, String name, LocalDate birthdate, ISO3166 citizenship, String birthplace, String imageUri,
      String bio, String remoteId, LocalDateTime lastUpdate, Occupation occupation) {
    this(id, name, birthdate, citizenship, birthplace, imageUri, new SubjectBio(bio), remoteId, lastUpdate, occupation);
  }

  /**
   * <p>
   * Instantiates a new Subject
   * </p>
   * 
   * @param id
   * @param name
   * @param birthdate
   * @param citizenship
   * @param birthplace
   * @param imageUri
   * @param bio
   * @param remoteId
   * @param lastUpdate
   * @param occupation
   */

  public Subject(String id, String name, LocalDate birthdate, ISO3166 citizenship, String birthplace, String imageUri,
      SubjectBio bio, String remoteId, LocalDateTime lastUpdate, Occupation occupation) {
    this.id = id;
    this.name = name;
    this.birthdate = birthdate;
    this.citizenship = citizenship;
    this.birthplace = birthplace;
    this.imageUri = imageUri;
    this.biography = bio;
    this.remoteId = remoteId;
    this.lastUpdate = lastUpdate;
    this.occupation = occupation;
  }

  /**
   * <p>
   * Instantiates a new subject (doesn't exist in the database)
   * </p>
   * 
   * @param name
   * @param birthdate
   * @param citizenship
   * @param birthplace
   * @param imageUri
   * @param bio
   * @param remoteId
   * @param occupation
   */

  public Subject(String name, LocalDate birthdate, ISO3166 citizenship, String birthplace, String imageUri, String bio,
      String remoteId, Occupation occupation) {
    this(new Uuidv4().getUUIDv4(), name, birthdate, citizenship, birthplace, imageUri, bio, remoteId,
        LocalDateTime.now(), occupation);
  }

  /**
   * <p>
   * Set id
   * </p>
   * 
   * @param id
   */

  public void setId(String id) {
    this.id = id;
  }

  // Getters

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public LocalDate getBirthdate() {
    return this.birthdate;
  }

  public ISO3166 getCitizenship() {
    return this.citizenship;
  }

  public String getBirthplace() {
    return this.birthplace;
  }

  public String getImageUri() {
    return this.imageUri;
  }

  public String getRemoteId() {
    return this.remoteId;
  }

  public LocalDateTime getLastUpdate() {
    return this.lastUpdate;
  }

}
