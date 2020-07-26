/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.meta.wikidata;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.meta.exceptions.MetadataReceiverException;
import it.hypocracy.robespierre.meta.exceptions.ParserException;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Category;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Datavalue;
import it.hypocracy.robespierre.meta.wikidata.wbentity.DatavalueVal;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Entity;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Label;
import it.hypocracy.robespierre.meta.wikidata.wbentity.Mainsnak;
import it.hypocracy.robespierre.meta.wikidata.wbentity.WbEntity;
import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.ISO8601;

public class WikiDataParser {

  private WikiDataApiClient apiClient;
  private static ArrayList<String> topicsTable = null;

  // Properties
  private static final String properyInstanceOf = "P31";
  private static final String propertyBirthdate = "P569";
  private static final String propertyCitizenship = "P27";
  private static final String propertyImage = "P18";
  private static final String propertyIso3166 = "P297";

  // IDs
  private static final String idHuman = "Q5";
  private static final String idIdeology = "Q7257";
  private static final String idLifestyle = "Q32090";
  private static final String idSocialMovement = "Q49773";
  private static final String idPhilosophicalViewpoint = "Q30046560";
  private static final String idPoliticalIdeology = "Q12909644";
  private static final String idWorldView = "Q49447";
  private static final String idSocialFormation = "Q1641112";
  private static final String idPoliticalSystem = "Q28108";
  private static final String idExtremism = "Q466439";
  private static final String idFormOfGovernment = "Q1307214";
  private static final String idGenocide = "Q41397";
  private static final String idPoliticalPhylosophy = "Q179805";
  private static final String idEconomicIdeology = "Q179805";

  /**
   * <p>
   * Instantiate a new WikiDataParser
   * </p>
   */

  public WikiDataParser() {
    this.apiClient = new WikiDataApiClient();
    if (topicsTable == null) {
      fillTopicsLookupTable();
    }
  }

  /**
   * <p>
   * Parse wb entity and fill article data if valid
   * </p>
   * 
   * @param wbEntity
   * @param id
   * @param article
   * @return true if data has changed article
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  public boolean parseWbEntity(WbEntity wbEntity, String id, Article article) throws ParserException, MetadataReceiverException {
    // Verify web entity
    Entity entity = wbEntity.entities.get(id);
    if (entity == null) {
      return false;
    }
    // Check if it is human
    if (isWbEntityHuman(entity)) {
      // Instantiate a subject from Human
      Subject articleSubject = wbEntityToSubject(entity, article.getLanguage());
      // If subject is null, return false
      if (articleSubject == null) {
        return false;
      }
      // Check if subject is duplicated
      Iterator<Subject> subjects = article.iterSubjects();
      if (!isSubjectDuped(subjects, articleSubject)) {
        // If not duped, add to article subjects
        article.addSubject(articleSubject);
      }
      return true; // Return true anyway
    } else if (isWbEntityTopic(entity)) {
      // Retrieve topic parameters
      Topic articleTopic = wbEntityToTopic(entity, article.getLanguage());
      // If topic is null, return false
      if (articleTopic == null) {
        return false;
      }
      // Check if topic is duplicated
      Iterator<Topic> topics = article.iterTopics();
      if (!isTopicDuped(topics, articleTopic)) {
        // If not duped, add to article topics
        article.addTopic(articleTopic);
      }
      return true; // Return true anyway
    } else {
      return false;
    }
  }

  // @! Humans

  /**
   * <p>
   * Checks whether a WbEntity belongs to a human
   * </p>
   * 
   * @param entity
   * @param id
   * @return boolean
   */

  private boolean isWbEntityHuman(Entity entity) {
    if (!entity.claims.containsKey(properyInstanceOf)) {
      return false;
    }
    ArrayList<Category> p31 = entity.claims.get(properyInstanceOf);
    // Iterate over p31
    Iterator<Category> p31Iterator = p31.iterator();
    while (p31Iterator.hasNext()) {
      Category category = p31Iterator.next();
      if (category.mainsnak == null) {
        continue;
      }
      if (category.mainsnak.datavalue == null) {
        continue;
      }
      Datavalue datavalue = category.mainsnak.datavalue;
      if (!datavalue.type.equals("wikibase-entityid")) {
        continue;
      }
      if (datavalue.value == null) {
        continue;
      }
      // Verify id
      if (datavalue.value.id == null) {
        continue;
      }
      // If value is Q5 return true
      if (datavalue.value.id.equals(idHuman)) {
        return true;
      }
    }
    return false;
  }

  /**
   * <p>
   * Convert a wbEntity to Subject
   * </p>
   * 
   * @param entity
   * @param country
   * @return Subject
   * @throws ParserException
   */

  private Subject wbEntityToSubject(Entity entity, ISO3166 country) throws ParserException, MetadataReceiverException {
    // We need to retrieve this arguments:
    // - name
    // - birthdate
    // - Citizenship
    // - Brief
    // - Remote ID (entity.title)
    // - Category

    final String language = country.toString().toLowerCase();
    // Collect name from labels
    Label label = entity.labels.get(language);
    if (label == null) {
      throw new ParserException("Label is null");
    }
    String name = label.value;
    // Get birthdate
    LocalDateTime birthdate = getBirthdate(entity);
    // Get citizenship
    ISO3166 citizenship = getCitizenship(entity, country);
    // Set remoteid
    String remoteId = entity.title;

  }

  /**
   * <p>
   * Retrieve birthdate from entity
   * </p>
   * 
   * @param entity
   * @return LocalDateTime or null
   */

  private LocalDateTime getBirthdate(Entity entity)  throws ParserException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyBirthdate, 0);
    if (datavalue == null) {
      return null;
    }
    if (datavalue.type == null) {
      return null;
    }
    if (datavalue.type.equals("time")) {
      DatavalueVal value = datavalue.value;
      if (value == null) {
        return null;
      }
      if (value.time == null) {
        return null;
      }
      // Parse ISO8601
      try {
        return ISO8601.toLocalDateTime(value.time);
      } catch (DateTimeParseException ex) {
        return null;
      }
    } else {
      return null;
    }
  }

  /**
   * <p>
   * Retrieve citizenship from entity
   * </p>
   * 
   * @param entity
   * @param country
   * @return
   */

  private ISO3166 getCitizenship(Entity entity, ISO3166 country) throws ParserException, MetadataReceiverException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyCitizenship, 0);
    if (datavalue == null) {
      return null;
    }
    if (datavalue.type == null) {
      return null;
    }
    if (!datavalue.type.equals("wikibase-entityid")) {
      return null;
    }
    if (datavalue.value == null) {
      return null;
    }
    if (datavalue.value.id == null) {
      return null;
    }
    // Send wbentity query
    WbEntity countryQuery = this.apiClient.getWebEntity(datavalue.value.id, country);
    Entity countryEntity = countryQuery.entities.get(datavalue.value.id);
    if (countryEntity == null) {
      return null;
    }
    Datavalue countryDatavalue = getDatavalueFromEntity(entity, propertyIso3166, 0);
    if (countryDatavalue == null) {
      return null;
    }
    if (countryDatavalue.type == null) {
      return null;
    }
    if (!countryDatavalue.type.equals("string")) {
    return null;
    }
    if (countryDatavalue.value == null) {
      return null;
    }
    return new ISO3166(countryDatavalue.value.value);
  }

  /**
   * <p>
   * Verify whethers a subject is already present in article subjects
   * </p>
   * 
   * @param subjects
   * @param newSubject
   * @return boolean
   */

  private boolean isSubjectDuped(Iterator<Subject> subjects, Subject newSubject) {
    while (subjects.hasNext()) {
      Subject thisSubject = subjects.next();
      if (thisSubject.getName().equals(newSubject.getName())) {
        return true;
      }
    }
    return false;
  }

  // @! Topics

  /**
   * <p>
   * Returns whether provided Entity is a topic looking up the topics table
   * </p>
   * 
   * @param entity
   * @return boolean
   */

  private boolean isWbEntityTopic(Entity entity) throws ParserException {
    if (!entity.claims.containsKey(properyInstanceOf)) {
      return false;
    }
    ArrayList<Category> p31 = entity.claims.get(properyInstanceOf);
    // Iterate over p31
    Iterator<Category> p31Iterator = p31.iterator();
    while (p31Iterator.hasNext()) {
      Category category = p31Iterator.next();
      if (category.mainsnak == null) {
        continue;
      }
      if (category.mainsnak.datavalue == null) {
        continue;
      }
      Datavalue datavalue = category.mainsnak.datavalue;
      if (!datavalue.type.equals("wikibase-entityid")) {
        continue;
      }
      if (datavalue.value == null) {
        continue;
      }
      // Verify id
      if (datavalue.value.id == null) {
        continue;
      }
      // Lookup topics table
      if (isInTopicTable(datavalue.value.id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * <p>
   * Retrieves data from Entity to instantiate Topic
   * </p>
   * 
   * @param entity
   * @param country
   * @return Topic or null
   */

  private Topic wbEntityToTopic(Entity entity, ISO3166 country) throws ParserException {

  }

  /**
   * <p>
   * Verify whethers a topic is already present in article topics
   * </p>
   * 
   * @param topics
   * @param newTopic
   * @return boolean
   */

  private boolean isTopicDuped(Iterator<Topic> topics, Topic newTopic) {
    while (topics.hasNext()) {
      Topic thisTopic = topics.next();
      if (thisTopic.getName().equals(newTopic.getName())) {
        return true;
      }
    }
    return false;
  }

  // @! Lookup

  /**
   * <p>
   * Fill topics lookup table
   * </p>
   */

  private void fillTopicsLookupTable() {
    topicsTable = new ArrayList<>();
    topicsTable.add(idIdeology); // Ideology
    topicsTable.add(idLifestyle); // Lifestyle
    topicsTable.add(idSocialMovement); // Social Movement
    topicsTable.add(idPhilosophicalViewpoint); // philosophical viewpoint
    topicsTable.add(idPoliticalIdeology); // Political Ideology
    topicsTable.add(idWorldView); // World View
    topicsTable.add(idSocialFormation); // Social Formation
    topicsTable.add(idPoliticalSystem); // Political System
    topicsTable.add(idExtremism); // Extremism
    topicsTable.add(idFormOfGovernment); // Form of government
    topicsTable.add(idGenocide); // Genocide
    topicsTable.add(idPoliticalPhylosophy); // political philosophy
    topicsTable.add(idEconomicIdeology); // economic ideology
  }

  /**
   * <p>
   * Checks whether a certain ID is in the topic table
   * </p>
   * 
   * @param id
   * @return boolean
   */

  private boolean isInTopicTable(String id) {
    Iterator<String> it = topicsTable.iterator();
    while (it.hasNext()) {
      String itValue = it.next();
      if (id.equals(itValue)) {
        return true;
      }
    }
    return false;
  }

  // @! Misc

  /**
   * <p>
   * Retrieve Datavalue object from entity. The index refers to Category List
   * </p>
   * 
   * @param entity
   * @param property
   * @param index
   * @return Datavalue or null
   */

  private Datavalue getDatavalueFromEntity(Entity entity, String property, int index) {
    ArrayList<Category> categoryEntries = entity.claims.get(property);
    if (categoryEntries == null) {
      return null;
    }
    if (categoryEntries.size() == 0) {
      return null;
    }
    Category cat = categoryEntries.get(index);
    if (cat == null) {
      return null;
    }
    Mainsnak mainsnak = cat.mainsnak;
    if (mainsnak == null) {
      return null;
    }
    return mainsnak.datavalue;
  }

}
