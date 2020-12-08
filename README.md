# Robespierre

[![License: MIT](https://img.shields.io/badge/License-MIT-teal.svg)](https://opensource.org/licenses/MIT) [![Stars](https://img.shields.io/github/stars/ChristianVisintin/Robespierre.svg)](https://github.com/ChristianVisintin/Robespierre) [![Issues](https://img.shields.io/github/issues/ChristianVisintin/Robespierre.svg)](https://github.com/ChristianVisintin/Robespierre/issues)

[![MvnBuild](https://github.com/ChristianVisintin/Robespierre/workflows/Maven/badge.svg)](https://github.com/ChristianVisintin/Robespierre/actions)

~ RSS Metadata collector daemon ~  
Developed by Christian Visintin  
Current version: 0.1.0 (??/12/2020)

- [Robespierre](#robespierre)
  - [About Robespierre 🎯](#about-robespierre-)
  - [Features 🎁](#features-)
  - [Setup 🛠](#setup-)
    - [Install Robespierre 📦](#install-robespierre-)
    - [Initialize the database 💾](#initialize-the-database-)
    - [Write your own configuration 📝](#write-your-own-configuration-)
    - [Run Robespierre](#run-robespierre)
  - [Contributing 🤝🏻](#contributing-)
  - [Known Issues 🩹](#known-issues-)
  - [Upcoming Features 🧪](#upcoming-features-)
  - [Changelog ⏳](#changelog-)
  - [License 📃](#license-)

---

## About Robespierre 🎯

Robespierre is a daemon written in Java, which purpose is to collect data from RSS feeds. What makes Robespierre special in what it does, is that it is able to collect also the **involved persons** (named "subjects") and **topics** from the articles.
To do so, Robespierre reads the feed titles and briefes and tokenize words according to a certain logic, then Robespierre will query a **Metadata Provider** to look for persons and topics involved in the provided feed. At the moment Robespierre gathers these metadata from [Wikidata](https://www.wikidata.org), but it wouldn't be harsh to implement a new Metadata receiver.

## Features 🎁

- **Easy to setup** 😎

    Just write an easy JSON configuration file and you're done

- **Can collect information from any RSS source in any language** 🌍

    Just provide the URI, the engine and the feed language

- **Fully schedulable based on your needs** 🕔

    You can easily set fetch intervals for each feed source

- **Everything you need get stored into a relational database** 💾

    Robespierre collects a lot of data from feeds, in details:

    1. title
    2. brief
    3. link to original article
    4. date
    5. country of the feed source
    6. involved subjects
      - name
      - birthdate
      - citizenship
      - birthplace
      - uri to an image
      - brief of their biography
      - occupation
    7. involved topics
      - name
      - description

- **The more it works, the more reliable and faster it gets** 🚀

    Thanks to how it is implemented, the metadata scraper, gets faster every time learning from its mistakes

- **Can interface with different databases** 🍥

    Since the database connector is implemented in an abstract way, changing the database just means changing a few keys in the configuration file

- **Can interface with different metadata providers** 💁🏻‍♀️

    Changing the metadata providers, just means changing a key in the configuration file

- **Written in Java 11** ☕

---

## Setup 🛠

Let's see in a **step-by-step** guide, how to setup Robespierre.

### Install Robespierre 📦

TODO: to complete after release
Download artifact from latest release

Specify also:
-Dlog4j.configuration="path.to.my.config.xml"

### Initialize the database 💾

Robespierre supports one of these database:

- mariadb

once you've installed and configure the basic parameters for it, we can start creating the Robespierre database.

1. Choose a name for your database; in this example I will use `robespierre`.
2. Open the init file for your database, which is located at `./database/init-{YOUR_DATABASE}.sql`
3. Change names and columns based on your needs

    Change the database name, if you're using another name:

    ```sql
    CREATE DATABASE IF NOT EXISTS robespierre; -- Change with your name

    USE robespierre; -- Change with your name
    ```

    Add or remove language columns; languages must be **ISO639** compliant

    ```sql
    CREATE TABLE IF NOT EXISTS occupation (
      id CHAR(36) NOT NULL PRIMARY KEY,
      it CHAR(128),
      en CHAR(128), -- Add or remove columns (e.g. fr, de...)
    );

    --- ...

    CREATE TABLE IF NOT EXISTS biography (
      id CHAR(36) NOT NULL PRIMARY KEY,
      it MEDIUMTEXT,
      en MEDIUMTEXT, -- Add or remove columns (e.g. fr, de...)
    );

    CREATE TABLE IF NOT EXISTS topic_data (
      id CHAR(36) NOT NULL PRIMARY KEY,
      name_it CHAR(64),
      desc_it MEDIUMTEXT,
      name_en CHAR(64),
      desc_en MEDIUMTEXT, -- Add or remove columns (e.g. fr, de...)
    );
    ```

4. Setup the blacklist

    Robespierre uses a blacklist to prevent some false positives while scraping for metadata. For instance, Wikidata, when finds the word `spagna` (which in italians is Spain), will return [Ivana Spagna](https://it.wikipedia.org/wiki/Ivana_Spagna), who is a singer. Blacklist's purpose is to prevent these false positives, based on your needs.
    I've created a simple blacklist based on my needs, but I'd be glad if you could contribute to this project adding further words in blacklist, maybe ordered by language and category. See [Contributing](#contributing-).

    Then, check the file `./database/blacklist-{METADATA_PROVIDERsql` and select the words, you want to blacklist for your installation.

### Write your own configuration 📝

You just need to write a JSON file, which looks like this:

```json
{
  "database": {
    "engine": "mariadb",
    "uri": "jdbc:mariadb://localhost:3306/robespierre",
    "user": "mydbuser",
    "password": "mysecret"
  },
  "feed": {
    "maxWorkers": 2,
    "sources": [
      {
        "uri": "http://xml2.corriereobjects.it/rss/politica.xml",
        "country": "IT",
        "interval": 720,
        "engine": "rss"
      },
      {
        "uri": "https://rss.nytimes.com/services/xml/rss/nyt/Politics.xml",
        "country": "US",
        "interval": 60,
        "engine": "rss"
      },
      {
        "uri": "https://www.lefigaro.fr/rss/figaro_politique.xml",
        "country": "FR",
        "interval": 240,
        "engine": "rss"
      },
      {
        "uri": "https://www.spiegel.de/politik/index.rss",
        "country": "DE",
        "interval": 240,
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

where:

- **database**: contains the database configuration
  - **engine**: the database engine; available engines: "mariadb"
  - **uri**: database uri
  - **user**: database user
  - **password**: database password
- **feed**: contains the feed configuration
  - **maxWorkers**: maximum amount of workers (threads) instantiated to fetch sources
  - **sources**: contains the feed sources configuration
    - **engine**: the feed protocol; available engines: "rss"
    - **uri**: feed uri
    - **country**: feed country, code must be **ISO6166-1 alpha-2** compliant.
    - **interval**: interval in **minutes** for fetching source
- **metadata**: contains the metadata configuration
  - **engine**: the engine to be used; available engines: "wikidata"
  - **cache**: contains the cache configuration; the cache is used to prevent fetching data from metadata provider, but to use the local database as provider instead
    - **duration**: duration, in **days**, of the cache
    - **withBlacklist**: describes whether the blacklist should be used

### Run Robespierre

Run Robespierre with these options:

```-p <pidfile>```: to write pidfile at provided location
```-c <config_file>```: specify the JSON configuration file path

---

## Contributing 🤝🏻

da completare

## Known Issues 🩹

da completare

## Upcoming Features 🧪

da completare

---

## Changelog ⏳

View [CHANGELOG](CHANGELOG.md)

---

## License 📃

Licensed under the MIT license; you may not use this file except in compliance with the License. You may obtain a copy of the License at

You can read the entire license [HERE](LICENSE)
