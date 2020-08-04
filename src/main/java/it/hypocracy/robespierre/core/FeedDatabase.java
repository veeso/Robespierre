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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import it.hypocracy.robespierre.article.Article;
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
      // TODO:
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
      // TODO: update subject
    } else {
      // Insert a new subject...
      // Start with checking if the occupation record already exists
      String occupationId = occupationExists(language, subject.occupation.getName());
      if (occupationId == null) {
        // Generate a new UUID
        occupationId = genUUID();
        // Insert occupation data
        insertOccupation(occupationId, subject.occupation.getName(), language);
      } else {
        // Update occupation data
        updateOccupation(occupationId, subject.occupation.getName(), language);
      }
      // insert bio
      final String bioId = insertBiography(subject.getBiography(), language);
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
    String[] columns = new String[] { articleSubjectFieldArticleId, articleSubjectFieldArticleId,
        articleSubjectFieldSubjectId };
    String[] values = new String[] { escapeString(assocId), escapeString(articleId), escapeString(subject.getId()) };
    InsertQuery query = new InsertQuery(articleSubjectTable, columns, values);
    dbFac.insert(query);
  }

  /**
   * <p>
   * Checks if subject already exists. If exists updates its uuid with the one
   * found in the database.
   * </p>
   * 
   * @param subject
   * @throws SQLException
   * @return boolean
   */

  private boolean subjectExists(Subject subject) throws SQLException {
    // Prepare query (select same name and birthdate (good enough to guarantee no
    // homonyms))
    String[] fields = new String[] { subjectFieldId };
    String[] tables = new String[] { subjectTable };
    Clause where = new Clause(subjectFieldName, escapeString(subject.getName()), ClauseOperator.EQUAL);
    where.setNext(
        new Clause(subjectFieldBirthdate, escapeString(subject.getBirthdate().toString()), ClauseOperator.EQUAL),
        ClauseRelation.AND);
    SelectQuery query = new SelectQuery(fields, tables, where);
    // Perform query
    ArrayList<Map<String, String>> result = this.dbFac.select(query);
    if (result.size() > 0) { // At least one result, duped.
      // Set id
      subject.setId(result.get(0).get(subjectFieldId));
      return true;
    }
    return false;
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
