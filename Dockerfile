# imagem padrao com maven + java 21
FROM maven:3.9-eclipse-temurin-21

# cria e entra na pasta.
WORKDIR /app

# copia o arquivo (pom.xml) que esta na raiz do projeto para o /app (.) <- pasta atual.
COPY pom.xml .

# Executa um comando de forma silencisa para chamar o maven que ira gerenciar as dependencias do pom.xml
# baixa as dependencias descritas no pom.xml para não dependenr de internet para executa-los.
RUN mvn dependency:go-offline -B

# copia a pasta src (contem os testes) para dentro do container
COPY src ./src

# comando para baixar os navegadores e as dependencias que o S.O precisa para executar estes navegadores.
RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"

# comando para executar os testes e não quebrar os testes se algum falhar
CMD ["mvn","test", "-Dmaven.test.failure.ignore=true"]