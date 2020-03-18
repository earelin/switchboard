#!/bin/sh

/usr/local/openjdk-11/bin/java ${JAVA_OPTS} \
  -Dspring.profiles.active=${SPRING_PROFILES} -jar /opt/app.jar
