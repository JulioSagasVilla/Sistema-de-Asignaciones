package sistema;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaPrincipal {

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Sistema de Asignaciones de Julio Sagastume carnet 23004956");

        // Botón para ir a VentanaAsignaciones
        Button btnAsignaciones = new Button("Asignaciones");
        btnAsignaciones.setOnAction(e -> {
            VentanaAsignaciones ventanaAsignaciones = new VentanaAsignaciones();
            ventanaAsignaciones.mostrar();
        });

        // Botón para ir a VentanaVerificacionCursos
        Button btnVerificacion = new Button("Verificación de Cursos");
        btnVerificacion.setOnAction(e -> {
            VentanaVerificacionCursos ventanaVerificacion = new VentanaVerificacionCursos();
            ventanaVerificacion.mostrar();
        });

        // Aplicar estilos personalizados a los botones
        btnAsignaciones.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnVerificacion.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");

        // Layout principal
        VBox layout = new VBox(20);
        layout.getChildren().addAll(btnAsignaciones, btnVerificacion);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        // Escena y mostrar ventana
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}