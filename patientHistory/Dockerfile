FROM openjdk:11-jdk

# Copier les fichiers nécessaires de l'application dans l'image Docker
COPY target/*.jar /patient_history.jar
# Exposer le port 8080 pour permettre la communication avec l'application
EXPOSE 8082
# Commande pour démarrer l'application
CMD ["java", "-jar", "/patient_history.jar"]