-- Create database

CREATE DATABASE IF NOT EXISTS robespierre;

USE robespierre;

-- Occupation
-- Add or remove columns based on your needs

CREATE TABLE IF NOT EXISTS occupation (
  id CHAR(36) NOT NULL PRIMARY KEY,
  it CHAR(128),
  en CHAR(128),
  fr CHAR(128),
  de CHAR(128),
  es CHAR(128)
);

-- Subject biograpgy table
-- Add or remove columns based on your needs

CREATE TABLE IF NOT EXISTS biography (
  id CHAR(36) NOT NULL PRIMARY KEY,
  it MEDIUMTEXT,
  en MEDIUMTEXT,
  fr MEDIUMTEXT,
  de MEDIUMTEXT,
  es MEDIUMTEXT
);

-- Subject table
-- id :: UUID v4
-- citizenship :: ISO 3166
-- image :: image URI
-- remote_id :: Identifier to access the data provider (e.g. WikiData)
-- last_update :: Last time data were retrieved

CREATE TABLE IF NOT EXISTS subject (
  id CHAR(36) NOT NULL PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  birthdate DATE,
  citizenship VARCHAR(2),
  birthplace VARCHAR(64),
  image VARCHAR(512),
  remote_id VARCHAR(256),
  last_update DATETIME NOT NULL,
  bio CHAR(36) NOT NULL,
  occupation CHAR(36) NOT NULL,
  CONSTRAINT `subject_bio_fk` FOREIGN KEY (bio) REFERENCES biography (id),
  CONSTRAINT `subject_occupation_fk` FOREIGN KEY (occupation) REFERENCES occupation (id)
);

-- Topic metadata table
-- Add or remove columns based on your needs

CREATE TABLE IF NOT EXISTS topic_data (
  id CHAR(36) NOT NULL PRIMARY KEY,
  name_it CHAR(64),
  desc_it MEDIUMTEXT,
  name_en CHAR(64),
  desc_en MEDIUMTEXT,
  name_fr CHAR(64),
  desc_fr MEDIUMTEXT,
  name_de CHAR(64),
  desc_de MEDIUMTEXT,
  name_es CHAR(64),
  desc_es MEDIUMTEXT
);

-- Topic table
-- id :: uuid4v4

CREATE TABLE IF NOT EXISTS topic (
  id CHAR(36) NOT NULL PRIMARY KEY,
  topic_data_id CHAR(36) NOT NULL,
  CONSTRAINT `topic_data_fk` FOREIGN KEY (topic_data_id) REFERENCES topic_data (id)
);

-- Article table
-- id :: UUIDv4
-- country :: Article source country ISO3166

CREATE TABLE IF NOT EXISTS article (
  id CHAR(36) NOT NULL PRIMARY KEY,
  title VARCHAR(1024) NOT NULL,
  brief MEDIUMTEXT NOT NULL,
  link VARCHAR(1024) NOT NULL,
  date DATETIME NOT NULL,
  country VARCHAR(2) NOT NULL
);

-- Article topic association

CREATE TABLE IF NOT EXISTS article_topic (
  id CHAR(36) NOT NULL PRIMARY KEY,
  article_id CHAR(36) NOT NULL,
  topic_id CHAR(36) NOT NULL,
  CONSTRAINT `article_topic_fk` FOREIGN KEY (article_id) REFERENCES article (id),
  CONSTRAINT `topic_fk` FOREIGN KEY (topic_id) REFERENCES topic (id)
);

-- Article subject association

CREATE TABLE IF NOT EXISTS article_subject (
  id CHAR(36) NOT NULL PRIMARY KEY,
  article_id CHAR(36) NOT NULL,
  subject_id CHAR(36) NOT NULL,
  CONSTRAINT `article_subject_fk` FOREIGN KEY (article_id) REFERENCES article (id),
  CONSTRAINT `subject_fk` FOREIGN KEY (subject_id) REFERENCES subject (id)
);

-- Metadata blacklist
-- id autoincrement
-- match text to match
-- language :: ISO639

CREATE TABLE IF NOT EXISTS metadata_blacklist (
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  word MEDIUMTEXT NOT NULL,
  language VARCHAR(2) NOT NULL,
  commit_date DATETIME NOT NULL
);
