function send() {
    let object = {
        "code": document.getElementById("code_snippet").value,
        "time": parseInt(document.getElementById("time_restriction").value),
        "views": parseInt(document.getElementById("views_restriction").value),
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);

    if (xhr.status == 200) {
        const id = JSON.parse(xhr.response).id;
        alert("Success!\n\nYour code is accessible via this id:\n" + id);
    }
}
