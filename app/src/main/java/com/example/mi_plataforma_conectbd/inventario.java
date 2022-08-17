package com.example.mi_plataforma_conectbd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class inventario extends AppCompatActivity {

    Spinner spiModulos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        spiModulos = (Spinner) findViewById(R.id.spiModulos);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.modulos, android.R.layout.simple_spinner_item);
        spiModulos.setAdapter(adapter);

    }
}