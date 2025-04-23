// async function getSong(id) {
//     try {
//         const response = await fetch('http://127.0.0.1:8080/api/songs/' + id);
//         if (!response.ok) {
//             throw new Error('Error en la solicitud: ' + response.status);
//         }
//         const data = await response.json();
//         console.log(data);
//         play(data)
//     } catch (error) {
//         console.error('Error:', error);
//     }
// }
//
// let player = document.getElementById("reproductorMedia")
// let container = document.getElementById("reproductor")
// console.log(container);
// let imagen = container.querySelector("img")
// let playbt = container.querySelector("#play-song")
// const progressBar = document.getElementById("pb");
//
//
// playbt.addEventListener("click", function () {
//     if (player.paused) {
//         playbt.innerHTML = "<i class=\"fa-solid fa-pause\"></i>";
//         player.play();
//     } else {
//         playbt.innerHTML = "<i class=\"fa-solid fa-play\"></i>";
//         player.pause();
//     }
// })
//
// function play(data) {
//     player.source = data.source
//     imagen.src = data.thumbImage
//     tTotal.innerText = secsToMMSS(data.length);
//
// }
//


const toggleDarkMode = document.getElementById('toggle-dark-mode');
toggleDarkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
});


// todo
async function getSong(id) {
    console.log("works")
    try {
        const response = await fetch(`http://10.0.2.103:8080/api/songs/${id}`);
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const data = await response.json();
        play(data); // Llamamos a play *después* de obtener los datos
    } catch (error) {
        console.error("Error al obtener la canción:", error);
    }
}


let elementos

document.addEventListener("DOMContentLoaded", function () {
    elementos = loadElements()
    let reproductor = document.getElementById("player")

    console.log(reproductor.querySelector("audio").src)
    if (reproductor.querySelector("audio").src === "http://localhost:8080/app") {
        elementos.songPlayer.style.display = "none";
    } else {
        elementos.songPlayer.style.display = "";

    }
})

function loadElements() {
    return {
        songPlayer: document.getElementById("player"),
        repro: document.getElementsByTagName("audio")[0],
        imagen_cancion: document.getElementById("player_track_song"),
        titulo_cancion: document.getElementById("current_track"),
        artist_cancion: document.getElementById("current_artist"),
        liked: document.getElementById("liked"),
        trackbar: document.getElementById("trackbar"),
        volume: document.getElementById("volume"),
        controls: document.getElementById("controls")
    }

}


function play(song) {
    elementos = loadElements()
    console.log(song)
    elementos.songPlayer.style.display = "block";


    elementos.repro.src = song.source;

    elementos.titulo_cancion.innerText = song.title
    elementos.imagen_cancion.src = song.thumbImage
    elementos.artist_cancion.innerText = song.artists[0].name
    elementos.trackbar.children[2].innerText = secsToMMSS(song.length)
    elementos.repro.play()
}


function secsToMMSS(secs) {
    let dateObj = new Date(secs * 1000);
    let minutes = dateObj.getUTCMinutes();
    let seconds = dateObj.getSeconds();
    if (seconds <= 9) {
        return `${minutes}:0${seconds}`;
    } else {
        return `${minutes}:${seconds}`;
    }
}

console.log(elementos.repro)
if (elementos) {
    elementos.repro.addEventListener("timeupdate", function () {
        console.log("pe")
        const progress = (elementos.songPlayer.currentTime / elementos.songPlayer.duration) * 100;
        elementos.trackbar.children[1].value = `${progress}`
        elementos.trackbar.children[0].innerText = `${progress}`
    });
}
