# Robespierre

[![License: MIT](https://img.shields.io/badge/License-MIT-teal.svg)](https://opensource.org/licenses/MIT) [![Stars](https://img.shields.io/github/stars/ChristianVisintin/Robespierre.svg)](https://github.com/ChristianVisintin/Robespierre) [![Issues](https://img.shields.io/github/issues/ChristianVisintin/Robespierre.svg)](https://github.com/ChristianVisintin/Robespierre/issues)

[![MvnBuild](https://github.com/ChristianVisintin/Robespierre/workflows/Maven/badge.svg)](https://github.com/ChristianVisintin/Robespierre/actions)

~ RSS Metadata collector daemon ~  
Developed by Christian Visintin  
Current version: 0.1.0 (??/12/2020)

- [Robespierre](#robespierre)
  - [Introduction](#introduction)
  - [Configuration](#configuration)
  - [Debugging](#debugging)
    - [Clean database up](#clean-database-up)
  - [Changelog](#changelog)
  - [License](#license)

---

## Introduction

Robespierre is a daemon written in Java, which purpose is to collect data from RSS feeds. What makes Robespierre special in what it does, is that it is able to collect also the **involved persons** (named "subjects") and **topics** from the articles.
To do so, Robespierre reads the feed titles and briefes and tokenize words according to a certain logic, then Robespierre will query a **Metadata Provider** to look for persons and topics involved in the provided feed. At the moment Robespierre gathers these metadata from 

## Configuration

Robespierre uses a JSON configuration:

```json
{
  "database": {
    "engine": "mariadb",
    "uri": "jdbc:mariadb://localhost:3306/robespierre",
    "user": "root",
    "password": null
  },
  "feed": {
    "maxWorkers": 16,
    "sources": [
      {
        "uri": "http://example.com/rss",
        "country": "IT",
        "interval": 360,
        "engine": "rss"
      }
    ]
  },
  "metadata": {
    "engine": "wikidata",
    "cache": {
      "duration": 180,
      "withBlacklist": true
    }
  }
}
```

Here follows the key description:

- database: contains the database configuration
  - engine: the database engine (e.g. mariadb)
  - uri: database uri
  - user: database user
  - password: database password
- feed: contains the feed configuration
  - maxWorkers: maximum amount of workers instantiated to fetch sources
  - sources: contains the sources array
    - engine: the feed protocol
    - uri: feed uri
    - country: feed country
    - interval: interval in minutes for fetching source
- metadata: contains the metadata configuration
  - engine: the engine to be used (e.g. wikidata)

A configuration sample can be found [HERE](./config/default.json).

## Debugging

### Clean database up

```sql
DELETE FROM article_topic;
DELETE FROM article_subject;
DELETE FROM article;
DELETE FROM subject;
DELETE FROM biography;
DELETE FROM occupation;
DELETE FROM topic;
DELETE FROM topic_data;
```

## Changelog

View [CHANGELOG](CHANGELOG.md)

---

## License

Licensed under the MIT license; you may not use this file except in compliance with the License. You may obtain a copy of the License at

You can read the entire license [HERE](LICENSE)
