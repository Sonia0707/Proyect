<?php
echo '<html>
	<body>

  <form action = "ContraseñaNueva.php" method= "post">
			<label>Emai:</label><br />
            <input type="text" name="email" maxlength="30" /><br />
            <label>Nueva contraseña:</label><br />
            <input type="text" name="password" maxlength="15" /><br />
            <label>Confirmar:</label><br />
            <input type="text" name="password2" maxlength="15" /><br />
            <input type="submit" name="enviar" value="Enviar" />
        </form>
		
	</body>
</html>';

?>