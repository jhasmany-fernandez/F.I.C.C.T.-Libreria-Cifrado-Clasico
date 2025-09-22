# Dockerfile para F.I.C.C.T. - Librería de Cifrado Clásico
# Multi-stage build: Build stage + Runtime stage

# =============================================
# BUILD STAGE - Compilar el proyecto con Maven
# =============================================
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Etiquetas para identificación
LABEL stage=builder
LABEL description="Build stage for F.I.C.C.T. Classic Cipher Library"

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven primero (para optimizar cache de Docker)
COPY pom.xml .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Ejecutar tests y compilar el proyecto
RUN mvn clean test compile war:war -B

# Verificar que el WAR fue generado
RUN ls -la target/ && test -f target/crypto-classic-jsp.war

# =============================================
# RUNTIME STAGE - Servidor Tomcat para producción
# =============================================
FROM tomcat:9.0-jdk21-temurin

# Etiquetas para identificación
LABEL maintainer="F.I.C.C.T. UAGRM"
LABEL description="F.I.C.C.T. Classic Cipher Library - Production Runtime"
LABEL version="1.0-SNAPSHOT"

# Variables de entorno
ENV CATALINA_HOME=/usr/local/tomcat
ENV CATALINA_BASE=/usr/local/tomcat
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

# Eliminar aplicaciones web por defecto de Tomcat
RUN rm -rf $CATALINA_HOME/webapps/*

# Copiar el WAR desde el stage de build
COPY --from=builder /app/target/crypto-classic-jsp.war $CATALINA_HOME/webapps/ROOT.war

# Crear directorio para logs
RUN mkdir -p $CATALINA_HOME/logs

# Configurar permisos
RUN chmod +x $CATALINA_HOME/bin/*.sh

# Exponer puerto 8080 (puerto estándar de Tomcat)
EXPOSE 8080

# Health check para verificar que la aplicación esté funcionando
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/ || exit 1

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]