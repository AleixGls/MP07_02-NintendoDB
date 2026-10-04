package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Cargamos las dos vistas
        UtilsViews.addView(Main.class, "Desktop", "/NintendoDB.fxml");
        UtilsViews.addView(Main.class, "Movil", "/NintendoDBMovil.fxml");

        // Creamos la escena
        Scene scene = new Scene(UtilsViews.parentContainer);

        // Mostramos Desktop inicialmente
        UtilsViews.setView("Desktop");

        // Escuchamos los cambios de ancho de la ventana
        scene.widthProperty().addListener((observable, oldWidth, newWidth) -> {

            if (newWidth.doubleValue() <= 300) {
                UtilsViews.setView("Movil");
            } else {
                UtilsViews.setView("Desktop");
            }
        });

        stage.setTitle("Nintendo DB");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}