FROM amazoncorretto:8

WORKDIR /app/target
COPY .env.production /app/target/.env
COPY ./target /app/target

EXPOSE 9000

CMD java -cp bnmoboxd-soap-service-jar-with-dependencies.jar com.bnmoboxd.Main