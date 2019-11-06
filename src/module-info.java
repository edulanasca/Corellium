module Corellium {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.graphics;
    requires java.desktop;

    opens Corellium;
    opens Corellium.Escritorio;
    opens Corellium.Ventana;
    opens Corellium.Ventana.Web;
    opens Corellium.Ventana.ExploradorArchivos;
    opens Corellium.utilidades;
    opens Corellium.img.Escritorio;
    opens Corellium.img.Escritorio.barraDeTareas;
    opens Corellium.img.ExploradorArchivos;
    opens Corellium.img.ExploradorArchivos.TipoArchivo;
}