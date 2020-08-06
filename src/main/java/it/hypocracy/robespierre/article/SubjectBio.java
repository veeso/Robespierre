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

import it.hypocracy.robespierre.utils.Uuidv4;

public class SubjectBio {

  private String id;
  private String brief;

  public SubjectBio(String id, String brief) {
    this.id = id;
    this.brief = brief;
  }

  public SubjectBio(String brief) {
    this(null, brief);
    // Generate UUID
    Uuidv4 uuid = new Uuidv4();
    this.id = uuid.getUUIDv4();
  }

  public String getId() {
    return this.id;
  }

  public String getBrief() {
    return this.brief;
  }

  public void setId(String id) {
    this.id = id;
  }

}
