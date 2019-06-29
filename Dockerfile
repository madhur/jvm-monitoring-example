FROM java:8-jdk-alpine

COPY ./build/libs/metrics-example-1.0-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

EXPOSE 8080

ENTRYPOINT ["java","-jar","metrics-example-1.0-SNAPSHOT.jar"]
