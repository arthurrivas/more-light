# Usar uma imagem base do OpenJDK
FROM eclipse-temurin:17-jdk

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pelo Maven para o container
COPY target/more-light-*.jar app.jar

# Expor a porta usada pela aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]