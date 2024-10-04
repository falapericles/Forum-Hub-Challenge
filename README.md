# 🗂️ Forum API

[![Java](https://img.shields.io/badge/Java-17.0.2-brightgreen.svg)](https://www.oracle.com/java/technologies/javase/17-releases.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.6.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-5.6.8-blue.svg)](https://hibernate.org/)
[![JWT](https://img.shields.io/badge/Auth0-JWT-blue.svg)](https://auth0.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> Uma API de fórum desenvolvida em **Java** e **Spring Boot**, com suporte para autenticação JWT e gerenciamento de usuários, tópicos e respostas. Este projeto é uma base para construir uma aplicação de fórum com controle de usuários e integração de autenticação.

---

## 📋 Sumário

- [✨ Funcionalidades](#-funcionalidades)
- [🛠 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [📂 Estrutura do Projeto](#-estrutura-do-projeto)
- [🔧 Instalação e Configuração](#-instalação-e-configuração)
- [✅ Testes](#-testes)
- [🚀 Uso](#-uso)
- [📑 Documentação com Swagger](#-documentação-com-swagger)
- [🤝 Contribuição](#-contribuição)
- [📄 Licença](#-licença)

---

## ✨ Funcionalidades

- 📌 **Cadastro e gerenciamento de usuários** com validações de dados.
- 📌 **Criação de tópicos**, associando com cursos e usuários.
- 📌 **Respostas a tópicos** com controle de solução (marcar resposta como solução).
- 🔒 **Autenticação e autorização** com JWT.
- ⚙️ **Controle de permissões** para acesso a recursos específicos.
- 📅 **Gerenciamento de status** dos tópicos.

---

## 🛠 Tecnologias Utilizadas

- [Java 17](https://www.oracle.com/java/technologies/javase/17-releases.html)
- [Spring Boot 2.6.6](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Auth0 JWT](https://auth0.com/)
- [Hibernate 5.6.8](https://hibernate.org/)
- [H2 Database](https://www.h2database.com/html/main.html) (para testes)
- [MySQL](https://www.mysql.com/) (para produção)
- [Maven](https://maven.apache.org/) para gerenciamento de dependências

---

## 📂 Estrutura do Projeto

A estrutura do projeto segue a convenção padrão do Spring Boot, organizada em pacotes para facilitar a manutenção e expansão:
```
src
├── main
│   ├── java
│   │   └── com.example.challenge          # Pacote principal
│   │       ├── controller                 # Controladores REST
│   │       ├── dto                        # Data Transfer Objects
│   │       ├── model                      # Entidades JPA
│   │       ├── repository                 # Interfaces de repositórios
│   │       ├── service                    # Lógica de negócios
│   │       └── infra                      # Configurações e segurança
│   └── resources
│       ├── application.properties         # Configurações de ambiente
│       └── db
│           └── migration
│               └── V1__Cria_tabelas.sql   # Scripts para popular dados
└── test                                   # Testes unitários e de integração
```
---
## 🔧 Instalação e Configuração

Siga os passos abaixo para configurar e executar o projeto em sua máquina:

1. **Clone o Repositório:**
   ```bash
   git clone [https://github.com/falapericles/Forum-Hub-Challenge.git]
   cd Forum-Hub-Challenge
   ```
   
2. **Configurar o Banco de Dados:**
-  Certifique-se de ter um banco de dados MySQL disponível.
-  Crie um schema chamado forum.
-  Atualize o arquivo src/main/resources/application.properties com as credenciais do banco de dados e defina a Secret para seu token JWT.:
```bash
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

api.security.token.secret=${JWT_SECRET}
```

3. **Build e Execução do Projeto:**
	•	Compile o projeto com Maven:
```
mvn clean install
```
-  Execute a aplicação:
 
```bash
mvn spring-boot:run
```

4. **Acesso à Aplicação**:
	•	A API estará disponível em http://localhost:8080.

## ✅ Testes
- Para executar os testes do projeto, utilize o comando abaixo:

```
mvn test
```
-  O projeto possui testes para validação de endpoints e lógica de negócios utilizando JUnit e Mockito.

## 🚀 Uso

A API possui os seguintes endpoints disponíveis:

🔒 Autenticação

- POST /auth/login - Realiza o login do usuário e retorna o token JWT.

👤 Usuários

-  POST /usuarios - Cadastro de novo usuário.

📝 Tópicos

-  POST /topicos - Criar novo tópico.
-  GET /topicos - Listar todos os tópicos.
-  PUT /topicos/{id} - Atualizar/editar um tópico.
-  DELETE /topicos/{id} - Autor do tópico pode excluir sua postagem (excluindo junto as respostas associadas).

💬 Respostas

- POST /topicos/{id} - Adicionar uma nova resposta a um tópico.
- GET /topicos/{id} - Buscao tópico por ID e lista suas resposta.
- PUT /topicos/{id}/respostas/{id} - Autor do tópico pode marcar uma resposta como solução ou autor da resposta pode atualizar/editar sua resposta.
- DELETE /topicos/{id}/respostas/{id} - Autor da resposta pode excluir sua postagem.

	Nota: Para realizar chamadas aos endpoints, utilize um cliente HTTP como Postman ou Insomnia e adicione um header de Authorization com o token JWT gerado após login.

## 📑 Documentação com Swagger

O projeto inclui o **Swagger** para documentação e teste dos endpoints da API.

### Acesso à Documentação

Após iniciar a aplicação, acesse o Swagger pelo navegador em:

http://localhost:8080/swagger-ui/index.html


### Utilização

A interface do Swagger permite:

- Visualizar os endpoints disponíveis na API.
- Realizar chamadas de teste para cada endpoint diretamente pela interface.
- Visualizar a estrutura dos parâmetros e respostas.

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Siga os passos abaixo para contribuir:

	1.	Faça um fork do projeto.
	2.	Crie uma nova branch (git checkout -b feature/sua-feature).
	3.	Commit suas mudanças (git commit -m 'Adicionando nova funcionalidade').
	4.	Faça um push para a branch (git push origin feature/sua-feature).
	5.	Abra um Pull Request.

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.

Desenvolvido por
  @FalaPericles.
