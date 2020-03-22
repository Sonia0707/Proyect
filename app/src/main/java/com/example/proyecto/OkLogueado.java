package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class OkLogueado extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_logueado);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("LoginUsuario", Context.MODE_PRIVATE);
                boolean sesion = preferences.getBoolean("sesion",false);

                if (sesion){
                    Intent intent = new Intent(getApplicationContext(), Usuario.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(),Login_Main.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);


    }
}
