package Corellium.Ventana.Calculadora;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CalculadoraController {

    @FXML
    TextField entradaOperaciones;
    @FXML
    TextField entradaNumero;
    @FXML
    Button raiz;
    @FXML
    Button potencia;
    @FXML
    Button invertido;
    @FXML
    Button C;
    @FXML
    Button division;
    @FXML
    Button multiplicacion;
    @FXML
    Button resta;
    @FXML
    Button suma;
    @FXML
    Button igual;

    private boolean resultado = false;
    private boolean operadorUnico = false;

    public void initialize() {

    }

    @FXML
    void onAction(@NotNull ActionEvent event) {

        if(Operaciones.resultado){
            entradaNumero.clear();
            Operaciones.resultado = false;
        }

        Button button = (Button) event.getSource();
        String entry = button.getText();
        Operaciones.Entrada(entradaNumero, entry);
    }

    @FXML
    void operacion(ActionEvent event) {
        operadorUnico = true;
        String numero = entradaNumero.getText().replaceAll(",", "");
        String operador = "";
        if(event.getSource().equals(raiz)) {
            operador = "√";
            entradaOperaciones.appendText("√");
        } else if (event.getSource().equals(potencia)) {
            operador = "**";
        } else if(event.getSource().equals(invertido)) {
            operador ="/-";
        }

        entradaNumero.setText(Operaciones.OperacionUnica(numero,operador).toString());
    }


    @FXML
    void signo(@NotNull ActionEvent event) {
        String numero = entradaNumero.getText().replaceAll(",", "");
        String operador = "";

        Operaciones.listaOperaciones.add(numero);
        if (event.getSource().equals(division)) {
            operador = "/";
        } else if (event.getSource().equals(multiplicacion)) {
            operador = "*";
        } else if (event.getSource().equals(resta)) {
            operador = "-";
        } else if (event.getSource().equals(suma)) {
            operador = "+";
        }

        if(event.getSource().equals(igual)) {
            entradaNumero.setText(Operaciones.Operacion().toString());
            Operaciones.resultado = true;
            entradaOperaciones.clear();
            List<String> remover = Operaciones.listaOperaciones;
            Operaciones.listaOperaciones.removeAll(remover);
        } else {
            entradaNumero.clear();
            Operaciones.listaOperaciones.add(operador);
            if(!operadorUnico) {
                entradaOperaciones.appendText(" " + numero + " " + operador);
            } else {
                entradaOperaciones.appendText(" " + operador);
            }
        }
    }


    @FXML
    void cambiarSigno() {
        double entrada = Double.parseDouble(entradaNumero.getText().replaceAll(",", ""));
        entrada *= -1;
        if (entradaNumero.getText().contains(".")) {
            entradaNumero.setText(String.valueOf(entrada));
        } else {
            entradaNumero.setText(String.valueOf((int) entrada));
        }
    }

    @FXML
    void borrar() {
        if (entradaNumero.getText() != null) {
            entradaNumero.end();
            entradaNumero.deletePreviousChar();
            Operaciones.Entrada(entradaNumero, "DEL");
        }
    }
}
