package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class OlvidoPassword extends AppCompatActivity implements View.OnClickListener {

    EditText correo;
    Button btnEnviar;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_password);

        correo=(EditText)findViewById(R.id.email);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        email= correo.getText().toString();

        if (!email.isEmpty()){
            //metodo enviar
            enviarCorreo("http://192.168.1.131/email/index.php");
        }else {
            Toast.makeText(this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
        }
    }
    public void enviarCorreo(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    if (response.equals("<return>Enviado</return>")) {
                        Toast.makeText(OlvidoPassword.this, "Mensaje enviado compruebe su email.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login_Main.class);
                        startActivity(intent);
                        finish();

                    } else if (response.equals("<return>Error</return>")) {
                        Toast.makeText(OlvidoPassword.this, "Error en el envio.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OlvidoPassword.this,"El sitio web no esta en servicio intentelo mas tarde.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("email", email);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
