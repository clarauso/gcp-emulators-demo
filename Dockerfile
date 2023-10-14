FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
COPY scripts ./scripts
RUN ./mvnw dependency:resolve

FROM base as test
ARG FIREBASE_CLI_VERSION=v12.7.0
ARG FIREBASE_CLI_PATH=/usr/local/bin
ARG NODE_MAJOR=20
# add configuration for Firebase emulators
COPY firebase-emulators ./firebase-emulators
# install Node.js (v20) using nodesource (https://deb.nodesource.com/)
RUN apt-get update && apt-get install -y ca-certificates curl gnupg \
  && curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /etc/apt/keyrings/nodesource.gpg
RUN echo "deb [signed-by=/etc/apt/keyrings/nodesource.gpg] https://deb.nodesource.com/node_$NODE_MAJOR.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list
RUN apt-get update && apt-get install nodejs -y
# install Firebase CLI using the standalone binaries
RUN echo "Installing Firebase CLI" \
    && curl -Lo ${FIREBASE_CLI_PATH}/firebase https://firebase.tools/bin/linux/${FIREBASE_CLI_VERSION} \
    && chmod +x ${FIREBASE_CLI_PATH}/firebase \
    && firebase --version || exit
# finally run the tests
RUN ["sh", "/app/scripts/all-tests.sh"]

FROM base as build
RUN ["./mvnw", "package"]

FROM eclipse-temurin:17-jre-alpine as jar
EXPOSE 8080
COPY --from=build /app/target/gcp-emulators-demo-*.jar /gcp-emulators-demo.jar
CMD ["java", "-jar", "/gcp-emulators-demo.jar"]