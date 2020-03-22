<?php
	include 'conexion.php';
	
	$email =$_REQUEST["email"];
	$password =$_REQUEST["password"];
	$password2 =$_REQUEST["password"];
	
	$hash= sha1($password);
	$hash2= sha1($password2);
		
	//Booleano para comprobar si existe el usuario:
	$sw = false;

//Consulta por nombre para identificar si ya existe en la base de datos:
$sql = "SELECT email FROM usuarios WHERE email = '".$email."'";

//Obtenemos todos los resultados de la sentecia:
	$resultado= $conexion->query($sql);
	
//Mediante una condicional validamos si se encontro el registro con los datos asignados
//y se los asignamos al array fila  
	if ($fila = $resultado->fetch_assoc()) {
		
		$sw = true;
		
		// 1º El email existe :
		if($fila['email']==$email){
		
		//Comprobar si las contraseñas coinciden:
			if($hash == $hash2){
						
				$sql = "UPDATE usuarios SET password='".$hash."' WHERE email='".$email."'";
						
				if (mysqli_query($conexion, $sql)) {
						echo" Cambio realizado. ";
				} else {
					echo "Error updating record: " . mysqli_error($conn);
				}
			}else{
				echo"Las contraseñas no coinciden";
			}
			 
		}	
	}
	if(!$sw){
		// 4º El email no existe:
		echo "No existe email registrado.";
	}


?>
     
