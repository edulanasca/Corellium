package Corellium.Ventana.Calculadora;

import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Operaciones {

    private static String numero = "";
    private static boolean punto = false;
    static List<String> listaOperaciones = new ArrayList<>();
    static boolean resultado = false;

    static void Entrada(TextField textField, @NotNull String digito) {

        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(10);

        if (digito.equals("DEL")) {
            if (!textField.getText().equals("")) {
                numero = numero.substring(0, numero.length() - 1);
                if (numero.endsWith(".")) {
                    numero = numero.substring(0, numero.length() - 1);
                    punto = false;
                }
                double d = Double.parseDouble(textField.getText().replaceAll(",", ""));
                textField.clear();
                textField.setText(format.format(d));
            } else {
                numero = "";
            }
        }


        if (digito.equals(".") && !punto) {
            punto = true;
            numero += digito;
            textField.setText(numero);
        } else if (Character.isDigit(digito.charAt(0))) {
            numero = textField.getText().replaceAll(",","") + digito;
            double d = Double.parseDouble(numero);
            textField.clear();
            textField.setText(format.format(d));
        }

    }

    static BigDecimal Operacion() {
        BigDecimal acumular = new BigDecimal(listaOperaciones.get(0));


        for(int i = 1; i < listaOperaciones.size(); i++) {
            if(i % 2 == 0) {
                BigDecimal n2 = new BigDecimal(listaOperaciones.get(i));
                switch (listaOperaciones.get(i - 1)) {
                    case "/":
                        acumular = acumular.divide(n2, 10, RoundingMode.HALF_UP);
                        break;
                    case "*":
                        acumular = acumular.multiply(n2);
                        break;
                    case "-":
                        acumular = acumular.subtract(n2);
                        break;
                    case "+":
                        acumular = acumular.add(n2);
                        break;
                }
            }
        }
        return acumular;
    }

    static BigDecimal OperacionUnica(String numero, String operador) {
        BigDecimal res = new BigDecimal(numero);
        switch (operador) {
            case "√":
                res = res.sqrt(new MathContext(10));
                break;
            case "**":
                res = res.pow(2);
                break;
            case "/-":
                res = BigDecimal.ONE.divide(res,10,RoundingMode.HALF_EVEN);
                break;
            case "%":
                res = res.divide(new BigDecimal("100"),2,RoundingMode.HALF_EVEN);
        }

        return res;
    }
}
