package com.example.mi_plataforma_conectbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingreso extends AppCompatActivity implements View.OnClickListener{

    EditText user, pass;
    Button btn_ingresar;
    ImageButton btn_atras_i;
    Spinner spinner_document;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);

        requestQueue = Volley.newRequestQueue(this);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_atras_i = findViewById(R.id.btn_atras_i);

        btn_ingresar.setOnClickListener(this);
        btn_atras_i.setOnClickListener(this);

        spinner_document = (Spinner) findViewById(R.id.spinner_document);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.tipo_documento, android.R.layout.simple_spinner_item);
        spinner_document.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_atras_i){
            startActivity(new Intent(Ingreso.this, Inicio.class));
        }else if (id == R.id.btn_ingresar){
            //verificar(user.getText().toString().trim(), pass.getText().toString().trim());
            //pass.setText(user.getText().toString().trim());
            startActivity(new Intent(Ingreso.this, inventario.class));
        }
    }
    private void verificar(String _user, String _pass){
        String url_verifUser = "http://192.168.53.195/mi_plataforma/usuarios_consulta.php?id_doc="+ _user;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url_verifUser,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Ingreso.this, "Contraseña correcta", Toast.LENGTH_SHORT).show();
                        String nombres, apellidos, telefono, email, contrasena;
                        try {
                            contrasena = response.getString("contrasena");

                            if (_pass == contrasena){
                                Toast.makeText(Ingreso.this, "Contraseña correcta", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Ingreso.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Ingreso.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}