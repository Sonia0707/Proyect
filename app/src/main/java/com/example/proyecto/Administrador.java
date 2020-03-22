package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Administrador extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnPersonal;
    ImageButton btnCompartida;
    ImageButton btnGrupal;
    Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnPersonal= (ImageButton)findViewById(R.id.btnPersonal);
        btnCompartida= (ImageButton)findViewById(R.id.btnCompartida);
        btnGrupal= (ImageButton)findViewById(R.id.btnGrupal);
        btnCerrar= (Button)findViewById(R.id.btnCerrar);

        btnPersonal.setOnClickListener(this);
        btnCompartida.setOnClickListener(this);
        btnGrupal.setOnClickListener(this);
        btnCerrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPersonal:
                //startActivity(new Intent(this, AGpersonal.class));

                break;
            case R.id.btnCompartida:
                //startActivity(new Intent(this, AGcompartida.class));
                break;
            case R.id.btnGrupal:
                //startActivity(new Intent(this, AGcompartida.class));
                break;
            case R.id.btnCerrar:

                SharedPreferences preferences = getSharedPreferences("LoginAdmin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent = new Intent(getApplicationContext(),Login_Main.class);
                startActivity(intent);
                finish();
                break;

        }

    }
}
