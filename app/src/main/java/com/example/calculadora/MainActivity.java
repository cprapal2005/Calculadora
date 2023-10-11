package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public double numero1=0, numero2=0, resultado=0;
    public String operacion="", numeroActual="";

    public void resetVariables(View view) {

        numero1=0;
        numero2=0;
        resultado=0;
        operacion="";
        numeroActual="";

    }

    public void borrarNumero(View view) {

        if(view.getId()==R.id.btnBorrar && numeroActual.length()>0) numeroActual.substring(0, numeroActual.length()-1);

    }






}