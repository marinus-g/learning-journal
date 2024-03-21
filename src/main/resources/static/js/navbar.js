function logout() {
    fetch('/logout', {
        method: 'POST',
        credentials: 'include',

    })
        .then(response => {
            if (response.redirected) {
                window.location = response.url;
            }
        }).catch(error => console.error(error));
}