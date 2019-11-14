module Corellium {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.graphics;
    requires java.desktop;
//    requires org.apache.httpcomponents.httpclient;
//    requires org.apache.httpcomponents.httpcore;

    opens Corellium;
    opens Corellium.Ventana.Escritorio;
    opens Corellium.Ventana;
    opens Corellium.Ventana.Web;
    opens Corellium.Ventana.Juegos.Buscaminas;
    opens Corellium.Ventana.Juegos.Pingpong;
    opens Corellium.Ventana.ExploradorArchivos;
    opens Corellium.Ventana.ExploradorArchivos.modelo;
    opens Corellium.img.Escritorio;
    opens Corellium.img.Escritorio.barraDeTareas;
    opens Corellium.img.ExploradorArchivos;
    opens Corellium.img.ExploradorArchivos.TipoArchivo;
    opens Corellium.img.ExploradorArchivos.TipoArchivo.NuevoArchivo;
}