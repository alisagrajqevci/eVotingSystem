const partySection = document.getElementById("party-section");
const candidatesSection = document.getElementById("candidates-section");
const candidatesContainer = document.querySelector(".candidates-container");
const candidatesTitle = document.getElementById("candidates-title");

const API_URL = "/api/v1";

function filterResults() {
    const query = document.getElementById("search-input").value.toLowerCase();

    const partyCards = document.querySelectorAll(".party-card");
    partyCards.forEach(card => {
        const partyName = card.querySelector("h3").textContent.toLowerCase();
        card.style.display = partyName.includes(query) ? "block" : "none";
    });

    const candidateCards = document.querySelectorAll(".candidate-card");
    candidateCards.forEach(card => {
        const candidateName = card.querySelector("h4").textContent.toLowerCase();
        card.style.display = candidateName.includes(query) ? "block" : "none";
    });
}
async function fetchAndRenderParties() {
    try {
        const response = await fetch(`${API_URL}/parties`);
        if (!response.ok) throw new Error("Failed to fetch parties");
        const parties = await response.json();
        renderParties(parties);
    } catch (error) {
        console.error("Error fetching parties:", error);
        partySection.innerHTML = `<p class="error">Failed to load parties. Please try again later.</p>`;
    }
}

function renderParties(parties) {
    partySection.innerHTML = "";
    if (parties.length === 0) {
        partySection.innerHTML = `<p>No parties available.</p>`;
        return;
    }
    parties.forEach(party => {
        const partyCard = document.createElement("div");
        partyCard.className = "party-card";
        partyCard.innerHTML = `<h3>${party.name}</h3>`;
        partyCard.onclick = () => fetchAndRenderCandidates(party.id, party.name);
        partySection.appendChild(partyCard);
    });

    partySection.style.animation = "fadeInSection 1s forwards";
}

async function fetchAndRenderCandidates(partyId, partyName) {
    try {
        const response = await fetch(`${API_URL}/candidates/party/${partyId}`);
        if (!response.ok) throw new Error("Failed to fetch candidates");
        const candidates = await response.json();
        renderCandidates(candidates, partyName);
    } catch (error) {
        console.error("Error fetching candidates:", error);
        candidatesContainer.innerHTML = `<p class="error">Failed to load candidates. Please try again later.</p>`;
    }
}

function renderCandidates(candidates, partyName) {
    candidatesContainer.innerHTML = "";

    candidatesTitle.textContent = `Candidates of ${partyName}`;
    candidatesSection.style.display = "block";
    partySection.style.display = "none";

    candidates.forEach(candidate => {
        const candidateCard = document.createElement("div");
        candidateCard.className = "candidate-card";
        candidateCard.innerHTML = `
            <h4>${candidate.firstName} ${candidate.lastName}</h4>
            <p>Nr. ${candidate.numberInParty}</p>
        `;
        candidatesContainer.appendChild(candidateCard);
    });
}

function goBack() {
    candidatesSection.style.display = "none";
    partySection.style.display = "flex";
}

fetchAndRenderParties();
