/**
 * @author Christian Visintin <christian.visintin1997@gmail.com>
 * @version 0.1.0
 * 
 * Copyright (C) Christian Visintin - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christian Visintin <christian.visintin1997@gmail.com>, 2020
 */

package it.hypocracy.robespierre;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import it.hypocracy.robespierre.config.Config;
import it.hypocracy.robespierre.config.ConfigParser;
import it.hypocracy.robespierre.config.exceptions.BadConfigException;
import it.hypocracy.robespierre.core.JobDispatcher;
import it.hypocracy.robespierre.utils.FileUtils;

/**
 * Robespierre entry point
 * 
 * CLI options:
 * 
 * -p <pidfile> -c <config_file> -h shows help and exit
 */

public class App {

  private final static Logger logger = Logger.getLogger("Main");

  public static void main(final String[] args) {
    // Parse CLI options
    final Options cliOptions = new Options();
    final Option pidfileOption = new Option("p", "pidfile", true, "write pidfile"); // 3rd argument is: hasArgument
    pidfileOption.setRequired(false);
    cliOptions.addOption(pidfileOption);
    final Option configFileOption = new Option("c", "config", true, "pass configuration file");
    configFileOption.setRequired(true);
    cliOptions.addOption(configFileOption);

    final CommandLineParser cliParser = new DefaultParser();
    final HelpFormatter helpFormatter = new HelpFormatter();
    CommandLine cli = null;

    try {
      cli = cliParser.parse(cliOptions, args);
    } catch (final ParseException e) {
      System.out.println(e.getMessage());
      helpFormatter.printHelp("Robespierre", cliOptions);
      System.exit(255);
    }

    final String pidfile = cli.getOptionValue("pidfile");
    final String configFile = cli.getOptionValue("config");

    // Write pidfile
    if (pidfile != null) {
      final long pid = ProcessHandle.current().pid();
      try {
        FileUtils.writeFile(pidfile, String.valueOf(pid));
      } catch (IOException e) {
        logger.error("Unable to write pidfile to " + pidfile + ": " + e.getMessage());
        logger.trace(e);
        System.exit(1);
      }
    }

    // Parse configuration
    logger.info("Gonna parse configuration...");
    ConfigParser configParser = new ConfigParser();
    Config config = null;
    try {
      config = configParser.parse(configFile);
    } catch (BadConfigException | IOException e) {
      logger.error("Unable to parse configuration: " + e.getMessage());
      logger.trace(e);
      System.exit(1);
    }

    logger.info("Configuration parsed!");
    // Start Job Dispatcher
    JobDispatcher jobDispatcher = new JobDispatcher(config);
    logger.debug("Job dispatcher ready");
    // Create shutdown handler
    logger.debug("Starting job dispatcher...");
    jobDispatcher.start();
    logger.info("Job dispatcher started!");
    // Create shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        logger.info("Shutting down JVM; terminating Robespierre...");
        jobDispatcher.stop();
        logger.info("Job dispatcher stopped!");
      }
    }));
    logger.debug("Entering main loop");
    // Main loop
    while (true) {
      // Processing job dispatcher
      logger.debug("Processing job dispatcher...");
      jobDispatcher.process();

      // Sleep for 3 seconds
      try {
        Thread.sleep(3000, 0);
      } catch (InterruptedException e) {
        logger.error("Sleep interrupted: " + e.getMessage());
        logger.trace(e);
        continue;
      }
    }
  }
}
