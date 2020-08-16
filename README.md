# Robespierre

- [Robespierre](#robespierre)
  - [Configuration](#configuration)
  - [Debugging](#debugging)
    - [Clean database up](#clean-database-up)
  - [Authors](#authors)
  - [Changelog](#changelog)
  - [Checklist](#checklist)
  - [License](#license)

Current Version [0.1.0](CHANGELOG.md#010)

Robespierre is the Data Processor Daemon for Hypocracy.
Its documentation can be found in the Hypocracy repository: <https://github.com/ChristianVisintin/Hypocracy>

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

## Authors

See [AUTHORS](AUTHORS.md)

## Changelog

See [CHANGELOG](CHANGELOG.md)

## Checklist

See Release Checklist [HERE](CHECKLIST.md)

## License

The entire body of the license can be viewed [HERE](LICENSE.txt)
