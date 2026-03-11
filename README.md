🏋️ Evolua — Fitness AI Coach

Evolua é uma API backend para um aplicativo fitness que utiliza Inteligência Artificial para ajudar usuários a gerar planos de treino e dietas personalizados.

A IA Arnold atua como um coach virtual, capaz de conversar com o usuário e decidir quando criar treinos, dietas ou apenas responder perguntas.

🚀 Funcionalidades
🔐 Autenticação

Cadastro de usuário

Login com JWT

Rotas protegidas

🤖 Assistente IA (Arnold)

O Arnold analisa a mensagem do usuário e decide automaticamente entre:

CHAT
CRIAR_TREINO
CRIAR_DIETA

Exemplo:

Usuário: Arnold, monte um treino para ganhar massa
Arnold: Cria automaticamente um plano de treino

🏋️ Sistema de Treinos

Estrutura do plano:

PlanoTreino
 └── DiaTreino
      └── Exercícios

A IA define:

grupos musculares

O backend seleciona automaticamente exercícios do banco de dados.

🥗 Sistema de Dietas

Estrutura:

PlanoDieta
 └── DiaDieta
      └── Refeicoes

Cada refeição contém:

tipo de refeição
nome
calorias

🧠 Arquitetura

O projeto segue uma arquitetura baseada em camadas:

Controller
   ↓
Service
   ↓
Mapper
   ↓
Repository
   ↓
Database

Integração com IA:

Controller
   ↓
ArnoldService
   ↓
LangChain4j
   ↓
ArnoldDecisao
   ↓
Serviços de domínio

🛠️ Tecnologias Utilizadas
Backend

Java 21

Spring Boot

Spring Security

JWT

Spring Data JPA

Hibernate

Flyway

PostgreSQL

Inteligência Artificial

LangChain4j

LLM Integration

🗄️ Banco de Dados

Principais entidades:

usuario
plano_treino
dia_treino
exercicio
treino_exercicio

plano_dieta
dia_dieta
refeicao

🤖 Exemplo de resposta da IA
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

🔑 Autenticação
Login
POST /auth/login

Body:

{
  "email": "usuario@email.com",
  "senha": "123456"
}

Resposta:

JWT Token
💬 Chat com o Arnold
POST /arnold/chat

Body:

{
  "mensagem": "Arnold, monte um treino para hipertrofia"
}

A IA decide automaticamente a ação.

📂 Estrutura do Projeto
src/main/java

application
 ├── arnold
 │   ├── ArnoldService
 │   ├── ArnoldAiService
 │   └── dto
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
 
🧪 Executando o Projeto

1️⃣ Clonar o repositório
git clone https://github.com/seuusuario/evolua.git

2️⃣ Configurar banco PostgreSQL

Crie um banco:
evolua

3️⃣ Configurar application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/evolua
spring.datasource.username=postgres
spring.datasource.password=senha

4️⃣ Executar aplicação
./mvnw spring-boot:run

📈 Futuras Melhorias

Sistema de progresso físico

Histórico de conversas com a IA

Ajuste automático de dieta

Recomendações inteligentes de treino

Integração com frontend Angular

🎯 Objetivo do Projeto

Este projeto foi desenvolvido como parte de estudo e prática em:

Arquitetura backend moderna

Integração com IA

APIs REST escaláveis

Domain modeling

👨‍💻 Autor

Desenvolvido por [Arnaldo Felipe]
LinkedIn: https://www.linkedin.com/in/arnaldo-felipe-da-silva-84b883233/

Backend Developer | Java | Spring Boot | AI Integration

⭐ Se gostou do projeto, considere dar uma estrela no repositório!
