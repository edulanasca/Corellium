module Corellium {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.graphics;
    requires java.desktop;
    requires org.jetbrains.annotations;

    opens Corellium;
    opens Corellium.img.Escritorio;
    opens Corellium.img.Escritorio.barraDeTareas;
    opens Corellium.img.ExploradorArchivos;
    opens Corellium.img.ExploradorArchivos.TipoArchivo;
    opens Corellium.img.ExploradorArchivos.TipoArchivo.NuevoArchivo;
    opens Corellium.img.Papelera;
    opens Corellium.Ventana;
    opens Corellium.Ventana.Calculadora;
    opens Corellium.Ventana.Escritorio;
    opens Corellium.Ventana.Escritorio.Default;
    opens Corellium.Ventana.Web;
    opens Corellium.Ventana.Juegos.Pingpong;
    opens Corellium.Ventana.Papelera;
    opens Corellium.Ventana.ExploradorArchivos;
    opens Corellium.Ventana.ExploradorArchivos.modelo;
}