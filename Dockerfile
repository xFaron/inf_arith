FROM eclipse-temurin:21

RUN mkdir /opt/app
COPY dist/MyInfArith.jar /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/MyInfArith.jar"]
CMD ["float", "div", "10", "3"]
