// Prefisso per l'API endpoint.
const API_URI = "http://localhost:8080";

// Effettua la chiamata GET "/domains" e restituisce l'elenco dei domini.
async function getDomains() {
    const endpoint = `${API_URI}/domains`;

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            throw new Error(`Risposta GET "${endpoint}" con errore: ${response.status} ${response.statusText}`);

        return await response.json();
    } catch(error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }   
}

async function getDomain(domainName) {
    const endpoint = `${API_URI}/domains/` + domainName;

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            return "DISPONIBILE";

        return await response.json();
    } catch(error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }   
}

async function postUser(user){
    const endpoint = `${API_URI}/users/`;

    try {
        const response = await fetch(endpoint, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
          });
    
          if (!response.ok)
            return "An account already exists with this email";
    
          // Fa lo split e restituisce l'ultima sottostringa, che è l'ID.
          return "OK";
    } catch(error) {
        onError(`Impossibile eseguire la richiesta POST "${endpoint}"`, error);
    }
}