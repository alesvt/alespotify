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
// function secsToMMSS(secs) {
//
//     dateObj = new Date(secs * 1000);
//     minutes = dateObj.getUTCMinutes();
//     seconds = dateObj.getSeconds();
//     console.log(seconds.size)
//     if (seconds.length <= 1) seconds = "0".concat(seconds);
//     console.log(seconds)
//     return `${minutes}:${seconds}`;
// }
//
//
// let tTotal = document.getElementById("tTotal")
// player.addEventListener('timeupdate', () => {
//     const progress = (player.currentTime / player.duration) * 100;
//     progressBar.style.width = `${progress}%`;
//
// });


const toggleDarkMode = document.getElementById('toggle-dark-mode');
toggleDarkMode.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
});


let songPlayer = document.getElementById("player")
let repro = songPlayer.getElementsByTagName("audio")[0]

let cancion = fetch('http://localhost:8080/api/songs/67d9331e22be08aece99c041')
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Datos recibidos:', data);
    })
    .catch(error => {
        console.error('Hubo un error al llamar a la API:', error);
    });

repro.src = cancion.source;
document.getElementById("current_track").innerText = cancion.title


// todo
async function getSong(id) {
    try {
        const response = await fetch('http://127.0.0.1:8080/api/songs/' + id);
        if (!response.ok) {
            throw new Error('Error en la solicitud: ' + response.status);
        }
        const data = await response.json();
        console.log(data);
        // play(data)
    } catch (error) {
        console.error('Error:', error);
    }
}