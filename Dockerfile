FROM amazoncorretto:21

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

COPY build/libs/SpecDoctor-BE-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILE} -jar /app.jar"]
