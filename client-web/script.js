// Prefisso per l'API endpoint.
const API_URI = "http://localhost:8080";

// Effettua la chiamata GET "/keyboards" e restituisce l'elenco delle tastiere.
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