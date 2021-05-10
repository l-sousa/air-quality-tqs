FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY /air-quality/pom.xml /workspace
COPY /air-quality/src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests 
FROM openjdk:11-jdk-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
