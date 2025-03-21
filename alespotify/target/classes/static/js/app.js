async function getSong(id) {
    try {
        const response = await fetch('http://127.0.0.1:8080/api/songs/' + id);
        if (!response.ok) {
            throw new Error('Error en la solicitud: ' + response.status);
        }
        const data = await response.json();
        console.log(data);
        play(data)
    } catch (error) {
        console.error('Error:', error);
    }
}

let player = document.getElementById("reproductorMedia")
let container = document.getElementById("reproductor")
console.log(container);
let imagen = container.querySelector("img")
let playbt = container.querySelector("#play-song")


playbt.addEventListener("click", function () {
    if (player.paused) {
        playbt.innerHTML = "<i class=\"fa-solid fa-pause\"></i>";
        player.play();
    } else {
        playbt.innerHTML = "<i class=\"fa-solid fa-play\"></i>";
        player.pause();
    }
})

function play(data) {
    player.source = data.source
    imagen.src = data.thumbImage
}

const progressBar = document.getElementById("pb");


player.addEventListener('timeupdate', () => {
    const progress = (player.currentTime / player.duration) * 100;
    progressBar.style.width = `${progress}%`;
});



