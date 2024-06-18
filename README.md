# Progetto Sistemi Distribuiti 2023-2024

Gruppo: Dipalma Enjoyers\
Progetto per la gestione dell'acquisto e rinnovo di domini internet

## Componenti del gruppo

* Samuele Perotti (899817) <s.perotti4@campus.unimib.it>
* Cristiano Melon (899647) <c.melon@campus.unimib.it>

## Compilazione ed esecuzione

Sia il server Web sia il database sono applicazioni Java gestire con Maven. All'interno delle rispettive cartelle si può trovare il file `pom.xml` in cui è presenta la configurazione di Maven per il progetto. Si presuppone l'utilizzo della macchina virtuale di laboratorio, per cui nel `pom.xml` è specificato l'uso di Java 21.

Il server Web e il database sono dei progetti Java che utilizano Maven per gestire le dipendenze, la compilazione e l'esecuzione.

### Client Web

Per avviare il client Web è necessario utilizzare l'estensione "Live Preview" su Visual Studio Code, come mostrato durante il laboratorio. Tale estensione espone un server locale con i file contenuti nella cartella `client-web`.

**Attenzione**: è necessario configurare CORS in Google Chrome come mostrato nel laboratorio.

### Server Web

Il server Web utilizza Jetty e Jersey. Si può avviare eseguendo `mvn jetty:run` all'interno della cartella `server-web`. Espone le API REST all'indirizzo `localhost` alla porta `8080`.

### Database

Il database è una semplice applicazione Java. Si possono utilizzare i seguenti comandi Maven:

* `mvn clean`: per ripulire la cartella dai file temporanei,
* `mvn compile`: per compilare l'applicazione,
* `mvn exec:java`: per avviare l'applicazione (presuppone che la classe principale sia `Main.java`). Si pone in ascolto all'indirizzo `localhost` alla porta `3030`.
