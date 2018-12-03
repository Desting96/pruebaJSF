-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-12-2018 a las 06:56:33
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `videominuto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificacion`
--

CREATE TABLE `calificacion` (
  `idCalificacion` int(11) NOT NULL,
  `calificacion` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `idCarrito` int(11) NOT NULL,
  `precioTotal` varchar(45) NOT NULL,
  `fecha` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito_has_infovideo`
--

CREATE TABLE `carrito_has_infovideo` (
  `IdCarrito_has_InfoVideo` int(11) NOT NULL,
  `Carrito_idCarrito` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clasificacion`
--

CREATE TABLE `clasificacion` (
  `idClasificacion` int(11) NOT NULL,
  `clasificacion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `clasificacion`
--

INSERT INTO `clasificacion` (`idClasificacion`, `clasificacion`) VALUES
(1, '+18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero`
--

CREATE TABLE `genero` (
  `idGenero` int(11) NOT NULL,
  `genero` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `genero`
--

INSERT INTO `genero` (`idGenero`, `genero`) VALUES
(1, 'Accion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genero_has_infovideo`
--

CREATE TABLE `genero_has_infovideo` (
  `IdGenero_has_InfoVideo` int(11) NOT NULL,
  `Genero_idGenero` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `infovideo`
--

CREATE TABLE `infovideo` (
  `idInfoVideo` int(11) NOT NULL,
  `nombreVideo` varchar(45) NOT NULL,
  `imagen` varchar(45) NOT NULL,
  `precio` varchar(45) NOT NULL,
  `personaje` varchar(45) DEFAULT NULL,
  `Clasificacion_idClasificacion` int(11) NOT NULL,
  `TipoVideo_idTipoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `infovideo`
--

INSERT INTO `infovideo` (`idInfoVideo`, `nombreVideo`, `imagen`, `precio`, `personaje`, `Clasificacion_idClasificacion`, `TipoVideo_idTipoVideo`) VALUES
(3, 'Caballo', 'resources/imagenes/nno7.PNG', '200', 'Carlos', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lenguaje`
--

CREATE TABLE `lenguaje` (
  `idLenguaje` int(11) NOT NULL,
  `lenguaje` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `idPersona` int(11) NOT NULL,
  `nombrePersona` varchar(45) NOT NULL,
  `Reparto_idReparto` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reparto`
--

CREATE TABLE `reparto` (
  `idReparto` int(11) NOT NULL,
  `reparto` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolusuario`
--

CREATE TABLE `rolusuario` (
  `idRolUsuario` int(11) NOT NULL,
  `rolUsuario` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rolusuario`
--

INSERT INTO `rolusuario` (`idRolUsuario`, `rolUsuario`) VALUES
(1, 'Admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud`
--

CREATE TABLE `solicitud` (
  `idSolicitud` int(11) NOT NULL,
  `solicitud` varchar(45) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subtitulo`
--

CREATE TABLE `subtitulo` (
  `idSubtitulo` int(11) NOT NULL,
  `subtitulo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipovideo`
--

CREATE TABLE `tipovideo` (
  `idTipoVideo` int(11) NOT NULL,
  `tipoVideo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipovideo`
--

INSERT INTO `tipovideo` (`idTipoVideo`, `tipoVideo`) VALUES
(1, 'Accion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `nick` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `saldo` varchar(45) NOT NULL,
  `RolUsuario_idRolUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `video`
--

CREATE TABLE `video` (
  `idVideo` int(11) NOT NULL,
  `nombreCapitulo` varchar(45) NOT NULL,
  `duracion` varchar(45) NOT NULL,
  `video` varchar(45) NOT NULL,
  `isan` varchar(45) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `video_has_lenguaje`
--

CREATE TABLE `video_has_lenguaje` (
  `IdVideo_has_Lenguaje` int(11) NOT NULL,
  `Video_idVideo` int(11) NOT NULL,
  `Lenguaje_idLenguaje` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `video_has_subtitulo`
--

CREATE TABLE `video_has_subtitulo` (
  `IdVideo_has_Subtitulo` int(11) NOT NULL,
  `Video_idVideo` int(11) NOT NULL,
  `Subtitulo_idSubtitulo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD PRIMARY KEY (`idCalificacion`),
  ADD KEY `fk_Calificacion_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`idCarrito`),
  ADD KEY `fk_Carrito_Usuario1_idx` (`Usuario_idUsuario`);

--
-- Indices de la tabla `carrito_has_infovideo`
--
ALTER TABLE `carrito_has_infovideo`
  ADD PRIMARY KEY (`IdCarrito_has_InfoVideo`),
  ADD KEY `fk_Carrito_has_InfoVideo_InfoVideo1_idx` (`InfoVideo_idInfoVideo`),
  ADD KEY `fk_Carrito_has_InfoVideo_Carrito1_idx` (`Carrito_idCarrito`);

--
-- Indices de la tabla `clasificacion`
--
ALTER TABLE `clasificacion`
  ADD PRIMARY KEY (`idClasificacion`);

--
-- Indices de la tabla `genero`
--
ALTER TABLE `genero`
  ADD PRIMARY KEY (`idGenero`);

--
-- Indices de la tabla `genero_has_infovideo`
--
ALTER TABLE `genero_has_infovideo`
  ADD PRIMARY KEY (`IdGenero_has_InfoVideo`),
  ADD KEY `fk_Genero_has_InfoVideo_InfoVideo1_idx` (`InfoVideo_idInfoVideo`),
  ADD KEY `fk_Genero_has_InfoVideo_Genero1_idx` (`Genero_idGenero`);

--
-- Indices de la tabla `infovideo`
--
ALTER TABLE `infovideo`
  ADD PRIMARY KEY (`idInfoVideo`),
  ADD KEY `fk_Infovideo_Clasificacion_idx` (`Clasificacion_idClasificacion`),
  ADD KEY `fk_InfoVideo_TipoVideo1_idx` (`TipoVideo_idTipoVideo`);

--
-- Indices de la tabla `lenguaje`
--
ALTER TABLE `lenguaje`
  ADD PRIMARY KEY (`idLenguaje`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`idPersona`),
  ADD KEY `fk_Persona_Reparto1_idx` (`Reparto_idReparto`),
  ADD KEY `fk_Persona_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `reparto`
--
ALTER TABLE `reparto`
  ADD PRIMARY KEY (`idReparto`);

--
-- Indices de la tabla `rolusuario`
--
ALTER TABLE `rolusuario`
  ADD PRIMARY KEY (`idRolUsuario`);

--
-- Indices de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD PRIMARY KEY (`idSolicitud`),
  ADD KEY `fk_Solicitud_Usuario1_idx` (`Usuario_idUsuario`);

--
-- Indices de la tabla `subtitulo`
--
ALTER TABLE `subtitulo`
  ADD PRIMARY KEY (`idSubtitulo`);

--
-- Indices de la tabla `tipovideo`
--
ALTER TABLE `tipovideo`
  ADD PRIMARY KEY (`idTipoVideo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `fk_Usuario_RolUsuario1_idx` (`RolUsuario_idRolUsuario`);

--
-- Indices de la tabla `video`
--
ALTER TABLE `video`
  ADD PRIMARY KEY (`idVideo`),
  ADD KEY `fk_Video_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `video_has_lenguaje`
--
ALTER TABLE `video_has_lenguaje`
  ADD PRIMARY KEY (`IdVideo_has_Lenguaje`),
  ADD KEY `fk_Video_has_Lenguaje_Lenguaje1_idx` (`Lenguaje_idLenguaje`),
  ADD KEY `fk_Video_has_Lenguaje_Video1_idx` (`Video_idVideo`);

--
-- Indices de la tabla `video_has_subtitulo`
--
ALTER TABLE `video_has_subtitulo`
  ADD PRIMARY KEY (`IdVideo_has_Subtitulo`),
  ADD KEY `fk_Video_has_Subtitulo_Subtitulo1_idx` (`Subtitulo_idSubtitulo`),
  ADD KEY `fk_Video_has_Subtitulo_Video1_idx` (`Video_idVideo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  MODIFY `idCalificacion` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `idCarrito` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `carrito_has_infovideo`
--
ALTER TABLE `carrito_has_infovideo`
  MODIFY `IdCarrito_has_InfoVideo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `clasificacion`
--
ALTER TABLE `clasificacion`
  MODIFY `idClasificacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `genero`
--
ALTER TABLE `genero`
  MODIFY `idGenero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `genero_has_infovideo`
--
ALTER TABLE `genero_has_infovideo`
  MODIFY `IdGenero_has_InfoVideo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `infovideo`
--
ALTER TABLE `infovideo`
  MODIFY `idInfoVideo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `lenguaje`
--
ALTER TABLE `lenguaje`
  MODIFY `idLenguaje` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `reparto`
--
ALTER TABLE `reparto`
  MODIFY `idReparto` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `rolusuario`
--
ALTER TABLE `rolusuario`
  MODIFY `idRolUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  MODIFY `idSolicitud` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `subtitulo`
--
ALTER TABLE `subtitulo`
  MODIFY `idSubtitulo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `tipovideo`
--
ALTER TABLE `tipovideo`
  MODIFY `idTipoVideo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `video`
--
ALTER TABLE `video`
  MODIFY `idVideo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `video_has_lenguaje`
--
ALTER TABLE `video_has_lenguaje`
  MODIFY `IdVideo_has_Lenguaje` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `video_has_subtitulo`
--
ALTER TABLE `video_has_subtitulo`
  MODIFY `IdVideo_has_Subtitulo` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD CONSTRAINT `fk_Calificacion_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `infovideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `fk_Carrito_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `carrito_has_infovideo`
--
ALTER TABLE `carrito_has_infovideo`
  ADD CONSTRAINT `fk_Carrito_has_InfoVideo_Carrito1` FOREIGN KEY (`Carrito_idCarrito`) REFERENCES `carrito` (`idCarrito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Carrito_has_InfoVideo_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `infovideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `genero_has_infovideo`
--
ALTER TABLE `genero_has_infovideo`
  ADD CONSTRAINT `fk_Genero_has_InfoVideo_Genero1` FOREIGN KEY (`Genero_idGenero`) REFERENCES `genero` (`idGenero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Genero_has_InfoVideo_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `infovideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `infovideo`
--
ALTER TABLE `infovideo`
  ADD CONSTRAINT `fk_InfoVideo_TipoVideo1` FOREIGN KEY (`TipoVideo_idTipoVideo`) REFERENCES `tipovideo` (`idTipoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Infovideo_Clasificacion` FOREIGN KEY (`Clasificacion_idClasificacion`) REFERENCES `clasificacion` (`idClasificacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `fk_Persona_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `infovideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Persona_Reparto1` FOREIGN KEY (`Reparto_idReparto`) REFERENCES `reparto` (`idReparto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD CONSTRAINT `fk_Solicitud_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_RolUsuario1` FOREIGN KEY (`RolUsuario_idRolUsuario`) REFERENCES `rolusuario` (`idRolUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `video`
--
ALTER TABLE `video`
  ADD CONSTRAINT `video_ibfk_1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `infovideo` (`idInfoVideo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `video_has_lenguaje`
--
ALTER TABLE `video_has_lenguaje`
  ADD CONSTRAINT `fk_Video_has_Lenguaje_Lenguaje1` FOREIGN KEY (`Lenguaje_idLenguaje`) REFERENCES `lenguaje` (`idLenguaje`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Video_has_Lenguaje_Video1` FOREIGN KEY (`Video_idVideo`) REFERENCES `video` (`idVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `video_has_subtitulo`
--
ALTER TABLE `video_has_subtitulo`
  ADD CONSTRAINT `fk_Video_has_Subtitulo_Subtitulo1` FOREIGN KEY (`Subtitulo_idSubtitulo`) REFERENCES `subtitulo` (`idSubtitulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Video_has_Subtitulo_Video1` FOREIGN KEY (`Video_idVideo`) REFERENCES `video` (`idVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
