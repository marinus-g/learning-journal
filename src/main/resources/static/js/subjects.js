const availableSubjects = JSON.parse(document.getElementById('hiddenSubjects').innerText)
document.getElementById('hiddenSubjects').remove()


function fetchTopicComponent(subjectId) {
    return fetch('subjects/' + subjectId + '/topics')
        .then(response => response.text())
        .catch(error => console.log(error))
}

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

function findSubject(id) {
    for (let availableSubject of availableSubjects) {
        if (availableSubject.id == id) {
            return availableSubject
        }
    }
    return null
}

function onExpandSubject(subject) {
    const subjectId = subject.getAttribute("id").substring(8)
    subject.classList.remove("subject-container-collapsed")
    subject.classList.add("subject-container-expanded")
    subject.removeAttribute("onclick")
    const subjectData = findSubject(subjectId)
    subject.innerHTML = "<div style='height: auto; 30%: fit-content; display: flex; flex-direction: row; justify-content: center; text-wrap: normal' class='mb-2 mt-1'>" +
        "<i title='Zuklappen' class='arrow up' onclick='onCollapseSubject(event, this.parentElement.parentElement)'></i>" +
        "</div>" +
        "<div style='display: flex; flex-direction: column; width: 100%'>" +
        "<h4>" + subjectData.name + "</h4>" +
        "<small style='color: #979797; text-wrap: normal'>" + subjectData.description + "</small>" +
        "</div>" +
        "<div id='expanded-" + subjectId + "'>" +
        "</div>" +
        "<hr>";
    fetchTopicComponent(subjectId).then(data => {
        document.getElementById("expanded-" + subjectId).innerHTML = data
    })
}

function onCollapseSubject(event, subject) {
    event.stopPropagation()
    const subjectId = subject.getAttribute("id").substring(8)
    const subjectData = findSubject(subjectId)

    subject.setAttribute("onclick", "onExpandSubject(this)")
    subject.classList.remove("subject-container-expanded")
    subject.classList.add("subject-container-collapsed")
    subject.innerHTML = "<h4>" + subjectData.name + "</h4>\n" +
        "            <small style=\"color: #979797\">" + subjectData.shortenedDescription + "</small>\n" +
        "            <hr>";
}

function onChangeTopicInput(id) {
    const name = document.getElementById("topicName-" + id).value
    const description = document.getElementById("topicDescription-" + id).value
    if (name === "" || description === "") {
        document.getElementById("createTopicButton-" + id).classList.remove("btn-success")
        document.getElementById("createTopicButton-" + id).classList.add("btn-dark");
        document.getElementById("createTopicButton-" + id).disabled = true;
        return;
    }
    document.getElementById("createTopicButton-" + id).classList.remove("btn-dark")
    document.getElementById("createTopicButton-" + id).classList.add("btn-success");
    document.getElementById("createTopicButton-" + id).disabled = false;

}

setTimeout(() => {
    for (let elementsByClassNameElement of document.getElementsByClassName("remove-me")) {
        elementsByClassNameElement.remove();
    }

}, 5000);


// Replace the URL with the current URL without the query string`s
let url = new URL(window.location.href);
url.search = '';
history.replaceState(null, null, url.toString());