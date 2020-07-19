# Robespierre

- [Robespierre](#robespierre)
  - [Configuration](#configuration)
  - [Authors](#authors)
  - [Changelog](#changelog)
  - [Checklist](#checklist)
  - [License](#license)

Current Version [0.1.0](CHANGELOG.md#010)

Robespierre is the Data Processor Daemon for Hypocracy.
Its documentation can be found in the Hypocracy repository: <https://github.com/ChristianVisintin/Hypocracy>

## Configuration

A configuration sample can be found [HERE](./config/default.json).

Here follows the key description:

- database: contains the database configuration
  - engine: the database engine (e.g. mariadb)
  - uri: database uri
  - user: database user
  - password: database password
- feed: contains the feed configuration
  - engine: the feed protocol
  - sources: contains the sources array
    - uri: feed uri
    - country: feed country
- metadata: contains the metadata configuration
  - engine: the engine to be used (e.g. wikidata)

## Authors

See [AUTHORS](AUTHORS.md)

## Changelog

See [CHANGELOG](CHANGELOG.md)

## Checklist

See Release Checklist [HERE](CHECKLIST.md)

## License

The entire body of the license can be viewed [HERE](LICENSE.txt)
