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
    public String signo;

    public TextView numeroActual;
    private FasesCalculo faseActual;
    private List<Button> botonesSignos;
    private List<Button> botonesNumeros;

    private Button botonPunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numero1=0;
        numero2=0;
        resultado=0;
        signo = "";

        numeroActual = findViewById(R.id.txtNumeros);
        botonPunto = findViewById(R.id.btnPunto);
        faseActual = FasesCalculo.DESDE0;
        botonesSignos = Arrays.asList(findViewById(R.id.btnDividir), findViewById(R.id.btnMenos), findViewById(R.id.btnMultiplicar), findViewById(R.id.btnSumar), findViewById(R.id.btnPorcentaje));
        botonesNumeros = Arrays.asList(findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9));

    }
    public void resetVariables(View view) {

        numero1=0;
        numero2=0;
        resultado=0;
        signo = "";
        numeroActual.setText("");
        faseActual = FasesCalculo.DESDE0;
        deshabilitarBotones(false);

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

        if(textoBoton.getId()==botonPunto.getId()) {

            if(numeroActual.length()==0) numeroActual.setText("0.");

            else if(!numeroActual.getText().toString().contains(".")) numeroActual.setText(numeroActual.getText() + ".");

        }

        if(esNumero(view) && faseActual==FasesCalculo.DESDE0) {
            numeroActual.setText(numeroActual.getText() + "" + textoBoton.getText());
            numero1 = Double.parseDouble((String) numeroActual.getText());
        }

        if(esSigno(view) && numeroActual.getText()!="0") {

            deshabilitarBotones(true);
            signo = (String) textoBoton.getText();
            faseActual = FasesCalculo.SEGUNDONUMERO;
            botonPunto.setEnabled(false);

        }

        if(esNumero(view) && numero2!=0 && faseActual==FasesCalculo.SEGUNDONUMERO) {
            botonPunto.setEnabled(true);
            numeroActual.setText(numeroActual.getText() + "" + textoBoton.getText());
            numero2 = Double.parseDouble((String) numeroActual.getText());
        }

        else if(esNumero(view) && numero2==0 && faseActual==FasesCalculo.SEGUNDONUMERO) {
            botonPunto.setEnabled(true);
            if(numeroActual.getText().toString().contains("0.")) {
                numeroActual.setText(numeroActual.getText() + "" + textoBoton.getText());
                numero2 = Double.parseDouble((String) numeroActual.getText());
            }
            else {
                numeroActual.setText(textoBoton.getText());
                numero2 = Double.parseDouble((String) numeroActual.getText());
            }

        }



        if(view.getId()==R.id.btnIgual && faseActual==FasesCalculo.SEGUNDONUMERO) {

            numero1 = operacionSigno(signo, numero1, numero2);
            double parteDecimal = numero1 % 1;
            if(numero1==0) numeroActual.setText("");
            else if(parteDecimal==0) numeroActual.setText(String.format("%.0f", numero1));
            else numeroActual.setText(numero1+"");
            numero2 = 0;
            faseActual = FasesCalculo.DESDE0;
            deshabilitarBotones(false);

        }


    }

    private double operacionSigno(String signo, double numero1, double numero2) {

        double resultado = 0;

        switch (signo) {

            case "+": resultado = numero1 + numero2;
                break;

            case "-": resultado = numero1 - numero2;
                break;

            case "X": resultado = numero1 * numero2;
                break;

            case "/": resultado = numero1 / numero2;
                break;

            case "%": resultado = numero1 % numero2;
                break;

        }

        return resultado;

    }

    public void borrarNumero(View view) {

        if(view.getId()==R.id.btnBorrar && numeroActual.getText().length()!=0) {
            String borradoNumero = (String) numeroActual.getText();
            borradoNumero = borradoNumero.substring(0, borradoNumero.length()-1);
            numeroActual.setText(borradoNumero);
            if(faseActual==FasesCalculo.DESDE0) {

                if(borradoNumero.length()==0) numero1=0;

                else numero1 = Double.parseDouble(borradoNumero);

            }

            else {
                if(borradoNumero.length()==0) numero2=0;

                else numero2 = Double.parseDouble(borradoNumero);
            }

        }

    }






}