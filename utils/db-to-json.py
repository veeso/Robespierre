#!/usr/bin/python3

"""
Convert all articles, subject and topics from MariaDB Robespierre database into a ready-to-use JSON dataset for Json-Server

Developed by Christian Visintin (C) 2020
"""

# Getopts
from getopt import getopt, GetoptError
# JSON
import json
# Locale
from locale import getdefaultlocale
# Mariadb
import mariadb
#Argv
from sys import argv, exit
# Typings
from typing import List, Tuple, Union

DEFAULT_COUNTRY = getdefaultlocale()[0].split("_")[1]
DEFAULT_LOCALE = getdefaultlocale()[0].split("_")[0]

PROGRAM_NAME = "db-to-json.py"
USAGE = "%s [OPTIONS...] [FILE]\n\
    Where options are:\n\
    \t-c\t<country>\tISO3166 (default: %s)\n\
    \t-d\t<database>\tSpecify database name\n\
    \t-D\t\t\tDebug\n\
    \t-l\t<language>\tSpecify client language (default: %s)\n\
    \t-H\t<host>\tSpecify db host\n\
    \t-p\t<password>\tSpecify user password\n\
    \t-u\t<user>\tSpecify username\n\
    \t-v\t\t\tVerbose\n\
    \t-h\t\t\tShow this page\n\
    " % (PROGRAM_NAME, DEFAULT_COUNTRY, DEFAULT_LOCALE)

KNRM = "\x1B[0m"
KRED = "\x1B[31m"
KGRN = "\x1B[32m"
KYEL = "\x1B[33m"
KBLU = "\x1B[34m"
KMAG = "\x1B[35m"
KCYN = "\x1B[36m"
KWHT = "\x1B[37m"

# Globals
debug = None
verbose = None


def print_err(message: str):
    """
    Print error

    :param message: message to print
    :type message: str
    """
    print("%s%s%s" % (KRED, message, KNRM))

def print_info(message: str):
    """
    Print information.
    The message will be displayed only when set in verbose mode

    :param message: message to print
    :type message: str
    """
    global verbose
    if verbose:
        print("%s%s%s" % (KYEL, message, KNRM))

def print_debug(message: str):
    """
    Print debug.
    The message will be displayed only when set in debug mode

    :param message: message to print
    :type message: str
    """
    global debug
    if debug:
        print("%s%s%s" % (KCYN, message, KNRM))

def usage(err: str = None):
    """
    Print usage
    """
    if err:
        print_err(err)
    print("%s" % USAGE)

# Mariadb

def connect(database: str, username: str, password: Union[str, None] = None, host: Union[str, None] = None):
    """
    Connect to the database with the provided parameters

    :param database: database name
    :param username: username to use
    :param password: password (optional)
    :type database: str
    :type username: str
    :type password: str
    :returns Tuple(Connection, Cursor)
    """
    # Connect to database
    if password:
        connection = mariadb.connect(user=username, database=database, password=password, host=host)
    else:
        connection = mariadb.connect(user=username, database=database, host=host)
    print_info("Established connection to database %s" % database)
    # Get cursor
    cursor = connection.cursor()
    return (connection, cursor)

def disconnect(dbtuple: tuple):
    """
    Disconnect from database

    :param dbtuple
    :type dbtuple: Tuple
    """
    dbtuple[1].close() # Close cursor
    dbtuple[0].close() # Close db connection
    print_info("Disconnected from database")

def get_topics(dbconn: tuple, data: list, lang: str):
    """
    Get topics from hypocracy database

    :param dbconn
    :param data
    """
    cursor = dbconn[1]
    query = "SELECT topic.id, name_%s, desc_%s FROM topic, topic_data WHERE topic_data_id = topic_data.id;" % (lang, lang)
    print_debug(query)
    cursor.execute(query)
    result = cursor.fetchall()
    # Iterate over topics
    for topic in result:
        topic_data = {"class": "topic", "id": topic[0], "name": topic[1], "desc": topic[2]}
        print_debug("Found topic %s" % topic_data)
        data.append(topic_data)

def get_subjects(dbconn: tuple, data: list, lang: str):
    """
    Get subjects from hypocracy database

    :param dbconn
    :param data
    :param lang
    """
    cursor = dbconn[1]
    query = "SELECT subject.id, name, birthdate, citizenship, birthplace, image, biography.%s, occupation.%s FROM subject, biography, occupation WHERE subject.bio = biography.id AND subject.occupation = occupation.id;" % (lang, lang)
    print_debug(query)
    cursor.execute(query)
    result = cursor.fetchall()
    # Iterate over subjects
    for subj in result:
        subj_data = {"class": "subject", "id": subj[0], "name": subj[1], "birthdate": subj[2].strftime("%Y-%m-%d"), "citizenship": subj[3], "birthplace": subj[4], "image": subj[5], "biography": subj[6], "occupation": subj[7]}
        print_debug("Found subject %s" % subj_data)
        data.append(subj_data)

def get_articles(dbconn: tuple, data: list, lang: str, country: str):
    """
    Get articles from hypocracy

    :param dbconn
    :param data
    :param lang
    :param country
    """
    cursor = dbconn[1]
    query = "SELECT id, title, brief, link, date, state, country FROM article WHERE country = \"%s\";" % country
    print_debug(query)
    cursor.execute(query)
    result = cursor.fetchall()
    # Iterate over articles
    for article in result:
        article_obj = {"class":"article", "id": article[0], "title": article[1], "brief": article[2], "link": article[3], "date": article[4].strftime("%Y-%m-%dT%H:%M:%SZ"), "state": article[5], "country": article[6]}
        # Get topics associated to this article
        topics = []
        query = "SELECT topic.id, name_%s, desc_%s FROM topic, topic_data, article_topic WHERE topic_data_id = topic_data.id AND topic_id = topic.id AND article_id = \"%s\";" % (lang, lang, article[0])
        print_debug(query)
        cursor.execute(query)
        result = cursor.fetchall()
        # Iterate over article topics
        for topic in result:
            topic_data = {"class": "topic", "id": topic[0], "name": topic[1], "desc": topic[2]}
            topics.append(topic_data)
        # Get subjects associated to this article
        subjects = []
        query = "SELECT subject.id, name, birthdate, citizenship, birthplace, image, biography.%s, occupation.%s FROM subject, biography, occupation, article_subject WHERE subject.bio = biography.id AND subject.occupation = occupation.id AND subject_id = subject.id AND article_id = \"%s\";" % (lang, lang, article[0])
        print_debug(query)
        cursor.execute(query)
        result = cursor.fetchall()
        # Iterate over subjects
        for subj in result:
            subj_data = {"class": "subject", "id": subj[0], "name": subj[1], "birthdate": subj[2].strftime("%Y-%m-%d"), "citizenship": subj[3], "birthplace": subj[4], "image": subj[5], "biography": subj[6], "occupation": subj[7]}
            subjects.append(subj_data)
        article_obj["topics"] = topics
        article_obj["subjects"] = subjects
        print_debug("Found article %s" % article_obj)
        data.append(article_obj)

def read_json(file: str) -> Union[dict, None]:
    """
    Read JSON file

    :param file
    """
    try:
        hnd = open(file, 'r')
        data = hnd.read()
        hnd.close()
        data = json.loads(data)
        return data
    except (IOError, json.JSONDecodeError) as err:
        return None

def write_json(file: str, data: dict):
    """
    Write JSON

    :param file
    :param data
    """
    hnd = open(file, 'w')
    hnd.write(json.dumps(data, indent=2))
    hnd.close()

def main(argc: int, argv: List[str]) -> int:
    global debug
    global verbose
    country = DEFAULT_COUNTRY
    lang = DEFAULT_LOCALE
    database_name = "hypocracy"
    dbhost = "localhost"
    username = "root"
    password = None
    json_file = None
    try:
        optlist, args = getopt(argv, "c::l::u::p:d::H::Dvh")
        #Iterate over options
        for opt, arg in optlist:
            if opt == "-u":
                username = arg
            elif opt == "-p":
                password = arg
            elif opt == "-H":
                dbhost = arg
            elif opt == "-d":
                database_name = arg
            elif opt == "-D":
                debug = True
            elif opt == "-c":
                country = arg
            elif opt == "-l":
                lang = arg
            elif opt == "-v":
                verbose = True
            elif opt == "-h":
                usage()
                return 255
        #Look for JSON file
        if args:
            json_file = args[0]
        else:
            usage("Missing JSON file")
            return 255
    except GetoptError as err:
        usage(err)
        return 255

    # Connect to database
    try:
        dbconn = connect(database_name, username, password, dbhost)
    except Exception as err:
        print_err("Could not connect to database: %s" % err)
        return 1
    # Get data
    data = []
    get_topics(dbconn, data, lang)
    get_subjects(dbconn, data, lang)
    get_articles(dbconn, data, lang, country)
    # Disconnect from database
    disconnect(dbconn)
    # Read output file
    data_json = read_json(json_file)
    # Update keys
    if not data_json:
        # Set data_json to data
        data_json = data
    else:
        # Check if search exists
        if 'search' in data_json or 'get' in data_json:
            data_json['search'] = data
            data_json['get'] = data
        else:
            data_json = data
    # Write JSON
    try:
        write_json(json_file, data_json)
    except IOError as err:
        print_err("Could not write JSON file: %s" % err)
        return 1
    return 0

if __name__ == "__main__":
    exit(main(len(argv) - 1, argv[1:]))
