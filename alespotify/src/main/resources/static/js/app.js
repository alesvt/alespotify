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
if (toggleDarkMode) {
    toggleDarkMode.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
    });
}


let elementos;
const userDataDOM = document.getElementById("user-data");
let userData
const modalContainer = document.getElementById('modal-container');
const modalContent = document.getElementById("modal-content");

console.log("pepe")
// todo
async function getSong(id) {
    console.log("works");
    try {
        const response = await fetch(`http://172.24.128.1:8080/api/songs/${id}`);
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const data = await response.json();
        play(data); // Llamamos a play *después* de obtener los datos
    } catch (error) {
        console.error("Error al obtener la canción:", error);
    }
}

async function getUser(id) {
    try {
        const response = await fetch(`http://172.24.128.1:8080/api/users/${id}`);
        if (!response.ok) {
            throw new Error("error al recibir usuario");
        }
        const data = await response.json();
        return data; // Devolver los datos del usuario
    } catch (error) {
        console.error("Error al recibir usuario", error);
        return null; // Es buena práctica devolver null o un valor indicativo de error
    }
}


function loadElements() {
    const songPlayer = document.getElementById("player");
    const repro = document.getElementsByTagName("audio")[0];
    const imagen_cancion = document.getElementById("player_track_song");
    const titulo_cancion = document.getElementById("current_track");
    const artist_cancion = document.getElementById("current_artist");
    const liked = document.getElementById("liked");
    const trackbar = document.getElementById("trackbar");
    const volume = document.getElementById("volume");
    const controls = document.getElementById("controls");

    return {
        songPlayer,
        repro,
        imagen_cancion,
        titulo_cancion,
        artist_cancion,
        liked,
        trackbar,
        volume,
        controls
    };
}
function loadUserData(){
    return userDataDOM ? {
        nombre: userDataDOM.getAttribute("name"),
        email: userDataDOM.getAttribute("email"),
        image: userDataDOM.getAttribute("image"),
        id: userDataDOM.getAttribute("id")
    } : null;
}

function play(song) {
    elementos = loadElements();
    console.log(song);
    if (elementos.songPlayer) {
        elementos.songPlayer.style.display = "flex";
    }


    if (elementos.repro) {
        elementos.repro.src = song.source;
        elementos.repro.load(); // Asegúrate de que el audio se cargue con la nueva fuente
    }

    if (elementos.titulo_cancion) elementos.titulo_cancion.innerText = song.name;
    if (elementos.imagen_cancion) elementos.imagen_cancion.src = song.image;
    if (song.artists && song.artists.length > 0 && elementos.artist_cancion) {
        elementos.artist_cancion.innerText = song.artists[0].name;
    }
    document.title = song.name + " - " + song.artists[0].name
    if (elementos.trackbar && elementos.trackbar.children.length >= 3) {
        elementos.trackbar.children[2].innerText = secsToMMSS(song.duration);
    }
    if (elementos.repro) {
        elementos.repro.play();
    }
}


function secsToMMSS(secs) {
    const dateObj = new Date(secs * 1000);
    const minutes = dateObj.getUTCMinutes();
    let seconds = dateObj.getSeconds();
    seconds = seconds < 10 ? `0${seconds}` : seconds;
    return `${minutes}:${seconds}`;
}


document.addEventListener("DOMContentLoaded", function () {
    const userDataDOM = document.getElementById("user-data");
    console.log(userDataDOM)
    userData = loadUserData()
    // Asegurarse de que userDataDOM existe antes de acceder a sus atributos
    elementos = loadElements();
    const reproductor = document.getElementById("player");
    const audioPlayer = reproductor ? reproductor.querySelector("audio") : null;

    const playButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(3)') : null;
    const prevButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(2)') : null;
    const nextButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(4)') : null;
    const repeatButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(5)') : null;

    const progressBar = reproductor ? reproductor.querySelector('#trackbar input[type="range"]') : null;
    const progressText = reproductor ? reproductor.querySelector('#trackbar span:first-child') : null;

    if (audioPlayer && audioPlayer.src === "http://localhost:8080/app") {
        if (elementos && elementos.songPlayer) {
            elementos.songPlayer.style.display = "none";
        }
    } else if (elementos && elementos.songPlayer) {
        elementos.songPlayer.style.display = "";
    }

    if (elementos && elementos.repro && progressBar && progressText) {
        elementos.repro.addEventListener("timeupdate", function () {
            if (isNaN(elementos.repro.duration)) return;

            const currentTime = elementos.repro.currentTime;
            const duration = elementos.repro.duration;

            progressBar.max = duration;
            progressBar.value = currentTime;
            progressText.innerText = secsToMMSS(currentTime);
        });

        progressBar.addEventListener('input', function () {
            const duration = elementos.repro.duration;
            if (!isNaN(duration)) {
                elementos.repro.currentTime = parseFloat(progressBar.value);
            }
        });
    }

    // Funcionalidad del botón de Play/Pause
    if (playButton && elementos && elementos.repro) {
        playButton.addEventListener('click', function () {
            if (elementos.repro.paused) {
                elementos.repro.play();
                // Cambiar el icono a pausa (puedes ajustar esto según tu HTML)
                playButton.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="6" y="4" width="4" height="16"></rect>
                        <rect x="14" y="4" width="4" height="16"></rect>
                    </svg>
                `;
            } else {
                elementos.repro.pause();
                // Cambiar el icono a play
                document.title = "Alespotify - Inicio"
                playButton.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polygon points="5 3 19 12 5 21 5 3"></polygon>
                    </svg>
                `;
            }
        });
    }

    // Funcionalidad del botón de Anterior (necesitarás lógica para la lista de reproducción)
    if (prevButton) {
        prevButton.addEventListener('click', function () {
            console.log("Botón de Anterior clickeado");
            // Aquí iría la lógica para cargar la canción anterior en la lista de reproducción
            // y llamar a la función play(nuevaCancion);
        });
    }

    // Funcionalidad del botón de Siguiente (necesitarás lógica para la lista de reproducción)
    if (nextButton) {
        nextButton.addEventListener('click', function () {
            console.log("Botón de Siguiente clickeado");
            // Aquí iría la lógica para cargar la siguiente canción en la lista de reproducción
            // y llamar a la función play(nuevaCancion);
        });
    }

    // Funcionalidad del botón de Repetir (toggling)
    if (repeatButton && elementos && elementos.repro) {
        repeatButton.addEventListener('click', function () {
            elementos.repro.loop = !elementos.repro.loop;
            // Puedes cambiar la apariencia del botón para indicar el estado de repetición
            if (elementos.repro.loop) {
                repeatButton.style.color = 'var(--accent-color)'; // Ejemplo de cambio de color
            } else {
                repeatButton.style.color = ''; // Restablecer el color
            }
            console.log("Repetir:", elementos.repro.loop);
        });
    }

    const createPlaylistButton = document.getElementById('create-playlist-button');
    if (createPlaylistButton) {
        createPlaylistButton.addEventListener('click', function () {
            openModal('create-playlist');
        });
    }
});

function openModal(modalType) {
    if (modalContent && modalContainer) {
        modalContent.innerHTML = "";
        modalContainer.style.display = 'flex';
        showModalInfo(modalType);
    }
}

function closeModal() {
    if (modalContainer) {
        modalContainer.style.display = 'none';
    }
}


async function showModalInfo(modalType) {
    if (!modalContent) return;

    const closeButton = document.createElement('button');
    closeButton.textContent = "Cerrar";
    closeButton.classList.add("play-button");
    closeButton.id = "modal-close-button";

    closeButton.addEventListener('click', closeModal);

    switch (modalType) {
        case "profile":
            const title = document.createElement('h2');
            title.textContent = "Perfil de Usuario";
            const nameParagraph = document.createElement('p');
            nameParagraph.innerHTML = `<b>Nombre:</b> ${userData && userData.nombre ? userData.nombre : 'N/A'}`;

            const emailParagraph = document.createElement('p');
            emailParagraph.innerHTML = `<b>Email:</b> ${userData && userData.email ? userData.email : 'N/A'}`;

            const imagen = document.createElement('img');
            if (userData && userData.image) {
                imagen.src = `${userData.image}`;
                imagen.height = 80;
                imagen.width = 80;
                imagen.style.padding = "12px";
            } else {
                imagen.alt = "No hay imagen de perfil";
            }


            modalContent.appendChild(title);
            modalContent.appendChild(nameParagraph);
            modalContent.appendChild(emailParagraph);
            modalContent.appendChild(imagen);
            modalContent.appendChild(closeButton);

            break;

        case "create-playlist":
            const formTitle = document.createElement('h2');
            formTitle.textContent = "Crear nueva lista de reproducción";

            const inputLabel = document.createElement('label');
            inputLabel.textContent = "Nombre de la lista:";
            inputLabel.setAttribute('for', 'playlist-name');

            const inputField = document.createElement('input');
            inputField.type = 'text';
            inputField.id = 'playlist-name';
            inputField.name = 'playlist-name';

            const createButton = document.createElement('button');
            createButton.textContent = "Crear";
            createButton.addEventListener('click', async function () {
                const playlistName = document.getElementById('playlist-name').value;
                if (playlistName && userData && userData.id) {
                    try {
                        const response = await fetch(`http://172.24.128.1:8080/api/users/${userData.id}/playlists`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({name: playlistName}),
                        });

                        if (response.ok) {
                            const updatedUser = await response.json();
                            if (updatedUser && updatedUser.playlists) {
                                updatePlaylistsDOM(updatedUser.playlists);
                                closeModal();
                            } else {
                                console.error("Error: No se recibieron las playlists actualizadas.");
                                alert("Error al actualizar la lista de reproducción.");
                            }
                        } else {
                            const errorData = await response.json();
                            console.error("Error al crear la playlist:", errorData);
                            alert(`Error al crear la playlist: ${errorData.message || response.statusText}`);
                        }
                    } catch (error) {
                        console.error("Error al crear la playlist:", error);
                        alert("Error al crear la playlist.");
                    }
                } else {
                    alert("Por favor, introduce un nombre para la lista.");
                }
            });

            modalContent.appendChild(formTitle);
            modalContent.appendChild(inputLabel);
            modalContent.appendChild(inputField);
            modalContent.appendChild(createButton);
            modalContent.appendChild(closeButton);
            break;
    }
}


function addNewPlaylistToDOM(playlistName) {
    const playlistsNav = document.getElementById('playlists-nav');
    if (playlistsNav) {
        const newPlaylistLink = document.createElement('a');
        newPlaylistLink.href = '#';
        newPlaylistLink.textContent = playlistName;

        const svgIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svgIcon.setAttribute("width", "24");
        svgIcon.setAttribute("height", "24");
        svgIcon.setAttribute("viewBox", "0 0 24 24");
        svgIcon.setAttribute("fill", "none");
        svgIcon.setAttribute("stroke", "currentColor");
        svgIcon.setAttribute("stroke-width", "2");
        svgIcon.setAttribute("stroke-linecap", "round");
        svgIcon.setAttribute("stroke-linejoin", "round");
        const listIconPath = document.createElementNS("http://www.w3.org/2000/svg", "path");
        listIconPath.setAttribute("d", "M9 22H5a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h4l2 3h7a2 2 0 0 1 2 2v13a2 2 0 0 1-2 2H9z");
        svgIcon.appendChild(listIconPath);
        newPlaylistLink.prepend(svgIcon);
        newPlaylistLink.appendChild(document.createTextNode(` ${playlistName}`));

        if (playlistsNav.children.length > 1) {
            playlistsNav.insertBefore(newPlaylistLink, playlistsNav.children[playlistsNav.children.length - 1]);
        } else {
            playlistsNav.appendChild(newPlaylistLink);
        }
    }
}

function updatePlaylistsDOM(playlists) {
    const playlistsNav = document.getElementById('playlists-nav');
    if (playlistsNav) {
        // Limpiar las listas existentes (manteniendo los dos primeros elementos si existen)
        while (playlistsNav.children.length > 2) {
            playlistsNav.removeChild(playlistsNav.lastChild);
        }
        // Añadir las nuevas listas desde el array 'playlists'
        playlists.forEach(playlist => {
            const newPlaylistLink = document.createElement('a');
            newPlaylistLink.href = '#';
            newPlaylistLink.textContent = playlist.name;

            const svgIcon = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            svgIcon.setAttribute("width", "24");
            svgIcon.setAttribute("height", "24");
            svgIcon.setAttribute("viewBox", "0 0 24 24");
            svgIcon.setAttribute("fill", "none");
            svgIcon.setAttribute("stroke", "currentColor");
            svgIcon.setAttribute("stroke-width", "2");
            svgIcon.setAttribute("stroke-linecap", "round");
            svgIcon.setAttribute("stroke-linejoin", "round");
            const listIconPath = document.createElementNS("http://www.w3.org/2000/svg", "path");
            listIconPath.setAttribute("d", "M9 22H5a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h4l2 3h7a2 2 0 0 1 2 2v13a2 2 0 0 1-2 2H9z");
            svgIcon.appendChild(listIconPath);
            newPlaylistLink.prepend(svgIcon);
            newPlaylistLink.appendChild(document.createTextNode(` ${playlist.name}`));

            // Insertar antes del último elemento (el botón "Crear playlist") si existe
            if (playlistsNav.children.length > 0) {
                playlistsNav.insertBefore(newPlaylistLink, playlistsNav.lastChild);
            } else {
                playlistsNav.appendChild(newPlaylistLink);
            }
        });
    }
}