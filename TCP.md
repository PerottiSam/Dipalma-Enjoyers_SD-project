# Documentazione del Protocollo TCP

## Introduzione

Il protocollo TCP qui descritto espone un database che supporta diverse operazioni. Il protocollo è progettato in modo testuale, simile a quello di Redis, per facilitarne l'implementazione.

## Porta di Ascolto

Il server ascolta sulla porta `3030`.

## Formato delle Richieste

Le richieste sono inviate come stringhe di testo con comandi e argomenti separati da spazi. I comandi supportati sono:
- `GET`
- `CREATE`
- `EDIT`

## GET

Il comando `GET` viene utilizzato per ottenere informazioni dal database. 

Formato:

***GET*** `tipo` `id`

Esempio: GET user <samper@gmail.com>

### Estensioni

 - ***GET*** domains `userEmail` restituisce tutti i domini registrati dall'utente specificato
 - ***GET*** domain `domainName` `domainAttribute` restituisce il valore dell'attributo selezionato per un dominio specifico
 - ***GET*** orders `userEmail` restituisce tutti gli ordini di un utente specifico
- ***GET*** user `userEmail` restitusce un utente specifico



## CREATE

Il comando `CREATE` viene utilizzato per creare nuove entità nel database.

Formato:

***CREATE*** `tipo` `id` `jsonData`

Esempio: CREATE user <samper@gmail.com> `{"email":"samper@gmail.com","name":"Samuele","surname":"Perotti"}`



## EDIT

Il comando `EDIT` viene utilizzato per modificare le entità esistenti nel database.

Formato:

***EDIT*** `tipo` `id` `proprieta` `newValue`


## Formato delle Risposte

Le risposte sono inviate come stringhe di testo. Ogni risposta termina con `END` per indicare la fine della trasmissione.
