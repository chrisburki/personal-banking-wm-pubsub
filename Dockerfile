#FROM openjdk:11
FROM openjdk:11-jre-slim
#FROM docker pull gcr.io/distroless/java:11
VOLUME /tmp
ARG JAR_FILE=build/libs/wm-pubsub-0.1.0.jar
ADD ${JAR_FILE} wm-pubsub.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/wm-pubsub.jar"]
#RUN mkdir /opt/application
#COPY .build/libs/wm-pubsub-0.1.0.jar /opt/application
#CMD ["java,"-jar","/opt/application/wm-pubsub-0.1.0.jar"]