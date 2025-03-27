document.querySelector('.submit-button').addEventListener('click', () => {
    const popup = document.getElementById('popup');
    popup.classList.add('active');

    setTimeout(() => {
        popup.classList.remove('active');
    }, 3000);
});