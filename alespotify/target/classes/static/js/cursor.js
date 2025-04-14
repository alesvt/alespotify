 // let cursor = document.getElementById("cursor");

document.addEventListener("mousemove", function (e) {

    cursor.style.top = e.clientY - 10 + "px";
    cursor.style.left = e.clientX - 10 + "px";
});

document.addEventListener("mousedown", function () {
    cursor.style.scale = "1.4";
    cursor.style.borderColor = "white"
})
document.addEventListener("mouseup", function () {
    cursor.style.scale = "1";
    cursor.style.borderColor = "#757575"
})
let c_enabled = true;
let toggler = document.getElementById("toggle-cursor");

toggler.addEventListener("click", function () {
    if (c_enabled) {
        document.style.cursor = "pointer";
        cursor.style.display = "none";
    } else {
        cursor.style.display = "block";
        document.style.cursor = "none";

    }
});
