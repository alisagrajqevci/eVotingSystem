function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.classList.remove('active'));

    const activeSection = document.getElementById(sectionId);
    if (activeSection) {
        activeSection.classList.add('active');
    }

    const sidebarLinks = document.querySelectorAll('.sidebar a');
    sidebarLinks.forEach(link => link.classList.remove('active'));
    const activeLink = document.querySelector(`.sidebar a[onclick="showSection('${sectionId}')"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    }
}

window.onload = function() {
    const hash = window.location.hash.substring(1);
    if (hash) {
        showSection(hash);
    } else {
        showSection('results'); // Default section
    }

    fetchRealTimeResults();
    fetchFeedback();
};

function fetchRealTimeResults() {
    fetch('/api/v1/voted-parties')
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length > 0) {
                const labels = Object.keys(data);
                const values = Object.values(data);

                const maxVotes = Math.max(...values);
                const winningPartyIndex = values.indexOf(maxVotes);
                const winningParty = labels[winningPartyIndex];

                if (resultsChart) {
                    resultsChart.data.labels = labels;
                    resultsChart.data.datasets[0].data = values;
                    resultsChart.update();
                } else {
                    const ctx = document.getElementById('resultsChart').getContext('2d');
                    resultsChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Vota për Partitë',
                                data: values,
                                backgroundColor: 'rgba(0, 123, 255, 0.6)',
                                borderColor: 'rgba(0, 123, 255, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                }

                const winnerSection = document.querySelector('.results-chart');
                const winnerElement = document.getElementById('winner');
                if (!winnerElement) {
                    const winnerDiv = document.createElement('div');
                    winnerDiv.id = 'winner';
                    winnerDiv.innerHTML = `<h4>Fituesi aktual: <strong>${winningParty}</strong> me <strong>${maxVotes}</strong> vota!</h4>`;
                    winnerSection.appendChild(winnerDiv);
                } else {
                    winnerElement.innerHTML = `<h4>Fituesi aktual: <strong>${winningParty}</strong> me <strong>${maxVotes}</strong> vota!</h4>`;
                }
            } else {
                document.getElementById('results-list').innerHTML = '<p>Ende nuk ka rezultate.</p>';
            }
        })
        .catch(error => {
            console.error('Gabim në marrjen e rezultateve:', error);
            document.getElementById('results-list').innerHTML =
                '<p>Gabim në ngarkimin e rezultateve.</p>';
        });
}

function fetchFeedback() {
    fetch('/api/v1/admin/admin-dashboard/feedback')
        .then(response => response.json())
        .then(data => {
            const feedbackList = document.getElementById('feedback-list');
            if (data.length > 0) {
                data.forEach(message => {
                    const feedbackItem = document.createElement('div');
                    feedbackItem.classList.add('feedback-item');

                    const feedbackContent = `
                        <h4>${message.name}</h4>
                        <p><strong>Email:</strong> ${message.email}</p>
                        <p><strong>Message:</strong> ${message.message}</p>
                    `;

                    feedbackItem.innerHTML = feedbackContent;
                    feedbackList.appendChild(feedbackItem);
                });
            } else {
                feedbackList.innerHTML = "<p>No feedback received yet.</p>";
            }
        })
        .catch(error => {
            console.error('Error fetching feedback:', error);
            const feedbackList = document.getElementById('feedback-list');
            feedbackList.innerHTML = "<p>Error loading feedback.</p>";
        });
}
