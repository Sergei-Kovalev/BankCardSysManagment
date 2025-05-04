# Build
FROM gradle:jdk17-corretto-al2023 AS builder
WORKDIR /usr/src/bcsm
COPY . .
RUN chown -R gradle:gradle /usr/src/bcsm
RUN gradle clean build -x test

# Package
FROM openjdk:17-slim-buster
WORKDIR /app
COPY --from=builder /usr/src/bcsm/build/libs/BankCardSysManagment*.jar /app/BankCardSysManagment.jar
EXPOSE 9009
ENTRYPOINT ["java", "-jar", "/app/BankCardSysManagment.jar"]