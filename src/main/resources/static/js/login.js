function openPasswordReset() {
    fetch('/login/password-reset')
        .then(response => response.text())
        .then(html => {
            console.log(document.getElementById('passwordResetContainer'))
            document.getElementById('passwordResetContainer').innerHTML = html;
            $('.modal').modal('show');
        });
}
document.querySelector('.password-reset').addEventListener(
    'click', openPasswordReset);
// Replace the URL with the current URL without the query string`s
let url = new URL(window.location.href);
url.search = '';
history.replaceState(null, null, url.toString());