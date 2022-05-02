FROM openjdk:11

RUN apt-get update && apt-get install bash

RUN mkdir -p /usr/app/

ENV PROJECT_HOME /usr/app/


COPY build/libs/procer-0.0.1-SNAPSHOT.jar $PROJECT_HOME/procer-0.0.1-SNAPSHOT.jar

WORKDIR $PROJECT_HOME

EXPOSE  8080
CMD ["java", "-jar", "./procer-0.0.1-SNAPSHOT.jar"]

