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
import android.widget.LinearLayout;
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
    Button btn_registrar, btn_b1, btn_b2, btn_b3;
    ImageButton btn_atras_r;
    TextView textoError;
    Spinner spinner_documento, spinner_pais;
    LinearLayout paso1, paso2, paso3;

    RequestQueue requestQueue;

    private static final String url_regUser = "http://192.168.222.38/mi_plataforma/insertar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // cargar la bilbioteca volley----------------------------------------------------
        requestQueue = Volley.newRequestQueue(this);

        initUI(); //iniciar el metodo que carga los botones-------------------------------

        //cargar los spinner de la vista------------------------------------------------------
        spinner_pais = (Spinner) findViewById(R.id.spinner_document);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.pais, android.R.layout.simple_spinner_item);
        spinner_pais.setAdapter(adapter1);
        spinner_documento = (Spinner) findViewById(R.id.spinner_documento);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.tipo_documento, android.R.layout.simple_spinner_item);
        spinner_documento.setAdapter(adapter2);

        //cargar el click de los botones-------------------------------------------------------
        btn_registrar.setOnClickListener(this);
        btn_atras_r.setOnClickListener(this);
        btn_b1.setOnClickListener(this);
        btn_b2.setOnClickListener(this);
        btn_b3.setOnClickListener(this);

    }
    //metodo que carga los botones\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private void initUI(){
        id_doc = findViewById(R.id.id_doc);
        nombres = findViewById(R.id.nombres);
        apellidos = findViewById(R.id.apellidos);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        textoError = findViewById(R.id.textView3);

        btn_registrar = findViewById(R.id.btn_registrar);
        btn_atras_r = findViewById(R.id.btn_atras_r);
        btn_b1 = findViewById(R.id.btn_b1);
        btn_b2 = findViewById(R.id.btn_b2);
        btn_b3 = findViewById(R.id.btn_b3);

        paso1 = findViewById(R.id.paso1);
        paso2 = findViewById(R.id.paso2);
        paso3 = findViewById(R.id.paso3);
    } /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) { //metodo OnClick para los botones_____________________________
        int id = v.getId();

        if (id == R.id.btn_registrar){
            textoError.setTextColor(Color.RED);
            validarDatos();

        }else if (id == R.id.btn_atras_r){
            startActivity(new Intent(Registro.this, Inicio.class));

        }else if (id == R.id.btn_b1){
            //Toast.makeText(this,"Presionado boton 1", Toast.LENGTH_SHORT).show();
            /*if (!btn_b1.isEnabled()){
                btn_b1.setEnabled(true);
                btn_b2.setEnabled(false);
                btn_b3.setEnabled(false);*/
                paso1.setVisibility(View.VISIBLE);
                paso2.setVisibility(View.GONE);
                paso3.setVisibility(View.GONE);
            //}
        }else if (id == R.id.btn_b2){
            //Toast.makeText(this,"Presionado boton 2", Toast.LENGTH_SHORT).show();
            /*if (!btn_b2.isEnabled()){
                btn_b1.setEnabled(false);
                btn_b2.setEnabled(true);
                btn_b3.setEnabled(false);*/
                paso1.setVisibility(View.GONE);
                paso2.setVisibility(View.VISIBLE);
                paso3.setVisibility(View.GONE);
            //}
        }else if (id == R.id.btn_b3){
            //Toast.makeText(this,"Presionado boton 3", Toast.LENGTH_SHORT).show();
            /*if (!btn_b3.isEnabled()){
                btn_b1.setEnabled(false);
                btn_b2.setEnabled(false);
                btn_b3.setEnabled(true);*/
                paso1.setVisibility(View.GONE);
                paso2.setVisibility(View.GONE);
                paso3.setVisibility(View.VISIBLE);
            //}
        }

    } ////////////////////////////////////////////////////////////////////////////////////////

    /* a continuacion se hace la conexion con XAMPP
       primero se validan los datos que se van a enviar de la vista
       luego utilizando el metodo POST se envian al XAMPP */
    public void validarDatos(){
        String doc = id_doc.getText().toString().trim();
        String nom = nombres.getText().toString().trim();
        String ape = apellidos.getText().toString().trim();
        String tel = telefono.getText().toString().trim();
        String ema = email.getText().toString().trim();
        String con = contrasena.getText().toString().trim();

        //filtros te textos--------------------------------------------------------------------
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
                if (docNum == conNum){   //si el documento y la contraseña son iguales se filtra
                    textoError.setText("El Usuario y la contraseña son iguales");
                }else {
                    textoError.setTextColor(Color.GREEN);
                    textoError.setText("Datos correctos");
                    validarUsuario(doc);
                }
            }

        } //Hasta aqui se hace el filtro de los datos de registro---------------------------------

    }
    private void validarUsuario(String doc){ //validar si el usuario ya existe____________________________
        String url_verifUser = "http://192.168.53.195/mi_plataforma/usuarios_consulta.php?id_doc="+ doc;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url_verifUser,
                null,
                new Response.Listener<JSONObject>() {

                    /*Validacion
                    * una vez se confirme que el usuario ya existe
                    * se termina el proceso y se deja un mensajer de alerta
                    * de lo contrario al dar error
                    * se continua con el proceso de registro.
                    * */

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
    // una vez se filtraron los datos se procede con el registro::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
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
                        Toast.makeText(Registro.this, "Error de Registro", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // por medio de un map se envian los datos al XAMPP
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