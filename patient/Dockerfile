FROM openjdk:8-jdk-alpine

# Copier les fichiers nécessaires de l'application dans l'image Docker
COPY target/*.jar /app.jar

# Exposer le port 8080 pour permettre la communication avec l'application
EXPOSE 8081

# Commande pour démarrer l'application
CMD ["java", "-jar", "/app.jar"]