package Corellium.Ventana.Juegos.Buscaminas;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.Objects;

class PanelBuscaminas {
    private boolean[][] descubierto = new boolean[30][50];
    private boolean[][] minas = new boolean[30][50];
    private GridPane tablero = new GridPane();

    PanelBuscaminas() {
    }

    GridPane llenarPanel() {
//        gridPane.setPrefSize(window.getWidth(),window.getHeight());

        tablero.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        for(int i = 0; i < 30; i++) {
            for(int j= 0; j < 50; j++) {
                minas[i][j] = false;
                descubierto[i][j] = false;
                tablero.add(mina(), j, i);
            }
        }

        for(int i = 0; i < 50; i++) {
            tablero.getColumnConstraints().add(new ColumnConstraints(10,20,Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER,true));
        }

        for(int i = 0; i < 30; i++) {
            tablero.getRowConstraints().add(new RowConstraints(10,20,Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER,true));
        }

        int f,c;
        int maxMinas = 250;
        for(int n = 0; n < maxMinas; n++) {
            do {
                f = (int)(Math.random()*30);
                c = (int)(Math.random()*50);
            }while (minas[f][c]);
            minas[f][c] = true;
        }

        return tablero;
    }

    private Button mina () {
        Button mina = new Button();
        mina.setPrefSize(10,10);
        mina.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        mina.setOnAction(event -> {
            int f = GridPane.getRowIndex(mina);
            int c = GridPane.getColumnIndex(mina);
            if(!descubierto[f][c]) {
                descubierto[f][c] = true;
                mina.setStyle("-fx-background-color: rgb(170,255,255)");
                if(minas[f][c]) {
                    mina.setText("*");
                    for(int i = 0; i < 30; i++)
                        for (int j = 0; j < 50; j++) {
                            if (minas[i][j]) {
                                Button boton = getNodo(i, j);
                                assert boton != null;
                                boton.setStyle("-fx-background-color: rgb(225,50,100)");
                                boton.setText("*");
                            }
                            // Termina because perdio uwu
                        }

                } else {
                    int numMinas = cuentaMinas(f, c);
                    System.out.println("setOnAction" + numMinas);
                    if(numMinas == 0) {
                        autoDescubrir(f, c);
                    } else {
                        Objects.requireNonNull(getNodo(f, c)).setText(Integer.toString(numMinas));
                    }
                }

            }
        });

        return mina;
    }

    private int cuentaMinas(int f, int c) {
        int num = 0;

        for(int x = f - 1; x <= f + 1; x++) {
            for(int y = c - 1; y <= c + 1; y++) {
                if(!(x == f && y == c) && x >= 0 && x < 30 && y >= 0 && y < 50) {
                    if(minas[x][y]) {
                        num++;
                    }
                }
            }
        }

        return num;
    }

    private void autoDescubrir(int f, int c) {
        Button button = getNodo(f, c);
        button.setStyle("-fx-background-color: rgb(170,255,255)");
        descubierto[f][c] = true;
        for(int x = f - 1; x <= f + 1; x++) {
            for(int y = c - 1; y <= c + 1; y++) {
                if((x >= 0 && x<30) && (y>=0 && y<50) && !(x == f && y == c) && !descubierto[f][c]) {
                    int num = cuentaMinas(f, c);
                    System.out.println("autoDescubrir" + num);
                    if(num == 0){
                        autoDescubrir(f , c);
                    } else {
                        System.out.println("autoDescubrirElse" + num);
                        Button current = getNodo(x, y);
                        minas[x][y] = true;
                        current.setStyle("-fx-background-color: rgb(170,255,255)");
                        current.setText(Integer.toString(num));
                    }
                }
            }
        }
    }

    private Button getNodo(int fila, int columna) {
        ObservableList<Node> hijos = tablero.getChildren();

        for(Node node: hijos) {
            if(GridPane.getRowIndex(node) == fila &&
                    GridPane.getColumnIndex(node) == columna) {
                return (Button)node;
            }
        }

        return null;
    }
}
