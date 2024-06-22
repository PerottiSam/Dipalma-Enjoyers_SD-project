// Prefisso per l'API endpoint.
const API_URI = "http://localhost:8080";
var timeLimitInMinutes = 3;
var timeLimitInSeconds = timeLimitInMinutes * 60;
var timerElement = document.getElementById('timer');

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


async function getDomain(domainName, verbose) {
    const endpoint = `${API_URI}/domains/` + domainName + '?verbose=' + verbose;

    if (verbose) {
        try {
            let response = await fetch(endpoint);

            if (!response.ok)
                return "DISPONIBILE";

            return await response.json();
        } catch (error) {
            onError(`Impossibile eseguire la richiesta GET "${endpoint}"`, error);
        }
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

function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        console.log(e);
    }
    return true;
}


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
