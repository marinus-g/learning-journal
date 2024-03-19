function uploadProfilePicture(file, userId) {
    const formData = new FormData();
    formData.append('file', file);
    fetch('/user/' + userId + '/avatar', {
        method: 'POST',
        body: formData,
        credentials: 'include'
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error uploading profile picture');
        }
    });
}

