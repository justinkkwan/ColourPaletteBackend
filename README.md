# ColourPaletteBackend
Spring Boot Backend for Image Transform Website - https://github.com/justinkkwan/image-transform-website

This service accepts requests on port 8080 that facilitate image processing/transformation via microservices. This service does not run (meaningfully) standalone.

## Build instructions:

### Prerequisites:

* JDK 21 and up (https://openjdk.org/install/)
* Maven (https://maven.apache.org/)

### Commands:

Build:

```
mvn clean package
```

Run/test:

```
java -jar target/colourpalettebackend-0.0.1-SNAPSHOT.jar
```

Run (production):

```
java -Dspring.profiles.active=prod -jar target/colourpalettebackend-0.0.1-SNAPSHOT.jar
```