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
https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&dark=0#R%3Cmxfile%3E%3Cdiagram%20name%3D%22Pagina-1%22%20id%3D%22fwghWq5rLBZAKxihC3Nl%22%3E7V1bc5s4FP41nn3aDEJcH3PrZbbdzTSdbvtIQLa1xcgLchLn169kwAZBYhkkIOm609gSGONzznfukmfwcvX4Pg3Wy88kQvHMNKLHGbyamSawocee%2BMy2mAFeMbNIcVTMHSZu8RMqJo1idoMjlNVOpITEFK%2FrkyFJEhTS2lyQpuShftqcxPVPXQcL1Ji4DYO4Ofs3jugyn%2FVM9zD%2FAeHFsvxk4Pj5kVVQnlx8k2wZROShMgWvZ%2FAyJYTmr1aPlyjm1Cvpkr%2Fv3TNH9zeWooTKvAGvH%2F%2F9GtGvP%2F%2F49mV5Ab5stx9uf2%2B5SjGV0W1JA3YdRm42uHhYYopu10HIjzwwlrO5JV3FbATYyzmO40sSk3T3Pji3%2BT8%2BTxJamc8fbD6jKfmJKkec3YMdye%2FhPog3xT18%2Bm1D2S0iNsue0oA9JztZi8kCJ8XpKKXosfItCkK8R2SFaLplpywrvPIKxjwc%2BApKZhVXsYvhtj4MCqFa7C98oDt7UZD%2BBDaY%2BXVR1BDGJl%2FIJg3RcZZW%2BZdE5xwKbBTGQZbhsM62lGySCPH7M9gIPWL6vTjCX%2F%2Fg82d2Mbp6rJx2tS0HjCXb79VB5V18eHjbblS%2BrwB0kC4QfeErwYY4HOduyasUxQHF93W6Vlj%2BEk%2BLT7ghmDFhLxTQq0uF6QqXyFlUvKuKSOFCJqxfyBeuk9OlcZ2dhO2%2FdXehgzLYT5dkdbfJTsf%2BfG6GYRvGI%2BfOsVsxnoVoEWOkCsxeHcsitbWB2VYIZqgIzAcA%2F6hAux3MkwYbBF3B5gqoNYZFmyOFtj3zwMmIiwLkzVsR54Qeupu3cfZjkqEUM9xxQ8r%2FhCmKUPKEgxh3gqFz3KbuXbvnGKHOqkKFSHSbSGSXvS2GJKVLsiBJEF8fZgUsHs75RMi6YOI%2FiNJt4fYGG0rqLO4O36M21bTaudkKYXm8upZ%2FZjnc8c%2F%2Fwjp6zWFB5z7PbzWgQyCykdsGOt9xYdBq5r6gBWanMyrrgpjjDwUxTyHCHGW2zlSOFn9SRtGCqoyiJVzIGhafACiUH%2F%2BNaWjwjIz11NAiz21nYJ6rtMqj8nxCETJwtMgKNOqyYomKRrOs%2BFL2e8AQ9bLqHzsxu6eLCN%2Bzlwv%2BMiRpyiQHlYfYd64c7WTtJeJagUdQTEeoC2wNhcgF5htT1%2FYz3FSsrh1vYHVtSmGwT%2BBqIy%2By2nDomXewPR18HoYMiJwKccxzwdmG%2F2W3yJ%2FWKb4PKHt1yY9gzryU3Bcp493pTGMoSzOJzjcQMhaWmNZThkfgqsRji%2Fs9Fh4NFXgEnhY82mL6YuCsLdCeSJp7IWq3i3eebdmtKcJrFqalHI8RhxvepZVW7P5IJ5RJhLiWUJqBolpUhzJfJcq8MVAmiZi%2BPmS%2FZJ1S52IUMneJCyRZ447JmpKa03HDuabRWy2yhHIB0GbFX5LzA3XZt6N1MrZRuU7ChCRIoHcxxcKXRcKGIS%2Bps%2FkLTj8cBvF5cWCFo6gFFA1G3H7sRH%2FYpL8jeFFCCtMSM1Pq6C%2Fl4E6U%2Fn%2F%2BpYn%2B%2B7H2qNJU6cXmVY3RvNhOCaFDDuhH9Vi%2FhJA5rjG3dDupfaud0a78YqQ4XGKUaSzFeC%2FVwxrBgzoOqKzMmBOKDZXkaqDfEx3P1GWE0H%2Fo1DpUqkpHZXq33HpR%2BVOsS2HfUnk%2FJMsF%2FAN639%2BYFp0zb2XG227zjPfTU9BJgx53xB1onBmVhzmQWwK1m7AueZar3GwxNzKfx7t8C2H%2FF3h2CWfnjBzGJtPYW%2BCKJSFtfXSWyowAHLc63EWbgZoqU1YpNMdUZiUbplVNKFC1xxPaQ0wrlKAQ49ra2nQslX0WcNwY68yVLxVUPAOPiV4NT77nHUHUbnSzy2TvovRB2%2Bv68Vsqp5EtgzV%2FuVnF5yEl1TzEp%2BAOxTckwxQTno%2B4I5SSVUuighIBjmRDY5ww3JXrcVrtWP55inAFhdSRUDDVVg43FYLKblm2MU1vWxIAtp7%2BUktwPxzXP3MqD7d%2BQc0Bl62y139cETglytbczGRramYSOim8gVcA2Cqj85GFpVN0rkdY9JT5953q5dqsgTvX7RdkY5yo%2F%2FyJuQLdfOHTm9gcbeUeR2VUaY%2FqCmuw2o6eJjbbnJTVnmR14kPXnjSZSFNsIrRepL%2B65SFK%2FaNXk5CWBJunx88Rweb5o4Jtkgsf35GQmU12CyjLeIQ7EO48fb3Zcj06A5P5JiXsKtqaAEXyAkNcn6DObZDzxwam7y2KEXfLeClmgUma6Ou39MaxII6j0II49pgWREMd2%2BnbF9huQRyB2a5Y49G9Vt6eIti%2B4WwTxHhX6mSsyX5musDmwCbYfP8ANk8X2JQu5x13xbyG2EhP04gtVIFc1ziD1ceg0HNVJqnGFYFOLSR6iq6urUJ0ztM02FZOWHMhyJ6XLFdU466wQ9iR80vJPIhYfgdqdb3cHg1DJroWC7xJFqjQ8jP4rpOel2htEdxXF%2BryXl1TJapfTSuFLDqhFsVuiewdVbGXXJtW58UlWa1x3K13rEPU4lq8mezAAVMX3lQ6Um7LJmDTjFrepBUVK86euM%2FmESt66vmDWF1XbvO%2FgbXBV25uGWd2Bphq0wpi2mgwraAyl%2BG%2BmlyG5pUuTl%2Bt0I%2Bnk0xVvC9ygUZWJgj1rcR2hHJDY9GDOlpLuTATXamnbaVkI3%2BgrXReGqpXSX9tKyVdMBj9lUZxLQ1EbdvtZUwB01N24evY7qOzqcFuSMNxxmvc0lYEbNfd%2B8QA0xl4uZmncie3qcqjhp1p3FHl8dT4xxb3C3wpPnnDwm4pFHbJbb1PlvV%2Biyd1eOfuaxJ1V9hU6CRJF%2FMEoLOkC3cxtKBPsmHn42pNMp4SMEgapJjHNmvmD5pGvv1b3saz34Aqo5sIa4t4bDEj5AxTnvVUZhXtli2SxtJBkrrEm9guzIIY%2BKLjLwt4Wyj3A2PgbZg9udrAgDW4zyQql5ivix4ybWU4yxfJr20dpq907%2BMJrReSXoapw8fovQSkH0%2F1dwZ2SKXvOy%2BNVQEljRlA0fMxjGEMoq%2ByvdyX%2FP2B7gFosXK5lH5wRPo1VrxL4%2F863HLx5w4AsE5xzBvmFXQ11KJn3rD4urdDn6RrXuuLjDTul2A7jQjL16RZgKGyD84fdTtSHXsrj2pwJ7cd6XUW4p3sx4P4q6K11VZw81VufdwGAsX21TzJvr5JWwnFfWuB0SeJ5YsbFL4WU1l%2B7f%2BLlTXdARqpBm0r0oAhFRj9ahwQgaFxzRowVMZHZZKo%2B%2B%2Bz6d0IoozDJ5IdFMtmwBC7u6XTg0J%2Byh%2F6F2AMqbBjokDW13czJJClXN5fjQMNW9Zh%2FQQbpoTQKmIYmZafSYT4Gf8B%3C%2Fdiagram%3E%3C%2Fmxfile%3E#%7B%22pageId%22%3A%22fwghWq5rLBZAKxihC3Nl%22%7D



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

  # Properties:
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
