-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 23-11-2018 a las 00:49:57
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
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
-- Estructura de tabla para la tabla `Calificacion`
--

CREATE TABLE `Calificacion` (
  `idCalificacion` int(11) NOT NULL,
  `calificacion` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Carrito`
--

CREATE TABLE `Carrito` (
  `idCarrito` int(11) NOT NULL,
  `precioTotal` varchar(45) NOT NULL,
  `fecha` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Carrito_has_InfoVideo`
--

CREATE TABLE `Carrito_has_InfoVideo` (
  `IdCarrito_has_InfoVideo` int(11) NOT NULL,
  `Carrito_idCarrito` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Clasificacion`
--

CREATE TABLE `Clasificacion` (
  `idClasificacion` int(11) NOT NULL,
  `clasificacion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Genero`
--

CREATE TABLE `Genero` (
  `idGenero` int(11) NOT NULL,
  `genero` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Genero`
--

INSERT INTO `Genero` (`idGenero`, `genero`) VALUES
(1, 'Accion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Genero_has_InfoVideo`
--

CREATE TABLE `Genero_has_InfoVideo` (
  `IdGenero_has_InfoVideo` int(11) NOT NULL,
  `Genero_idGenero` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `InfoVideo`
--

CREATE TABLE `InfoVideo` (
  `idInfoVideo` int(11) NOT NULL,
  `nombreVideo` varchar(45) NOT NULL,
  `imagen` varchar(45) NOT NULL,
  `precio` varchar(45) NOT NULL,
  `personaje` varchar(45) DEFAULT NULL,
  `Clasificacion_idClasificacion` int(11) NOT NULL,
  `TipoVideo_idTipoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Lenguaje`
--

CREATE TABLE `Lenguaje` (
  `idLenguaje` int(11) NOT NULL,
  `lenguaje` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Persona`
--

CREATE TABLE `Persona` (
  `idPersona` int(11) NOT NULL,
  `nombrePersona` varchar(45) NOT NULL,
  `Reparto_idReparto` int(11) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Reparto`
--

CREATE TABLE `Reparto` (
  `idReparto` int(11) NOT NULL,
  `reparto` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `RolUsuario`
--

CREATE TABLE `RolUsuario` (
  `idRolUsuario` int(11) NOT NULL,
  `rolUsuario` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `RolUsuario`
--

INSERT INTO `RolUsuario` (`idRolUsuario`, `rolUsuario`) VALUES
(1, 'Admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Solicitud`
--

CREATE TABLE `Solicitud` (
  `idSolicitud` int(11) NOT NULL,
  `solicitud` varchar(45) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Subtitulo`
--

CREATE TABLE `Subtitulo` (
  `idSubtitulo` int(11) NOT NULL,
  `subtitulo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TipoVideo`
--

CREATE TABLE `TipoVideo` (
  `idTipoVideo` int(11) NOT NULL,
  `tipoVideo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
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
-- Estructura de tabla para la tabla `Video`
--

CREATE TABLE `Video` (
  `idVideo` int(11) NOT NULL,
  `nombreCapitulo` varchar(45) NOT NULL,
  `duracion` varchar(45) NOT NULL,
  `video` varchar(45) NOT NULL,
  `isan` varchar(45) NOT NULL,
  `InfoVideo_idInfoVideo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Video_has_Lenguaje`
--

CREATE TABLE `Video_has_Lenguaje` (
  `IdVideo_has_Lenguaje` int(11) NOT NULL,
  `Video_idVideo` int(11) NOT NULL,
  `Lenguaje_idLenguaje` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Video_has_Subtitulo`
--

CREATE TABLE `Video_has_Subtitulo` (
  `IdVideo_has_Subtitulo` int(11) NOT NULL,
  `Video_idVideo` int(11) NOT NULL,
  `Subtitulo_idSubtitulo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Calificacion`
--
ALTER TABLE `Calificacion`
  ADD PRIMARY KEY (`idCalificacion`),
  ADD KEY `fk_Calificacion_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `Carrito`
--
ALTER TABLE `Carrito`
  ADD PRIMARY KEY (`idCarrito`),
  ADD KEY `fk_Carrito_Usuario1_idx` (`Usuario_idUsuario`);

--
-- Indices de la tabla `Carrito_has_InfoVideo`
--
ALTER TABLE `Carrito_has_InfoVideo`
  ADD PRIMARY KEY (`IdCarrito_has_InfoVideo`),
  ADD KEY `fk_Carrito_has_InfoVideo_InfoVideo1_idx` (`InfoVideo_idInfoVideo`),
  ADD KEY `fk_Carrito_has_InfoVideo_Carrito1_idx` (`Carrito_idCarrito`);

--
-- Indices de la tabla `Clasificacion`
--
ALTER TABLE `Clasificacion`
  ADD PRIMARY KEY (`idClasificacion`);

--
-- Indices de la tabla `Genero`
--
ALTER TABLE `Genero`
  ADD PRIMARY KEY (`idGenero`);

--
-- Indices de la tabla `Genero_has_InfoVideo`
--
ALTER TABLE `Genero_has_InfoVideo`
  ADD PRIMARY KEY (`IdGenero_has_InfoVideo`),
  ADD KEY `fk_Genero_has_InfoVideo_InfoVideo1_idx` (`InfoVideo_idInfoVideo`),
  ADD KEY `fk_Genero_has_InfoVideo_Genero1_idx` (`Genero_idGenero`);

--
-- Indices de la tabla `InfoVideo`
--
ALTER TABLE `InfoVideo`
  ADD PRIMARY KEY (`idInfoVideo`),
  ADD KEY `fk_Infovideo_Clasificacion_idx` (`Clasificacion_idClasificacion`),
  ADD KEY `fk_InfoVideo_TipoVideo1_idx` (`TipoVideo_idTipoVideo`);

--
-- Indices de la tabla `Lenguaje`
--
ALTER TABLE `Lenguaje`
  ADD PRIMARY KEY (`idLenguaje`);

--
-- Indices de la tabla `Persona`
--
ALTER TABLE `Persona`
  ADD PRIMARY KEY (`idPersona`),
  ADD KEY `fk_Persona_Reparto1_idx` (`Reparto_idReparto`),
  ADD KEY `fk_Persona_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `Reparto`
--
ALTER TABLE `Reparto`
  ADD PRIMARY KEY (`idReparto`);

--
-- Indices de la tabla `RolUsuario`
--
ALTER TABLE `RolUsuario`
  ADD PRIMARY KEY (`idRolUsuario`);

--
-- Indices de la tabla `Solicitud`
--
ALTER TABLE `Solicitud`
  ADD PRIMARY KEY (`idSolicitud`),
  ADD KEY `fk_Solicitud_Usuario1_idx` (`Usuario_idUsuario`);

--
-- Indices de la tabla `Subtitulo`
--
ALTER TABLE `Subtitulo`
  ADD PRIMARY KEY (`idSubtitulo`);

--
-- Indices de la tabla `TipoVideo`
--
ALTER TABLE `TipoVideo`
  ADD PRIMARY KEY (`idTipoVideo`);

--
-- Indices de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `fk_Usuario_RolUsuario1_idx` (`RolUsuario_idRolUsuario`);

--
-- Indices de la tabla `Video`
--
ALTER TABLE `Video`
  ADD PRIMARY KEY (`idVideo`),
  ADD KEY `fk_Video_InfoVideo1_idx` (`InfoVideo_idInfoVideo`);

--
-- Indices de la tabla `Video_has_Lenguaje`
--
ALTER TABLE `Video_has_Lenguaje`
  ADD PRIMARY KEY (`IdVideo_has_Lenguaje`),
  ADD KEY `fk_Video_has_Lenguaje_Lenguaje1_idx` (`Lenguaje_idLenguaje`),
  ADD KEY `fk_Video_has_Lenguaje_Video1_idx` (`Video_idVideo`);

--
-- Indices de la tabla `Video_has_Subtitulo`
--
ALTER TABLE `Video_has_Subtitulo`
  ADD PRIMARY KEY (`IdVideo_has_Subtitulo`),
  ADD KEY `fk_Video_has_Subtitulo_Subtitulo1_idx` (`Subtitulo_idSubtitulo`),
  ADD KEY `fk_Video_has_Subtitulo_Video1_idx` (`Video_idVideo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Calificacion`
--
ALTER TABLE `Calificacion`
  MODIFY `idCalificacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Carrito`
--
ALTER TABLE `Carrito`
  MODIFY `idCarrito` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Carrito_has_InfoVideo`
--
ALTER TABLE `Carrito_has_InfoVideo`
  MODIFY `IdCarrito_has_InfoVideo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Clasificacion`
--
ALTER TABLE `Clasificacion`
  MODIFY `idClasificacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Genero`
--
ALTER TABLE `Genero`
  MODIFY `idGenero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `Genero_has_InfoVideo`
--
ALTER TABLE `Genero_has_InfoVideo`
  MODIFY `IdGenero_has_InfoVideo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `InfoVideo`
--
ALTER TABLE `InfoVideo`
  MODIFY `idInfoVideo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Lenguaje`
--
ALTER TABLE `Lenguaje`
  MODIFY `idLenguaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Persona`
--
ALTER TABLE `Persona`
  MODIFY `idPersona` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Reparto`
--
ALTER TABLE `Reparto`
  MODIFY `idReparto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `RolUsuario`
--
ALTER TABLE `RolUsuario`
  MODIFY `idRolUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `Solicitud`
--
ALTER TABLE `Solicitud`
  MODIFY `idSolicitud` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Subtitulo`
--
ALTER TABLE `Subtitulo`
  MODIFY `idSubtitulo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `TipoVideo`
--
ALTER TABLE `TipoVideo`
  MODIFY `idTipoVideo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Video`
--
ALTER TABLE `Video`
  MODIFY `idVideo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Video_has_Lenguaje`
--
ALTER TABLE `Video_has_Lenguaje`
  MODIFY `IdVideo_has_Lenguaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Video_has_Subtitulo`
--
ALTER TABLE `Video_has_Subtitulo`
  MODIFY `IdVideo_has_Subtitulo` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Calificacion`
--
ALTER TABLE `Calificacion`
  ADD CONSTRAINT `fk_Calificacion_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `InfoVideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Carrito`
--
ALTER TABLE `Carrito`
  ADD CONSTRAINT `fk_Carrito_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Carrito_has_InfoVideo`
--
ALTER TABLE `Carrito_has_InfoVideo`
  ADD CONSTRAINT `fk_Carrito_has_InfoVideo_Carrito1` FOREIGN KEY (`Carrito_idCarrito`) REFERENCES `Carrito` (`idCarrito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Carrito_has_InfoVideo_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `InfoVideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Genero_has_InfoVideo`
--
ALTER TABLE `Genero_has_InfoVideo`
  ADD CONSTRAINT `fk_Genero_has_InfoVideo_Genero1` FOREIGN KEY (`Genero_idGenero`) REFERENCES `Genero` (`idGenero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Genero_has_InfoVideo_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `InfoVideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `InfoVideo`
--
ALTER TABLE `InfoVideo`
  ADD CONSTRAINT `fk_InfoVideo_TipoVideo1` FOREIGN KEY (`TipoVideo_idTipoVideo`) REFERENCES `TipoVideo` (`idTipoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Infovideo_Clasificacion` FOREIGN KEY (`Clasificacion_idClasificacion`) REFERENCES `Clasificacion` (`idClasificacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Persona`
--
ALTER TABLE `Persona`
  ADD CONSTRAINT `fk_Persona_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `InfoVideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Persona_Reparto1` FOREIGN KEY (`Reparto_idReparto`) REFERENCES `Reparto` (`idReparto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Solicitud`
--
ALTER TABLE `Solicitud`
  ADD CONSTRAINT `fk_Solicitud_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `Usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD CONSTRAINT `fk_Usuario_RolUsuario1` FOREIGN KEY (`RolUsuario_idRolUsuario`) REFERENCES `RolUsuario` (`idRolUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Video`
--
ALTER TABLE `Video`
  ADD CONSTRAINT `fk_Video_InfoVideo1` FOREIGN KEY (`InfoVideo_idInfoVideo`) REFERENCES `InfoVideo` (`idInfoVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Video_has_Lenguaje`
--
ALTER TABLE `Video_has_Lenguaje`
  ADD CONSTRAINT `fk_Video_has_Lenguaje_Lenguaje1` FOREIGN KEY (`Lenguaje_idLenguaje`) REFERENCES `Lenguaje` (`idLenguaje`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Video_has_Lenguaje_Video1` FOREIGN KEY (`Video_idVideo`) REFERENCES `Video` (`idVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `Video_has_Subtitulo`
--
ALTER TABLE `Video_has_Subtitulo`
  ADD CONSTRAINT `fk_Video_has_Subtitulo_Subtitulo1` FOREIGN KEY (`Subtitulo_idSubtitulo`) REFERENCES `Subtitulo` (`idSubtitulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Video_has_Subtitulo_Video1` FOREIGN KEY (`Video_idVideo`) REFERENCES `Video` (`idVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
