#!/bin/bash

mvn clean package spring-boot:repackage -DskipTests

FILE=target/mini-search-engine-0.0.1-SNAPSHOT.jar
if [[ -f "$FILE" ]]; then
	exit 0 
fi
exit 1
