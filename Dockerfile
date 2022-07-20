FROM openjdk:17

WORKDIR /opt/app
ADD target/app.jar /opt/app/app.jar

ARG PROFILE
USER 1000

ENV JAVA_OPTS="-noverify -XX:TieredStopAtLevel=1 -Dspring.profiles.active=${PROFILE}"

ENTRYPOINT ["/bin/sh", "-c", "java ${JAVA_OPTS} -jar /opt/app/app.jar"]