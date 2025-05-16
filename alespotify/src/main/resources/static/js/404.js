document.addEventListener("DOMContentLoaded", () => {
    const vinylRecord = document.querySelector(".vinyl-record")
    const pianoKeys = document.querySelectorAll(".piano-key")
    const container = document.querySelector(".container")
    const homeButton = document.getElementById("homeButton")

    // Notas musicales para el piano
    const notes = {
        C4: 261.63,
        "C#4": 277.18,
        D4: 293.66,
        "D#4": 311.13,
        E4: 329.63,
        F4: 349.23,
        "F#4": 369.99,
        G4: 392.0,
        "G#4": 415.3,
        A4: 440.0,
        "A#4": 466.16,
        B4: 493.88,
    }

    // Contexto de audio
    let audioContext

    // Inicializar el contexto de audio al primer clic
    function initAudio() {
        if (!audioContext) {
            audioContext = new (window.AudioContext || window.webkitAudioContext)()
            document.removeEventListener("click", initAudio)
        }
    }

    document.addEventListener("click", initAudio)

    // Función para reproducir una nota
    function playNote(frequency) {
        if (!audioContext) return

        const oscillator = audioContext.createOscillator()
        const gainNode = audioContext.createGain()

        oscillator.type = "sine"
        oscillator.frequency.value = frequency

        gainNode.gain.setValueAtTime(0.3, audioContext.currentTime)
        gainNode.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 1)

        oscillator.connect(gainNode)
        gainNode.connect(audioContext.destination)

        oscillator.start()
        oscillator.stop(audioContext.currentTime + 1)
    }

    // Evento para las teclas del piano
    pianoKeys.forEach((key) => {
        key.addEventListener("mousedown", function () {
            const note = this.getAttribute("data-note")
            this.classList.add("active")

            // Reproducir sonido
            playNote(notes[note])

            // Crear animación de nota musical
            createNoteAnimation(this)
        })

        key.addEventListener("mouseup", function () {
            this.classList.remove("active")
        })

        key.addEventListener("mouseleave", function () {
            this.classList.remove("active")
        })

        // Para dispositivos táctiles
        key.addEventListener("touchstart", function (e) {
            e.preventDefault()
            const note = this.getAttribute("data-note")
            this.classList.add("active")

            // Reproducir sonido
            playNote(notes[note])

            // Crear animación de nota musical
            createNoteAnimation(this)
        })

        key.addEventListener("touchend", function () {
            this.classList.remove("active")
        })
    })

    // Función para crear animación de nota musical
    function createNoteAnimation(keyElement) {
        const noteSymbols = ["♪", "♫", "♩", "♬"]
        const randomNote = noteSymbols[Math.floor(Math.random() * noteSymbols.length)]

        const noteElement = document.createElement("div")
        noteElement.className = "note-animation"
        noteElement.textContent = randomNote

        // Posicionar la nota sobre la tecla
        const rect = keyElement.getBoundingClientRect()
        const containerRect = container.getBoundingClientRect()

        noteElement.style.left = rect.left - containerRect.left + rect.width / 2 + "px"
        noteElement.style.top = rect.top - containerRect.top + "px"

        container.appendChild(noteElement)

        // Eliminar la nota después de la animación
        setTimeout(() => {
            noteElement.remove()
        }, 2000)
    }

    // Efecto de hover para el disco de vinilo
    vinylRecord.addEventListener("mouseenter", function () {
        this.style.animationPlayState = "paused"
    })

    vinylRecord.addEventListener("mouseleave", function () {
        this.style.animationPlayState = "running"
    })

    // Efecto de click en el disco de vinilo
    vinylRecord.addEventListener("click", function () {
        // Reproducir una secuencia de notas (una pequeña melodía)
        const melody = ["C4", "E4", "G4", "C4", "E4", "G4", "B4", "A4"]
        let delay = 0

        melody.forEach((note) => {
            setTimeout(() => {
                playNote(notes[note])

                // Crear una nota animada en una posición aleatoria cerca del disco
                const noteElement = document.createElement("div")
                noteElement.className = "note-animation"
                noteElement.textContent = ["♪", "♫", "♩", "♬"][Math.floor(Math.random() * 4)]

                const rect = this.getBoundingClientRect()
                const containerRect = container.getBoundingClientRect()

                const randomX = Math.random() * 100 - 50
                const randomY = Math.random() * 100 - 50

                noteElement.style.left = rect.left - containerRect.left + rect.width / 2 + randomX + "px"
                noteElement.style.top = rect.top - containerRect.top + rect.height / 2 + randomY + "px"

                container.appendChild(noteElement)

                setTimeout(() => {
                    noteElement.remove()
                }, 2000)
            }, delay)
            delay += 200
        })

        // Efecto visual de "scratch"
        this.style.animation = "none"
        this.style.transform = "rotate(10deg)"

        setTimeout(() => {
            this.style.transition = "transform 0.5s ease-out"
            this.style.transform = "rotate(-10deg)"

            setTimeout(() => {
                this.style.transition = "none"
                this.style.animation = "rotate 20s linear infinite"
            }, 500)
        }, 100)
    })

    // Efecto de click en el botón
    homeButton.addEventListener("click", () => {
        // Efecto de explosión musical
        const explosion = document.createElement("div")
        explosion.style.position = "absolute"
        explosion.style.top = "50%"
        explosion.style.left = "50%"
        explosion.style.transform = "translate(-50%, -50%)"
        explosion.style.width = "10px"
        explosion.style.height = "10px"
        explosion.style.borderRadius = "50%"
        explosion.style.backgroundColor = "#f39c12"
        explosion.style.boxShadow = "0 0 20px 10px #f39c12"
        explosion.style.zIndex = "100"
        document.body.appendChild(explosion)

        // Reproducir un acorde final
        if (audioContext) {
            ;["C4", "E4", "G4", "B4"].forEach((note) => {
                playNote(notes[note])
            })
        }

        // Animación de explosión
        const expandAnimation = explosion.animate(
            [
                {width: "10px", height: "10px", opacity: 1},
                {width: "2000px", height: "2000px", opacity: 0},
            ],
            {
                duration: 1000,
                easing: "ease-out",
            },
        )

        expandAnimation.onfinish = () => {
            // Redirigir a la página principal
            window.location.href = "/"
        }
    })

    // Crear notas musicales aleatorias que flotan por la pantalla
    function createRandomNotes() {
        setInterval(() => {
            const noteSymbols = ["♪", "♫", "♩", "♬"]
            const randomNote = noteSymbols[Math.floor(Math.random() * noteSymbols.length)]

            const noteElement = document.createElement("div")
            noteElement.className = "note-animation"
            noteElement.textContent = randomNote
            noteElement.style.fontSize = Math.random() * 2 + 1 + "rem"

            // Posición aleatoria en la pantalla
            noteElement.style.left = Math.random() * 100 + "%"
            noteElement.style.top = "100%"

            // Animación personalizada
            noteElement.animate(
                [
                    {top: "100%", opacity: 0},
                    {top: "0%", opacity: 1},
                ],
                {
                    duration: Math.random() * 5000 + 3000,
                    easing: "ease-out",
                },
            )

            container.appendChild(noteElement)

            // Eliminar la nota después de la animación
            setTimeout(() => {
                noteElement.remove()
            }, 8000)
        }, 2000)
    }

    // Iniciar la creación de notas aleatorias
    createRandomNotes()

    // Soporte para teclado físico
    const keyMap = {
        a: "C4",
        w: "C#4",
        s: "D4",
        e: "D#4",
        d: "E4",
        f: "F4",
        t: "F#4",
        g: "G4",
        y: "G#4",
        h: "A4",
        u: "A#4",
        j: "B4",
    }

    document.addEventListener("keydown", (e) => {
        if (e.repeat) return // Evitar repetición al mantener presionada la tecla

        const note = keyMap[e.key.toLowerCase()]
        if (note) {
            const keyElement = document.querySelector(`.piano-key[data-note="${note}"]`)
            if (keyElement) {
                keyElement.classList.add("active")
                playNote(notes[note])
                createNoteAnimation(keyElement)
            }
        }
    })

    document.addEventListener("keyup", (e) => {
        const note = keyMap[e.key.toLowerCase()]
        if (note) {
            const keyElement = document.querySelector(`.piano-key[data-note="${note}"]`)
            if (keyElement) {
                keyElement.classList.remove("active")
            }
        }
    })
})
