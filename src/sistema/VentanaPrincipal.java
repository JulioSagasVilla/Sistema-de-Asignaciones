package sistema;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaPrincipal {

    private Stage stage;

    public void mostrar() {
        stage = new Stage();
        stage.setTitle("Sistema de Asignaciones de Julio Sagastume carnet 23004956");

        Button btnAsignaciones = new Button("Asignaciones");
        btnAsignaciones.setOnAction(e -> {
            VentanaAsignaciones ventanaAsignaciones = new VentanaAsignaciones();
            ventanaAsignaciones.mostrar(stage);
            stage.hide();
        });

        Button btnVerificacion = new Button("Verificación de Cursos");
        btnVerificacion.setOnAction(e -> {
            VentanaVerificacionCursos ventanaVerificacion = new VentanaVerificacionCursos();
            ventanaVerificacion.mostrar(stage); 
            stage.hide(); 
        });

        btnAsignaciones.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnVerificacion.setStyle("-fx-font-size: 14px; -fx-background-color: #2196F3; -fx-text-fill: white;");

        VBox layout = new VBox(20);
        layout.getChildren().addAll(btnAsignaciones, btnVerificacion);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}