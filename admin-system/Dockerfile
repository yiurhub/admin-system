FROM openjdk:17

MAINTAINER Yiur <3227341995@qq.com>

ENV WORK "www"

RUN mkdir $WORK

COPY target/*.jar /$WORK/app.jar

WORKDIR /$WORK

CMD ["--server.port=9786"]

EXPOSE 9786

ENTRYPOINT ["java", "-jar", "/www/app.jar"]