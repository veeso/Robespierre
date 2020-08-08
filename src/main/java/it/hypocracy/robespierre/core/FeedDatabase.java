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

import it.hypocracy.robespierre.article.Article;
import it.hypocracy.robespierre.article.Occupation;
import it.hypocracy.robespierre.article.Subject;
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
import it.hypocracy.robespierre.utils.ISO8601;
import it.hypocracy.robespierre.utils.Uuidv4;

public class FeedDatabase {

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

  public void commitArticle(Article article) throws SQLException, DupedRecordException {
    // Connect to database
    dbFac.connect();
    try {
      // Start with article entity
      // Verify if article is duped
      if (isArticleDuped(article)) {
        throw new DupedRecordException("Article already exists in the database");
      }
      final String language = article.getCountry().toISO639().toString();
      // Ok, it is now safe to insert article
      insertArticle(article);
      // Insert subjects
      Iterator<Subject> sIt = article.iterSubjects();
      while (sIt.hasNext()) {
        commitSubject(sIt.next(), article.getId(), language);
      }
      // Insert topics
      Iterator<Topic> tIt = article.iterTopics();
      while (tIt.hasNext()) {
        commitTopic(tIt.next(), article.getId(), language);
      }
      // Commit changes
      dbFac.commit();
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
    // last week
    LocalDateTime lastWeekDate = article.getDate();
    lastWeekDate = lastWeekDate.minus(7, ChronoUnit.DAYS);
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
    values[6] = article.getCountry().toString();
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
    if (subjectExists(subject)) {
      // Update subject (update: image, last_update)
      HashMap<String, String> fields = new HashMap<>();
      fields.put(subjectFieldImage, escapeString(subject.getImageUri()));
      fields.put(subjectFieldLastUpdate, escapeString(subject.getLastUpdate().toString()));
      Clause where = new Clause(subjectFieldId, escapeString(subject.getId()), ClauseOperator.EQUAL);
      UpdateQuery query = new UpdateQuery(subjectTable, fields, where);
      dbFac.update(query);
      // Update bio record
      updateBiography(subject.biography.getId(), subject.biography.getBrief(), language);
      // Update occupation id
      updateOccupation(subject.occupation.getId(), subject.occupation.getName(), language);
    } else {
      // Insert a new subject...
      // Start with checking if the occupation record already exists
      String occupationId = occupationExists(language, subject.occupation.getName());
      if (occupationId == null) {
        // Generate a new UUID
        occupationId = genUUID();
        // Insert occupation data
        insertOccupation(occupationId, subject.occupation.getName(), language);
      } // NOTE: no need to update occupation here. It's data is already correct for
        // sure (in occupationExists, we searched it by text)
      // insert bio
      final String bioId = insertBiography(subject.biography.getBrief(), language);
      // Insert subject
      String[] columns = new String[] { subjectFieldId, subjectFieldName, subjectFieldBirthdate,
          subjectFieldCitizenship, subjectFieldBirthplace, subjectFieldImage, subjectFieldRemoteId,
          subjectFieldLastUpdate, subjectFieldBioId, subjectFieldOccupationId };
      String[] values = new String[] { escapeString(subject.getId()), escapeString(subject.getName()),
          escapeString(subject.getBirthdate().toString()), escapeString(subject.getCitizenship().toString()),
          escapeString(subject.getBirthplace()), escapeString(subject.getImageUri()),
          escapeString(subject.getRemoteId()), escapeString(subject.getLastUpdate().toString()), escapeString(bioId),
          escapeString(occupationId) };
      InsertQuery query = new InsertQuery(subjectTable, columns, values);
      dbFac.insert(query);
    }
    // Add association between subject and article
    final String assocId = genUUID();
    String[] columns = new String[] { articleSubjectFieldId, articleSubjectFieldArticleId,
        articleSubjectFieldSubjectId };
    String[] values = new String[] { escapeString(assocId), escapeString(articleId), escapeString(subject.getId()) };
    InsertQuery query = new InsertQuery(articleSubjectTable, columns, values);
    dbFac.insert(query);
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
    where.setNext(
        new Clause(subjectFieldBirthdate, escapeString(subject.getBirthdate().toString()), ClauseOperator.EQUAL),
        ClauseRelation.AND);
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
   * @param language
   * @return Subjects
   * @throws SQLException
   * @throws IllegalArgumentException
   */

  public Subject[] searchSubject(String match, LocalDateTime expiration, String language) throws SQLException, IllegalArgumentException {
    // Connect to database
    Subject[] subjects = null;
    try {
      dbFac.connect();
      // Prepare Where statement
      StringBuilder matchBuilder = new StringBuilder();
      if (! match.startsWith("%")) { // Add '%' if missing
        matchBuilder.append("%");
      }
      matchBuilder.append(match);
      if (! match.endsWith("%")) { // Add '%' if missing
        matchBuilder.append("%");
      }
      // Where subject name likes match and expiration date is in the future
      Clause where = new Clause(subjectFieldName, escapeString(matchBuilder.toString()), ClauseOperator.LIKE);
      where.setNext(new Clause(subjectFieldLastUpdate, expiration.toString(), ClauseOperator.LESS_THAN), ClauseRelation.AND);
      // Prepare Columns
      String[] columns = new String[] { subjectFieldId, subjectFieldName, subjectFieldBirthdate, subjectFieldBirthplace, subjectFieldCitizenship, subjectFieldImage, subjectFieldRemoteId, subjectFieldLastUpdate, subjectFieldOccupationId, subjectFieldBioId };
      String[] tables = new String[] { subjectTable };
      // Select
      SelectQuery query = new SelectQuery(columns, tables, where);
      ArrayList<Map<String, String>> rows = dbFac.select(query);
      // Prepare subjects
      subjects = new Subject[rows.size()];
      // Iterate over rows
      Iterator<Map<String, String>> subjectIt = rows.iterator();
      int subjIdx = 0;
      while (subjectIt.hasNext()) {
        // Get occupation
        Map<String, String> row = subjectIt.next();
        String occupationStr = getOccupation(row.get(subjectFieldOccupationId), language);
        String biography = getBiography(row.get(subjectFieldBioId), language);
        // Instantiate subject
        Occupation occupation = new Occupation(row.get(subjectFieldOccupationId), occupationStr);
        subjects[subjIdx] = new Subject(row.get(subjectFieldId), row.get(subjectFieldName), LocalDate.parse(row.get(subjectFieldBirthdate)), new ISO3166(row.get(subjectFieldCitizenship)), row.get(subjectFieldBirthplace), row.get(subjectFieldImage), biography, row.get(subjectFieldRemoteId), ISO8601.toLocalDateTime(row.get(subjectFieldLastUpdate)), occupation);
        subjIdx++; // Increment subject index
      }
    } finally { // Disconnect in finally statement is mandatory
      dbFac.disconnect();
    }
    return subjects;
  }

  // @! Occupation

  /**
   * <p>
   * Checks whether the occupation already exists. If exists return its uuid,
   * otherwise null
   * </p>
   * 
   * @param occupation
   * @return string
   * @throws SQLException
   */

  private String occupationExists(String language, String occupation) throws SQLException {
    String[] fields = new String[] { occupationFieldId };
    String[] tables = new String[] { occupationTable };
    // Check if `language` is equal to occupation name
    Clause where = new Clause(language, escapeString(occupation), ClauseOperator.EQUAL);
    SelectQuery query = new SelectQuery(fields, tables, where);
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Return occcupation id
      return result.get(0).get(occupationFieldId);
    }
    // Occupation doesn't exist, return null
    return null;
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

  private void insertOccupation(String uuid, String occupation, String language) throws SQLException {
    String[] columns = new String[] { occupationFieldId, language };
    String[] values = new String[] { escapeString(uuid), escapeString(occupation) };
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

  private void updateOccupation(String uuid, String occupation, String language) throws SQLException {
    HashMap<String, String> fields = new HashMap<>();
    fields.put(language, escapeString(occupation));
    Clause where = new Clause(occupationFieldId, escapeString(uuid), ClauseOperator.EQUAL);
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
   * Insert a new biography in the database. Returns the UUID of the generated
   * biography
   * </p>
   * 
   * @param biography
   * @param language
   * @throws SQLException
   * @return biography uuid
   */

  private String insertBiography(String biography, String language) throws SQLException {
    // Generate UUID
    final String uuid = genUUID();
    String[] columns = new String[] { biographyFieldId, language };
    String[] values = new String[] { escapeString(uuid), escapeString(biography) };
    InsertQuery query = new InsertQuery(biographyTable, columns, values);
    dbFac.insert(query);
    return uuid;
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
    if (topicExists(topic)) {
      // Update topic data
      updateTopicData(topic.getDescriptionId(), topic.getName(), topic.getDescription(), language);
    } else {
      // Create topic data
      final String topicDataId = insertTopicData(topic.getName(), topic.getDescription(), language);
      // Insert topic
      String[] columns = new String[] { topicFieldId, topicFieldDataId };
      topic.setDescriptionId(topicDataId);
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

  private boolean topicExists(Topic topic) throws SQLException {
    // Prepare query (select same name)
    String[] fields = new String[] { topicFieldId, topicFieldDataId };
    String[] tables = new String[] { topicTable };
    Clause where = new Clause(subjectFieldName, escapeString(topic.getName()), ClauseOperator.EQUAL);
    ;
    SelectQuery query = new SelectQuery(fields, tables, where);
    // Perform query
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Set ids
      topic.setId(result.get(0).get(topicFieldId));
      topic.setDescriptionId(result.get(0).get(topicFieldId));
      return true;
    }
    return false;
  }

  // @! Topic Data

  /**
   * <p>
   * Insert a new topic data in the database. Returns the UUID of the generated
   * topic data
   * </p>
   * 
   * @param description
   * @param language
   * @throws SQLException
   * @return biography uuid
   */

  private String insertTopicData(String name, String description, String language) throws SQLException {
    // Generate UUID
    final String uuid = genUUID();
    final String nameColumn = topicDataFieldName + "_" + language;
    final String descColumn = topicDataFieldDesc + "_" + language;
    String[] columns = new String[] { topicDataFieldId, nameColumn, descColumn };
    String[] values = new String[] { escapeString(uuid), escapeString(name), escapeString(description) };
    InsertQuery query = new InsertQuery(topicDataTable, columns, values);
    dbFac.insert(query);
    return uuid;
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
