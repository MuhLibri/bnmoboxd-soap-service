FROM maven:3-amazoncorretto-8 as build

WORKDIR /app

COPY . .

RUN --mount=type=cache,target=/root/.m2 mvn clean install assembly:single

FROM amazoncorretto:8

WORKDIR /target

COPY --from=build /app/target .

EXPOSE 9000

CMD java -cp bnmoboxd-soap-service-jar-with-dependencies.jar com.bnmoboxd.Main