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

package it.deskichup.robespierre.meta.wikidata;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.article.Occupation;
import it.deskichup.robespierre.article.Subject;
import it.deskichup.robespierre.article.Topic;
import it.deskichup.robespierre.meta.exceptions.MetadataReceiverException;
import it.deskichup.robespierre.meta.exceptions.ParserException;
import it.deskichup.robespierre.meta.search.SearchTarget;
import it.deskichup.robespierre.meta.wikidata.imageinfo.Image;
import it.deskichup.robespierre.meta.wikidata.imageinfo.ImageInfoUri;
import it.deskichup.robespierre.meta.wikidata.imageinfo.Imageinfo;
import it.deskichup.robespierre.meta.wikidata.wbentity.WbProperty;
import it.deskichup.robespierre.meta.wikidata.wbentity.Datavalue;
import it.deskichup.robespierre.meta.wikidata.wbentity.DatavalueVal;
import it.deskichup.robespierre.meta.wikidata.wbentity.Description;
import it.deskichup.robespierre.meta.wikidata.wbentity.Entity;
import it.deskichup.robespierre.meta.wikidata.wbentity.Label;
import it.deskichup.robespierre.meta.wikidata.wbentity.Mainsnak;
import it.deskichup.robespierre.meta.wikidata.wbentity.WbEntity;
import it.deskichup.robespierre.utils.ISO3166;
import it.deskichup.robespierre.utils.ISO8601;

/**
 * The WikiDataParser is the class which takes care of parsing the data web
 * entity received from WikiData and to collect its metadata to build up
 * Article's subjects and topics
 */

public class WikiDataParser {

  protected final static Logger logger = Logger.getLogger(WikiDataParser.class.getName());

  private WikiDataApiClient apiClient;
  private static ArrayList<String> topicsTable = null;

  // Properties
  private static final String properyInstanceOf = "P31";
  private static final String propertyBirthdate = "P569";
  private static final String propertyCitizenship = "P27";
  private static final String propertyBirthplace = "P19";
  private static final String propertyImage = "P18";
  private static final String propertyOccupation = "P106";
  private static final String propertyIso3166 = "P297";
  private static final String propertyOfficialName = "P1448";
  private static final String propertyIsDead = "P570";

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
   * @param targets
   * @return true if data has changed article
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  public boolean parseWbEntity(WbEntity wbEntity, String id, Article article, SearchTarget[] targets)
      throws ParserException, MetadataReceiverException {
    // Verify web entity
    logger.debug("Parsing entity " + id);
    Entity entity = wbEntity.entities.get(id);
    if (entity == null) {
      return false;
    }
    // If in targets there is subject, check if it's human...
    if (inTargets(targets, SearchTarget.SUBJECT)) {
      logger.debug("SUBJECT is in targets for " + id);
      // Check if it is human
      if (isWbEntityHuman(entity)) {
        logger.debug("Entity " + id + " is a human");
        // Instantiate a subject from Human
        Subject articleSubject = wbEntityToSubject(entity, article.getCountry());
        // If subject is null, return false
        if (articleSubject == null) {
          logger.debug("Entity " + id + " didn't return a subject candidate");
          return false;
        }
        // Check if subject is duplicated
        Iterator<Subject> subjects = article.iterSubjects();
        if (!isSubjectDuped(subjects, articleSubject)) {
          // If not duped, add to article subjects
          logger.debug("Added to article " + article.getId() + " a new subject: " + articleSubject.getName());
          article.addSubject(articleSubject);
        }
        return true; // Return true anyway
      }
    }
    if (inTargets(targets, SearchTarget.TOPIC)) {
      logger.debug("TOPIC is in targets for " + id);
      if (isWbEntityTopic(entity)) {
        logger.debug("Entity " + id + " is a topic");
        // Retrieve topic parameters
        Topic articleTopic = wbEntityToTopic(entity, article.getCountry());
        // If topic is null, return false
        if (articleTopic == null) {
          logger.debug("Entity " + id + " didn't return a topic candidate");
          return false;
        }
        // Check if topic is duplicated
        Iterator<Topic> topics = article.iterTopics();
        if (!isTopicDuped(topics, articleTopic)) {
          // If not duped, add to article topics
          logger.debug("Added to article " + article.getId() + " a new topic: " + articleTopic.getName());
          article.addTopic(articleTopic);
        }
        return true; // Return true anyway
      }
    }
    logger.debug("Entity " + id + " is nothing");
    return false;
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
    ArrayList<WbProperty> p31 = entity.claims.get(properyInstanceOf);
    // Iterate over p31
    Iterator<WbProperty> p31Iterator = p31.iterator();
    while (p31Iterator.hasNext()) {
      WbProperty category = p31Iterator.next();
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
    // - Birthplace
    // - Image
    // - Brief
    // - Occupation
    // - Remote ID (entity.title)

    // First, check if human entity is dead, because if it is, ignore it...
    if (isHumanDead(entity)) {
      logger.debug(entity.title + " is dead... R.I.P.");
      return null;
    }
    // Okay it's alive, let's parse it...
    final String language = country.toISO639().toString();
    // Collect name from labels
    Label label = entity.labels.get(language);
    if (label == null) {
      throw new ParserException("Label is null for " + entity.title);
    }
    String name = label.value.toLowerCase();
    if (name == null) {
      throw new ParserException("Label value is null for" + entity.title);
    }
    logger.debug(entity.title + " has name: " + name);
    // Get brief
    Description description = entity.descriptions.get(language);
    if (description == null) {
      throw new ParserException("Description is null for" + entity.title);
    }
    String brief = description.value.toLowerCase();
    if (brief == null) {
      throw new ParserException("Description value is null for" + entity.title);
    }
    logger.debug(entity.title + " has brief: " + brief);
    // Get birthdate
    LocalDate birthdate = getBirthdate(entity);
    if (birthdate != null) {
      logger.debug(entity.title + " has birthdate: " + birthdate.toString());
    }
    // Get citizenship
    ISO3166 citizenship = getCitizenship(entity, country);
    if (citizenship != null) {
      logger.debug(entity.title + " has citizenship: " + citizenship.toString());
    }
    // Get birthplace
    ISO3166 cityCountry = (citizenship != null) ? citizenship : country;
    String birthplace = getBirthplace(entity, cityCountry);
    logger.debug(entity.title + " has birthplace: " + birthplace);
    // Get image
    String imageUri = getImage(entity);
    logger.debug(entity.title + " has imageUri: " + imageUri);
    // occupation
    Occupation occupation = getOccupation(entity, country);
    if (occupation != null) {
      logger.debug(entity.title + " has occupation: " + occupation.getName());
    }
    // Set remoteid
    String remoteId = entity.title;
    // Return subject
    return new Subject(name, birthdate, citizenship, birthplace, imageUri, brief, remoteId, occupation);
  }

  /**
   * <p>
   * Checks whether a certain human is dead
   * </p>
   * 
   * @param entity
   * @return boolean
   */

  private boolean isHumanDead(Entity entity) {
    return getDatavalueFromEntity(entity, propertyIsDead, 0) != null;
  }

  /**
   * <p>
   * Retrieve birthdate from entity
   * </p>
   * 
   * @param entity
   * @return LocalDateTime or null
   * @throws ParserException
   */

  private LocalDate getBirthdate(Entity entity) throws ParserException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyBirthdate, 0);
    if (datavalue == null) {
      return null;
    }
    if (datavalue.type == null) {
      throw new ParserException("Property Birthdate has no datavalue type");
    }
    if (datavalue.type.equals("time")) {
      DatavalueVal value = datavalue.value;
      if (value == null) {
        throw new ParserException("Property Birthdate has no value");
      }
      if (value.time == null) {
        throw new ParserException("Property Birthdate has no datavalue time");
      }
      // Parse ISO8601
      try {
        return ISO8601.parse(value.time).toLocalDate();
      } catch (DateTimeParseException ex) {
        throw new ParserException("Property Birthdate has invalid ISO8601 value");
      }
    } else {
      throw new ParserException("Property Birthdate has no datavalue type equal to time");
    }
  }

  /**
   * <p>
   * Retrieve citizenship from entity
   * </p>
   * 
   * @param entity
   * @param country
   * @return ISO3166
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  private ISO3166 getCitizenship(Entity entity, ISO3166 country) throws ParserException, MetadataReceiverException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyCitizenship, 0);
    if (datavalue == null) {
      throw new ParserException("Citizenship has no datavalue");
    }
    if (datavalue.type == null) {
      throw new ParserException("Citizenship has no datavalue type");
    }
    if (!datavalue.type.equals("wikibase-entityid")) {
      throw new ParserException("Citizenship has type not equal to wikibase-entityid");
    }
    if (datavalue.value == null) {
      throw new ParserException("Citizenship has no datavalue value");
    }
    if (datavalue.value.id == null) {
      throw new ParserException("Citizenship has no datavalue value id");
    }
    // Send wbentity query
    String countryId = datavalue.value.id;
    WbEntity countryQuery = this.apiClient.getWebEntity(countryId, country);
    Entity countryEntity = countryQuery.entities.get(countryId);
    if (countryEntity == null) {
      throw new ParserException("Country query returned bad object");
    }
    Datavalue countryDatavalue = getDatavalueFromEntity(countryEntity, propertyIso3166, 0);
    if (countryDatavalue == null) {
      return null; // There's no property and could be ok
    }
    if (countryDatavalue.type == null) {
      throw new ParserException("Country entity has no datavalue type");
    }
    if (!countryDatavalue.type.equals("string")) {
      throw new ParserException("Country entity has bad datatype");
    }
    if (countryDatavalue.value == null) {
      throw new ParserException("Country entity has no value");
    }
    try {
      return new ISO3166(countryDatavalue.value.value);
    } catch (IllegalArgumentException ex) {
      throw new ParserException("Country has bad ISO3166 value");
    }
  }

  /**
   * <p>
   * Retrieve birthplace from entity. The provided country must be the citizenship
   * of the subject
   * </p>
   * 
   * @param entity
   * @param country
   * @return ISO3166
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  private String getBirthplace(Entity entity, ISO3166 country) throws ParserException, MetadataReceiverException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyBirthplace, 0);
    if (datavalue == null) {
      return null;
    }
    if (datavalue.type == null) {
      throw new ParserException("Birthplace has no datavalue type");
    }
    if (!datavalue.type.equals("wikibase-entityid")) {
      throw new ParserException("Birthplace has type not equal to wikibase-entityid");
    }
    if (datavalue.value == null) {
      throw new ParserException("Birthplace has no datavalue value");
    }
    if (datavalue.value.id == null) {
      throw new ParserException("Birthplace has no datavalue value id");
    }
    // Send wbentity query
    WbEntity cityQuery = this.apiClient.getWebEntity(datavalue.value.id, country);
    Entity cityEntity = cityQuery.entities.get(datavalue.value.id);
    if (cityEntity == null) {
      throw new ParserException("City query returned bad object");
    }
    // Try with official name
    Datavalue officialName = getDatavalueFromEntity(cityEntity, propertyOfficialName, 0);
    if (officialName != null) {
      if (officialName.value == null) {
        throw new ParserException("Birthplace has no officialname value");
      }
      if (officialName.value.text == null) {
        throw new ParserException("Birthplace has no officialname text value");
      }
      return officialName.value.text.toLowerCase();
    } else {
      // Get label otherwise
      Label cityLabel = cityEntity.labels.get(country.toString().toLowerCase());
      if (cityLabel == null) {
        return null;
      }
      if (cityLabel.value == null) {
        throw new ParserException("City query has no label value");
      }
      return cityLabel.value.toLowerCase();
    }
  }

  /**
   * <p>
   * Fetch image from entity querying wikidata api
   * </p>
   * 
   * @param entity
   * @return String
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  private String getImage(Entity entity) throws ParserException, MetadataReceiverException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyImage, 0);
    if (datavalue == null) {
      return null;
    }
    if (datavalue.type == null) {
      throw new ParserException("Image has no datavalue type");
    }
    if (!datavalue.type.equals("string")) {
      throw new ParserException("Image has type not equal to string");
    }
    if (datavalue.value == null) {
      throw new ParserException("Image has no datavalue value");
    }
    if (datavalue.value.value == null) {
      throw new ParserException("Image has no datavalue value id");
    }
    Imageinfo imginfo = this.apiClient.getImageInfo(datavalue.value.value);
    if (imginfo.query == null) {
      throw new ParserException("Imageinfo has no query");
    }
    Image page = imginfo.query.pages.get("-1");
    if (page == null) {
      throw new ParserException("Imageinfo has no page -1");
    }
    if (page.imageinfo.size() == 0) {
      throw new ParserException("Imageinfo has imageinfo array with size 0");
    }
    ImageInfoUri info = page.imageinfo.get(0);
    if (info.url == null) {
      throw new ParserException("Imageinfo has no url");
    }
    return info.url;
  }

  /**
   * <p>
   * Retrieve category (occupation) from entity
   * </p>
   * 
   * @param entity
   * @param country
   * @return Occupation
   * @throws ParserException
   * @throws MetadataReceiverException
   */

  private Occupation getOccupation(Entity entity, ISO3166 country) throws ParserException, MetadataReceiverException {
    Datavalue datavalue = getDatavalueFromEntity(entity, propertyOccupation, 0);
    if (datavalue == null) {
      throw new ParserException("Occupation has no datavalue");
    }
    if (datavalue.type == null) {
      throw new ParserException("Occupation has no datavalue type");
    }
    if (!datavalue.type.equals("wikibase-entityid")) {
      throw new ParserException("Occupation has type not equal to wikibase-entityid");
    }
    if (datavalue.value == null) {
      throw new ParserException("Occupation has no datavalue value");
    }
    if (datavalue.value.id == null) {
      throw new ParserException("Occupation has no datavalue value id");
    }
    // Send wbentity query
    WbEntity occupationQuery = this.apiClient.getWebEntity(datavalue.value.id, country);
    Entity occupationEntity = occupationQuery.entities.get(datavalue.value.id);
    if (occupationEntity == null) {
      throw new ParserException("Occupation query returned bad object");
    }
    // Get label
    Label occupationLabel = occupationEntity.labels.get(country.toString().toLowerCase());
    if (occupationLabel == null) {
      throw new ParserException("Occupation query has no label");
    }
    if (occupationLabel.value == null) {
      throw new ParserException("Occupation query has no label value");
    }
    return new Occupation(occupationLabel.value.toLowerCase());
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
   * @throws ParserException
   */

  private boolean isWbEntityTopic(Entity entity) throws ParserException {
    if (!entity.claims.containsKey(properyInstanceOf)) {
      return false;
    }
    ArrayList<WbProperty> p31 = entity.claims.get(properyInstanceOf);
    // Iterate over p31
    Iterator<WbProperty> p31Iterator = p31.iterator();
    while (p31Iterator.hasNext()) {
      WbProperty category = p31Iterator.next();
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
    // For topic we need to retrieve
    // - name
    // - description
    final String language = country.toISO639().toString();
    // Collect name from labels
    Label label = entity.labels.get(language);
    if (label == null) {
      throw new ParserException("Label is null");
    }
    String name = label.value.toLowerCase();
    if (name == null) {
      throw new ParserException("Label value is null");
    }
    logger.debug(entity.title + " has name: " + name);
    // Get brief
    Description description = entity.descriptions.get(language);
    if (description == null) {
      throw new ParserException("Description is null");
    }
    String brief = description.value.toLowerCase();
    if (brief == null) {
      throw new ParserException("Description value is null");
    }
    logger.debug(entity.title + " has brief: " + brief);
    // Instantiate topic
    return new Topic(name, brief);
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
   * Retrieve Datavalue object from entity. The index refers to WbProperty List
   * </p>
   * 
   * @param entity
   * @param property
   * @param index
   * @return Datavalue or null
   */

  private Datavalue getDatavalueFromEntity(Entity entity, String property, int index) {
    ArrayList<WbProperty> categoryEntries = entity.claims.get(property);
    if (categoryEntries == null) {
      return null;
    }
    if (categoryEntries.size() == 0) {
      return null;
    }
    WbProperty cat = categoryEntries.get(index);
    if (cat == null) {
      return null;
    }
    Mainsnak mainsnak = cat.mainsnak;
    if (mainsnak == null) {
      return null;
    }
    return mainsnak.datavalue;
  }

  /**
   * <p>
   * Check if `what` is in targets
   * </p>
   * 
   * @param targets
   * @param what
   * @returns boolean
   */

  private boolean inTargets(SearchTarget[] targets, SearchTarget what) {
    for (SearchTarget t : targets) {
      if (t == what) {
        return true;
      }
    }
    return false;
  }

}
