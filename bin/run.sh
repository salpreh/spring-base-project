#!/usr/bin/env bash

BOOT_FOLDER=boot/target
JAR=$1
if [ $# -lt 1 ]; then
  JAR="$BOOT_FOLDER"/$(ls "$BOOT_FOLDER" | grep -E "boot-.*\.jar$")
  if [ -z "$JAR" ]; then
    echo "Unable to find jar in boot module. Specify jar manually."
    echo "Usage: $0 [jar file]";
    exit 1;
  fi
fi

echo "Running jar $JAR"

export $(cat .env | xargs) && java -Dspring.profiles.active=local -jar "$JAR"

