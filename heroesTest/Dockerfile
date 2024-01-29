FROM eclipse-temurin:21_35-jdk

EXPOSE 8080

COPY target/*.jar superheroes_api.jar

ENTRYPOINT ["java", "-jar", "superheroes_api.jar"]