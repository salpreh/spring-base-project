#!/usr/bin/env bash

BOOT_FOLDER=target
JAR=$1
if [ $# -lt 1 ]; then
  JAR="$BOOT_FOLDER"/$(ls "$BOOT_FOLDER" | grep -E ".*\.jar$")
  if [ -z "$JAR" ]; then
    echo "Unable to find jar in target folder. Specify jar manually."
    echo "Usage: $0 [jar file]";
    exit 1;
  fi
fi

echo "Running jar $JAR"

java -Dspring.profiles.active=local -jar "$JAR"

