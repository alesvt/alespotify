# Usa una imagen base de Java
FROM openjdk:23-jdk-slim

# Copia el archivo JAR de la aplicación
COPY target/alespotify-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación escucha
EXPOSE 8080

# Define el comando de inicio
CMD ["java", "-jar", "/app.jar"]