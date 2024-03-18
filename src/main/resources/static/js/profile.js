function uploadProfilePicture(file) {
    const formData = new FormData();
    formData.append('file', file);
    fetch('/user/uploadProfilePicture', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error uploading profile picture');
        }
    });
}

