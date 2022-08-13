package com.example.mi_plataforma_conectbd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    EditText id_doc, nombres, apellidos, telefono, email, contrasena;
    Button btn_registrar;
    ImageButton btn_atras_r;
    TextView textoError;
    Spinner spinner_documento;

    RequestQueue requestQueue;

    private static final String url_regUser = "http://192.168.53.195/mi_plataforma/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        requestQueue = Volley.newRequestQueue(this);
        initUI();

        spinner_documento = (Spinner) findViewById(R.id.spinner_pais);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.tipo_documento, android.R.layout.simple_spinner_item);
        spinner_documento.setAdapter(adapter);

        btn_registrar.setOnClickListener(this);
        btn_atras_r.setOnClickListener(this);

    }
    private void initUI(){                      //llamar las etiqueteas de la vista
        /*id_doc = findViewById(R.id.id_doc);
        nombres = findViewById(R.id.nombres);
        apellidos = findViewById(R.id.apellidos);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        textoError = findViewById(R.id.textView3);*/

        btn_registrar = findViewById(R.id.btn_registrar);
        btn_atras_r = findViewById(R.id.btn_atras_r);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_registrar){
            textoError.setTextColor(Color.RED);
            validarDatos();

        }else if (id == R.id.btn_atras_r){
            startActivity(new Intent(Registro.this, Inicio.class));
        }

    }
    public void validarDatos(){
        String doc = id_doc.getText().toString().trim();
        String nom = nombres.getText().toString().trim();
        String ape = apellidos.getText().toString().trim();
        String tel = telefono.getText().toString().trim();
        String ema = email.getText().toString().trim();
        String con = contrasena.getText().toString().trim();

        //filtros te textos
        if (doc.length() < 8){
            if (doc.length() == 0){
                textoError.setText("el campo Documento está vacio");
            }else{
                textoError.setText("el Documento debe tener mas digitos");
            }
        }else if (nom.replaceAll(" ", "").length() == 0){
            textoError.setText("El campo Nombres está vacio");
        }else if (ape.replaceAll(" ", "").length() == 0){
            textoError.setText("El campo Apellidos está vacio");
        }else if (tel.replaceAll(" ", "").length() == 0){
            textoError.setText("El campo Telefono está vacio");
        }else if (tel.length() != 7 && tel.length() != 10){
            textoError.setText("El Telefono registrado no existe");
        }else if (Character.getNumericValue(tel.charAt(0)) != 3 && tel.length() == 10){
            textoError.setText("El Telefono registrado no existe");
        }else if (Character.getNumericValue(tel.charAt(0)) != 2 && tel.length() == 7){
            textoError.setText("El Telefono registrado no existe");
        }else if (ema.replaceAll(" ", "").length() == 0){
            textoError.setText("El campo Email está vacio");
        }else if (con.length() < 8){
            if (con.length() == 0) {
                textoError.setText("El campo Contraseña está vacio");
            }else {
                textoError.setText("La contraseña debe tener más digitos");
            }
        }else{
            if (con.matches("[+-]?\\d*(\\.\\d+)?") && con.length() <= 10){
                int docNum = Integer.parseInt(doc);
                int conNum = Integer.parseInt(con);
                if (docNum == conNum){
                    textoError.setText("El Usuario y la contraseña son iguales"); //si el documento y la contraseña son iguales se filtra
                }else {
                    textoError.setTextColor(Color.GREEN);
                    textoError.setText("Datos correctos");
                    validarUsuario(doc);
                }
            }

        }

    }
    private void validarUsuario(String doc){ //validar si el usuario ya existe
        String url_verifUser = "http://192.168.53.195/mi_plataforma/usuarios_consulta.php?id_doc="+ doc;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url_verifUser,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textoError.setText("El Usuario ya esta registrado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String nom = nombres.getText().toString().trim();
                        String ape = apellidos.getText().toString().trim();
                        String tel = telefono.getText().toString().trim();
                        String ema = email.getText().toString().trim();
                        String con = contrasena.getText().toString().trim();
                        registrarUsuario(doc, nom, ape, tel, ema, con);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    private void registrarUsuario(final String doc, final String nom, final String ape, final String tel, final String ema, final String con){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url_regUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registro.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id_doc", doc);
                params.put("nombres", nom);
                params.put("apellidos", ape);
                params.put("telefono", tel);
                params.put("email", ema);
                params.put("contrasena", con);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}