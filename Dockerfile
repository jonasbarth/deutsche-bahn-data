FROM amazoncorretto:11

WORKDIR /deutsche-bahn-data/app

COPY . .

RUN ./gradlew bootJar

COPY deutsche-bahn-data-acquisition/build/libs/deutsche-bahn-data-acquisition-*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dbearer.token=dec0445e9c29bf5bc4e8c1641c6ba1fc", "app.jar"]
