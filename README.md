# 🎓 Escola API — Spring Boot + Thymeleaf + Azure SQL Server

Este projeto tem como objetivo disponibilizar uma aplicação Java em nuvem utilizando recursos da Microsoft Azure, com persistência em banco de dados Azure SQL Database e posterior publicação da aplicação Web no Azure App Service. A criação da infraestrutura inicial do banco foi realizada com Azure CLI, que permite provisionar recursos de forma automatizada pela linha de comando.

---

## 🗂️ Arquitetura em Camadas

```
HTTP Request
     │
     ▼
┌──────────────────────────────────────────┐
│            CONTROLLER LAYER              │
│  controller/web/   → Thymeleaf MVC       │
│  controller/api/   → REST JSON (@/api)   │
└──────────────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│             SERVICE LAYER                │
│  CursoService  │  AlunoService           │
│  (regras de negócio)                     │
└──────────────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│           REPOSITORY LAYER               │
│  CursoRepository  │  AlunoRepository     │
│  (Spring Data JPA)                       │
└──────────────────────────────────────────┘
     │
     ▼
┌──────────────────────────────────────────┐
│              MODEL LAYER                 │
│  Curso (master)  ──FK──►  Aluno (detail) │
└──────────────────────────────────────────┘
     │
     ▼
  Azure SQL Server (PaaS)
```

## 📁 Estrutura do Projeto

```
src/main/
├── java/com/escola/api/
│   ├── EscolaApiApplication.java
│   ├── model/
│   │   ├── Curso.java              ← Entidade master
│   │   └── Aluno.java              ← Entidade detail (FK → Curso)
│   ├── repository/
│   │   ├── CursoRepository.java
│   │   └── AlunoRepository.java
│   ├── service/
│   │   ├── CursoService.java
│   │   └── AlunoService.java
│   └── controller/
│       ├── web/                    ← Controllers MVC (Thymeleaf)
│       │   ├── HomeWebController.java
│       │   ├── CursoWebController.java
│       │   └── AlunoWebController.java
│       └── api/                    ← Controllers REST (/api/*)
│           ├── CursoApiController.java
│           └── AlunoApiController.java
└── resources/
    ├── templates/                  ← Thymeleaf (HTML renderizado server-side)
    │   ├── fragments/
    │   │   └── layout.html         ← Base layout + navbar + footer
    │   ├── index.html              ← Dashboard home
    │   ├── curso/
    │   │   ├── lista.html
    │   │   └── form.html
    │   └── aluno/
    │       ├── lista.html
    │       └── form.html
    ├── static/                     ← Assets estáticos (CSS, JS)
    │   ├── css/style.css
    │   └── js/app.js
    └── application.properties
```

## 🌐 Rotas da Aplicação

### Web (Thymeleaf — renderizado no servidor)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/` | Dashboard |
| GET | `/cursos` | Listar cursos |
| GET | `/cursos/novo` | Formulário novo curso |
| POST | `/cursos/salvar` | Salvar curso |
| GET | `/cursos/editar/{id}` | Formulário edição |
| GET | `/cursos/deletar/{id}` | Remover curso |
| GET | `/alunos` | Listar alunos |
| GET | `/alunos/novo` | Formulário novo aluno |
| POST | `/alunos/salvar` | Salvar aluno |
| GET | `/alunos/editar/{id}` | Formulário edição |
| GET | `/alunos/deletar/{id}` | Remover aluno |

### API REST (JSON — /api/*)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/cursos` | Listar |
| POST | `/api/cursos` | Criar |
| PUT | `/api/cursos/{id}` | Atualizar |
| DELETE | `/api/cursos/{id}` | Remover |
| GET | `/api/alunos` | Listar |
| POST | `/api/alunos` | Criar |
| PUT | `/api/alunos/{id}` | Atualizar |
| DELETE | `/api/alunos/{id}` | Remover |

## 🚀 Deploy

### Opção A – Azure CLI (script automático)
```bash
chmod +x docs/azure_cli_deploy.sh
./docs/azure_cli_deploy.sh
```

### Opção B – GitHub Actions (CI/CD)
1. Configure os **Secrets** no GitHub (veja docs/)
2. Faça `git push origin main`

### Opção C – Maven Plugin
```bash
# Configure seu subscriptionId no pom.xml
mvn azure-webapp:deploy
```

### Opção D – VS Code / IntelliJ
- VS Code: extensão **Azure Tools** → clique direito → *Deploy to Web App*
- IntelliJ: plugin **Azure Toolkit** → *Tools > Azure > Deploy*

## ⚙️ Variáveis de Ambiente

| Variável | Descrição |
|----------|-----------|
| `AZURE_SQL_SERVER` | Nome do servidor SQL (sem .database.windows.net) |
| `AZURE_SQL_DB` | Nome do banco |
| `AZURE_SQL_USER` | Usuário SQL |
| `AZURE_SQL_PASSWORD` | Senha SQL |
| `APPINSIGHTS_INSTRUMENTATIONKEY` | Chave do Application Insights |

## 📊 Monitoração (Application Insights)
- Rastreamento automático de requisições HTTP
- Métricas de performance (CPU, memória, tempo de resposta)
- Log de exceções e erros
- Live Metrics Stream em tempo real
