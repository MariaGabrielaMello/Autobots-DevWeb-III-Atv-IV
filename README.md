# Autobots - Projeto de DevWeb III

Bem-vindo ao repositório da atividade **Autobots**, um projeto desenvolvido para a disciplina **DevWeb III** do Curso de Desenvolvimento de Software da Fatec SJC. Este projeto é um sistema para gerenciamento de dados, onde você pode visualizar informações em um banco de dados utilizando Java.

## 🛠️ Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
- **Java 17**
- **Maven** (para gerenciamento de dependências)
- **MySQL** (ou outro banco de dados compatível)
- **IDE** (Eclipse, IntelliJ IDEA, ou outra de sua preferência)
- **Git** (para clonar o repositório)

---

## 🚀 Como rodar o projeto

Siga os passos abaixo para rodar o projeto localmente.

### 1. Acessar o repositório no Git
1. Abra o terminal.
2. Clone o repositório usando o comando:
   git clone [https://github.com/seu-usuario/autobots.git](https://github.com/MariaGabrielaMello/Autobots-DevWeb-III-Atv-I.git)
3. Abrir o projeto no Software de sua preferência (Eclipse, IntelliJ IDEA, etc.)
   
### 2. Configurar o Banco de Dados no MySQL Workbench ou outro cliente de sua preferência).
1. No arquivo "application", no caminho automanager\src\main\resources altere spring.datasource.username=usuario e
spring.datasource.password=senha os valores "usuario" e "senha" para o seu usuário e sua senha do Mysql
2. Crie um banco de dados com o nome autobots
3. Copie o arquivo database-setup e cole no Mysql

### 3. Execute o Run
No caminho automanager\src\main\java\com\autobots\automanager dê o comando run no arquivo AutomanagerApplication

📚 Tecnologias Utilizadas
- Java (Spring Boot)
- MySQL
- Maven
- Git
