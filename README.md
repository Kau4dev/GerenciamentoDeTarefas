# Sistema de Gerenciamento de Tarefas

API REST para gerenciamento de tarefas desenvolvida com Spring Boot, permitindo o gerenciamento completo de tarefas, usuários e comentários de forma eficiente e organizada.

## 🚀 Funcionalidades

- Gerenciamento completo de usuários (CRUD)
- Criação e acompanhamento de tarefas
- Sistema de comentários em tarefas
- Autenticação e autorização
- Documentação API com Swagger

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Maven
- Lombok
- MapStruct
- JUnit 5 & Mockito
- Bean Validation
- Swagger/OpenAPI

## ⚙️ Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose (para execução containerizada)
- PostgreSQL 14+ (para execução local)

## 🚀 Como Executar

### 🐳 Com Docker

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/gerenciamento-tarefas.git
cd gerenciamento-tarefas
```

2. Crie um arquivo `.env` na raiz do projeto:
```env
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

3. Execute com Docker Compose:
```bash
docker-compose up -d
```

A aplicação estará disponível em:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui/index.html
- Banco de dados: localhost:5432

### 💻 Localmente

1. Clone o repositório
2. Configure o `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_tarefas
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute:
```bash
mvn clean install
mvn spring-boot:run
```

## 🗄️ Estrutura do Projeto

```
GerenciamentoDeTarefas/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
├── .env
├── src/
│   ├── main/
│   │   ├── java/com/kau4dev/GerenciamentoDeTarefas/
│   │   │   ├── business/         # Serviços e regras de negócio
│   │   │   ├── config/           # Configurações de segurança, JWT, etc
│   │   │   ├── controller/       # Endpoints REST
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Exceções customizadas
│   │   │   ├── infrastructure/   # Entidades JPA e repositórios
│   │   │   └── mapper/           # MapStruct mappers
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/kau4dev/GerenciamentoDeTarefas/
│           ├── business/         # Testes unitários dos serviços
│           └── GerenciamentoDeTarefasApplicationTests.java
└── target/                       # Arquivos gerados (build)
```

## 📌 API Endpoints

### Autenticação
```http
# Login
POST /auth/login
{
    "login": "email@exemplo.com",
    "senha": "suaSenhaSegura"
}
# Resposta
{
    "token": "<jwt_token>"
}
```

---

### Usuários
```http
# Listar usuários
GET /usuarios

# Buscar usuário por ID
GET /usuarios/{idUsuario}

# Criar usuário
POST /usuarios
{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "suaSenhaSegura"
}

# Atualizar usuário
PUT /usuarios/{idUsuario}
{
    "nome": "João Silva Atualizado",
    "senha": "SenhaNovaSegura"
}

# Deletar usuário
DELETE /usuarios/{idUsuario}
```

---

### Tarefas
```http
# Criar tarefa para um usuário
POST /usuarios/{idUsuario}/tarefas
{
    "titulo": "Implementar API",
    "descricao": "Desenvolver endpoints REST",
    "status": "PENDENTE"
}

# Listar tarefas de um usuário
GET /usuarios/{idUsuario}/tarefas

# Buscar tarefa por ID
GET /usuarios/{idUsuario}/tarefas/{idTarefa}

# Atualizar tarefa
PUT /usuarios/{idUsuario}/tarefas/{idTarefa}
{
    "titulo": "Novo título",
    "descricao": "Nova descrição",
    "status": "CONCLUIDA"
}

# Alterar status da tarefa
PATCH /usuarios/{idUsuario}/tarefas/{idTarefa}/status
{
    "status": "CONCLUIDA"
}

# Deletar tarefa
DELETE /usuarios/{idUsuario}/tarefas/{idTarefa}
```

---

### Comentários
```http
# Criar comentário em uma tarefa
POST /usuarios/{idUsuario}/tarefas/{idTarefa}/comentarios
{
    "texto": "Iniciando implementação"
}

# Listar comentários de uma tarefa
GET /usuarios/{idUsuario}/tarefas/{idTarefa}/comentarios

# Deletar comentário
DELETE /usuarios/{idUsuario}/tarefas/{idTarefa}/comentarios/{idComentario}
```

## 🧪 Testes

Execute os testes unitários:
```bash
# Testes unitários
mvn test

# Testes com relatório de cobertura
mvn verify
```

## 📝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📊 Status do Projeto

🟢 Em desenvolvimento ativo

## 🐛 Reportando Problemas

Encontrou um bug ou tem uma sugestão? Por favor, abra uma issue:
1. Descreva claramente o problema/sugestão
2. Inclua passos para reproduzir (se aplicável)
3. Inclua exemplos relevantes

## ✨ Próximos Passos

- [x] Implementar autenticação JWT
- [x] Adicionar endpoints de login
- [x] Cobertura de testes para autenticação e autorização
- [x] Melhorar cobertura de testes dos serviços
- [x] Melhorar documentação da API (Swagger)
- [x] Melhorar tratamento de erros globais
- [ ] Adicionar paginação nas listagens
- [ ] Implementar sistema de notificações
- [ ] Adicionar testes de integração para controllers
- [ ] Melhorar exemplos e descrição dos endpoints no Swagger
