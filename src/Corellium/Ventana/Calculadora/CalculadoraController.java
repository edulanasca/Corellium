package Corellium.Ventana.Calculadora;

import Corellium.Ventana.Ventana;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CalculadoraController {

    @FXML
    BorderPane root;
    @FXML
    ToolBar barraSuperior;

    @FXML
    TextField entradaOperaciones;
    @FXML
    TextField entradaNumero;

    @FXML
    Button porcentaje;
    @FXML
    Button raiz;
    @FXML
    Button potencia;
    @FXML
    Button invertido;
    @FXML
    Button CE;
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

    private double posX;
    private double posY;

    private boolean operadorUnico = false;

    public void initialize() { }

    @FXML
    void mousePressed(MouseEvent event) {
        Ventana.onMousePressed(event);
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        Stage stage = (Stage)barraSuperior.getScene().getWindow();
        Ventana.onMouseDragged(stage, event);
    }

    @FXML
    void cerrar() {
        Stage stage = (Stage)barraSuperior.getScene().getWindow();
        Ventana.cerrar(stage);
    }

    @FXML
    void minimizar() {
        Stage stage = (Stage)barraSuperior.getScene().getWindow();
        stage.setMaximized(false);
        Ventana.minimizar(stage,root.getPrefHeight(), root.getPrefWidth());
        stage.setX(posX);
        stage.setY(posY);
    }

    @FXML
    void maximizar() {
        Stage stage = (Stage)barraSuperior.getScene().getWindow();
        posX = stage.getX();
        posY = stage.getY();
        Ventana.maximizar(stage);
    }

    @FXML
    void onAction(@NotNull ActionEvent event) {

        if(Operaciones.resultado){
            entradaNumero.clear();
            Operaciones.resultado = false;
        }

        if(event.getSource().equals(C)) {
            entradaOperaciones.clear();
            entradaNumero.setText("0");
            Operaciones.resultado = false;
            operadorUnico = false;
            List<String> lista = Operaciones.listaOperaciones;
            Operaciones.listaOperaciones.removeAll(lista);
        } else if (event.getSource().equals(CE)) {
            entradaNumero.setText("0");
        } else {
            Button button = (Button) event.getSource();
            String entry = button.getText();
            Operaciones.Entrada(entradaNumero, entry);
        }
    }

    @FXML
    void operacion(ActionEvent event) {
        // Realiza una operacion simple como raiz, potencia
        operadorUnico = true;
        String numero = entradaNumero.getText().replaceAll(",", "");
        String operador = "";

        if(event.getSource().equals(raiz)) {
            operador = "√";
            entradaOperaciones.appendText("√(" + entradaNumero.getText() + ")");
        } else if (event.getSource().equals(potencia)) {
            operador = "**";
            entradaOperaciones.appendText("sqr(" + entradaNumero.getText() + ")");
        } else if(event.getSource().equals(invertido)) {
            operador ="/-";
            entradaOperaciones.appendText("1/(" + entradaNumero.getText() + ")");
        } else if(event.getSource().equals(porcentaje)) {
            operador = "%";
            entradaOperaciones.appendText(entradaNumero.getText() + "%");
        }
        // Coloca el resultado en la entrada
        entradaNumero.setText(Operaciones.OperacionUnica(numero,operador).toString());
    }


    @FXML
    void signo(@NotNull ActionEvent event) {
        // Enlista la entrada anterior y la limpia para esperar por otra entrada
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
            // Al presionar igual retorna las operaciones, limpia la entrada de Operaciones y borra la lista de Operaciones
            entradaNumero.setText(Operaciones.Operacion().toString());
            Operaciones.resultado = true;
            entradaOperaciones.clear();
            List<String> remover = Operaciones.listaOperaciones;
            Operaciones.listaOperaciones.removeAll(remover);
        } else {
            // Cualquiera otra tecla espera por una nueva entrada
            entradaNumero.clear();
            Operaciones.listaOperaciones.add(operador);
            entradaOperaciones.appendText(" " + numero + " " + operador);
//            if(!operadorUnico) {
//                entradaOperaciones.appendText(" " + numero + " " + operador);
//            } else {
//                entradaOperaciones.appendText(" " + operador);
//            }
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

