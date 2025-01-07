document.addEventListener('DOMContentLoaded', function() {
    const dogModal = document.getElementById('dogModal');
    const shelterId = document.querySelector('#shelterId').value;

    document.getElementById('dogForm').addEventListener('submit', function(e) {
        const shelterId = document.querySelector('#shelterId').value;
        console.log('Shelter ID:', shelterId);  // 打印传递的 shelterId
        if (!shelterId) {
            e.preventDefault();
            alert('Shelter ID is required');
        }
    });

    dogModal.addEventListener('show.bs.modal', function(event) {
        const button = event.relatedTarget;
        const id = button.getAttribute('data-id');
        const name = button.getAttribute('data-name');
        const breed = button.getAttribute('data-breed');
        const age = button.getAttribute('data-age');
        const shelterId = button.getAttribute('data-shelter');

        const modalTitle = dogModal.querySelector('.modal-title');
        const dogIdInput = dogModal.querySelector('#dogId');
        const nameInput = dogModal.querySelector('#name');
        const breedInput = dogModal.querySelector('#breed');
        const ageInput = dogModal.querySelector('#age');
        const shelterInput = dogModal.querySelector('#shelterId');
        const fileInput = dogModal.querySelector('#file');

        if (id) {
            modalTitle.textContent = 'Edit Dog';
            dogIdInput.value = id;
            nameInput.value = name;
            breedInput.value = breed;
            ageInput.value = age;
            shelterInput.value = shelterId;
            fileInput.removeAttribute('disabled');
            dogModal.querySelector('form').setAttribute('action', `/dogs/edit/${id}`);
        } else {
            modalTitle.textContent = 'Add New Dog';
            dogIdInput.value = '';
            nameInput.value = '';
            breedInput.value = '';
            ageInput.value = '';
            shelterInput.value = '';
            fileInput.removeAttribute('disabled');
            dogModal.querySelector('form').setAttribute('action', '/dogs/add');
        }
    });
});
