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
- Swagger: http://localhost:8080/swagger-ui.html
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
src/
├── main/
│   ├── java/com/kau4dev/GerenciamentoDeTarefas/
│   │   ├── business/      # Regras de negócio e serviços
│   │   ├── controller/    # Endpoints da API
│   │   ├── dto/          # Objetos de transferência
│   │   └── infrastructure/# Configurações, entidades e repos
│   └── resources/
│       └── application.properties
└── test/
    └── java/             # Testes unitários
```

## 📌 API Endpoints

### Usuários
```http
# Listar usuários
GET /api/usuarios

# Buscar usuário
GET /api/usuarios/{id}

# Criar usuário
POST /api/usuarios
{
    "nome": "João Silva",
    "email": "joao@email.com"
}

# Atualizar usuário
PUT /api/usuarios/{id}
{
    "nome": "João Silva Atualizado",
    "email": "joao.novo@email.com"
}

# Deletar usuário
DELETE /api/usuarios/{id}
```

### Tarefas
```http
# Criar tarefa
POST /api/tarefas
{
    "titulo": "Implementar API",
    "descricao": "Desenvolver endpoints REST",
    "status": "PENDENTE",
    "usuarioId": 1
}

# Listar tarefas
GET /api/tarefas

# Atualizar status
PUT /api/tarefas/{id}/status
{
    "status": "CONCLUIDA"
}
```

### Comentários
```http
# Criar comentário
POST /api/comentarios
{
    "texto": "Iniciando implementação",
    "tarefaId": 1,
    "usuarioId": 1
}

# Listar comentários por tarefa
GET /api/comentarios?tarefaId={id}
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

- [ ] Implementar autenticação JWT
- [ ] Adicionar paginação nas listagens
- [ ] Implementar sistema de notificações
- [ ] Melhorar cobertura de testes
