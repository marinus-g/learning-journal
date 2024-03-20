function onChangeSubjectInput() {
    const name = document.getElementById("subjectName").value
    const description = document.getElementById("subjectDescription").value
    if (name === "" || description === "") {
        document.getElementById("createSubjectButton").classList.remove("btn-success")
        document.getElementById("createSubjectButton").classList.add("btn-dark");
        document.getElementById("createSubjectButton").disabled = true;
        return;
    }
    document.getElementById("createSubjectButton").classList.remove("btn-dark")
    document.getElementById("createSubjectButton").classList.add("btn-success");
    document.getElementById("createSubjectButton").disabled = false;
}

setTimeout(() => {
    for (let elementsByClassNameElement of document.getElementsByClassName("remove-me")) {
        elementsByClassNameElement.remove();
    }

}, 5000);
let url = new URL(window.location.href);
url.search = '';
history.replaceState(null, null, url.toString());