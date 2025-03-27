document.addEventListener("DOMContentLoaded", function() {
    const searchElectionsInput = document.getElementById("search-elections");
    const electionItems = document.querySelectorAll(".election-item");

    document.querySelectorAll('.extra-info').forEach(extraInfo => {
        if (extraInfo.style.display !== 'block') {
            extraInfo.style.display = 'none';
        }
    });

    searchElectionsInput.addEventListener("input", () => {
        const query = searchElectionsInput.value.trim().toLowerCase();

        electionItems.forEach(item => {
            const electionYear = item.querySelector(".election-year").textContent.toLowerCase();
            item.style.display = electionYear.includes(query) ? "flex" : "none";
        });
    });

    document.querySelectorAll('.election-button').forEach(button => {
        button.addEventListener('click', (event) => {
            event.preventDefault();

            const electionItem = button.closest('.election-item');
            const extraInfo = electionItem.querySelector('.extra-info');
            const electionImage = electionItem.querySelector('.election-image');

            if (extraInfo.style.display === 'none' || !extraInfo.style.display) {
                extraInfo.style.display = 'block';
                button.textContent = 'Mbyll';

                extraInfo.classList.add('expand-info');
                electionImage.classList.add('hidden-image');
            } else {
                extraInfo.style.display = 'none';
                button.textContent = 'Shiko më shumë';

                extraInfo.classList.remove('expand-info');
                electionImage.classList.remove('hidden-image');
            }
        });
    });
});
