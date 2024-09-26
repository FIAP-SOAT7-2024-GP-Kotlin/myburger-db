#!/bin/bash

# Set the path to the Liquibase executable
LIQUIBASE_PATH=liquibase
CHANGELOG_MASTER_PATH=src/main/resources/db/changelog/master.xml
LOG_LEVEL=debug
LIQUIBASE_COMMAND_UPDATE_SHOW_SUMMARY=verbose
LIQUIBASE_COMMAND_DRIVER=org.postgresql.Driver
LIQUIBASE_CLASSPATH="$PWD/lib/*"


# Set the connection URL, username, and password for the database
. ./.env

# Execute the Liquibase update command with the provided parameters
$LIQUIBASE_PATH update \
    --url=$DATABASE_URL \
    --username=$DATABASE_USER --password=$DATABASE_PASSWORD \
    --log-level=$LOG_LEVEL \
    --changelog-file=$CHANGELOG_MASTER_PATH
    
