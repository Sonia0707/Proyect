<?php
//Conexion a la Base de datos del proyecto:

	$hostname = "localhost";
	$database = "proyecto1";
	$username = "proyecto1";
	$password = "dam2";

       // Crear conexion:
		$conexion = mysqli_connect($hostname, $username, $password, $database);
		// Chekear conexion:
		if (!$conexion) {
			echo "El sitio web no está en servicio inténtelo más tarde";
		}
	
?>