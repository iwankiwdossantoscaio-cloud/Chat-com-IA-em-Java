# ☕ JavaBot – Chat IA com Spring Boot + Claude

Chat de IA completo com backend em **Java (Spring Boot)** e frontend em **HTML/CSS/JS**.

## 📁 Estrutura do Projeto

```
ia-chat-java/
├── pom.xml                                         ← Dependências Maven
├── README.md
└── src/main/
    ├── java/com/iachat/
    │   ├── IaChatApplication.java                  ← Ponto de entrada
    │   ├── controller/
    │   │   └── ChatController.java                 ← REST: POST /api/chat
    │   ├── model/
    │   │   ├── ChatRequest.java                    ← Dados da requisição
    │   │   └── ChatResponse.java                   ← Dados da resposta
    │   └── service/
    │       └── AnthropicService.java               ← Chama a API Claude
    └── resources/
        ├── application.properties                  ← Configurações
        └── static/
            └── index.html                          ← Frontend (HTML/CSS/JS)
```

## ✅ Pré-requisitos

- **Java 17+** instalado → `java -version`
- **Maven 3.8+** instalado → `mvn -version`
- **API Key da Anthropic** → https://console.anthropic.com/

## 🚀 Como Rodar

### 1. Adicione sua API Key

Abra `src/main/resources/application.properties` e substitua:

```properties
anthropic.api.key=SUA_API_KEY_AQUI
```

pela sua chave real, ex.:

```properties
anthropic.api.key=sk-ant-api03-xxxxxxxxxxxxxxx
```

### 2. Compile e execute

```bash
# Entrar na pasta do projeto
cd ia-chat-java

# Rodar com Maven (baixa dependências automaticamente)
mvn spring-boot:run
```

### 3. Abra no navegador

```
http://localhost:8080
```

Pronto! O chat estará funcionando. ✅

## 🔌 Endpoints da API

| Método | URL         | Descrição              |
|--------|-------------|------------------------|
| GET    | /api/health | Verifica se está no ar |
| POST   | /api/chat   | Envia pergunta à IA    |

### Exemplo de chamada POST /api/chat

```json
// Request
{
  "message": "Qual é a capital do Brasil?",
  "history": []
}

// Response
{
  "reply": "A capital do Brasil é Brasília!",
  "success": true
}
```

## ⚙️ Configurações (application.properties)

| Propriedade              | Padrão                    | Descrição           |
|--------------------------|---------------------------|---------------------|
| `server.port`            | `8080`                    | Porta do servidor   |
| `anthropic.api.key`      | *(obrigatório)*           | Sua chave da API    |
| `anthropic.model`        | `claude-sonnet-4-20250514`| Modelo Claude usado |
| `anthropic.max-tokens`   | `1000`                    | Tamanho da resposta |

## 🛠️ Tecnologias

- **Java 17** – linguagem
- **Spring Boot 3.2** – framework web
- **java.net.http.HttpClient** – chamadas HTTP nativas (sem dependências extras)
- **Jackson** – serialização JSON
- **Anthropic Claude API** – modelo de IA
- **HTML + CSS + JS** – frontend (servido como arquivo estático)

## 💡 Dicas

- Para mudar a porta: edite `server.port` em `application.properties`
- Para gerar um JAR executável: `mvn package` → roda com `java -jar target/ia-chat-1.0.0.jar`
- O histórico da conversa é mantido no frontend e enviado a cada requisição
