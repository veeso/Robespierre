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
