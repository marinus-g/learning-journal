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

function togglePasswordView(element) {
    document.getElementById(element)
        .setAttribute('type',
            document.getElementById(element)
                .getAttribute('type') === 'password' ? 'text' : 'password')
}

function deleteAccount(id) {
    fetch('/user/' + id + '/delete', {
        method: 'DELETE',
        credentials: 'include'
    }).then(response => {
        if (response.redirected) {
            if (response.url.endsWith('/logout')) {
                fetch(response.url, {
                    method: 'POST',
                    credentials: 'include'
                }).then(value => {
                    if (value.redirected) {
                        window.location = value.url;
                    }
                }).catch(reason => console.log(reason))
            }
        }
    }).catch(reason => console.log(reason))
}


setTimeout(() => {
    document.getElementById('couldNotChangePassword').remove()
    document.getElementById('changedPassword').remove()
}, 15_000);
// Replace the URL with the current URL without the query string`s
let url = new URL(window.location.href);
url.search = '';
history.replaceState(null, null, url.toString());