<?php

//Incluimos la conexion:

	include 'conexion.php';

//Recibimos varibles por el metodo que queramos $_REQUEST==> a POST o GET
	$usuario =$_REQUEST['usuario'];
	$pass =$_REQUEST['pass'];
	
//Booleano para comprobar si existe el usuario:
	$sw = false;

//Convertir la password en clave cifrada a traves de un hash:
	$hash = sha1($pass);

//Sentencia preparada para una buena practica de seguridad mejor utilizar las interrogaciones:
	$sentencia=$conexion->prepare("SELECT * FROM usuarios WHERE nombre=? OR password=?");

//Le pasamos los parametros recibidos por el Metodo $_REQUEST la password cifrada con hash:
//La cadena 'ss' indica que son dos parametros de tipo String:
	$sentencia->bind_param('ss',$usuario,$hash);

//Ejecutamos la sentecia:
	$sentencia->execute();

//Obtenemos todos los resultados de la sentecia:
	$resultado = $sentencia->get_result();


//Mediante una condicional validamos si se encontro el registro con los datos asignados
//y se los asignamos al array fila  
	if ($fila = $resultado->fetch_assoc()) {
		
		//Existe: (Pero tenemos las siguientes restrinciones):
		$sw = true;
		
		// 1º El usuario existe pero la clave no coincide:
		if($fila['nombre']==$usuario && $fila['password'] != $hash){
			echo "<return>-2</return>";
			
		}
		// 2º El usuario y contraseña son correctos:
		if($fila['nombre']==$usuario && $fila['password'] == $hash){
			
				// 3º Recorremos los registros y miramos si idRoles es 1 o 2 = Para saber si es administrador o usuario:
			if($fila['idRoles'] == 1){
				//ADMINISTRADOR//
				echo "<return>1</return>";
				
			}else if($fila['idRoles'] == 2){
				//USUARIO//
				echo "<return>2</return>";
			}		
		}	
	}
	if(!$sw){
		// 4º El usuario no existe
		echo "<return>-1</return>";
	}
	
		//Por ultimo cerramos la sentecia y la conexion a la base:
	$sentencia->close();
	$conexion->close();
		
?>
