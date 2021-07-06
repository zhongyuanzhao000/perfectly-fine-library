FROM openjdk:11
# Do not copy stubs jar
ARG JAR_FILE=target/*[^s].jar 
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]