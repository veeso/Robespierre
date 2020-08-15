/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre.core;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Occupation;
import it.hypocracy.robespierre.article.Subject;
import it.hypocracy.robespierre.article.SubjectBio;
import it.hypocracy.robespierre.article.Topic;
import it.hypocracy.robespierre.core.errors.DupedRecordException;
import it.hypocracy.robespierre.database.driver.DatabaseFacade;
import it.hypocracy.robespierre.database.query.InsertQuery;
import it.hypocracy.robespierre.database.query.SelectQuery;
import it.hypocracy.robespierre.database.query.UpdateQuery;
import it.hypocracy.robespierre.database.query.syntax.Clause;
import it.hypocracy.robespierre.database.query.syntax.ClauseOperator;
import it.hypocracy.robespierre.database.query.syntax.ClauseRelation;
import it.hypocracy.robespierre.utils.ISO3166;
import it.hypocracy.robespierre.utils.MySqlDateTime;
import it.hypocracy.robespierre.utils.Uuidv4;

/**
 * The FeedDatabase is the Hypocracy facade for the database. It provides methods to
 * operate on the database entity related to Hypocracy and to manage data in a safe way
 */

public class FeedDatabase {

  private final static Logger logger = Logger.getLogger(FeedDatabase.class.getName());

  private DatabaseFacade dbFac;

  // Tables
  private final static String articleTable = "article";
  private final static String articleTopicTable = "article_topic";
  private final static String articleSubjectTable = "article_subject";
  private final static String topicTable = "topic";
  private final static String topicDataTable = "topic_data";
  private final static String subjectTable = "subject";
  private final static String biographyTable = "biography";
  private final static String occupationTable = "occupation";
  private final static String metadataBlacklistTable = "metadata_blacklist";

  // Fields
  // Article
  private final static String articleFieldId = "id";
  private final static String articleFieldTitle = "title";
  private final static String articleFieldBrief = "brief";
  private final static String articleFieldLink = "link";
  private final static String articleFieldDate = "date";
  private final static String articleFieldState = "state";
  private final static String articleFieldCountry = "country";
  // Article topic
  private final static String articleTopicFieldId = "id";
  private final static String articleTopicFieldArticleId = "article_id";
  private final static String articleTopicFieldTopicId = "topic_id";
  // Article Subject
  private final static String articleSubjectFieldId = "id";
  private final static String articleSubjectFieldArticleId = "article_id";
  private final static String articleSubjectFieldSubjectId = "subject_id";
  // Topic
  private final static String topicFieldId = "id";
  private final static String topicFieldDataId = "topic_data_id";
  private final static String topicDataFieldId = "id";
  private final static String topicDataFieldName = "name"; // NOTE: _COUNTRY must be added
  private final static String topicDataFieldDesc = "desc"; // NOTE: _COUNTRY must be added
  // Subject
  private final static String subjectFieldId = "id";
  private final static String subjectFieldName = "name";
  private final static String subjectFieldBirthdate = "birthdate";
  private final static String subjectFieldCitizenship = "citizenship";
  private final static String subjectFieldBirthplace = "birthplace";
  private final static String subjectFieldImage = "image";
  private final static String subjectFieldRemoteId = "remote_id";
  private final static String subjectFieldLastUpdate = "last_update";
  private final static String subjectFieldBioId = "bio";
  private final static String subjectFieldOccupationId = "occupation";
  // Biography
  private final static String biographyFieldId = "id";
  // Occupation
  private final static String occupationFieldId = "id";
  // Metadata blacklist
  private final static String metadataBlacklistFieldId = "id";
  private final static String metadataBlacklistFieldWord = "word";
  private final static String metadataBlacklistFieldLanguage = "language";
  private final static String metadataBlacklistFieldCommitDate = "commit_date";

  /**
   * 
   * @param dbFacade
   */

  public FeedDatabase(DatabaseFacade dbFacade) {
    this.dbFac = dbFacade;
  }

  // @! Articles

  /**
   * <p>
   * Insert an article into the database. Duped article won't be inserted. In that
   * case a DupedRecordException will be thrown
   * </p>
   * 
   * @param article
   * @throws SQLException
   * @throws DupedRecordException
   */

  public synchronized void commitArticle(Article article) throws SQLException, DupedRecordException {
    // Connect to database
    dbFac.connect();
    logger.info("Commiting article '" + article.getTitle() + "'");
    try {
      // Start with article entity
      // Verify if article is duped
      logger.debug("Checking if article is duped");
      if (isArticleDuped(article)) {
        logger.debug("Article duped");
        throw new DupedRecordException("Article already exists in the database");
      }
      logger.debug("Article is not duped");
      final String language = article.getCountry().toISO639().toString();
      // Ok, it is now safe to insert article
      insertArticle(article);
      // Insert subjects
      Iterator<Subject> sIt = article.iterSubjects();
      while (sIt.hasNext()) {
        Subject s = sIt.next();
        logger.debug("Inserting subject " + s.getName());
        commitSubject(s, article.getId(), language);
      }
      // Insert topics
      Iterator<Topic> tIt = article.iterTopics();
      while (tIt.hasNext()) {
        Topic t = tIt.next();
        logger.debug("Inserting topic " + t.getName());
        commitTopic(t, article.getId(), language);
      }
      // Commit changes
      dbFac.commit();
      logger.debug("Article commited");
    } finally { // Disconnect in finally statement is mandatory
      dbFac.disconnect();
    }
  }

  /**
   * <p>
   * Checks whether if an article is duplicated. An article is duplicated if: -
   * has the same title of another article published in the last 7 days NOTE: more
   * clauses should be added in the future
   * </p>
   * 
   * @param article
   * @return boolean
   * @throws SQLException
   */

  private boolean isArticleDuped(Article article) throws SQLException {
    // Check if an article with the same title exists and has been published in the
    // last 2 weeks
    LocalDateTime lastWeekDate = article.getDate();
    lastWeekDate = lastWeekDate.minus(14, ChronoUnit.DAYS);
    // Prepare query
    Clause where = new Clause(articleFieldTitle, escapeString(article.getTitle()), ClauseOperator.EQUAL);
    where.setNext(new Clause(articleFieldDate, escapeString(lastWeekDate.toString()), ClauseOperator.BIGGER_EQUAL),
        ClauseRelation.AND);
    String[] fields = new String[] { articleFieldId };
    String[] tables = new String[] { articleTable };
    SelectQuery query = new SelectQuery(fields, tables, where);
    // Perform query
    if (this.dbFac.select(query).size() > 0) { // At least one result, duped.
      return true;
    }
    return false;
  }

  /**
   * <p>
   * Insert article in the article table
   * </p>
   * 
   * @param article
   * @throws SQLException
   */

  private void insertArticle(Article article) throws SQLException {
    // Prepare insert query
    String[] columns = new String[7];
    columns[0] = articleFieldId;
    columns[1] = articleFieldTitle;
    columns[2] = articleFieldBrief;
    columns[3] = articleFieldLink;
    columns[4] = articleFieldDate;
    columns[5] = articleFieldState;
    columns[6] = articleFieldCountry;
    String[] values = new String[7]; // 7 columns
    values[0] = escapeString(article.getId());
    values[1] = escapeString(article.getTitle());
    values[2] = escapeString(article.getBrief());
    values[3] = escapeString(article.getLink().toString());
    values[4] = escapeString(article.getDate().toString());
    values[5] = "0";
    values[6] = escapeString(article.getCountry().toString());
    InsertQuery query = new InsertQuery(articleTable, columns, values);
    // Perform
    dbFac.insert(query);
  }

  // @! Subjects

  /**
   * <p>
   * Insert subject and all its associated tables into the database. Adds also the
   * association between the subject and the article
   * </p>
   * 
   * @param subject
   * @param articleId
   * @param language
   * @throws SQLException
   */

  private void commitSubject(Subject subject, String articleId, String language) throws SQLException {
    // Check if subject already exists
    logger.debug("Committing subject " + subject.getName());
    if (subjectExists(subject)) {
      logger.debug("Subject exists: updating subject...");
      // Update subject (update: image, last_update)
      HashMap<String, String> fields = new HashMap<>();
      fields.put(subjectFieldImage, escapeString(subject.getImageUri()));
      fields.put(subjectFieldLastUpdate, escapeString(subject.getLastUpdate().toString()));
      Clause where = new Clause(subjectFieldId, escapeString(subject.getId()), ClauseOperator.EQUAL);
      UpdateQuery query = new UpdateQuery(subjectTable, fields, where);
      dbFac.update(query);
      logger.debug("Subject updated (" + subject.getId() + ")");
      // Update bio record
      updateBiography(subject.biography.getId(), subject.biography.getBrief(), language);
      logger.debug("Biography updated: " + subject.biography.getId());
      // Update occupation id
      updateOccupation(subject.occupation, language);
      logger.debug("Occupation updated: " + subject.occupation.getId());
    } else {
      // Insert a new subject...
      logger.debug("Inserting new subject");
      // Start with checking if the occupation record already exists
      if (!occupationExists(language, subject.occupation)) {
        // Insert occupation data
        logger.debug("Occupation doesn't exist; creating a new one");
        insertOccupation(subject.occupation, language);
      } // NOTE: no need to update occupation here. Its data (occupation ID) is already correct for
        // sure (in occupationExists, we searched it by text)
      // insert bio
      logger.debug("Inserting biography");
      insertBiography(subject.biography, language);
      // Insert subject
      String[] columns = new String[] { subjectFieldId, subjectFieldName, subjectFieldBirthdate,
          subjectFieldCitizenship, subjectFieldBirthplace, subjectFieldImage, subjectFieldRemoteId,
          subjectFieldLastUpdate, subjectFieldBioId, subjectFieldOccupationId };
      String[] values = new String[] { escapeString(subject.getId()), escapeString(subject.getName()),
          escapeString(subject.getBirthdate().toString()), escapeString(subject.getCitizenship().toString()),
          escapeString(subject.getBirthplace()), escapeString(subject.getImageUri()),
          escapeString(subject.getRemoteId()), escapeString(subject.getLastUpdate().toString()),
          escapeString(subject.biography.getId()), escapeString(subject.occupation.getId()) };
      InsertQuery query = new InsertQuery(subjectTable, columns, values);
      dbFac.insert(query);
      logger.debug("Subject inserted (" + subject.getId() + ")");
    }
    // Add association between subject and article
    final String assocId = genUUID();
    String[] columns = new String[] { articleSubjectFieldId, articleSubjectFieldArticleId,
        articleSubjectFieldSubjectId };
    String[] values = new String[] { escapeString(assocId), escapeString(articleId), escapeString(subject.getId()) };
    InsertQuery query = new InsertQuery(articleSubjectTable, columns, values);
    dbFac.insert(query);
    logger.debug("Added subject/article assoc (" + assocId + ")");
  }

  /**
   * <p>
   * Checks if subject already exists. If exists updates its uuid with the one
   * found in the database. It also updates bio ID and Occupation ID
   * </p>
   * 
   * @param subject
   * @throws SQLException
   * @return boolean
   */

  private boolean subjectExists(Subject subject) throws SQLException {
    // Prepare query (select same name and birthdate (good enough to guarantee no
    // homonyms))
    String[] fields = new String[] { subjectFieldId, subjectFieldBioId, subjectFieldOccupationId };
    String[] tables = new String[] { subjectTable };
    Clause where = new Clause(subjectFieldName, escapeString(subject.getName()), ClauseOperator.EQUAL);
    if (subject.getBirthdate() != null) { // Check birthdate if set
      where.setNext(
          new Clause(subjectFieldBirthdate, escapeString(subject.getBirthdate().toString()), ClauseOperator.EQUAL),
          ClauseRelation.AND);
    } else if (subject.getBirthplace() != null) { // Try with birthplace maybe
      where.setNext(
          new Clause(subjectFieldBirthplace, escapeString(subject.getBirthplace()), ClauseOperator.EQUAL),
          ClauseRelation.AND);
    } else if (subject.getCitizenship() != null) { // Last try: citizenship
      where.setNext(
          new Clause(subjectFieldCitizenship, escapeString(subject.getCitizenship().toString()), ClauseOperator.EQUAL),
          ClauseRelation.AND);
    }
    SelectQuery query = new SelectQuery(fields, tables, where);
    // Perform query
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Set ids
      subject.setId(result.get(0).get(subjectFieldId));
      subject.biography.setId(result.get(0).get(subjectFieldBioId));
      subject.occupation.setId(result.get(0).get(subjectFieldOccupationId));
      return true;
    }
    return false;
  }

  /**
   * <p>
   * Search subject into the database by name
   * </p>
   * 
   * @param match
   * @param expiration
   * @param language
   * @return Subjects
   * @throws SQLException
   * @throws IllegalArgumentException
   */

  public Subject[] searchSubject(String match, int expiration, String language)
      throws SQLException, IllegalArgumentException {
    // Connect to database
    Subject[] subjects = null;
    try {
      dbFac.connect();
      // Prepare Where statement
      StringBuilder matchBuilder = new StringBuilder();
      if (!match.startsWith("%")) { // Add '%' if missing
        matchBuilder.append("%");
      }
      matchBuilder.append(match);
      if (!match.endsWith("%")) { // Add '%' if missing
        matchBuilder.append("%");
      }
      // Where subject name likes match and expiration date is in the future
      Clause where = new Clause(subjectFieldName, escapeString(matchBuilder.toString()), ClauseOperator.LIKE);
      // Prepare Columns
      String[] columns = new String[] { subjectFieldId, subjectFieldName, subjectFieldBirthdate, subjectFieldBirthplace,
          subjectFieldCitizenship, subjectFieldImage, subjectFieldRemoteId, subjectFieldLastUpdate,
          subjectFieldOccupationId, subjectFieldBioId };
      String[] tables = new String[] { subjectTable };
      // Select
      SelectQuery query = new SelectQuery(columns, tables, where);
      ArrayList<Map<String, String>> rows = dbFac.select(query);
      // Iterate over rows
      Iterator<Map<String, String>> subjectIt = rows.iterator();
      ArrayList<Subject> subjectsList = new ArrayList<>();
      while (subjectIt.hasNext()) {
        // Get occupation
        Map<String, String> row = subjectIt.next();
        // Check expiration date
        LocalDateTime lastUpdate = MySqlDateTime.parse(row.get(subjectFieldLastUpdate));
        LocalDateTime expirationDate = lastUpdate.plusDays(expiration);
        LocalDateTime timeNow = LocalDateTime.now();
        // If not expired, add subject
        if (expirationDate.isAfter(timeNow)) {
          String occupationStr = getOccupation(row.get(subjectFieldOccupationId), language);
          String biography = getBiography(row.get(subjectFieldBioId), language);
          // Instantiate subject
          Occupation occupation = new Occupation(row.get(subjectFieldOccupationId), occupationStr);
          subjectsList.add(new Subject(row.get(subjectFieldId), row.get(subjectFieldName),
              LocalDate.parse(row.get(subjectFieldBirthdate)), new ISO3166(row.get(subjectFieldCitizenship)),
              row.get(subjectFieldBirthplace), row.get(subjectFieldImage),
              new SubjectBio(row.get(subjectFieldBioId), biography), row.get(subjectFieldRemoteId),
              MySqlDateTime.parse(row.get(subjectFieldLastUpdate)), occupation));
        }
      }
      // Prepare subjects
      subjects = new Subject[subjectsList.size()];
      for (int i = 0; i < subjectsList.size(); i++) {
        subjects[i] = subjectsList.get(i);
      }
    } finally { // Disconnect in finally statement is mandatory
      dbFac.disconnect();
    }
    return subjects;
  }

  // @! Occupation

  /**
   * <p>
   * Checks whether the occupation already exists.
   * If exists, set found occupation ID to occupation
   * </p>
   * 
   * @param occupation
   * @return boolean
   * @throws SQLException
   */

  private boolean occupationExists(String language, Occupation occupation) throws SQLException {
    String[] fields = new String[] { occupationFieldId };
    String[] tables = new String[] { occupationTable };
    // Check if `language` is equal to occupation name
    Clause where = new Clause(language, escapeString(occupation.getName()), ClauseOperator.EQUAL);
    SelectQuery query = new SelectQuery(fields, tables, where);
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Set occcupation id
      occupation.setId(result.get(0).get(occupationFieldId));
      return true;
    }
    // Occupation doesn't exist, return false
    return false;
  }

  /**
   * <p>
   * Insert occupation data
   * </p>
   * 
   * @param uuid
   * @param occupation
   * @param language
   * @param rowExists
   * @throws SQLException
   */

  private void insertOccupation(Occupation occupation, String language) throws SQLException {
    String[] columns = new String[] { occupationFieldId, language };
    String[] values = new String[] { escapeString(occupation.getId()), escapeString(occupation.getName()) };
    InsertQuery query = new InsertQuery(occupationTable, columns, values);
    dbFac.insert(query);
  }

  /**
   * <p>
   * Update occupation data
   * </p>
   * 
   * @param uuid
   * @param occupation
   * @param language
   * @param rowExists
   * @throws SQLException
   */

  private void updateOccupation(Occupation occupation, String language) throws SQLException {
    HashMap<String, String> fields = new HashMap<>();
    fields.put(language, escapeString(occupation.getName()));
    Clause where = new Clause(occupationFieldId, escapeString(occupation.getId()), ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery(occupationTable, fields, where);
    dbFac.update(query);
  }

  /**
   * <p>
   * Retrieves the name of the occupation associated to the UUID
   * </p>
   * 
   * @param uuid
   * @param language
   * @return string
   * @throws SQLException
   */

  private String getOccupation(String uuid, String language) throws SQLException {
    String[] columns = new String[] { language };
    String[] table = new String[] { occupationTable };
    Clause where = new Clause(occupationFieldId, escapeString(uuid), ClauseOperator.EQUAL);
    SelectQuery query = new SelectQuery(columns, table, where);
    ArrayList<Map<String, String>> rows = dbFac.select(query);
    Iterator<Map<String, String>> it = rows.iterator();
    if (it.hasNext()) {
      return it.next().get(language);
    } else {
      return null;
    }
  }

  // @! Bio

  /**
   * <p>
   * Insert a new biography in the database
   * </p>
   * 
   * @param uuid
   * @param biography
   * @param language
   * @throws SQLException
   */

  private void insertBiography(SubjectBio biography, String language) throws SQLException {
    String[] columns = new String[] { biographyFieldId, language };
    String[] values = new String[] { escapeString(biography.getId()), escapeString(biography.getBrief()) };
    InsertQuery query = new InsertQuery(biographyTable, columns, values);
    dbFac.insert(query);
  }

  /**
   * <p>
   * Update existing biography for a certain subject in the database biography
   * </p>
   * 
   * @param id
   * @param brief
   * @param language
   * @throws SQLException
   */

  private void updateBiography(String id, String brief, String language) throws SQLException {
    HashMap<String, String> fields = new HashMap<>();
    fields.put(language, escapeString(brief));
    Clause where = new Clause(biographyFieldId, escapeString(id), ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery(biographyTable, fields, where);
    dbFac.update(query);
  }

  /**
   * <p>
   * Retrieves the text of the biography associated to the UUID
   * </p>
   * 
   * @param uuid
   * @param language
   * @return string
   * @throws SQLException
   */

  private String getBiography(String uuid, String language) throws SQLException {
    String[] columns = new String[] { language };
    String[] table = new String[] { biographyTable };
    Clause where = new Clause(biographyFieldId, escapeString(uuid), ClauseOperator.EQUAL);
    SelectQuery query = new SelectQuery(columns, table, where);
    ArrayList<Map<String, String>> rows = dbFac.select(query);
    Iterator<Map<String, String>> it = rows.iterator();
    if (it.hasNext()) {
      return it.next().get(language);
    } else {
      return null;
    }
  }

  // @! Topics

  /**
   * <p>
   * Insert topic and all its associated tables into the database. Adds also the
   * association between the topic and the article
   * </p>
   * 
   * @param topic
   * @param articleId
   * @param language
   * @throws SQLException
   */

  private void commitTopic(Topic topic, String articleId, String language) throws SQLException {
    // Check if topic exists
    logger.debug("Committing topic " + topic.getName());
    if (topicExists(topic, language)) {
      logger.debug("topic " + topic.getName() + " (" + topic.getId() + ") already exists; updating it...");
      // Update topic data
      updateTopicData(topic.getDescriptionId(), topic.getName(), topic.getDescription(), language);
    } else {
      // Create topic data
      logger.debug("topic " + topic.getName() + "(" + topic.getId() + ") doesn't exist yet; creating it");
      insertTopicData(topic.getDescriptionId(), topic.getName(), topic.getDescription(), language);
      // Insert topic
      String[] columns = new String[] { topicFieldId, topicFieldDataId };
      String[] values = new String[] { escapeString(topic.getId()), escapeString(topic.getDescriptionId()) };
      InsertQuery query = new InsertQuery(topicTable, columns, values);
      dbFac.insert(query);
    }
    // Add association between topic and article
    final String assocId = genUUID();
    String[] columns = new String[] { articleTopicFieldId, articleTopicFieldArticleId, articleTopicFieldTopicId };
    String[] values = new String[] { escapeString(assocId), escapeString(articleId), escapeString(topic.getId()) };
    InsertQuery query = new InsertQuery(articleTopicTable, columns, values);
    dbFac.insert(query);
    logger.debug("Added relation between topic and article: " + assocId);
  }

  /**
   * <p>
   * Checks if topic already exists. If exists updates its uuid with the one found
   * in the database. It also updates the description ID
   * </p>
   * 
   * @param topic
   * @throws SQLException
   * @return boolean
   */

  private boolean topicExists(Topic topic, String language) throws SQLException {
    // Prepare query (select same name)
    final String nameColumn = topicDataFieldName + "_" + language;
    final String topicIdCol = topicTable + "." + topicFieldId;
    final String topicDataIdCol = topicDataTable + "." + topicDataFieldId;
    String[] fields = new String[] { topicIdCol, topicFieldDataId };
    String[] tables = new String[] { topicTable, topicDataTable };
    Clause where = new Clause(nameColumn, escapeString(topic.getName()), ClauseOperator.EQUAL);
    where.setNext(new Clause(topicFieldDataId, topicDataIdCol, ClauseOperator.EQUAL), ClauseRelation.AND);
    SelectQuery query = new SelectQuery(fields, tables, where);
    // Perform query
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Set ids
      topic.setId(result.get(0).get(topicFieldId));
      topic.setDescriptionId(result.get(0).get(topicFieldDataId));
      return true;
    }
    return false;
  }

  /**
   * <p>
   * Search topic into the database by name
   * </p>
   * 
   * @param match
   * @param language
   * @return Topic
   * @throws SQLException
   * @throws IllegalArgumentException
   */

  public Topic[] searchTopic(String match, String language) throws SQLException, IllegalArgumentException {
    // Connect to database
    Topic[] topics = null;
    try {
      dbFac.connect();
      // Prepare Where statement
      // Where subject name is exactly match
      final String nameColumn = topicDataFieldName + "_" + language;
      final String descColumn = topicDataFieldDesc + "_" + language;
      final String topicIdCol = topicTable + "." + topicFieldId;
      final String topicTopicDataIdCol = topicTable + "." + topicFieldDataId;
      final String topicDataIdCol = topicDataTable + "." + topicDataFieldId;
      String[] fields = new String[] { topicIdCol, topicFieldDataId, nameColumn, descColumn };
      String[] tables = new String[] { topicTable, topicDataTable };
      Clause where = new Clause(nameColumn, escapeString(match), ClauseOperator.EQUAL);
      where.setNext(new Clause(topicTopicDataIdCol, topicDataIdCol, ClauseOperator.EQUAL), ClauseRelation.AND);
      SelectQuery query = new SelectQuery(fields, tables, where);
      ArrayList<Map<String, String>> rows = dbFac.select(query);
      // Prepare topics
      topics = new Topic[rows.size()];
      // Iterate over rows
      Iterator<Map<String, String>> topicIt = rows.iterator();
      int topIdx = 0;
      while (topicIt.hasNext()) {
        Map<String, String> row = topicIt.next();
        topics[topIdx] = new Topic(row.get(topicFieldId), row.get(nameColumn), row.get(descColumn),
            row.get(topicFieldDataId));
        topIdx++; // Increment topic index
      }
    } finally { // Disconnect in finally statement is mandatory
      dbFac.disconnect();
    }
    return topics;
  }

  // @! Topic Data

  /**
   * <p>
   * Insert a new topic data in the database topic data
   * </p>
   * 
   * @param description
   * @param language
   * @throws SQLException
   * @return biography uuid
   */

  private void insertTopicData(String uuid, String name, String description, String language) throws SQLException {
    final String nameColumn = topicDataFieldName + "_" + language;
    final String descColumn = topicDataFieldDesc + "_" + language;
    String[] columns = new String[] { topicDataFieldId, nameColumn, descColumn };
    String[] values = new String[] { escapeString(uuid), escapeString(name), escapeString(description) };
    InsertQuery query = new InsertQuery(topicDataTable, columns, values);
    dbFac.insert(query);
  }

  /**
   * <p>
   * Update existing topic data for a certain subject in the database biography
   * </p>
   * 
   * @param id
   * @param name
   * @param description
   * @param language
   * @throws SQLException
   */

  private void updateTopicData(String id, String name, String description, String language) throws SQLException {
    final String nameColumn = topicDataFieldName + "_" + language;
    final String descColumn = topicDataFieldDesc + "_" + language;
    HashMap<String, String> fields = new HashMap<>();
    fields.put(nameColumn, escapeString(name));
    fields.put(descColumn, escapeString(description));
    Clause where = new Clause(topicDataFieldId, escapeString(id), ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery(topicDataTable, fields, where);
    dbFac.update(query);
  }

  // @! Metadata blacklist

  /**
   * <p>
   * Search for match inside of metadata blacklist.
   * </p>
   * 
   * @param match
   * @param expiration
   * @param language
   * @return boolean
   * @throws SQLException
   * @throws IllegalArgumentException
   */

  public boolean searchBlacklist(String match, int expiration, String language)
      throws SQLException, IllegalArgumentException {
    dbFac.connect();
    try {
      // Prepare query
      final String[] tables = new String[] { metadataBlacklistTable };
      final String[] columns = new String[] { metadataBlacklistFieldCommitDate };
      Clause where = new Clause(metadataBlacklistFieldWord, escapeString(match), ClauseOperator.EQUAL);
      where.setNext(new Clause(metadataBlacklistFieldLanguage, escapeString(language), ClauseOperator.EQUAL),
          ClauseRelation.AND);
      SelectQuery query = new SelectQuery(columns, tables, where);
      ArrayList<Map<String, String>> rows = dbFac.select(query);
      // Iterate over rows
      Iterator<Map<String, String>> it = rows.iterator();
      while (it.hasNext()) {
        // Get occupation
        Map<String, String> row = it.next();
        // Check expiration date
        final LocalDateTime commitDate = MySqlDateTime.parse(row.get(metadataBlacklistFieldCommitDate));
        final LocalDateTime expirationDate = commitDate.plusDays(expiration);
        final LocalDateTime timeNow = LocalDateTime.now();
        // If not expired, return true
        if (expirationDate.isAfter(timeNow)) {
          return true;
        }
      }
    } finally {
      dbFac.disconnect();
    }
    return false;
  }

  /**
   * <p>
   * Commit blacklist word
   * </p>
   * 
   * @param word
   * @param language
   * @throws SQLException
   */

  public void commitBlacklistWord(String word, String language) throws SQLException {
    // Open database
    dbFac.connect();
    try {
      // Check if blacklist word exists
      final int blacklistId = blacklistWordExists(word, language);
      if (blacklistId == -1) {
        // Insert new record
        insertBlacklistRecord(word, language);
      } else {
        // Update commit date of blacklisted word
        updateBlacklistRecord(blacklistId);
      }
      dbFac.commit();
      logger.debug("Blacklisted metadata word: '" + word + "' for " + language);
    } finally {
      dbFac.disconnect();
    }
  }

  /**
   * <p>
   * Checks whether a certain blacklist word exists in the database
   * </p>
   * 
   * @param word
   * @param language
   * @return record id (or -1)
   * @throws SQLException
   */

  private int blacklistWordExists(String word, String language) throws SQLException {
    // Prepare query
    final String[] tables = new String[] { metadataBlacklistTable };
    final String[] columns = new String[] { metadataBlacklistFieldId };
    Clause where = new Clause(metadataBlacklistFieldWord, escapeString(word), ClauseOperator.EQUAL);
    where.setNext(new Clause(metadataBlacklistFieldLanguage, escapeString(language), ClauseOperator.EQUAL),
        ClauseRelation.AND);
    SelectQuery query = new SelectQuery(columns, tables, where);
    ArrayList<Map<String, String>> rows = dbFac.select(query);
    // Iterate over rows
    Iterator<Map<String, String>> it = rows.iterator();
    while (it.hasNext()) {
      // Get occupation
      Map<String, String> row = it.next();
      return Integer.parseInt(row.get(metadataBlacklistFieldId));
    }
    return -1;
  }

  /**
   * <p>
   * Update blacklist commit_date record
   * </p>
   * 
   * @param id
   * @throws SQLException
   */

  private void updateBlacklistRecord(int id) throws SQLException {
    HashMap<String, String> values = new HashMap<>();
    LocalDateTime now = LocalDateTime.now();
    values.put(metadataBlacklistFieldCommitDate, escapeString(now.toString()));
    Clause where = new Clause(metadataBlacklistFieldId, String.valueOf(id), ClauseOperator.EQUAL);
    UpdateQuery query = new UpdateQuery(metadataBlacklistTable, values, where);
    dbFac.update(query);
  }

  /**
   * <p>
   * Insert a new record into the blacklist
   * </p>
   * 
   * @param word
   * @param language
   * @throws SQLException
   */

  private void insertBlacklistRecord(String word, String language) throws SQLException {
    LocalDateTime now = LocalDateTime.now();
    String[] columns = new String[] { metadataBlacklistFieldWord, metadataBlacklistFieldLanguage,
        metadataBlacklistFieldCommitDate };
    String[] values = new String[] { escapeString(word), escapeString(language), escapeString(now.toString()) };
    InsertQuery query = new InsertQuery(metadataBlacklistTable, columns, values);
    dbFac.insert(query);
  }

  // @! SQL

  /**
   * <p>
   * Escape rval
   * </p>
   * 
   * @param val
   * @return string
   */
  private String escapeString(String val) {
    StringBuilder escaped = new StringBuilder();
    escaped.append("\"");
    escaped.append(val);
    escaped.append("\"");
    return escaped.toString();
  }

  /**
   * <p>
   * Generate a new UUID
   * </p>
   * 
   * @return string
   */

  private String genUUID() {
    Uuidv4 uuid = new Uuidv4();
    return uuid.getUUIDv4();
  }

}
