const APP_IP = "172.205.130.42"
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


async function getSong(id) {
    try {
        const response = await fetch(`http://${APP_IP}/api/songs/${id}`);
        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status}`);
        }
        const data = await response.json();
        //console.log(data)
        play(data);
    } catch (error) {
        console.error("Error al obtener la canción:", error);
    }
}

async function getPlaylist(id) {
    currentSongIndex = 0
    try {
        const response = await fetch(`http://${APP_IP}/api/playlists/${id}`)
        if (!response.ok) {
            throw new Error("Error en la solicitud")
        }
        const data = await response.json()
        playqueue = data
        colaOrdenada = playqueue
        console.log(playqueue)
        playFromQueue(0)
    } catch (error) {
        console.error(error)
    }
}

let colaOrdenada = []

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
    const userId = document.getElementById("user-data").getAttribute("userId");
    const userName = document.getElementById("user-data").getAttribute("userName")
    const userImage = document.getElementById("user-data").getAttribute("userImage")
    const userEmail = document.getElementById("user-data").getAttribute("userEmail")
    const loop = document.getElementById("loop-button")
    return {
        loop,
        songPlayer,
        repro,
        imagen_cancion,
        titulo_cancion,
        artist_cancion,
        liked,
        trackbar,
        volume,
        controls,
        userImage,
        userId,
        userName,
        userEmail
    };
}

async function loadUserData() {
    return userDataDOM ? {
        nombre: userDataDOM.getAttribute("name"),
        email: userDataDOM.getAttribute("email"),
        image: userDataDOM.getAttribute("image"),
        id: userDataDOM.getAttribute("id")
    } : null;
}

function play(song) {
    elementos = loadElements();
    if (elementos.songPlayer) {
        elementos.songPlayer.style.display = "flex";
    }

    if (elementos.repro) {
        elementos.repro.src = song.source;
        elementos.repro.load();
        elementos.repro.play();
    }

    if (elementos.titulo_cancion) elementos.titulo_cancion.innerText = song.name;
    if (elementos.imagen_cancion) elementos.imagen_cancion.src = song.image;
    if (song.artists && song.artists.length > 0 && elementos.artist_cancion) {
        elementos.artist_cancion.innerText = song.artists[0].name;
    }
    document.title = song.name + " - " + song.artists[0].name
    antetitle = document.title
    if (elementos.trackbar && elementos.trackbar.children.length >= 3) {
        elementos.trackbar.children[2].innerText = secsToMMSS(song.duration);
    }
    if (elementos.repro) {
        elementos.repro.play();
    }

    const playButton = document.querySelector('.player-controls-buttons button:nth-child(3)');
    if (playButton) {
        playButton.innerHTML = `
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="6" y="4" width="4" height="16"></rect>
                <rect x="14" y="4" width="4" height="16"></rect>
            </svg>
        `;
    }
    return song
}


function secsToMMSS(secs) {
    const dateObj = new Date(secs * 1000);
    const minutes = dateObj.getUTCMinutes();
    let seconds = dateObj.getSeconds();
    seconds = seconds < 10 ? `0${seconds}` : seconds;
    return `${minutes}:${seconds}`;
}


document.addEventListener("DOMContentLoaded", function () {
    userData = loadUserData()
    elementos = loadElements();
    const reproductor = document.getElementById("player");
    const audioPlayer = reproductor ? reproductor.querySelector("audio") : null;

    const playButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(3)') : null;
    const prevButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(2)') : null;
    const nextButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(4)') : null;
    const repeatButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(5)') : null;
    const shuffleButton = reproductor ? reproductor.querySelector('.player-controls-buttons button:nth-child(1)') : null;

    const progressBar = reproductor ? reproductor.querySelector('#trackbar input[type="range"]') : null;
    const progressText = reproductor ? reproductor.querySelector('#trackbar span:first-child') : null;

    if (audioPlayer) {
        audioPlayer.addEventListener('ended', function () {
            // Reproducir la siguiente canción en la cola automáticamente
            playNext();
        });
    }

    if (audioPlayer && audioPlayer.src === `http://172.205.130.42/app`) {
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
    if (prevButton) {
        prevButton.addEventListener('click', function () {
            console.log("Botón de Anterior clickeado");
            if (currentSongIndex > 0) {
                playFromQueue(currentSongIndex - 1);
            } else {
                console.log("Estás en la primera canción de la cola.");
                // Opcional: Volver al inicio de la canción actual o no hacer nada
                if (elementos && elementos.repro) {
                    elementos.repro.currentTime = 0;
                }
            }
        });
    }
    if (nextButton) {
        nextButton.addEventListener('click', function () {
            console.log("Botón de Siguiente clickeado");
            playNext();
        });
    }
    const antetitle = ""
    // Funcionalidad del botón de Play/Pause
    if (playButton && elementos && elementos.repro) {
        playButton.addEventListener('click', function () {

            if (elementos.repro.paused) {
                document.title = antetitle
                elementos.repro.play();
                playButton.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="6" y="4" width="4" height="16"></rect>
                        <rect x="14" y="4" width="4" height="16"></rect>
                    </svg>
                `;
            } else {
                elementos.repro.pause();
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


    if (repeatButton && elementos && elementos.repro) {
        repeatButton.addEventListener('click', function () {
            elementos.repro.loop = !elementos.repro.loop;
            if (elementos.repro.loop) {
                repeatButton.classList.add("checked")
                repeatButton.style.color = 'var(--accent-color)';
            } else {
                repeatButton.classList.remove("checked")
                repeatButton.style.color = '';
            }
            console.log("Repetir:", elementos.repro.loop);
        });
    }


    if (shuffleButton && elementos && elementos.repro && playqueue) {
        shuffleButton.addEventListener('click', function () {
            if (!shuffleButton.classList.contains('checked')) {
                shuffleButton.classList.add('checked')
                randomizarCola(playqueue);
            } else {
                shuffleButton.classList.remove('checked')
                playqueue = colaOrdenada
            }
        })
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

function randomizarCola(list) {
    let currentIndex = list.length
    list.sort(() => Math.random() - .5)

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
            let user = document.getElementById("user-data")
            let uId = user.getAttribute("userId");
            let uName = user.getAttribute("userName")
            let uEmail = user.getAttribute("userEmail");

            title.textContent = "Perfil de Usuario";
            const nameParagraph = document.createElement('p');
            nameParagraph.innerHTML = `<b>Nombre:</b> ${user && uName ? uName : 'N/A'}`;

            const emailParagraph = document.createElement('p');
            emailParagraph.innerHTML = `<b>Email:</b> ${user && uEmail ? uEmail : 'N/A'}`;


            modalContainer.style.zIndex = "99";
            modalContent.appendChild(title);
            modalContent.appendChild(nameParagraph);
            modalContent.appendChild(emailParagraph);
            modalContent.appendChild(closeButton);

            break;

        case "create-playlist":
            let userId = document.getElementById("create-playlist-button").getAttribute("userId")

            const formTitle = document.createElement('h2');
            formTitle.textContent = "Crear nueva lista de reproducción";

            const inputLabel = document.createElement('label');
            inputLabel.textContent = "Nombre de la lista:";
            inputLabel.setAttribute('for', 'playlist-name');

            const inputField = document.createElement('input');
            inputField.type = 'text';
            inputField.id = 'playlist-name';
            inputField.name = 'playlist-name';

            const publicLabel = document.createElement('label');
            publicLabel.textContent = "¿Hacer pública?";
            publicLabel.setAttribute('for', 'ispublic-playlist');
            const publicField = document.createElement('input')
            publicField.type = 'checkbox';
            publicField.id = "ispublic-playlist"
            publicField.name = 'ispublic';

            const createButton = document.createElement('button');
            createButton.textContent = "Crear";
            createButton.addEventListener('click', async function () {
                const playlistName = document.getElementById('playlist-name').value;
                const isPublic = document.getElementById('ispublic-playlist').value == "checked" ? true : false;
                if (playlistName && userId) {
                    try {
                        const response = await fetch(`http://${APP_IP}/api/playlists/new?nombre=${playlistName}&userId=${userId}&isPublic=${isPublic}`, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            }
                        });

                        if (response.ok) {
                            const createdPlaylist = await response.json();
                            if (createdPlaylist) {
                                updatePlaylistsDOM(userId);
                                closeModal();
                                location.reload()
                            } else {
                                console.error("Error: No se recibieron las playlists actualizadas.");
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

            modalContainer.style.zIndex = "99";

            modalContent.appendChild(formTitle);
            modalContent.appendChild(inputLabel);
            modalContent.appendChild(inputField);
            modalContent.appendChild(publicField);
            modalContent.appendChild(publicLabel);
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

async function updatePlaylistsDOM(userId) {
    const playlistsNav = document.getElementById('playlists-nav');
    if (playlistsNav) {
        // Limpiar las listas existentes (manteniendo los dos primeros elementos si existen)
        while (playlistsNav.children.length > 2) {
            playlistsNav.removeChild(playlistsNav.lastChild);
        }
        let playlists = await fetch(`http://${APP_IP}/api/playlists/user?userId=${userId}`)
        await console.log(playlists)
        // Añadir las nuevas listas desde el array 'playlists'
        await playlists.forEach(playlist => {
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


let playqueue = []

let currentSongIndex = 0
let currentSongData = null;


function addToQueue(songId) {
    playqueue.push(songId)
}

async function playFromQueue(index) {
    //console.log(playqueue)
    if (index >= 0 && index <= playqueue.songs.length) {
        console.log(index)
        if (playqueue.songs) {
            console.log("la playlist tiene canciones")
            const songId = playqueue.songs[index].id
            console.log("la canción tiene el id:" + songId)
            currentSongData = getSong(songId)
            console.log("reproduciendo desde playlist")
            console.log(currentSongData)
        }
    } else {
        console.log("no hay más canciones en cola")
        if (repeatButton.classList.contains("checked")) {
            playFromQueue(0)
        } else {
            elementos.repro.stop()
        }
    }
}

function playNext() {
    console.log(`indice: ${currentSongIndex}, cancion: ${playqueue.songs[currentSongIndex].id}`)

    if (currentSongIndex < playqueue.songs.length) {
        playFromQueue(currentSongIndex + 1);
        currentSongIndex++
    } else {
        console.log("Fin de la cola.");
        // Aquí podrías implementar la lógica para repetir la cola o detener la reproducción
        if (elementos && elementos.repro) {
            elementos.repro.pause();
            const playButton = document.querySelector('.player-controls-buttons button:nth-child(3)');
            if (playButton) {
                playButton.innerHTML = `
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polygon points="5 3 19 12 5 21 5 3"></polygon>
                    </svg>
                `;
            }
            document.title = "Alespotify - Inicio";
        }
    }
}


const volumeSlider = document.getElementById('volume-slider');
const volumeIcon = document.getElementById('volume-icon')
const volumeMediumPath = volumeIcon.querySelector('.volume-medium');
const volumeHighPath = volumeIcon.querySelector('.volume-high');

function updateVolumeIcon(volume) {

    volumeMediumPath.style.display = 'none';
    volumeHighPath.style.display = 'none';

    if (volume === 0) {

        volumeIcon.setAttribute('stroke', 'currentColor');
        volumeIcon.innerHTML = `
                <polygon points="11 5 6 9 2 9 2 15 6 15 11 19 11 5"></polygon>
                <line x1="22" y1="18" x2="16" y2="12"></line>
                <line x1="16" y1="18" x2="22" y2="12"></line>
            `;

    } else {
        volumeIcon.innerHTML = `
                <polygon points="11 5 6 9 2 9 2 15 6 15 11 19 11 5"></polygon>
                <path class="volume-medium" d="M15.54 8.46a5 5 0 0 1 0 7.07"></path>
                <path class="volume-high" d="M19.07 4.93a9 9 0 0 1 0 14.14"></path>
            `;

        const newVolumeMediumPath = volumeIcon.querySelector('.volume-medium');
        const newVolumeHighPath = volumeIcon.querySelector('.volume-high');

        if (volume > 0 && volume <= 33) {
        } else if (volume > 33 && volume <= 66) {
            if (newVolumeMediumPath) newVolumeMediumPath.style.display = 'block';
        } else if (volume > 66) {
            if (newVolumeMediumPath) newVolumeMediumPath.style.display = 'block';
            if (newVolumeHighPath) newVolumeHighPath.style.display = 'block';
        }
    }
}


volumeSlider.addEventListener('input', (event) => {
    const currentVolume = parseInt(event.target.value); // Obtenemos el valor del slider como número entero
    console.log('Volumen:', currentVolume); // Para depuración, puedes ver el valor en la consola
    if (elementos.repro) {
        elementos.repro.volume = currentVolume / 100
    }
    updateVolumeIcon(currentVolume);
});

updateVolumeIcon(parseInt(volumeSlider.value));
