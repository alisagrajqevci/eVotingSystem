async function fetchPartiesAndCandidates() {
    try {
        const partyResponse = await fetch('/api/v1/parties');
        const parties = await partyResponse.json();

        const partiesList = document.getElementById('parties-list');
        partiesList.innerHTML = '';

        parties.forEach(party => {
            const partyLabel = document.createElement('label');
            partyLabel.innerHTML = ` <input type="radio" name="party" value="${party.id}" /> ${party.name} `;
            partiesList.appendChild(partyLabel);
        });

        document.querySelectorAll('input[name="party"]').forEach(radio => {
            radio.addEventListener('change', async (event) => {
                const partyId = event.target.value;
                await loadCandidates(partyId);
            });
        });
    } catch (error) {
        showFeedback('error', 'Error fetching parties.');
    }
}

async function loadCandidates(partyId) {
    try {
        const candidateResponse = await fetch(`/api/v1/candidates/party/${partyId}`);
        const candidates = await candidateResponse.json();

        const candidatesList = document.getElementById('candidates-list');
        candidatesList.innerHTML = '';

        candidates.forEach(candidate => {
            const candidateLabel = document.createElement('label');
            candidateLabel.innerHTML = `
                <input type="checkbox" name="candidate" value="${candidate.id}" /> ${candidate.numberInParty}
            `;
            candidatesList.appendChild(candidateLabel);
        });
    } catch (error) {
        showFeedback('error', 'Error fetching candidates.');
    }
}

function showFeedback(type, message) {
    const feedbackMessage = document.getElementById('feedback-message');
    feedbackMessage.className = `feedback ${type}`;
    feedbackMessage.textContent = message;
}

window.onload = fetchPartiesAndCandidates;

document.getElementById('ballotForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    const selectedParty = document.querySelector('input[name="party"]:checked');
    const selectedCandidates = document.querySelectorAll('input[name="candidate"]:checked');
    const voterId = document.querySelector('input[name="voterId"]').value;

    if (!selectedParty) {
        showFeedback('error', 'Please select a party.');
        return;
    }

    if (selectedCandidates.length < 1 || selectedCandidates.length > 10) {
        showFeedback('error', 'Please select between 1 and 10 candidates.');
        return;
    }

    try {
        const response = await fetch('/api/v1/ballots', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                voterId: voterId,
                partyId: selectedParty.value,
                candidateIds: Array.from(selectedCandidates).map(c => c.value),
            }),
        });

        if (response.ok) {
            window.location.href = '/ballot/confirmation';
        } else {
            const errorMsg = await response.text();
            showFeedback('error', `Failed to submit vote: ${errorMsg}`);
        }
    } catch (error) {
        showFeedback('error', 'An error occurred while submitting the vote.');
    }
});
