FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app-V1.0.jar
RUN bash -c 'touch /app-V1.0.jar'
EXPOSE 8800
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app-V1.0.jar"]