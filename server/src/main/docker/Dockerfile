FROM openjdk:11-jre

ARG user=spring
ARG group=spring
ARG uid=999
ARG gid=999
ARG http_port=8080

ENV JAVA_OPTS -Djava.security.egd=file:/dev/./urandom
ENV DATABASE_HOST localhost
ENV	DATABASE_PORT 3306
ENV	DATABASE_NAME switchboard
ENV	DATABASE_USERNAME switchboard
ENV	DATABASE_PASSWORD secret
ENV SPRING_PROFILES prod

HEALTHCHECK --timeout=3s \
  CMD curl -f http://localhost:${http_port}/actuator/health

EXPOSE ${http_port}

COPY *.jar /opt/app.jar
COPY *.sh /bin/

RUN set -x \
  && groupadd -g ${gid} ${group} \
  && useradd -u ${uid} -g ${group} -N -m -s /bin/bash ${user} \
  && chmod 755 /bin/*.sh

USER ${user}

CMD spring-boot.sh
