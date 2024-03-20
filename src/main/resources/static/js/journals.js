const topics = JSON.parse(document.getElementById("hiddenTopics").innerText);
function onChangeInput() {
    if (document.getElementById("titleInput").value === ""
        || document.getElementById("subjectSelect").value === ""
        || document.getElementById("topicSelect").value === ""
        || document.getElementById("topicSelect").value === ""
        || document.getElementById("entryInput").value === "") {
        document.getElementById("entrySubmit").disabled = true;
        document.getElementById("entrySubmit").classList.remove("btn-success")
        document.getElementById("entrySubmit").classList.add("btn-dark");
    } else {
        document.getElementById("entrySubmit").disabled = false;
        document.getElementById("entrySubmit").classList.remove("btn-dark")
        document.getElementById("entrySubmit").classList.add("btn-success");
    }
}

function buildTopicOption(topic) {
    return "<option value=\"" + topic.id + "\">" + topic.name + "</option>"
}

function onChangeSubject(subject) {
    let str = "";
    let foundTopic = false;
    for (let topic of topics) {
        if (topic.subject == subject) { // JAVASCRIPT XD MOMENT
            str += buildTopicOption(topic);
            foundTopic = true;
        }
    }
    if (foundTopic) {
        str = "<option disabled selected value=''>Thema auswählen</option>" + str;
        document.getElementById("topicSelect").innerHTML = str;
        document.getElementById("topicSelect").removeAttribute("disabled");
    } else {
        document.getElementById("topicSelect").innerHTML = "<option selected value=''>Kein Thema gefunden</option>";
        document.getElementById("topicSelect").setAttribute("disabled", "disabled");
    }
}

for (let elementsByClassNameElement of document.getElementsByClassName("default-selection")) {
    elementsByClassNameElement.setAttribute("disabled", "disabled");
}
document.getElementById("hiddenTopics").remove()

