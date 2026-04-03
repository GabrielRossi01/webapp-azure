# 🎓 Escola API — Java + Spring Boot + Thymeleaf + Azure SQL

API REST desenvolvida com **Java 17 + Spring Boot 3**, com persistência em **Azure SQL Database (PaaS)**
e deploy no **Azure App Service** via **Azure CLI**.

---

## 📁 Arquivos no repositório

| Arquivo | Descrição |
|---|---|
| `ddl-tables.sql` | Script DDL com criação das tabelas, PKs e FKs |
| `api-operations.json` | JSON com exemplos das operações GET, POST, PUT e DELETE |
| `scripts-cli.txt` | Comandos Azure CLI utilizados para criação dos recursos e deploy |
| `src/` | Código-fonte completo da aplicação |
| `README.md` | How-to de implantação em nuvem |

---

## 🗄️ Modelo de Dados

Duas tabelas com relacionamento **master-detail**:

- **curso** (master) → `id_curso`, `nome`, `descricao`, `carga_horaria`
- **aluno** (detail) → `id_aluno`, `nome`, `email`, `matricula`, `id_curso (FK)`

---

## ☁️ Implantação na Azure — Passo a Passo

### Pré-requisitos

- [Azure CLI](https://learn.microsoft.com/pt-br/cli/azure/install-azure-cli) instalado
- [Java 17](https://adoptium.net/) e [Maven](https://maven.apache.org/) instalados
- Conta ativa na Azure

---

### 1. Login na Azure

```bash
az login
```

---

### 2. Criar o Resource Group

```bash
az group create \
  --name <SEU_RESOURCE_GROUP> \
  --location <SUA_REGIAO>
```

---

### 3. Criar o SQL Server (PaaS)

```bash
az sql server create \
  --name <SEU_SQL_SERVER> \
  --resource-group <SEU_RESOURCE_GROUP> \
  --location <SUA_REGIAO> \
  --admin-user <USUARIO> \
  --admin-password <SENHA>
```

---

### 4. Criar o Banco de Dados

```bash
az sql db create \
  --resource-group <SEU_RESOURCE_GROUP> \
  --server <SEU_SQL_SERVER> \
  --name <SEU_BANCO> \
  --edition Basic
```

---

### 5. Configurar Firewall do SQL Server

Permitir que outros serviços Azure acessem o servidor:

```bash
az sql server firewall-rule create \
  --resource-group <SEU_RESOURCE_GROUP> \
  --server <SEU_SQL_SERVER> \
  --name AllowAzureServices \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0
```

---

### 6. Criar o App Service Plan

```bash
az appservice plan create \
  --name <SEU_PLAN> \
  --resource-group <SEU_RESOURCE_GROUP> \
  --sku B1 \
  --is-linux
```

---

### 7. Criar o Web App (Java 17)

```bash
az webapp create \
  --name <SEU_APP_NAME> \
  --resource-group <SEU_RESOURCE_GROUP> \
  --plan <SEU_PLAN> \
  --runtime "JAVA:17-java17"
```

---

### 8. Configurar as Variáveis de Ambiente

```bash
az webapp config appsettings set \
  --name <SEU_APP_NAME> \
  --resource-group <SEU_RESOURCE_GROUP> \
  --settings \
    SPRING_DATASOURCE_URL="jdbc:sqlserver://<SEU_SQL_SERVER>.database.windows.net:1433;database=<SEU_BANCO>;encrypt=true;trustServerCertificate=false;" \
    SPRING_DATASOURCE_USERNAME="<USUARIO>" \
    SPRING_DATASOURCE_PASSWORD="<SENHA>"
```

---

### 9. Gerar o JAR da Aplicação

```bash
mvn clean package -DskipTests
```

---

### 10. Realizar o Deploy

```bash
az webapp deploy \
  --name <SEU_APP_NAME> \
  --resource-group <SEU_RESOURCE_GROUP> \
  --src-path target/app.jar \
  --type jar
```

---

### 11. Verificar os Logs

```bash
az webapp log tail \
  --name <SEU_APP_NAME> \
  --resource-group <SEU_RESOURCE_GROUP>
```

Aguarde a mensagem:
```bash
HikariPool-1 - Start completed.
Started EscolaApiApplication
```


---

## 🔗 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/cursos` | Lista todos os cursos |
| POST | `/api/cursos` | Cria um novo curso |
| PUT | `/api/cursos/{id}` | Atualiza um curso |
| DELETE | `/api/cursos/{id}` | Remove um curso |
| GET | `/api/alunos` | Lista todos os alunos |
| POST | `/api/alunos` | Cria um novo aluno |
| PUT | `/api/alunos/{id}` | Atualiza um aluno |
| DELETE | `/api/alunos/{id}` | Remove um aluno |

> Consulte `api-operations.json` para os exemplos completos de request/response.

---

## 📊 Monitoração

A aplicação utiliza **Azure Application Insights** para monitoramento de requisições,
erros e métricas de performance.

## 📹 Vídeo do funcionamento da aplicação

> 🎬 Clique na imagem abaixo para assistir no YouTube

[Assista ao vídeo](https://youtu.be/EbYR3NALDLs)
