#FROM findepi/graalvm:java17-native
FROM openjdk:17.0.2-oraclelinux8

MAINTAINER Lion Li

RUN mkdir -p /ruoyi/monitor/logs

WORKDIR /ruoyi/monitor

ENV LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_OPTS=""

EXPOSE 9100

ADD ./target/ruoyi-monitor.jar ./app.jar

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar app.jar \
           -XX:+HeapDumpOnOutOfMemoryError -Xlog:gc*,:time,tags,level -XX:+UseZGC ${JAVA_OPTS}
