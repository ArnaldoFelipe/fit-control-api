# 🏋️ Evolua — Fitness AI Coach

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Spring-Boot-green?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/AI-LangChain4j-purple?style=for-the-badge"/>
</p>

<p align="center">
Backend para um aplicativo fitness inteligente que gera <b>treinos e dietas personalizados</b> utilizando Inteligência Artificial.
</p>

---

# 🤖 Sobre o Projeto

**Evolua** é uma API backend para um aplicativo fitness que utiliza **IA para gerar planos personalizados de treino e dieta**.

O sistema possui um assistente virtual chamado **Arnold**, que atua como um **coach fitness inteligente**.

O Arnold é capaz de:

* Conversar com o usuário
* Gerar planos de treino
* Criar dietas personalizadas
* Responder dúvidas sobre fitness

Tudo isso analisando a mensagem enviada pelo usuário.

---

# 🚀 Funcionalidades

## 🔐 Autenticação

* Cadastro de usuários
* Login com **JWT**
* Rotas protegidas com **Spring Security**

---

## 🤖 Assistente IA (Arnold)

O Arnold analisa a mensagem do usuário e decide automaticamente qual ação executar:

```
CHAT
CRIAR_TREINO
CRIAR_DIETA
```

### Exemplo

```
Usuário:
Arnold, monte um treino para ganhar massa

Resposta:
Plano de treino criado automaticamente
```

---

# 🏋️ Sistema de Treinos

Estrutura utilizada:

```
PlanoTreino
 └── DiaTreino
      └── Exercícios
```

A IA define:

```
Grupos musculares
```

O backend seleciona automaticamente **exercícios do banco de dados** para cada grupo muscular.

---

# 🥗 Sistema de Dietas

Estrutura do plano de dieta:

```
PlanoDieta
 └── DiaDieta
      └── Refeições
```

Cada refeição contém:

```
Tipo da refeição
Nome
Calorias
```

---

# 🧠 Arquitetura do Sistema

A aplicação segue uma arquitetura em camadas:

```
Controller
   ↓
Service
   ↓
Mapper
   ↓
Repository
   ↓
Database
```

### Integração com IA

```
Controller
   ↓
ArnoldService
   ↓
LangChain4j
   ↓
ArnoldDecisao
   ↓
Serviços de domínio
```

---

# 🛠️ Tecnologias Utilizadas

## Backend

* **Java 21**
* **Spring Boot**
* **Spring Security**
* **JWT**
* **Spring Data JPA**
* **Hibernate**
* **Flyway**
* **PostgreSQL**

## Inteligência Artificial

* **LangChain4j**
* **LLM Integration**

---

# 🗄️ Estrutura do Banco de Dados

Principais entidades:

```
usuario

plano_treino
 └── dia_treino
      └── treino_exercicio
           └── exercicio

plano_dieta
 └── dia_dieta
      └── refeicao
```

---

# 🤖 Exemplo de Resposta da IA

```json
{
  "tipoResposta": "CRIAR_DIETA",
  "mensagem": "Vamos montar uma dieta para ganho de massa!",
  "planoTreino": null,
  "planoDieta": {
    "usuarioId": 1,
    "objetivoFitness": "HIPERTROFIA",
    "caloriasDiarias": 3000,
    "dias": [
      {
        "dia": "SEGUNDA",
        "refeicoes": [
          {
            "tpRefeicao": "CAFE_DA_MANHA",
            "nome": "Ovos mexidos com aveia",
            "calorias": 400
          },
          {
            "tpRefeicao": "ALMOCO",
            "nome": "Frango grelhado com arroz",
            "calorias": 700
          }
        ]
      }
    ]
  }
}
```

---

# 🔑 Autenticação

### Login

```
POST /auth/login
```

Body:

```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

Resposta:

```
JWT Token
```

---

# 💬 Chat com o Arnold

```
POST /arnold/chat
```

Body:

```json
{
  "mensagem": "Arnold, monte um treino para hipertrofia"
}
```

A IA decide automaticamente qual ação executar.

---

# 📂 Estrutura do Projeto

```
src
 └── main
      └── java
           └── application
                ├── arnold
                │    ├── ArnoldService
                │    ├── ArnoldAiService
                │    └── dto
                │
                ├── treino
                │
                ├── dieta
                │
                ├── usuario
                │
                ├── security
                │
                └── config
```

---

# 🧪 Como Executar o Projeto

## 1️⃣ Clonar o repositório

```
git clone https://github.com/seuusuario/evolua.git
```

---

## 2️⃣ Criar banco PostgreSQL

```
evolua
```

---

## 3️⃣ Configurar `application.properties`

```
spring.datasource.url=jdbc:postgresql://localhost:5432/evolua
spring.datasource.username=postgres
spring.datasource.password=senha
```

---

## 4️⃣ Executar a aplicação

```
./mvnw spring-boot:run
```

---

# 📈 Melhorias Futuras

* Sistema de **progresso físico**
* Histórico de conversas com a IA
* Ajuste automático de dieta
* Recomendações inteligentes de treino
* Integração com **frontend Angular**

---

# 🎯 Objetivo do Projeto

Este projeto foi desenvolvido para praticar:

* Arquitetura backend moderna
* Integração com Inteligência Artificial
* APIs REST escaláveis
* Domain Modeling com Spring Boot

---

# 👨‍💻 Autor

**Arnaldo Felipe**

Backend Developer — Java | Spring Boot | AI Integration

🔗 LinkedIn
https://www.linkedin.com/in/arnaldo-felipe-da-silva-84b883233/

---

⭐ Se este projeto te ajudou ou você gostou da ideia, considere dar uma **estrela no repositório**.
