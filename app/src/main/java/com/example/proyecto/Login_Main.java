package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login_Main extends AppCompatActivity implements View.OnClickListener {

    //Creacion de las variables:
    Button btnLogin;
    EditText idUsername, idPassword;
    TextView linkRegistro, linkContraseña;
    String usuario, pass;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Referencia a la vista:
        idUsername=(EditText)findViewById(R.id.idUsername);
        idPassword=(EditText)findViewById(R.id.idPassword);

        linkRegistro=(TextView) findViewById(R.id.linkRegistro);
        linkContraseña=(TextView) findViewById(R.id.linkContraseña);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        //Escuchar el click dependiento cual pulsemos, para ello implementamos el oyente View.OnClickListener
        btnLogin.setOnClickListener(this);
        linkRegistro.setOnClickListener(this);
        linkContraseña.setOnClickListener(this);

        //Recuperamos el nombre y la contrseña del usuario para que le aparezca en el login
        //ya una vez se haya logueado y le sea mas facil:
            recuperarUsuAndPassUsuario();


    }

    //Metodo de switch Obtiene el Id de la vista que notificó:  btnLogin, linkRegistro o linkContraseña.
    public void onClick(View v){
        switch (v.getId()){

            //Al pulsar el boton de login comprueba si los campos estan vacios
            // y si no es así manda la URL al método validar usurios de la clase Volley.
            case R.id.btnLogin:
                usuario=idUsername.getText().toString();
                pass=idPassword.getText().toString();
                if (!usuario.isEmpty() && !pass.isEmpty()){
                    validarUsuario("http://192.168.1.131/Proyecto/Android/Login/loginAndroid.php");

                }else {
                    Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.linkRegistro:
                //Nos nevia a la activity de Registro:
                startActivity(new Intent(this, Registro.class));
                break;
                //Nos envia a la activity de Oolvide Contraseña:
            case R.id.linkContraseña:
                startActivity(new Intent(this, OlvidoPassword.class));
                break;

        }
    }
    //1ºCreamos un método con el nombre validar usuario agregándole un parámetro string destinado
    // a la ruta de nuestro servicio web:
    public void validarUsuario(String URL){

        //2º En la siguiente línea hacemos uso de un objeto tipo StringRequest y luego dentro del constructor de la
        //clase colocamos como parámetros el tipo de método de envío (POST) la URL y seguidamente
        //agregamos la clase response listener:

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            // 3º La cual nos generará automaticamente el listener onResponse que éste reaccionara en
            //caso de que la petición se procese:
            @Override
            public void onResponse(String response) {



                //Validamos que el response no esté vacío esto dará a entender que el usuario y password
                // ingresados existen y que el servicio php nos está devolviendo la fila encontrada
                if (!response.isEmpty()){

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String respuesta= jsonObject.getString("respuesta");
                        int idUsuario= jsonObject.getInt("idUsuario");



                      //  Toast.makeText(Login_Main.this, idUsuario, Toast.LENGTH_LONG).show();

                    //Hacemos una serie de restricciones de logueo:

                    //A) El usuario existe por lo tanto si su rol es 1 es administrador:
                    if (respuesta.equals("1")){

                        //Guardamos datos para manejo de ellos en el futuro:
                        guardarUsuarioAndPasswordAdmin(idUsuario);


                        //Lanzamos a la activity de administrador:
                        Intent intent = new Intent(getApplicationContext(),Administrador.class);
                        startActivity(intent);
                        finish();

                    //B) El usuario existe por lo tanto si su rol es 2 es usuario normal:
                    }else if (respuesta.equals("2")){
                        //Guardamos datos para manejo de ellos en el futuro:
                        guardarUsuarioAndPasswordUsuario(idUsuario);

                        //Lanzamos a la activity de usuario:
                        Intent intent = new Intent(getApplicationContext(), Usuario.class);
                        startActivity(intent);
                        finish();

                    //C) El nombre del usuario es correcto pero la contraseña no: -2
                    }else if (respuesta.equals("noClave")){
                        Toast.makeText(Login_Main.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();

                        //D) El usuraio no existe en la base de datos:
                    }else if (respuesta.equals("noUsuario")){
                        Toast.makeText(Login_Main.this, "El usuario no existe.", Toast.LENGTH_SHORT).show();
                    }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            //4º Agregaremos la clase Response.ErrorListener() este nos generará el listener de un error response
            //el cual reaccionará en caso de no procesarse la petición al servidor:
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Comprobacion de se la conexion es correcta entre Android y el Servidor:
                Toast.makeText(Login_Main.this,"El sitio web no esta en servicio intentelo mas tarde.", Toast.LENGTH_LONG).show();
            }

        }){//5º Agregamos el método getParams() dentro de éste colocaremos los parámetros que nuestro servicio solicita
            //para devolvernos una respuesta:
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //En el primer parámetro se colocará el nombre de la variable tipo POST que declaramos en nuestro servicio PHP y en
                //el segundo agregaremos el dato que deseamos enviar, en este caso nuestros EditText:
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("pass",pass);

                //Despues retornamos toda la colección de datos mediante la instancia creada:
                return parametros;
            }
        };

        //6º Por ulltimo hacemos uso de la clase RequestQueue creamos una instancia de ésta y en la siguiente línea agregaremos la
        //instancia de nuestro objeto stringRequest ésta nos ayudará a procesar todas las peticiones hechas:
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //Guardar usurio para una vez logueados si no cerramos la sesión pero si la aplicación que no nos pregunte de nuevo por el loguin:
    private  void guardarUsuarioAndPasswordUsuario(int idUsuario){
        SharedPreferences preferences = getSharedPreferences("LoginUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idUsuario",idUsuario);
        editor.putString("usuario",usuario);
        editor.putString("password",pass);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    //Guardemos datos de admistrador para futuras cosas:
    private  void guardarUsuarioAndPasswordAdmin(int idUsuario){
        SharedPreferences preferences = getSharedPreferences("LoginAdmin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idUsuario",idUsuario);
        editor.putString("usuario",usuario);
        editor.putString("password",pass);
        editor.putBoolean("sesion2",true);
        editor.commit();
    }

    //Aparecerá en el Login => nombre y contraseña que hayamos metido en el ultimo momento:
    private void recuperarUsuAndPassUsuario(){
        SharedPreferences preferences = getSharedPreferences("LoginUsuario", Context.MODE_PRIVATE);
        idUsername.setText(preferences.getString("usuario","Usuario1"));
        idPassword.setText(preferences.getString("password","0000"));
    }


}
