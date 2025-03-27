document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('.contact-form');

    form.addEventListener('submit', function(event) {

        form.style.pointerEvents = 'none';
        const submitButton = form.querySelector('button');
        submitButton.textContent = 'Dërgo Mesazhin...';

        setTimeout(function() {
            form.reset();
            submitButton.textContent = 'Dërgo Mesazhin';
            form.style.pointerEvents = 'auto';
            alert("Mesazhi u dërgua me sukses!");
        }, 2000);
    });
});
