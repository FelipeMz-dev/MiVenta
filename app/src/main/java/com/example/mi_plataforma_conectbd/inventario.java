package com.example.mi_plataforma_conectbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class inventario extends AppCompatActivity implements View.OnClickListener {

    Spinner spiModulos;
    Button btnDate, btnHour;
    private int dia, mes, ano, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        btnDate = findViewById(R.id.btnDate);
        btnHour = findViewById(R.id.btnHour);

        btnDate.setOnClickListener(this);
        btnHour.setOnClickListener(this);

        //Spiner de MODULOS --- funcion desplegable y selecchion de item -------------------::::::::
        spiModulos = (Spinner) findViewById(R.id.spiModulos);
        ArrayAdapter<CharSequence> adapterModulos=ArrayAdapter.createFromResource(this, R.array.modulos, R.layout.spinner_item_modulos);
        spiModulos.setAdapter(adapterModulos);
        spiModulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String seleccion = adapterView.getItemAtPosition(i).toString().trim();
                //Toast.makeText(adapterView.getContext(),seleccion,Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        inventarioFragment inventarioFrag = new inventarioFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView, inventarioFrag);
                        fragmentTransaction.commit();
                        //Toast.makeText(adapterView.getContext(),"inventario funciona",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        comprasFragment comprasFrag = new comprasFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView, comprasFrag);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        ventasFragment ventasFrag = new ventasFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView, ventasFrag);
                        fragmentTransaction.commit();
                        //Toast.makeText(adapterView.getContext(),"ventas funciona",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        gastosFragment gastosFrag = new gastosFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView, gastosFrag);
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        aplicarFechaHora();
    }
    //Poner Fecha y hora actual-------------------------------------------------------
    public void aplicarFechaHora (){
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minuto = calendar.get(Calendar.MINUTE);
        btnDate.setText(dia + "/" + (mes +1) + "/" + ano);
        if (hora >= 12){
            btnHour.setText((hora -12) + ":" + minuto + " p.m.");
        }else{
            btnHour.setText(hora + ":" + minuto + " a.m.");
        }

    }
//Botones-----------------------------------------Botones-----------------------------Botones----------------
    @Override
    public void onClick(View v) {
        if (v == btnDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
                    btnDate.setText(dayOfMonth + "/" + (monthOfYear +1) + "/" + Year);
                }
            }, ano, mes, dia);
            datePickerDialog.show();
        }
        if (v == btnHour){
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    if (hour >= 12){
                        btnHour.setText((hour -12) + ":" + minute + " p.m.");
                    }else{
                        btnHour.setText(hour + ":" + minute + " a.m.");
                    }
                }
            }, hora, minuto,  false);
            timePickerDialog.show();
        }
    }
}