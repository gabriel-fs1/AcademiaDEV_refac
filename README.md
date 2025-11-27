# Desafio AcademiaDev - Clean Architecture Refactoring

Este repositório contém a implementação do sistema de gestão **AcademiaDev**, refatorado para seguir estritamente os princípios da **Clean Architecture** (Arquitetura Limpa), conforme proposto por Robert C. Martin (Uncle Bob).

O objetivo principal deste projeto é demonstrar o desacoplamento entre as regras de negócio e os detalhes de implementação (como interface de usuário, persistência e exportação de dados).


## 👥 Integrantes do Grupo
* **Gabriel Feitoza da Silva** - GU3046567
* **Nathalie Gonçalves Xavier** - GU3046443


## 🏗️ Estrutura do Projeto

O projeto foi modularizado em três camadas concêntricas, respeitando a **Regra da Dependência**:

```text
src/main/java/br/com/academiadev
├── domain          # (Camada Central) Entidades e Regras de Negócio Corporativas
├── application     # (Camada Intermediária) Casos de Uso e Interfaces (Portas)
├── infrastructure  # (Camada Externa) UI, Persistência e Ferramentas (Detalhes)
└── main            # Ponto de Entrada e Injeção de Dependência
```


## 🏛️ Justificativa de Design e Clean Architecture
A arquitetura foi desenhada para garantir que o núcleo do sistema (regras de negócio) não dependa de detalhes externos. Abaixo, detalho como cada requisito do Clean Architecture foi atendido:

1. A Regra da Dependência
A regra dourada estabelece que dependências de código fonte devem apontar apenas para dentro, em direção às políticas de alto nível.

- Domain: Não possui dependências de nenhuma outra camada. Contém apenas POJOs (Student, Course, SupportTicket) e lógica pura de domínio (ex: regras de limite de plano em BasicPlan).
- Application: Depende apenas do Domain. Orquestra o fluxo de dados através dos Casos de Uso (EnrollStudentUseCase, ProcessTicketUseCase).
- Infrastructure: Depende das camadas Application e Domain. É aqui que os "detalhes" residem.

2. Isolamento da Persistência (Inversão de Dependência)
- Os Casos de Uso na camada application precisam salvar e buscar dados, mas não sabem como isso é feito.
- Definição: As interfaces (contratos) dos repositórios (ex: CourseRepository, StudentRepository) são definidas na camada Application.
- Implementação: As classes concretas (ex: InMemoryCourseRepository) estão na camada Infrastructure.

Resultado: O banco de dados (neste caso, em memória) é um detalhe. Se quiséssemos mudar para MySQL ou MongoDB, alteraríamos apenas a camada infrastructure, sem tocar em uma linha sequer das regras de negócio.

3. Isolamento de Detalhes (CSV e UI)
- UI (Console): A interação com o usuário (ConsoleController, ConsoleView) está isolada na infraestrutura. O Controller converte a entrada do usuário e chama os Casos de Uso. A lógica de negócio desconhece se está sendo executada via Terminal, API REST ou Web.
- Exportação CSV: A funcionalidade de CSV (GenericCsvExporter) foi implementada como uma ferramenta de infraestrutura que utiliza Reflection. O domínio não sabe o que é um CSV.


## 📊 Diagrama de Classes
O diagrama UML que representa a estrutura das classes, relacionamentos e a divisão das camadas pode ser encontrado no arquivo:

📂 diagramaClasse.puml

Este diagrama ilustra visualmente como as camadas de Infraestrutura implementam as interfaces definidas na camada de Aplicação.


## 🚀 Como Executar
Pré-requisitos
- Java 17 ou superior
- Maven

Passos
1. Clone o repositório.
2. Navegue até a pasta raiz do projeto.
3. Compile e execute via Maven ou IDE:

```text
# Compilar e rodar os testes
mvn clean test

# Executar a aplicação
mvn clean compile exec:java -Dexec.mainClass="br.com.academiadev.main.Main"
Ao iniciar, o sistema carregará dados iniciais (InitialData) para facilitar os testes manuais via terminal.
```


## 🧪 Testes
O projeto possui testes unitários cobrindo as regras de negócio e casos de uso, localizados em src/test/java.

- Testes de Domínio: Validam regras invariantes (ex: SubscriptionPlanTest).
- Testes de Casos de Uso: Utilizam Mockito para simular o comportamento dos repositórios, garantindo que a lógica de aplicação funcione independentemente do banco de dados real.
