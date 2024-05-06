FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 9000
ARG JAR_FILE=target/totvs.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]