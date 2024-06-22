# Progetto Sistemi Distribuiti 2023-2024 - API REST

Documentazione delle API REST del progetto Gestione Domini Web, fornite dal server web.
## `/users`

### GET

**Descrizione**: Restituisce l'elenco di tutti gli utenti presenti nel sistema.

**Parametri**: Nessuno.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **200 OK**: Elenco degli utenti con i seguenti campi:
  - `email` (stringa)
  - `name` (stringa)
  - `surname` (stringa)

**Codici di stato restituiti**:
- `200 OK`
- `500 Internal Server Error`: Problemi lato server.

### POST

**Descrizione**: Aggiunge un nuovo utente al sistema.

**Parametri**: Nessuno.

**Header**: Nessuno.

**Body richiesta**:
- **Utente** con i seguenti campi:
  - `email` (stringa)
  - `name` (stringa)
  - `surname` (stringa)

**Risposta**:
- **201 Created**: Risorsa creata, header `Location` indica l'URL della nuova risorsa.
- **409 Conflict**: Esiste già un utente con la stessa email.

**Codici di stato restituiti**:
- `201 Created`
- `409 Conflict`
- `500 Internal Server Error`: Problemi lato server.

## `/users/{email}/orders`

### GET

**Descrizione**: Restituisce tutti gli ordini di un utente specifico

**Parametri**:
- `email` (stringa): Email dell'utente.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **200 OK**: Elenco degli ordini con i seguenti campi:
 	- `id` (stringa)
	- `userEmail` (stringa)
   	- `domain` (stringa)
   	- `registration`(boolean)
   	- `paid` (double)
   	- `orderDate` (stringa)

**Codici di stato restituiti**:
- `200 OK`
- `500 Internal Server Error`: Problemi lato server.

### POST

**Descrizione**: Aggiunge un nuovo ordine per un utente specifico.

**Parametri**:
- `email` (stringa): Email dell'utente.

**Header**: Nessuno.

**Body richiesta**:
- **Order** con i seguenti campi:
 	- `id` (stringa)
	- `userEmail` (stringa)
   	- `domain` (stringa)
   	- `registration` (boolean)
   	- `paid` (double)
   	- `orderDate` (stringa)

**Risposta**:
- **201 Created**: Risorsa creata, header `Location` indica l'URL della nuova risorsa.
- **409 Conflict**: Esiste già un ordine con lo stesso ID.

**Codici di stato restituiti**:
- `201 Created`
- `409 Conflict`
- `500 Internal Server Error`: Problemi lato server.

## `/users/{email}/domains`

### GET

**Descrizione**: Restituisce tutti i domini di un utente specifico

**Parametri**:
- `email` (stringa): Email dell'utente.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **200 OK**: Elenco dei domini con i seguenti campi:
 	- `domainName` (stringa)
	- `userEmail` (stringa)
   	- `registrationDate` (stringa)
   	- `expirationDate` (stringa)

**Codici di stato restituiti**:
- `200 OK`
- `500 Internal Server Error`: Problemi lato server.

## `/users/{email}`

### GET

**Descrizione**: Restituisce uno specifico utente

**Parametri**:
- `email` (stringa): Email dell'utente.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **200 OK**: Utente con i seguenti campi:
  - `email` (stringa)
  - `name` (stringa)
  - `surname` (stringa)


**Codici di stato restituiti**:
- `200 OK`
- `500 Internal Server Error`: Problemi lato server.

## `/domains`

### GET

**Descrizione**: Restituisce l'elenco di tutti i domini.

**Parametri**: Nessuno.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **200 OK**: Elenco dei domini con i seguenti campi:
 	- `domainName` (stringa)
	- `userEmail` (stringa)
   	- `registrationDate` (stringa)
   	- `expirationDate` (stringa)

**Codici di stato restituiti**:
- `200 OK`
- `500 Internal Server Error`: Problemi lato server.

### POST

**Descrizione**: Aggiunge un nuovo dominio.

**Parametri**: Nessuno.

**Header**: Nessuno.

**Body richiesta**:
- **Domain** con i seguenti campi:
 	- `domainName` (stringa)
	- `userEmail` (stringa)
   	- `registrationDate` (stringa)
   	- `expirationDate` (stringa)

**Risposta**:
- **201 Created**: Risorsa creata, header `Location` indica l'URL della nuova risorsa.
- **409 Conflict**: Esiste già un dominio con lo stesso nome.

**Codici di stato restituiti**:
- `201 Created`
- `409 Conflict`
- `500 Internal Server Error`: Problemi lato server.

## `/domains/{domainName}`
### GET
**Descrizione**: Restituisce informazioni inerenti al dominio.

**Parametri**:
- `domainName` (stringa): Identificatore del dominio.
- `@QueryParam verbose` (boolean): Specifica se vuole conoscere tutti i dettagli del dominio o solo se è già presente o meno.

**Header**: Nessuno.

**Body richiesta**: Nessuno.

**Risposta**:
- **`Verbose = true`**
	- **200 OK**: Dominio con i seguenti campi:
 		- `domainName` (stringa)
		- `userEmail` (stringa)
   		- `registrationDate` (stringa)
   		- `expirationDate` (stringa)
- **`Verbose = false`**
	- **200 OK**: Il dominio è stato trovato ed è attivo.
	- **404 Not Found**: Il dominio cercato non è stato trovato oppure è scaduto.

**Codici di stato restituiti**:
- `200 OK`
- `404 Not Found`.
- `500 Internal Server Error`: Problemi lato server.

## `/domains/{domainName}/{property}`
### PUT
**Descrizione**: Modifica una proprietà del dominio

**Parametri**:
- `domainName` (stringa): Identificatore del dominio.
- `property` (stringa): La proprietà da modificare

**Header**: Nessuno.

**Body richiesta**:
- Una stringa rappresentate il nuovo **valore** della proprietà

**Risposta**:
- **200 OK**: La proprietà è stata modificata.
- **404 NOT FOUND**: La proprietà non è stata trovata

**Codici di stato restituiti**:
- `200 OK`
- `404 Not Found`.
- `500 Internal Server Error`: Problemi lato server.

