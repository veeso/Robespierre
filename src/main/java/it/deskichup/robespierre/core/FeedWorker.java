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

package it.deskichup.robespierre.core;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;

import org.apache.log4j.Logger;

import it.deskichup.robespierre.article.Article;
import it.deskichup.robespierre.article.Subject;
import it.deskichup.robespierre.article.Topic;
import it.deskichup.robespierre.config.DatabaseConfig;
import it.deskichup.robespierre.config.MetadataConfig;
import it.deskichup.robespierre.core.errors.BusyWorkerException;
import it.deskichup.robespierre.core.errors.DupedRecordException;
import it.deskichup.robespierre.database.driver.DatabaseFacade;
import it.deskichup.robespierre.database.driver.MariaFacade;
import it.deskichup.robespierre.feed.Feed;
import it.deskichup.robespierre.feed.FeedSource;
import it.deskichup.robespierre.feed.RSSFeedSource;
import it.deskichup.robespierre.feedreceiver.FeedReceiver;
import it.deskichup.robespierre.feedreceiver.RSSFeedReceiver;
import it.deskichup.robespierre.meta.MetadataReceiver;
import it.deskichup.robespierre.meta.cache.CacheProvider;
import it.deskichup.robespierre.meta.cache.MariaCacheProvider;
import it.deskichup.robespierre.meta.wikidata.WikiDataReceiver;

/**
 * FeedWorker is the class which takes care of fetching a certain source and
 * getting articles from it, then it saves them into the database. The Worker is
 * a thread which waits for incoming jobs.
 */

public class FeedWorker implements Runnable {

  // Static
  private static final FeedWorkerGodfather godfather = new FeedWorkerGodfather(true);

  // Attributes
  private String name;
  private final Logger logger;
  private FeedDatabase database;
  private boolean available; // Is the worker doing something?
  private boolean started; // Has the worker started?
  private boolean stopWorker = false; // Flag to terminate workers
  ArticleAssembler articleAssembler;

  // Runtime
  private FeedSource workingSource;
  private Thread worker;

  /**
   * <p>
   * FeedWorker constructor
   * </p>
   * 
   * @param dbConfig
   * @param metadataConfig
   */

  public FeedWorker(DatabaseConfig dbConfig, MetadataConfig metadataConfig) {
    // Get name and logger
    this.name = godfather.baptize();
    StringBuilder nameBuilder = new StringBuilder();
    nameBuilder.append(FeedWorker.class.getName());
    nameBuilder.append("-");
    nameBuilder.append(this.name);
    this.logger = Logger.getLogger(nameBuilder.toString());
    // Prepare database
    initDatabase(dbConfig);
    // Prepare metadata engine
    initMetadataReceiver(dbConfig, metadataConfig);
    // Initialize other parameters
    this.available = true;
    this.workingSource = null;
    this.stopWorker = false;
    // Start thread
    this.worker = new Thread(this);
  }

  /**
   * <p>
   * Start feed worker thread
   * </p>
   */

  public void start() {
    this.logger.info("Starting worker thread...");
    this.worker.start();
    this.logger.info("Worker thread started!");
  }

  /**
   * <p>
   * Assign new job to Feed Worker
   * </p>
   * 
   * @throws BusyWorkerException
   */

  public void assignNewJob(FeedSource source) throws BusyWorkerException {
    synchronized (this) {
      if (this.isAvailable() && this.workingSource == null) {
        // Set working source
        this.workingSource = source;
        // Notify thread
        notify();
      } else {
        throw new BusyWorkerException("Worker '" + name + "' is busy");
      }
    }
  }

  /**
   * <p>
   * Stop worker
   * </p>
   */

  public void stop() {
    if (hasStarted()) {
      stopWorker = true;
      synchronized (this) {
        notifyAll();
      }
    }
  }

  /**
   * <p>
   * Returns whether the Worker is available for a new job
   * </p>
   * 
   * @return boolean
   */

  public boolean isAvailable() {
    return this.available;
  }

  /**
   * <p>
   * Returns whether the worker thread has already started
   * </p>
   * 
   * @return bool
   */

  public boolean hasStarted() {
    return this.started;
  }

  /**
   * <p>
   * Join FeedWorker thread
   * </p>
   * 
   */

  public void join() {
    if (hasStarted()) {
      try {
        this.worker.join();
      } catch (InterruptedException e) {
        logger.error("FeedWorker join throwned exception 'InterruptedException': " + e.getMessage());
        this.logger.trace(e);
      }
    }
    started = false;
  }

  /**
   * <p>
   * Return worker's name
   * </p>
   * 
   * @return name
   */

  public String getName() {
    return this.name;
  }

  @Override
  public void run() {
    logger.info("A new feed worker started...");
    started = true;
    while (!stopWorker) { // Until stopWorkers becomes true...
      // If there is a job...
      if (this.workingSource != null) {
        this.available = false;
        // Process source
        Instant jobStartedTime = Instant.now();
        logger.info("A new job started: '" + this.workingSource.getURI().toString() + "'");
        processSource(); // <--- All job is done here
        logger.info("Job finished; took " + Duration.between(jobStartedTime, Instant.now()).toMinutes() + " minutes");
        // Set source to null and set itself as available
        this.workingSource = null;
        this.available = true;
      }
      if (stopWorker) { // Prevent sleeping after terminating
        continue;
      }
      // Wait next task
      try {
        this.logger.info("Waiting for a new job...");
        synchronized (this) {
          wait();
        }
        logger.debug("FeedWorker awaked");
      } catch (InterruptedException e) {
        logger.error("FeedWorker wait throwned exception 'InterruptedException': " + e.getMessage());
      }
    }
  }

  // @! Private stuff

  /**
   * <p>
   * Initialize database object
   * </p>
   * 
   * @param config
   */

  private void initDatabase(DatabaseConfig config) {
    DatabaseFacade dbFac = null;
    if (config.engine.equals("mariadb")) {
      // @! Maria DB
      dbFac = new MariaFacade(config.uri, config.user, config.password);
      this.logger.debug("Using MariaDB as database");
    } else {
      this.logger.error("Uknown database engine: " + config.engine); // NOTE: this should never happen
    }
    this.database = new FeedDatabase(dbFac);
  }

  /**
   * <p>
   * Initialize Metadata Receiver
   * </p>
   * 
   * @param dbConfig
   * @param metadataConfig
   */

  private void initMetadataReceiver(DatabaseConfig dbConfig, MetadataConfig metadataConfig) {
    CacheProvider cacheProvider = null;
    if (dbConfig.engine.equals("mariadb")) {
      cacheProvider = new MariaCacheProvider(dbConfig.uri, dbConfig.user, dbConfig.password,
          metadataConfig.cache.duration, metadataConfig.cache.withBlacklist);
      this.logger.debug("Using MariaDB as metadata cache provider");
    } else {
      this.logger.error("Uknown database engine: " + dbConfig.engine);
    }
    MetadataReceiver metadataReceiver = null;
    if (metadataConfig.engine.equals("wikidata")) {
      metadataReceiver = new WikiDataReceiver(cacheProvider);
      this.logger.debug("Using wikidata receiver as metadata provider"); // NOTE: this should never happen
    } else {
      this.logger.error("Uknown metadata engine: " + metadataConfig.engine);
    }
    // Instantiate Article Assembler
    this.articleAssembler = new ArticleAssembler(metadataReceiver);
  }

  /**
   * <p>
   * Process source. This method is pratically the method which executes all the
   * tasks the feed worker has to do
   * </p>
   */

  private void processSource() {
    // Prepare feed receiver and feed parser based on feed source type
    FeedReceiver feedReceiver = null;
    if (this.workingSource.getClass().getName() == RSSFeedSource.class.getName()) {
      feedReceiver = new RSSFeedReceiver();
      logger.info("Feed engine: RSS; source: '" + workingSource.getURI().toString() + "'; country: "
          + workingSource.getCountry().toString());
    } else {
      logger.error("Can't process source '" + workingSource.getURI().toString() + "'");
      return;
    }
    // Receive from feed
    Instant tStarted = Instant.now();
    Feed[] feed;
    try {
      feed = feedReceiver.fetchFeed(this.workingSource);
    } catch (Exception e) {
      logger.error("It was not possible to fetch feed: " + e.getMessage());
      return;
    }
    this.logger.info("Feed fetched; took " + Duration.between(tStarted, Instant.now()).toSeconds() + " seconds");
    // Iterate over feeds
    for (Feed entry : feed) {
      // Assemble article (and get metadata)
      tStarted = Instant.now();
      this.logger.info("Assembling article with title: '" + entry.getTitle() + "'");
      Article article = null;
      article = this.articleAssembler.assemble(entry);
      // Print information for debug
      this.logger.debug("Assembled article > title: " + article.getTitle() + "; Brief: " + article.getBrief()
          + "; URI: " + article.getLink().toString());
      Iterator<Subject> subjects = article.iterSubjects();
      while (subjects.hasNext()) {
        Subject s = subjects.next();
        this.logger.debug(
            "Found article subject > name: " + s.getName() + "; birthdate: " + s.getBirthdate() + "; Birthplace: "
                + s.getBirthplace() + "; Brief: " + s.biography.getBrief() + "; Occupation: " + s.occupation.getName());
      }
      Iterator<Topic> topics = article.iterTopics();
      while (topics.hasNext()) {
        Topic t = topics.next();
        this.logger.debug("Found article topic > name: " + t.getName() + "; description: " + t.getDescription());
      }
      // Insert article into database
      this.logger.debug("Inserting article " + article.getId() + " into database");
      try {
        this.database.commitArticle(article);
      } catch (SQLException e) {
        this.logger.error("Database SQL Error: " + e.getMessage());
        this.logger.trace(e);
        continue;
      } catch (DupedRecordException e) {
        this.logger.error("Duplicated article: " + e.getMessage());
        this.logger.trace(e);
        continue;
      }
      this.logger.info("Article was successfully saved into database (id: " + article.getId() + "); Took "
          + Duration.between(tStarted, Instant.now()).toSeconds() + " seconds");
      // Keep iterating with the next article
    }
  }

}
