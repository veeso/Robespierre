/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.article;

import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.Uuidv4;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Subject {

  private String id; // UUIDv4
  protected String name;
  protected LocalDate birthdate;
  protected ISO3166 citizenship; // 2 chars
  protected String biography;
  private String remoteId; // Access id for metadata receiver
  private LocalDateTime lastUpdate; // Last information update
  public Category category;

  /**
   * <p>
   * Instantiates a new Subject
   * </p>
   * 
   * @param id
   * @param name
   * @param birthdate
   * @param citizenship
   * @param bio
   * @param remoteId
   * @param lastUpdate
   * @param category
   */

  public Subject(String id, String name, LocalDate birthdate, ISO3166 citizenship, String bio, String remoteId,
      LocalDateTime lastUpdate, Category category) {
    this.id = id;
    this.name = name;
    this.birthdate = birthdate;
    this.citizenship = citizenship;
    this.biography = bio;
    this.remoteId = remoteId;
    this.lastUpdate = lastUpdate;
    this.category = category;
  }

  /**
   * <p>
   * Instantiates a new subject (doesn't exist in the database)
   * </p>
   * 
   * @param name
   * @param birthdate
   * @param citizenship
   * @param bio
   * @param remoteId
   * @param category
   */

  public Subject(String name, LocalDate birthdate, ISO3166 citizenship, String bio, String remoteId, Category category) {
    this(new Uuidv4().getUUIDv4(), name, birthdate, citizenship, bio, remoteId, LocalDateTime.now(), category);
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

  public String getCitizenship() {
    return this.citizenship.toString();
  }

  public String getBiography() {
    return this.biography;
  }

  public String getRemoteId() {
    return this.remoteId;
  }

  public LocalDateTime getLastUpdate() {
    return this.lastUpdate;
  }

}
