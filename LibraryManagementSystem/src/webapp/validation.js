document.addEventListener('DOMContentLoaded', function() {
    const registrationForm = document.getElementById('registrationForm');

    registrationForm.addEventListener('submit', function(event) {
        event.preventDefault();
        event.stopPropagation();

        // Reset previous validation states
        const inputs = registrationForm.querySelectorAll('input');
        inputs.forEach(input => input.classList.remove('is-invalid', 'is-valid'));

        // Validation checks
        let isValid = true;

        // First Name Validation
        const firstName = document.getElementById('firstName');
        if (firstName.value.trim() === '') {
            firstName.classList.add('is-invalid');
            isValid = false;
        } else {
            firstName.classList.add('is-valid');
        }

        // Last Name Validation
        const lastName = document.getElementById('lastName');
        if (lastName.value.trim() === '') {
            lastName.classList.add('is-invalid');
            isValid = false;
        } else {
            lastName.classList.add('is-valid');
        }

        // Email Validation
        const email = document.getElementById('email');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value)) {
            email.classList.add('is-invalid');
            isValid = false;
        } else {
            email.classList.add('is-valid');
        }

        // Phone Validation
        const phone = document.getElementById('phone');
        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phone.value)) {
            phone.classList.add('is-invalid');
            isValid = false;
        } else {
            phone.classList.add('is-valid');
        }

        // If all validations pass, submit the form or send data
        if (isValid) {
            console.log('Form is valid. Ready to submit!');
            // TODO: Add AJAX call to backend or form submission logic
        }

        registrationForm.classList.add('was-validated');
    });

    // Real-time validation on input
    const inputs = registrationForm.querySelectorAll('input');
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            this.classList.remove('is-invalid', 'is-valid');
        });
    });
});