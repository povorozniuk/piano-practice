FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY ./target/backend-0.0.1-SNAPSHOT.jar /app
RUN chmod +x backend-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080