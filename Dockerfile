FROM openjdk:11
EXPOSE 8080
ADD target/lynx-pratica-docker.jar lynx-pratica-docker.jar
ENTRYPOINT ["java", ".jar", "/lynx-pratica-docker.jar"]