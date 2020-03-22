<?php

//Registro de Android para ROL=> USUARIO NORMAL:

//Incluimos la conexion:

	include 'conexion.php';

//Recibimos varibles por el metodo que queramos $_REQUEST==> a POST o GET
	
	$nombre=$_REQUEST["nombre"];
	$pass= $_REQUEST["pass"];
	$pass2= $_REQUEST["pass2"];
	$email= $_REQUEST["email"];
	
	
	//Booleano para identificar si el usuario esta o no:
	$sw = false;

//Ejemplo para probar en el navegador con el POST:
//$usuario="Sonia";
//$pass="1234";
//$email= $_REQUEST["email"];
//$rol = $_REQUEST['rol'];

//Cifrar contraseña para poder buscarla en la base de datos:
 $hash= sha1($pass);
 $hash2= sha1($pass2);
 
//Consulta por nombre para identificar si ya existe en la base de datos:
$sql = "SELECT nombre, email FROM usuarios WHERE nombre = '".$nombre."' OR email= '".$email."'"; 
 
//Recorremos 
$resultado= $conexion->query($sql);

if ($usuarios = $resultado->fetch_assoc()){
	$sw = true;
	//Comprobamos usuario si existe el usuario 
	//no lo registra y lanzará un TOAST en Android diciendo que ya existe:
	if($usuarios['nombre'] == $nombre || $usuarios['email'] == $email){
			echo"<return>existe</return>";
			
	}
}
if($sw == false){
	//Si no existe el usuario:
	echo"<return>usunuevo</return>";
	
	//Fecha por defecto el día,mes y año en el que esta:
	$date = date("d") . "/" . date("m") . "/" . date("Y");
	
	//Rol por defecto en el caso de usuario normal:
	$rol =2;
	if($hash == $hash2 ){
		//Insertará el nuevo usuario:
	$sql = "INSERT INTO usuarios (nombre, password, email, fecha_created, idRoles) VALUES ('$nombre', '$hash', '$email','$date','$rol')";
	}else{
		//No coinciden las contraseñas:
		echo"<return>false</return>";
	}
	
	//Comprueba la conxion e inserta si la conesión es correcta:
	if (mysqli_query($conexion, $sql)) {
     // echo "New record created successfully";
} else {
      echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
}
}

mysqli_close($conexion);
?>