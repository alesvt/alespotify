-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: db
-- Tiempo de generación: 16-05-2025 a las 08:57:32
-- Versión del servidor: 9.3.0
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `alespotify`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `album`
--

CREATE TABLE `album`
(
    `id`           int          NOT NULL,
    `nombre`       varchar(255) NOT NULL,
    `imagen`       varchar(255) DEFAULT NULL,
    `fecha_salida` date         DEFAULT NULL,
    `artista_id`   int          DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `album`
--

INSERT INTO `album` (`id`, `nombre`, `imagen`, `fecha_salida`, `artista_id`)
VALUES (5, 'Sinfonía No. 5',
        'https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/BeethovenSinfonia5autografo.jpg/330px-BeethovenSinfonia5autografo.jpg',
        '1808-01-01', 5),
       (6, 'Good Bye and good Riddance',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', '2018-05-23', 6),
       (7, 'Se nos lleva el aire',
        'https://www.mondosonoro.com/wp-content/uploads/2023/12/Robe-Se-nos-lleva-el-aire.jpg', '2023-12-15', 7),
       (8, '111XPANTIA', 'https://i.scdn.co/image/ab67616d0000b2734f13783ebca8686ddc7992e7', '2025-05-02', 8),
       (10, 'Año Zero', 'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', '2010-04-27', 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artista`
--

CREATE TABLE `artista`
(
    `id`             int          NOT NULL,
    `nombre`         varchar(255) NOT NULL,
    `descripcion`    text,
    `imagen`         varchar(255)      DEFAULT NULL,
    `fecha_creacion` timestamp    NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `artista`
--

INSERT INTO `artista` (`id`, `nombre`, `descripcion`, `imagen`, `fecha_creacion`)
VALUES (5, 'Ludwig van Beethoven', 'Compositor y pianista alemán del período clásico.',
        'https://www.dallassymphony.org/wp-content/uploads/2022/03/Ludwig-van-Beethoven.jpg', '2025-05-08 13:01:11'),
       (6, 'Juice WRLD',
        'Jarad Anthony Higgins, conocido por su nombre artístico Juice WRLD, fue un rapero estadounidense. A lo largo de su carrera de cuatro años, fue una figura destacada en los géneros de emo y rap de SoundCloud, que atrajeron la atención del público general a mediados y finales de la década de 2010.',
        'https://publitronic.es/wp-content/uploads/2025/05/Stay-High-mp3-image.jpg', '2025-05-09 11:00:19'),
       (7, 'Robe',
        'Roberto Iniesta Ojea ​ conocido simplemente como Robe, es un compositor, músico, poeta y escritor español conocido por ser el fundador e imagen del grupo de rock Extremoduro.',
        'https://s3.ppllstatics.com/elcorreo/www/multimedia/2025/02/06/robe-iniesta-kpDH-U230782701128pFH-1200x840@El%20Correo.jpg',
        '2025-05-12 10:33:29'),
       (8, 'Fuerza Regida',
        '5 MORROS DEL BARRIO CON AMOR A LA MUSICA WE GOING GLOBAL GAD!!!   1era voz: Jesus Ortiz 2da voz y requinto: Samuel Jaimez Armonía: Khrystian Ramos Tuba: José Garcia Tololoche: Moises Lopez',
        'https://i.scdn.co/image/ab6761670000ecd4ea25532e2d5b8e11fa8c52a7', '2025-05-12 10:37:16'),
       (9, 'La Fúmiga',
        'La Fúmiga es un grupo de música valenciano originario de la ciudad de Alcira en el año 2012. ​Su estilo mezcla influencias de la música banda y de la alternativa. ​',
        'https://publitronic.es/wp-content/uploads/2025/05/segonaconjugacio-mp3-image.jpg', '2025-05-12 11:03:17'),
       (10, 'M-Clan',
        'M-Clan es un grupo español de rock. Sus componentes son Carlos Tarque y Ricardo Ruipérez. En su último álbum les acompañaron Iván González “Chapo”, Coki Giménez y Prisco.​',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSwjlVwDnxDBl-vbtAriBgs2slet6TsArBG-lUWUFu4-yJqXET-UlMI9oRHpYuRQWNyCigoUC52ahfaSKuF7GTfzg',
        '2025-05-12 11:14:47'),
       (11, 'Carlos Baute',
        'Carlos Roberto Baute Jiménez es un cantautor, arreglista, actor y presentador venezolano. Con trece años de edad, formó parte de la banda juvenil Los Chamos y posteriormente siguió con sus estudios musicales de canto, guitarra y piano.',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTHLLGmpzeg2t7qpItFMJ56Y0clITcsz5kPuPCaOCME1w84nA3kFJQ_frIiC9XiXsKYO44mV0JKEZw6sTfECYTowg',
        '2025-05-12 11:14:47'),
       (12, 'Pignoise',
        'Pignoise es una banda de pop punk​ formada por el exfutbolista del Real Madrid Álvaro Benito, Pablo Alonso Álvarez y Héctor Polo.',
        'https://s1.ppllstatics.com/leonoticias/www/multimedia/2024/10/29/pignoise-kY8D-U2201648217107ZMG-1200x840@Leonoticias.jpg',
        '2025-05-12 11:14:47'),
       (13, 'Despistaos', 'Despistaos', 'https://lafonoteca.net/wp-content/uploads/2008/05/despistaos.jpg',
        '2025-05-12 11:17:04'),
       (14, 'Los delinqüentes', 'Los delinqüentes', 'https://i.scdn.co/image/ab6761610000e5eb395c224b8c609a0d904ea3bf',
        '2025-05-12 11:17:04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artista_albums`
--

CREATE TABLE `artista_albums`
(
    `artista_id` int NOT NULL,
    `albums_id`  int NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cancion`
--

CREATE TABLE `cancion`
(
    `id`                int          NOT NULL,
    `nombre`            varchar(255) NOT NULL,
    `enlace`            varchar(255) NOT NULL,
    `imagen`            varchar(255) DEFAULT NULL,
    `duracion`          int UNSIGNED DEFAULT NULL,
    `veces_reproducido` int UNSIGNED DEFAULT '0',
    `genero_id`         int          DEFAULT NULL,
    `album_id`          int          DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cancion`
--

INSERT INTO `cancion` (`id`, `nombre`, `enlace`, `imagen`, `duracion`, `veces_reproducido`, `genero_id`, `album_id`)
VALUES (5, 'Sinfonía No. 5 en Do menor, Op. 67: I. Allegro con brio', 'https://ejemplo.com/sinfonia_5_mov1',
        'https://m.media-amazon.com/images/I/610XfOR9wnL._UXNaN_FMjpg_QL85_.jpg', 480, 2, 5, 5),
       (7, 'Lean wit me', 'https://publitronic.es/wp-content/uploads/2025/05/Lean-Wit-Me.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 175, 74, 4, 6),
       (11, 'Lucid Dreams', 'https://publitronic.es/wp-content/uploads/2025/05/Lucid-Dreams.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 240, 76, 4, 6),
       (12, 'GodFather', 'https://publitronic.es/wp-content/uploads/2025/05/GodFather-Fuerza-Regida.mp3',
        'https://i.scdn.co/image/ab67616d0000b2734f13783ebca8686ddc7992e7', 176, 17, 6, 8),
       (13, 'Segona Conjugació', 'https://publitronic.es/wp-content/uploads/2025/05/segonaconjugacio.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/segonaconjugacio-mp3-image.jpg', 187, 12, 1, NULL),
       (14, 'El aire de la calle', 'https://publitronic.es/wp-content/uploads/2025/05/elairedelacalle.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Carolina-mp3-image.jpg', 211, 44, 2, NULL),
       (15, 'Colgando en tus manos', 'https://publitronic.es/wp-content/uploads/2025/05/colgandoentusmanos.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Carolina-mp3-image.jpg', 234, 33, 1, NULL),
       (16, 'I\'ll be fine', 'https://publitronic.es/wp-content/uploads/2025/05/Ill-Be-Fine.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 244, 0, 4, 6),
       (17, 'Candles', 'https://publitronic.es/wp-content/uploads/2025/05/Candles.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 184, 0, 4, 6),
       (18, 'Armed and dangerous', 'https://publitronic.es/wp-content/uploads/2025/05/Armed-And-Dangerous.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 170, 0, 4, 6),
       (19, 'All girls are the same', 'https://publitronic.es/wp-content/uploads/2025/05/All-Girls-Are-The-Same.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 166, 7, 4, 6),
       (20, 'Black & White', 'https://publitronic.es/wp-content/uploads/2025/05/Black-White.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/Wasted-feat-Lil-Uzi-Vert-mp3-image.jpg', 187, 8, 4, 6),
       (21, 'Año Zero', 'https://publitronic.es/wp-content/uploads/2025/05/Ano-Zero.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 38, 0, 2, 10),
       (22, 'Animal', 'https://publitronic.es/wp-content/uploads/2025/05/Animal.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 220, 0, 2, 10),
       (23, 'Todo se muere', 'https://publitronic.es/wp-content/uploads/2025/05/Todo-Se-Muere.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 204, 21, 2, 10),
       (24, 'Todo me da igual', 'https://publitronic.es/wp-content/uploads/2025/05/Todo-Me-Da-Igual.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 205, 0, 2, 10),
       (25, 'Quiero', 'https://publitronic.es/wp-content/uploads/2025/05/Quiero.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 160, 17, 2, 10),
       (26, 'Perdido en la oscuridad', 'https://publitronic.es/wp-content/uploads/2025/05/Perdido-En-La-Oscuridad.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 208, 1, 2, 10),
       (27, 'Mundo muerto', 'https://publitronic.es/wp-content/uploads/2025/05/Mundo-Muerto.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 177, 38, 2, 10),
       (28, 'Lo tuve', 'https://publitronic.es/wp-content/uploads/2025/05/Lo-Tuve.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 261, 1, 2, 10),
       (29, 'Entre nosotros', 'https://publitronic.es/wp-content/uploads/2025/05/Entre-Nosotros.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 229, 4, 2, 10),
       (30, 'Desesperado', 'https://publitronic.es/wp-content/uploads/2025/05/Desesperado.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 219, 2, 2, 10),
       (31, 'Culpables', 'https://publitronic.es/wp-content/uploads/2025/05/Culpables.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 232, 3, 2, 10),
       (32, 'Cama vacía', 'https://publitronic.es/wp-content/uploads/2025/05/Cama-Vacia.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 243, 28, 2, 10),
       (33, 'Bajo tu suela', 'https://publitronic.es/wp-content/uploads/2025/05/Bajo-Tu-Suela.mp3',
        'https://i.scdn.co/image/ab67616d0000b273e52e9265981a892d9d29ccbc', 215, 0, 2, 10),
       (34, 'A la orilla del río', 'https://publitronic.es/wp-content/uploads/2025/05/A-la-orilla-del-rio-Robe.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/A-la-orilla-del-rio-Robe-mp3-image.jpg', 240, 3, 2, 7),
       (35, 'Puntos suspensivos', 'https://publitronic.es/wp-content/uploads/2025/05/Puntos-suspensivos-Robe.mp3',
        'https://publitronic.es/wp-content/uploads/2025/05/A-la-orilla-del-rio-Robe-mp3-image.jpg', 352, 4, 2, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cancion_album`
--

CREATE TABLE `cancion_album`
(
    `cancion_id` int DEFAULT NULL,
    `album_id`   int DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cancion_album`
--

INSERT INTO `cancion_album` (`cancion_id`, `album_id`)
VALUES (7, 6),
       (11, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cancion_artista`
--

CREATE TABLE `cancion_artista`
(
    `cancion_id` int NOT NULL,
    `artista_id` int NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cancion_artista`
--

INSERT INTO `cancion_artista` (`cancion_id`, `artista_id`)
VALUES (5, 5),
       (7, 6),
       (11, 6),
       (16, 6),
       (17, 6),
       (18, 6),
       (19, 6),
       (20, 6),
       (34, 7),
       (35, 7),
       (12, 8),
       (13, 9),
       (15, 11),
       (21, 12),
       (22, 12),
       (23, 12),
       (24, 12),
       (25, 12),
       (26, 12),
       (27, 12),
       (28, 12),
       (29, 12),
       (30, 12),
       (31, 12),
       (32, 12),
       (33, 12),
       (14, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos`
--

CREATE TABLE `favoritos`
(
    `usuario_id`  int NOT NULL,
    `playlist_id` int NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `favoritos`
--

INSERT INTO `favoritos` (`usuario_id`, `playlist_id`)
VALUES (19, 15),
       (20, 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE `genero`
(
    `id`     int         NOT NULL,
    `nombre` varchar(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`id`, `nombre`)
VALUES (5, 'Clásica'),
       (6, 'Corrido'),
       (3, 'Electrónica'),
       (4, 'Hip Hop'),
       (1, 'Pop'),
       (7, 'Reggaeton'),
       (2, 'Rock');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `playlist`
--

CREATE TABLE `playlist`
(
    `id`             int          NOT NULL,
    `nombre`         varchar(255) NOT NULL,
    `usuario_id`     int          NOT NULL,
    `publica`        tinyint(1)        DEFAULT '0',
    `imagen`         varchar(255)      DEFAULT NULL,
    `fecha_creacion` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    `fecha_edicion`  timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `playlist`
--

INSERT INTO `playlist` (`id`, `nombre`, `usuario_id`, `publica`, `imagen`, `fecha_creacion`, `fecha_edicion`)
VALUES (2, 'Rock Clásico', 1, 1, 'https://publitronic.es/wp-content/uploads/2025/05/Rock-clasico.png',
        '2025-05-08 13:01:11', '2025-05-14 12:56:20'),
       (15, 'Favoritos de alesv', 19, 0, 'https://placehold.co/200?text=Mis%20favoritos', '2025-05-09 09:36:10',
        '2025-05-09 09:36:10'),
       (24, 'Favoritos de pedro', 20, 0, 'https://placehold.co/200?text=Mis%20favoritos', '2025-05-12 11:06:59',
        '2025-05-12 11:06:59'),
       (25, 'Los 2000 España', 1, 1, 'https://publitronic.es/wp-content/uploads/2025/05/Carolina-mp3-image.jpg',
        '2025-05-12 11:19:15', '2025-05-12 11:19:15'),
       (26, 'Descubrimiento semanal', 1, 1, 'https://publitronic.es/wp-content/uploads/2025/05/discovery.png',
        '2025-05-12 11:23:48', '2025-05-14 12:44:30');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `playlist_cancion`
--

CREATE TABLE `playlist_cancion`
(
    `playlist_id`       int       NOT NULL,
    `cancion_id`        int       NOT NULL,
    `veces_en_playlist` int UNSIGNED   DEFAULT '1',
    `fecha_agregacion`  timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `playlist_cancion`
--

INSERT INTO `playlist_cancion` (`playlist_id`, `cancion_id`, `veces_en_playlist`, `fecha_agregacion`)
VALUES (2, 23, 1, '2025-05-14 13:11:03'),
       (2, 25, 1, '2025-05-14 13:11:03'),
       (2, 27, 1, '2025-05-14 13:11:03'),
       (2, 32, 1, '2025-05-14 13:11:03'),
       (25, 14, 1, '2025-05-12 11:19:40'),
       (25, 15, 1, '2025-05-12 11:21:30'),
       (26, 12, 1, '2025-05-14 11:34:09'),
       (26, 13, 1, '2025-05-14 11:34:09'),
       (26, 19, 1, '2025-05-14 11:34:09'),
       (26, 20, 1, '2025-05-14 11:34:09'),
       (26, 29, 1, '2025-05-14 11:34:09'),
       (26, 32, 1, '2025-05-14 11:34:09');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario`
(
    `id`             int          NOT NULL,
    `nombre`         varchar(100) NOT NULL,
    `email`          varchar(255) NOT NULL,
    `contrasena`     varchar(255) NOT NULL,
    `fecha_creacion` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    `imagen`         varchar(255)      DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `email`, `contrasena`, `fecha_creacion`, `imagen`)
VALUES (1, 'Usuario Uno', 'uno@ejemplo.com', 'clave123', '2025-05-08 13:01:11', NULL),
       (2, 'Usuario Dos', 'dos@ejemplo.com', 'miclave456', '2025-05-08 13:01:11', NULL),
       (19, 'alesv', 'a@b.c', '$2a$10$.7qCmmXb0zJlBwl0MggNye8L/6tqx32wuJsC5.RjDTRCilDhII0S6', '2025-03-14 09:52:19',
        'https://pbs.twimg.com/profile_images/1741945682640986114/qBVODaHb_400x400.jpg'),
       (20, 'pedro', '1@2.com', '$2a$10$2TMkOQUvkJBEfhaIuAuphOsZd.IgCCXTK4rLCuE/BfwCKEmybidRa', '2025-05-12 11:06:59',
        NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `album`
--
ALTER TABLE `album`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `nombre` (`nombre`, `artista_id`),
    ADD KEY `artista_id` (`artista_id`);

--
-- Indices de la tabla `artista`
--
ALTER TABLE `artista`
    ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `artista_albums`
--
ALTER TABLE `artista_albums`
    ADD PRIMARY KEY (`artista_id`, `albums_id`),
    ADD UNIQUE KEY `UKegu5jxyegm49jsolb82u11vln` (`albums_id`);

--
-- Indices de la tabla `cancion`
--
ALTER TABLE `cancion`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `enlace` (`enlace`),
    ADD KEY `genero_id` (`genero_id`),
    ADD KEY `album_id` (`album_id`);

--
-- Indices de la tabla `cancion_album`
--
ALTER TABLE `cancion_album`
    ADD KEY `cancion_album_ibfk_1` (`cancion_id`),
    ADD KEY `cancion_album_ibfk_2` (`album_id`);

--
-- Indices de la tabla `cancion_artista`
--
ALTER TABLE `cancion_artista`
    ADD PRIMARY KEY (`cancion_id`, `artista_id`),
    ADD KEY `artista_id` (`artista_id`);

--
-- Indices de la tabla `favoritos`
--
ALTER TABLE `favoritos`
    ADD PRIMARY KEY (`usuario_id`),
    ADD UNIQUE KEY `playlist_id` (`playlist_id`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `playlist`
--
ALTER TABLE `playlist`
    ADD PRIMARY KEY (`id`),
    ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `playlist_cancion`
--
ALTER TABLE `playlist_cancion`
    ADD PRIMARY KEY (`playlist_id`, `cancion_id`),
    ADD KEY `cancion_id` (`cancion_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `album`
--
ALTER TABLE `album`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 11;

--
-- AUTO_INCREMENT de la tabla `artista`
--
ALTER TABLE `artista`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 15;

--
-- AUTO_INCREMENT de la tabla `cancion`
--
ALTER TABLE `cancion`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 36;

--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 8;

--
-- AUTO_INCREMENT de la tabla `playlist`
--
ALTER TABLE `playlist`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 28;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
    MODIFY `id` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 27;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `album`
--
ALTER TABLE `album`
    ADD CONSTRAINT `album_ibfk_1` FOREIGN KEY (`artista_id`) REFERENCES `artista` (`id`);

--
-- Filtros para la tabla `artista_albums`
--
ALTER TABLE `artista_albums`
    ADD CONSTRAINT `FK4b47nilu0pbq3rggkf4jlperb` FOREIGN KEY (`artista_id`) REFERENCES `artista` (`id`),
    ADD CONSTRAINT `FKfdrnkttf6q87otnmcycx5j9xq` FOREIGN KEY (`albums_id`) REFERENCES `album` (`id`);

--
-- Filtros para la tabla `cancion`
--
ALTER TABLE `cancion`
    ADD CONSTRAINT `cancion_ibfk_1` FOREIGN KEY (`genero_id`) REFERENCES `genero` (`id`),
    ADD CONSTRAINT `cancion_ibfk_2` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`);

--
-- Filtros para la tabla `cancion_album`
--
ALTER TABLE `cancion_album`
    ADD CONSTRAINT `cancion_album_ibfk_1` FOREIGN KEY (`cancion_id`) REFERENCES `cancion` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `cancion_album_ibfk_2` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `cancion_artista`
--
ALTER TABLE `cancion_artista`
    ADD CONSTRAINT `cancion_artista_ibfk_1` FOREIGN KEY (`cancion_id`) REFERENCES `cancion` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `cancion_artista_ibfk_2` FOREIGN KEY (`artista_id`) REFERENCES `artista` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `favoritos`
--
ALTER TABLE `favoritos`
    ADD CONSTRAINT `favoritos_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `favoritos_ibfk_2` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `playlist`
--
ALTER TABLE `playlist`
    ADD CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `playlist_cancion`
--
ALTER TABLE `playlist_cancion`
    ADD CONSTRAINT `playlist_cancion_ibfk_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `playlist_cancion_ibfk_2` FOREIGN KEY (`cancion_id`) REFERENCES `cancion` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
