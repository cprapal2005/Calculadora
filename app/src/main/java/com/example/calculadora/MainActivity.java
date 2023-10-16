package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public double numero1, numero2, resultado;
    public String operacion, signo;

    public TextView numeroActual;
    private FasesCalculo faseActual;
    private List<Button> botonesSignos;
    private List<Button> botonesNumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numero1=0;
        numero2=0;
        resultado=0;
        operacion="";
        signo = "";

        numeroActual = findViewById(R.id.txtNumeros);

        faseActual = FasesCalculo.DESDE0;
        botonesSignos = Arrays.asList(findViewById(R.id.btnDividir), findViewById(R.id.btnMenos), findViewById(R.id.btnMultiplicar), findViewById(R.id.btnSumar), findViewById(R.id.btnPorcentaje));
        botonesNumeros = Arrays.asList(findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9));

    }
    private void resetVariables(View view) {

        numero1=0;
        numero2=0;
        resultado=0;
        operacion="";
        numeroActual.setText("0");

    }

    private void deshabilitarBotones(boolean flag) {

        for (int i = 0; i < this.botonesSignos.size(); i++) {

            if(flag == true) this.botonesSignos.get(i).setEnabled(false);

            else this.botonesSignos.get(i).setEnabled(true);

        }

    }

    private boolean esSigno(View view) {

        boolean flag = false;

        for (int i = 0; i < this.botonesSignos.size(); i++) {

            if(view.getId() == this.botonesSignos.get(i).getId()) flag = true;

        }

        return flag;

    }

    private boolean esNumero(View view) {

        boolean flag = false;

        for (int i = 0; i < this.botonesNumeros.size(); i++) {

            if(view.getId() == this.botonesNumeros .get(i).getId()) flag = true;

        }

        return flag;

    }

    public void iniciarCalculo(View view) {

        TextView textoBoton = (TextView) view;

        if(esNumero(view)==true && faseActual==FasesCalculo.DESDE0) {
            numeroActual.setText(numeroActual.getText() + "" + textoBoton.getText());
            numero1 = Double.parseDouble((String) numeroActual.getText());
        }

        if(esSigno(view)==true && numeroActual.getText()!="0") {

            deshabilitarBotones(true);
            signo = (String) textoBoton.getText();
            faseActual = FasesCalculo.SEGUNDONUMERO;

        }

        if(esNumero(view)==true && numero2!=0 && faseActual==FasesCalculo.SEGUNDONUMERO) {
            numeroActual.setText(numeroActual.getText() + "" + textoBoton.getText());
            numero2 = Double.parseDouble((String) numeroActual.getText());
        }

        else if(esNumero(view)==true && numero2==0 && faseActual==FasesCalculo.SEGUNDONUMERO) {
            numeroActual.setText(textoBoton.getText());
            numero2 = Double.parseDouble((String) numeroActual.getText());
        }

        if(view.getId()==R.id.btnIgual && faseActual==FasesCalculo.SEGUNDONUMERO) {

            numero1 = numero1+numero2;
            double parteDecimal = numero1 % 1;
            if(parteDecimal==0) numeroActual.setText(String.format("%.0f", numero1));
            else numeroActual.setText(numero1+"");
            numero2 = 0;
            faseActual = FasesCalculo.DESDE0;
            deshabilitarBotones(false);

        }


    }

    public void borrarNumero(View view) {

        if(view.getId()==R.id.btnBorrar && numeroActual.getText()!="0") {
            String borradoNumero = (String) numeroActual.getText();
            borradoNumero = borradoNumero.substring(0, borradoNumero.length()-1);
            numeroActual.setText(borradoNumero);
        }

        else numeroActual.setText("0");

    }






}