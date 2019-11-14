package Corellium.Ventana.Web;

import Corellium.Ventana.Ventana;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class WebController {

    @FXML
    BorderPane webBorderPane;
    @FXML
    VBox barraSuperior;

    @FXML
    Button retroceder;
    @FXML
    Button avanzar;
    @FXML
    Button recargar;
    @FXML
    Button home;

    @FXML
    TextField dirURL;
    @FXML
    TextField busqueda;

    @FXML
    TabPane tabPane;
    @FXML
    Tab addPst;

    private ListView<String> historial = new ListView<>();
    private ListView<String> urlWeb = new ListView<>();


    @FXML
    public void initialize() {
        barraSuperior.getChildren().add(0, Ventana.barraTitulo(this.getClass()));
        cargarDeHistorial();
        // Al iniciar el navegador o seleccionar el Tab añadir pestaña los botones se desactivan
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldState, newState) -> {
            if(newState.getTabPane().getSelectionModel().isSelected(tabPane.getTabs().size()-1)) {
                retroceder.setDisable(true);
                avanzar.setDisable(true);
                dirURL.setText("");
            }
        });

    }

    @FXML
    void accionNavegar(ActionEvent event) {
    // Acciones para el botón retroceder, avanzar, recargar y home
        WebView pagWeb = (WebView) tabPane.getSelectionModel().getSelectedItem().getContent();

        if(event.getSource().equals(retroceder)) {
            pagWeb.getEngine().getHistory().go(-1);
        } else if(event.getSource().equals(avanzar)) {
            pagWeb.getEngine().getHistory().go(1);
        } else if(event.getSource().equals(recargar)) {
            pagWeb.getEngine().reload();
        } else if(event.getSource().equals(home)) {
            nuevaPagWeb("Home");
        }
    }

    @FXML
    void realizarBusqueda(KeyEvent event) {
        /*
        Busca la direccion URL si el objeto es el TextField dirURL si es
        TextField busqueda busca lo digitado por el usuario, los dos presionando la tecla Enter,
        en caso el usuario este en la pestaña agregar pestaña, se carga otra pestaña automáticamente
         */
        if(event.getCode().equals(KeyCode.ENTER)) {
            if(tabPane.getSelectionModel().isSelected(tabPane.getTabs().size() - 1)) {
                if(event.getSource().equals(dirURL)) {
                    nuevaPagWeb("Home");
                } else if(event.getSource().equals(busqueda)) {
                    nuevaPagWeb("https://www.google.com/search?q=" + busqueda.getText().replaceAll(" ","+"));
                }

            } else {
                Tab pst = tabPane.getSelectionModel().getSelectedItem();
                WebView pagWeb = (WebView)pst.getContent();
                pagWeb.getEngine().setJavaScriptEnabled(true);
                if (event.getSource().equals(dirURL)){
                    System.out.println(dirURL.getText());
                    busquedaURL(pagWeb);
                } else if (event.getSource().equals(busqueda)) {
                    pagWeb.getEngine().load("https://www.google.com/search?q=" + busqueda.getText().replaceAll(" ","+"));
                }
                getTituloWeb(pst, pagWeb);
            }
        }
    }

    @FXML
    void seleccionBusqueda(MouseEvent event) {
    // Eventos para seleccion de la URL actual, con un click selecciona todo el texto, con dos selecciona el final
        if(event.getClickCount() % 2 == 0 && !event.isConsumed()) {
            event.consume();
            dirURL.deselect();
            dirURL.selectEnd();
        } else {
            dirURL.selectAll();
        }
    }

    @FXML
    void mostrarHistorial() {
        // ActionEvent: Carga el historial en el lado izquierdo del navegador
        if(webBorderPane.getRight() == null) {
            webBorderPane.setRight(historial);
        } else {
            webBorderPane.setRight(null);
        }
    }


    private void cargarDeHistorial() {
        // Carga la pagina seleccionada del historial
        historial.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && !event.isConsumed()) {
                event.consume();
                if(tabPane.getSelectionModel().isSelected(tabPane.getTabs().size() - 1)) {
                    nuevaPagWeb(urlWeb.getItems().get(historial.getSelectionModel().getSelectedIndex()));
                } else {
                    WebView pagActual = (WebView)tabPane.getSelectionModel().getSelectedItem().getContent();
                    pagActual.getEngine().load(urlWeb.getItems().get(historial.getSelectionModel().getSelectedIndex()));
                }
            }
        });
    }

    @FXML
    void agregarPst() {
        // ActionEvent: Añade una pestaña limpiando los campos de URL y búsqueda
        dirURL.setText("");
        nuevaPagWeb("Home");
    }

    private void nuevaPagWeb(String url) {
        // Añade una nueva página web, si el url es "Home" se carga buscador predeterminado
        Tab nuevaPst = new Tab();
        WebView pagWeb = new WebView();
        pagWeb.getEngine().setJavaScriptEnabled(true);
        nuevaPst.setContent(pagWeb); // Añade el WebView a la pestaña
        nuevaPst.setClosable(true); // Habilita el cerrar la pestaña

        if(url.equals("Home")) {
            pagWeb.getEngine().load("https://google.com");
        } else {
            pagWeb.getEngine().load(url);
            busquedaURL(pagWeb);
        }

        tabPane.getTabs().add(0,nuevaPst); // Se añade la pestaña al inicio
        tabPane.getTabs().set(tabPane.getTabs().size() - 1, addPst); // La pestaña de añadir al último

        pstSeleccionada(nuevaPst, pagWeb);
        getTituloWeb(nuevaPst, pagWeb);
    }

    // Cambia la direccion URL de acuerdo a la ventana seleccionada así como los botones de avanzar y retroceder
    private void pstSeleccionada(Tab tab, WebView webView) {
        tab.setOnSelectionChanged(event -> {
            setBotonesNav(webView);
            dirURL.setText(webView.getEngine().getLocation());
        });
    }

    // Busca la pagina digita por el usuario
    private void busquedaURL(WebView webView) {
        if(dirURL.getText().matches("^(https?://)([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$")) {
            webView.getEngine().load(dirURL.getText());
        } else if (dirURL.getText().contains(".")) {
            webView.getEngine().load("https://" + dirURL.getText());
        } else if (dirURL.getText().isEmpty()) {
            webView.getEngine().load("https://www.google.com.pe");
        } else {
            webView.getEngine().load("https://www.google.com/searchq?=" + dirURL.getText().replaceAll(" ", "+"));
        }
    }


    // Obtiene la URL para dirURL y el titulo de pagina web para el Tab actual
    private void getTituloWeb(Tab tab, WebView pagActual) {
        pagActual.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            tabPane.getSelectionModel().selectFirst();
            if (newState == Worker.State.SUCCEEDED) {
                dirURL.setText(pagActual.getEngine().getLocation());
                tab.setText(pagActual.getEngine().getTitle());
                setBotonesNav(pagActual);
                // Una vez cargada la página la añade al historial
                    pagActual.getEngine().getHistory().getEntries().forEach((pagWeb) -> {
                        if(!historial.getItems().contains(pagWeb.getTitle())
                                && !urlWeb.getItems().contains(pagWeb.getUrl())){
                            historial.getItems().add(pagWeb.getTitle() + " " + pagWeb.getLastVisitedDate());
                            urlWeb.getItems().add(pagWeb.getUrl());
                        }
                    });
            }
        });
    }

    // Inhabilita los botones de acuerdo al índice de la página web actualmente visitada
    private void setBotonesNav(WebView webView) {
        WebHistory webHistory = webView.getEngine().getHistory();
        if (webHistory.getCurrentIndex() == 0 && (webHistory.getCurrentIndex() == webHistory.getEntries().size() - 1)) {
            retroceder.setDisable(true);
            avanzar.setDisable(true);
        } else if (webHistory.getCurrentIndex() == 0) {
            retroceder.setDisable(true);
            avanzar.setDisable(false);
        } else if (webHistory.getCurrentIndex() == webHistory.getEntries().size() - 1) {
            retroceder.setDisable(false);
            avanzar.setDisable(true);
        } else {
            retroceder.setDisable(false);
            avanzar.setDisable(false);
        }
    }

}
