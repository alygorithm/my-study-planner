# MyStudyPlanner

**MyStudyPlanner** è un'applicazione full-stack progettata per supportare gli studenti nella pianificazione e nel monitoraggio delle attività di studio. Offre una gestione avanzata delle task, un timer Pomodoro integrato e statistiche dettagliate, il tutto protetto da autenticazione sicura.

Progetto sviluppato da **Alina Rosi** e **Nicholas Fiorani** per il corso di *Informatica per la Comunicazione Digitale* (A.A. 2025/2026).

##  Funzionalità Principali

- **Gestione Task**: Creazione, modifica ed eliminazione di attività con scadenze e materie.
- **Pianificazione Carico**: Distribuzione automatica del carico di studio nei giorni disponibili.
- **Focus Timer**: Timer Pomodoro (25min studio / 5min pausa) per tracciare sessioni reali.
- **Statistiche**: Dashboard con ore studiate, task completate e trend settimanali.
- **Sicurezza**: Autenticazione JWT stateless e supporto OAuth2 Google(non più implementato).

---

##  Architettura e Scelte Progettuali

L'applicazione adotta un'architettura **Client-Server** disaccoppiata per garantire scalabilità e manutenzione indipendente dei moduli.

### Stack Tecnologico
- **Frontend**: Ionic Framework + Angular (SPA, Mobile-first). Scelto per la capacità di deploy multipiattaforma (Web/Mobile) con un'unica codebase.
- **Backend**: Spring Boot + Spring Security. Scelto per la robustezza, l'ecosistema Java e la facilità di integrazione con database relazionali.
- **Database**: MariaDB. Scelto come RDBMS open-source affidabile, eseguito in container Docker per isolamento e portabilità.
- **Sicurezza**: 
  - **JWT**: Permette autenticazione stateless, ideale per API REST consumate da client mobili/web.
  - **BCrypt**: Per l'hashing sicuro delle password.
  - **OAuth2**: Integrazione con Google Identity per login sociale semplificato.

### Project Flowchart
https://drive.googl



## Istruzioni per l'Esecuzione
Segui questi passaggi per avviare il progetto partendo da zero.

Prerequisiti
- Docker Desktop (installato e attivo)
- Java JDK 17+
- Node.js v18+ e npm
- Git


### Passo 1: Clonare il Repository
git clone https://github.com/alygorithm/my-study-planner.git
cd my-study-planner

### Passo 2: Avvio del Database
Il database MariaDB viene eseguito in un container isolato.

    # Opzione A: Se hai già un container 'mystudy-db' esistente
    docker start mystudy-db

    # Opzione B: Se devi crearlo da zero
    docker run -d --name mystudy-db -p 3306:3306 \
      -e MARIADB_ROOT_PASSWORD=root \
      -e MARIADB_DATABASE=studyplanner \
      -e MARIADB_USER=studyuser \
      -e MARIADB_PASSWORD=S6868645s \
      mariadb:11

  Verifica che sia attivo con docker ps

### Passo 3: Configurazione e Avvio del backend
1. Naviga nella cartella del backend 
  cd backend/src

2. Configura le proprietà del database. Copia il file di esempio o rinominalo e modifica application.properties.example in application.properties

  Properties:
    spring.datasource.url=jdbc:mariadb://localhost:3306/studyplanner
    spring.datasource.username=studyuser
    spring.datasource.password=S6868645s
    server.port=8081
    # Inserisci qui il tuo JWT Secret generato
    jwt.secret=TUO_SECRET_KEY_GENERATO

3. Compila ed esegui il backend

  # Installa dipendenze e compila
  ./mvnw clean install -DskipTests
  # Avvia l'applicazione
  ./mvnw spring-boot:run

  Attendi il messaggio: Started MyStudyPlannerApplication in ... seconds.



  ### Avvio del frontend
  1. Aprire un nuovo terminale e vai nella cartella frontend
    cd frontend/src

  2. Installa le dipendenze (solo al primo avvio)
    npm install

  3. Avvia il serve di sviluppo
    ionic serve