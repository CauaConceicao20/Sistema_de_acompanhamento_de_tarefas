
# Task Management System - A Web-Based Employee Task Tracking Solution

Uma aplicação web completa para que organizações possam gerenciar e acompanhar tarefas entre diferentes papéis (Gerentes, Supervisores e Funcionários), com atualizações de status em tempo real e relatórios detalhados.

O sistema oferece controle de acesso baseado em papéis, permitindo que:
- **Gerentes**: gerem relatórios.
- **Supervisores**: atribuam e monitorem tarefas.
- **Funcionários**: visualizem e atualizem suas tarefas atribuídas.

Construído com Java EE e SQLite, o sistema fornece uma interface web responsiva para gerenciamento e geração de relatórios em toda a organização.

---

## 📁 Estrutura do Repositório

```
.
├── pom.xml                 
└── src/main               
    ├── java/org/sistema/acompanhamento/tarefas
    │   ├── controller      
    │   ├── exception       
    │   ├── model          
    │   ├── repository     
    │   ├── services       
    │   └── util           
    ├── resources
    │   ├── database.db   
    │   └── script.sql    
    └── webapp            
        ├── css           
        ├── js            
        └── WEB-INF
```

---

## ⚙️ Pré-requisitos

- [Java JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- [Apache Maven 3.6+](https://maven.apache.org/download.cgi)
- [Apache Tomcat 11.0.7+](https://tomcat.apache.org/download-11.cgi)
- SQLite 3.x

⚠️ **Importante**: Configure as variáveis de ambiente JAVA_HOME e MAVEN_HOME, e adicione os executáveis (`java`, `mvn`) ao PATH.

---

## 🚀 Instalação e Execução

### 0. Configure a variável de ambiente `TOMCAT_HOME`

Essa variável define o caminho de instalação do Apache Tomcat. A forma de configurá-la depende do sistema operacional e do terminal utilizado:

#### Linux/macOS

- **Bash / Zsh / Fish (temporariamente):**

```bash
export TOMCAT_HOME=/caminho/para/apache-tomcat-11.0.7
```

- **Persistente (bash):**
  Adicione ao final do arquivo `~/.bashrc` ou `~/.bash_profile`:
```bash
export TOMCAT_HOME=/caminho/para/apache-tomcat-11.0.7
```

- **Zsh:** use `~/.zshrc`

- **Fish shell:**
```fish
set -Ux TOMCAT_HOME /caminho/para/apache-tomcat-11.0.7
```

#### Windows

- **CMD (temporariamente):**
```cmd
set TOMCAT_HOME=C:\caminho\para\apache-tomcat-11.0.7
```

- **PowerShell (temporariamente):**
```powershell
$env:TOMCAT_HOME = "C:\caminho\para\apache-tomcat-11.0.7"
```

- **Variável permanente:**
    1. Painel de Controle > Sistema > Configurações Avançadas > Variáveis de Ambiente
    2. Adicione `TOMCAT_HOME` com o caminho da pasta do Tomcat

---

### 1. Clone o repositório

```bash
git clone <repository-url>
cd sistema-de-acompanhamento-de-tarefas
```

### 2. Compile o projeto

```bash
mvn clean install
```

### 3. Gere e implante o WAR

```bash
cp target/sistema-de-acompanhamento-de-tarefas-1.0-SNAPSHOT.war "$TOMCAT_HOME/webapps/"
```

#### Dica: para garantir um deploy limpo:

```bash
cd "$TOMCAT_HOME/webapps"
rm -rf sistema-de-acompanhamento-de-tarefas*
cp /caminho/projeto/target/sistema-de-acompanhamento-de-tarefas-1.0-SNAPSHOT.war .
```

> É recomendável parar o Tomcat antes de sobrescrever e iniciá-lo novamente após a cópia do WAR:

#### Parar o servidor Tomcat:
```bash
"$TOMCAT_HOME/bin/shutdown.sh"
# ou shutdown.bat no Windows
```
#### Iniciar o Servidor Tomcat
```bash
"$TOMCAT_HOME/bin/startup.sh"
# ou startup.bat no Windows
```

### 4. Acesse a aplicação

Abra no navegador:
```
http://localhost:8080/sistema-de-acompanhamento-de-tarefas-1.0-SNAPSHOT/login.jsp
```

---

## 🔑 Login

- **Gerente**: acessa relatórios e visão geral
- **Supervisor**: gerencia tarefas e funcionários
- **Funcionário**: visualiza e atualiza tarefas

---

## 🧰 Solução de Problemas

### Erro: “Database file not found”
- Confirme que `database.db` está em `src/main/resources`
- Verifique permissões do arquivo
- Verifique se o driver JDBC do SQLite está incluído no `pom.xml`

### Erro: “User not authenticated”
- Limpe cookies do navegador
- Verifique `web.xml` para timeout de sessão
- Confirme login e senha corretos

### Erro: “Unable to generate report”
- Confirme se o usuário tem papel de Gerente
- Verifique conectividade com o banco
- Verifique se o ID do Supervisor existe

---

## 🔁 Fluxo de Dados

```
[Web Browser] 
    ↓ 
[Controllers] 
    ↓ 
[Services] 
    ↓ 
[Repositories] 
    ↓ 
[SQLite DB]
```

- Autenticação: `LoginController` → `LoginService`
- Tarefas: `TarefaController` → `TarefaService`
- Relatórios: controladores específicos → `RelatorioService`
- Conexão DB: centralizada via `DataBaseConnection`
- Sessões e validação de papéis: `ControllerUtils`
- DTOs separam camadas de forma limpa
- Lógica JS cliente separada por papel

---

## ✅ Observações Finais

Se você já tiver Java, Maven e Tomcat configurados corretamente e o repositório clonado, **não é necessário instalar nenhuma dependência adicional manualmente** — o Maven cuidará disso durante o `clean install`.

Para garantir que tudo funcione:

- Verifique as variáveis de ambiente: `JAVA_HOME`, `MAVEN_HOME`, `TOMCAT_HOME`, `PATH`
- Certifique-se de que seu Tomcat esteja com a porta 8080 disponível
- Confirme que o SQLite funciona corretamente no ambiente local
