FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} DubboServer-V1.0.jar
RUN bash -c 'touch /DubboServer-V1.0.jar'
EXPOSE 8800
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/DubboServer-V1.0.jar"]