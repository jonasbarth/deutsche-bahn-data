FROM amazoncorretto:11

WORKDIR /deutsche-bahn-data/app

COPY . .

RUN ["chmod", "+x", "./gradlew"]
RUN ./gradlew bootJar

RUN mv deutsche-bahn-data-acquisition/build/libs/deutsche-bahn-data-acquisition-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dbearer.token=dec0445e9c29bf5bc4e8c1641c6ba1fc", "app.jar"]
