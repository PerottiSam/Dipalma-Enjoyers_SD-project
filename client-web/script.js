// Prefisso per l'API endpoint.
const API_URI = "http://localhost:8080";
var timeLimitInMinutes = 3;
var timeLimitInSeconds = timeLimitInMinutes * 60;
var timerElement = document.getElementById('timer');

//Effettua chiamata PUT per modificare la data di scadenza del dominio domainName
async function renewDomain(domainName, newExpirationDate) {
    const endpoint = `${API_URI}/domains/${domainName}/expirationDate/`;

    try {
        const response = await fetch(endpoint, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newExpirationDate)
        });

        if (!response.ok)
            return "ERROR";

        return "OK";
    } catch (error) {
        onError(`Impossibile eseguire la richiesta POST "${endpoint}"`, error);
    }
}

// Effettua la chiamata GET "/domains" e restituisce l'elenco dei domini.
async function getDomains() {
    const endpoint = `${API_URI}/domains`;

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            throw new Error(`Risposta GET "${endpoint}" con errore: ${response.status} ${response.statusText}`);

        return await response.json();
    } catch (error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }
}

//Effettua chiamata GET per recuperare tutti i domini di cui l'utente "userEmail" è proprietario
async function getUserDomains(userEmail) {
    const endpoint = `${API_URI}/users/` + userEmail + '/domains';

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            throw new Error(`Risposta GET "${endpoint}" con errore: ${response.status} ${response.statusText}`);

        return await response.json();
    } catch (error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }
}

//Effettua chiamata GET recuperare tutti gli ordini dell utente userEmail
async function getOrders(userEmail) {
    const endpoint = `${API_URI}/users/` + userEmail + '/orders';

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            throw new Error(`Risposta GET "${endpoint}" con errore: ${response.status} ${response.statusText}`);

        return await response.json();
    } catch (error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }
}

//Effettua chiamata GET per recuperare informazioni del dominio "domainName"
async function getDomain(domainName, verbose) {
    const endpoint = `${API_URI}/domains/` + domainName + '?verbose=' + verbose;

    //Verbose true, restituisce tutte le info del dominio
    if (verbose) {
        try {
            let response = await fetch(endpoint);

            if (!response.ok)
                return "DISPONIBILE";

            return await response.json();
        } catch (error) {
            onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
        }

    //Verbose false, restituisce solo se è già presente o meno (quindi disponibile)
    } else {
        try {
            let response = await fetch(endpoint);

            if (!response.ok)
                return "DISPONIBILE";

            return "ERRORE: Impossibile effettuare l'acquisto ora!\n" +
                "Il dominio è già registrato da un altro utente o un utente lo sta registrando " +
                "in questo momento";
        } catch (error) {
            onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
        }
    }
}

//Effettua chiamata GET per recuperare info dell utente "email"
async function getUser(email) {
    const endpoint = `${API_URI}/users/` + email;

    try {
        let response = await fetch(endpoint);

        if (!response.ok)
            return "ERRORE: Non esiste un account associato a questa email";

        return await response.json();
    } catch (error) {
        onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
    }
}

//Effettua chiamata POST per creare l'utente User
async function postUser(user) {
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
    } catch (error) {
        onError(`Impossibile eseguire la richiesta POST "${endpoint}"`, error);
    }
}

//Effettua chiamata POST per creare l'ordine order e legarlo all'utente "userEmail"
async function postOrder(userEmail, order) {
    const endpoint = `${API_URI}/users/` + encodeURIComponent(userEmail) + "/orders";

    try {
        const response = await fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(order)
        });

        if (!response.ok)
            throw error

        // Fa lo split e restituisce l'ultima sottostringa, che è l'ID.
        return "OK";
    } catch (error) {
        onError(`Impossibile eseguire la richiesta POST "${endpoint}"`, error);
    }
}

//Effettua chiamata POST creare il dominio "domain"
async function postDomain(domain) {
    const endpoint = `${API_URI}/domains/`;

    try {
        const response = await fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(domain)
        });

        if (!response.ok)
            return "A domain already exists";

        // Fa lo split e restituisce l'ultima sottostringa, che è l'ID.
        return "OK";
    } catch (error) {
        onError(`Impossibile eseguire la richiesta POST "${endpoint}"`, error);
    }
}

//Non mi ricordo se l'ho usata ma ho paura di rimuoverla
function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        console.log(e);
    }
    return true;
}

//Fa partire il timer di 3 minuti, un po EXTRA ma carino da vedere
function startTimer() {
    timeLimitInSeconds--;
    var minutes = Math.floor(timeLimitInSeconds / 60);
    var seconds = timeLimitInSeconds % 60;

    if (timeLimitInSeconds < 0) {
        timerElement.textContent = '00:00';
        clearInterval(timerInterval);
        return;
    }

    minutes = '0' + minutes;

    if (seconds < 10) {
        seconds = '0' + seconds;
    }

    timerElement.textContent = minutes + ':' + seconds;

    if (parseInt(minutes) === 0 && parseInt(seconds) === 0) {
        window.location.href = "registraDominio.html";
    }
}
