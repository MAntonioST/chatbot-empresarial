# 🤖 Chatbot Empresarial

Sistema de chatbot empresarial com IA

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen?style=flat&logo=spring)
![Spring AI](https://img.shields.io/badge/Spring%20AI-Latest-blue?style=flat)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação](#-instalação)
- [Configuração](#-configuração)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Testes](#-testes)
- [Deploy](#-deploy)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

---

## 🎯 Sobre o Projeto

O **Chatbot Empresarial** é uma solução completa para integração de inteligência artificial conversacional em ambientes corporativos. Utilizando o modelo Llama 3.3 70B através da API gratuita do Groq, oferece respostas de alta qualidade com latência reduzida e custo zero.

### Principais Diferenciais

- ✅ **Gratuito**: Utiliza Groq API (sem custos)
- ✅ **Rápido**: Latência média de 500ms
- ✅ **Escalável**: Arquitetura baseada em Spring Boot
- ✅ **Profissional**: Código limpo, organizado e documentado
- ✅ **Moderno**: Java 21, Records, Lombok

---

## ✨ Funcionalidades

- 💬 **Chat com IA**: Conversação natural com Llama 3.3 70B
- 🔄 **Gerenciamento de Sessões**: Controle de contexto por usuário
- 📊 **Métricas de Uso**: Tracking de tokens consumidos
- 🛡️ **Exception Handling**: Tratamento global de erros
- 📝 **Logs Estruturados**: Monitoramento completo
- 🐳 **Docker Ready**: PostgreSQL containerizado
- 🧪 **Testes Automatizados**: Scripts de validação

---

## 🚀 Tecnologias

### Backend
- **Java 21** (LTS)
- **Spring Boot 3.4.1**
- **Spring AI** (integração com LLMs)
- **Spring Data JPA** (persistência)
- **Lombok** (redução de boilerplate)
- **Maven** (gerenciamento de dependências)

### Banco de Dados
- **PostgreSQL 16**
- **Docker Compose** (orquestração)

### IA/LLM
- **Groq API** (inferência)
- **Llama 3.3 70B Versatile** (modelo)

### Ferramentas
- **Jackson** (serialização JSON)
- **SLF4J + Logback** (logging)
- **Bean Validation** (validação de dados)

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                    CLIENTE (HTTP)                       │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                  CONTROLLER LAYER                       │
│  ┌──────────────────────────────────────────────────┐  │
│  │          ChatController                          │  │
│  │  - POST /api/v1/chat                            │  │
│  │  - GET  /api/v1/chat/health                     │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                   SERVICE LAYER                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │          ChatbotService                          │  │
│  │  - Lógica de negócio                            │  │
│  │  - Gerenciamento de sessões                     │  │
│  │  - Integração com Spring AI                     │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                 SPRING AI LAYER                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │          ChatClient                              │  │
│  │  - Abstração de alto nível                      │  │
│  │  - Prompt templates                             │  │
│  │  - Response parsing                             │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│                   GROQ API                              │
│           Llama 3.3 70B Versatile                       │
└─────────────────────────────────────────────────────────┘
```

### Estrutura de Pacotes

```
src/main/java/com/techcorp/chatbot/
├── config/
│   └── GroqConfig.java              # Configuração Spring AI + Groq
├── controller/
│   └── ChatController.java          # REST endpoints
├── service/
│   └── ChatbotService.java          # Lógica de negócio
├── dto/
│   ├── ChatRequest.java             # Request DTO (Record)
│   └── ChatResponse.java            # Response DTO (Record)
├── exception/
│   └── GlobalExceptionHandler.java  # Tratamento global de erros
└── ChatbotEmpresarialApplication.java
```

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ☕ **Java 21 JDK** ([Download](https://adoptium.net/))
- 🐳 **Docker** e **Docker Compose** ([Download](https://www.docker.com/))
- 📦 **Maven 3.9+** (ou use o wrapper `./mvnw`)
- 🔑 **Conta Groq** (gratuita - [Criar conta](https://console.groq.com/))

### Verificar Instalações

```bash
# Java
java -version
# Deve mostrar: openjdk version "21.x.x"

# Docker
docker --version
docker compose version

# Maven (opcional, projeto inclui wrapper)
mvn -version
```

---

## 🔧 Instalação

### 1. Clonar o Repositório

```bash
git clone https://github.com/MAntonioST/chatbot-empresarial.git
cd chatbot-empresarial
```

### 2. Obter API Key do Groq

1. Acesse: [https://console.groq.com/](https://console.groq.com/)
2. Crie uma conta (gratuita, sem cartão de crédito)
3. Navegue para: [https://console.groq.com/keys](https://console.groq.com/keys)
4. Clique em **"Create API Key"**
5. Dê um nome: `chatbot-empresarial-dev`
6. **Copie a chave** (começa com `gsk_...`)

### 3. Configurar Variáveis de Ambiente

```bash
# Criar arquivo .env na raiz do projeto
cat > .env << 'EOL'
GROQ_API_KEY=gsk_sua_chave_aqui
EOL

# Adicionar .env ao .gitignore (se ainda não estiver)
echo ".env" >> .gitignore
```

### 4. Subir o PostgreSQL

```bash
docker compose up -d
```

Verificar se está rodando:

```bash
docker compose ps
# Deve mostrar: chatbot-postgres ... Up
```

---

## ⚙️ Configuração

### Arquivo `application.yaml`

```yaml
spring:
  application:
    name: chatbot-empresarial

  datasource:
    url: jdbc:postgresql://localhost:5432/chatbot_db
    username: chatbot_user
    password: chatbot_pass
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    open-in-view: false

groq:
  api:
    key: ${GROQ_API_KEY}

server:
  port: 8090

logging:
  level:
    com.techcorp.chatbot: INFO
```

### Personalização

Para alterar o modelo ou parâmetros, edite `GroqConfig.java`:

```java
OpenAiChatOptions options = OpenAiChatOptions.builder()
    .withModel("llama-3.3-70b-versatile")  // Modelo
    .withTemperature(0.7)                   // Criatividade (0-1)
    .withMaxTokens(1000)                    // Tamanho máximo da resposta
    .build();
```

**Modelos disponíveis no Groq:**
- `llama-3.3-70b-versatile` (recomendado)
- `llama-3.1-8b-instant` (mais rápido)
- `mixtral-8x7b-32768` (contexto longo)
- `gemma2-9b-it` (Google Gemma)

---

## 🚀 Uso

### Iniciar a Aplicação

```bash
# Carregar variáveis de ambiente
export $(cat .env | xargs)

# Compilar e executar
./mvnw spring-boot:run
```

**Ou use o script helper:**

```bash
chmod +x start.sh
./start.sh
```

### Aguardar Inicialização

Procure no log:

```
INFO  c.t.c.ChatbotEmpresarialApplication : Started ChatbotEmpresarialApplication in 3.005 seconds
```

---

## 📡 API Endpoints

### Base URL

```
http://localhost:8090/api/v1/chat
```

### Endpoints Disponíveis

#### 1. Health Check

Verifica se o serviço está funcionando.

**Request:**
```bash
curl http://localhost:8090/api/v1/chat/health
```

**Response:**
```
Chatbot está funcionando! 🤖
```

---

#### 2. Enviar Mensagem

Envia uma mensagem para o chatbot e recebe resposta da IA.

**Request:**
```bash
curl -X POST http://localhost:8090/api/v1/chat   -H "Content-Type: application/json"   -d '{
    "message": "Explique o que é Spring Boot em 2 linhas"
  }'
```

**Request Body:**
```json
{
  "message": "Sua pergunta aqui",
  "sessionId": "opcional-id-da-sessao"
}
```

**Response:**
```json
{
  "response": "Spring Boot é um framework Java que simplifica o desenvolvimento de aplicações...",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "timestamp": "2025-12-30T13:45:00",
  "tokensUsed": 87
}
```

**Campos:**
- `response`: Resposta gerada pela IA
- `sessionId`: ID da sessão (gerado automaticamente se não fornecido)
- `timestamp`: Data/hora da resposta
- `tokensUsed`: Número de tokens consumidos

---

## 🧪 Testes

### Script de Testes Automatizados

```bash
chmod +x test-chatbot.sh
./test-chatbot.sh
```

**Saída esperada:**
```
🧪 Testando Chatbot Empresarial com Groq

1️⃣  Health Check...
✅ Chatbot está funcionando! 🤖

2️⃣  Pergunta: O que é Java 21?
✅ Resposta recebida

🎉 Todos os testes passaram!
```

### Testes Manuais

```bash
# Teste 1: Health
curl http://localhost:8090/api/v1/chat/health

# Teste 2: Pergunta simples
curl -X POST http://localhost:8090/api/v1/chat   -H "Content-Type: application/json"   -d '{"message": "Olá!"}'

# Teste 3: Pergunta técnica
curl -X POST http://localhost:8090/api/v1/chat   -H "Content-Type: application/json"   -d '{"message": "Explique microserviços"}'
```

---

## 📦 Deploy

### Gerar JAR

```bash
./mvnw clean package -DskipTests
```

O JAR será gerado em: `target/chatbot-empresarial-0.0.1-SNAPSHOT.jar`

### Executar JAR

```bash
export GROQ_API_KEY=gsk_sua_chave_aqui
java -jar target/chatbot-empresarial-0.0.1-SNAPSHOT.jar
```

### Docker

Criar `Dockerfile`:

```dockerfile
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8090

ENV GROQ_API_KEY=""

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build e run:

```bash
# Build
docker build -t chatbot-empresarial .

# Run
docker run -p 8090:8090   -e GROQ_API_KEY=gsk_sua_chave_aqui   chatbot-empresarial
```

---

## 📊 Performance

| **Métrica** | **Valor** |
|-------------|-----------|
| Latência média | ~500ms |
| Tokens/segundo | ~100 |
| Limite (Groq free) | 30 req/min |
| Custo | $0.00 |

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga os passos:

1. Fork o projeto
2. Crie uma branch: `git checkout -b feature/minha-feature`
3. Commit suas mudanças: `git commit -m 'feat: adiciona minha feature'`
4. Push para a branch: `git push origin feature/minha-feature`
5. Abra um Pull Request

---

## 📝 Roadmap

- [ ] Implementar memória de contexto (histórico de conversas)
- [ ] Adicionar RAG (busca em documentos)
- [ ] Criar interface web (React)
- [ ] Implementar autenticação JWT
- [ ] Adicionar rate limiting
- [ ] Dashboard de analytics
- [ ] Suporte a múltiplos idiomas
- [ ] Function calling / Tools

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👥 Autores

- **Marco Antonio Teixeira** - *Desenvolvimento inicial* - [GitHub](https://github.com/MAntonioST)

---

## 🙏 Agradecimentos

- [Spring AI](https://spring.io/projects/spring-ai) - Framework de IA
- [Groq](https://groq.com/) - API gratuita de LLM
- [Meta](https://ai.meta.com/) - Modelo Llama 3.3
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework base

---

## 📞 Suporte

- 📧 Email: m.antonyteixeira@gmail.com
- 💬 Issues: [GitHub Issues](https://github.com/MAntonioST/chatbot-empresarial/issues)
- 📚 Documentação: [Wiki](https://github.com/MAntonioST/chatbot-empresarial/wiki)

---

## 🔗 Links Úteis

- [Documentação Spring AI](https://docs.spring.io/spring-ai/reference/)
- [Groq API Docs](https://console.groq.com/docs/)
- [Llama 3.3 Model Card](https://ai.meta.com/llama/)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela! ⭐**

Made with ❤️ and ☕ by [Marco Antonio Teixeira]

</div>
