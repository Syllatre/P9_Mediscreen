FROM openjdk:11-jdk

# Copier les fichiers nécessaires de l'application dans l'image Docker
COPY target/*.jar /risk.jar

# Exposer le port 8080 pour permettre la communication avec l'application
EXPOSE 8083

# Commande pour démarrer l'application
CMD ["java", "-jar", "/risk.jar"]