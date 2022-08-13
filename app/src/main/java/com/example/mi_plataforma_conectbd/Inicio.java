package com.example.mi_plataforma_conectbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    Button btn_ingresar, btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_ingresar.setOnClickListener(this);
        btn_registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_ingresar){
            startActivity(new Intent(Inicio.this, Ingreso.class));
        }else if (id == R.id.btn_registrar){
            startActivity(new Intent(Inicio.this, Registro.class));
        }

    }
}