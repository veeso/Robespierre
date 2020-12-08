-- Preloaded Blacklist  with some words which can't be blacklisted runtime, but should be

-- Italian words

INSERT INTO metadata_blacklist (word, language, commit_date) VALUES ("spagna", "it", "1970-01-01T00:00:00"); -- Returns Ivana Spagna otherwise
INSERT INTO metadata_blacklist (word, language, commit_date) VALUES ("40mila", "it", "1970-01-01T00:00:00"); -- Returns Mila Kunis ???
INSERT INTO metadata_blacklist (word, language, commit_date) VALUES ("secondo", "it", "1970-01-01T00:00:00"); -- Returned Q3954008 (who is him?)
INSERT INTO metadata_blacklist (word, language, commit_date) VALUES ("iva", "it", "1970-01-01T00:00:00"); -- Returns Iva Zanicchi LOL
-- INSERT INTO metadata_blacklist (word, language, commit_date) VALUES ("rossi", "it", "1970-01-01T00:00:00"); -- Returns Valentino Rossi
