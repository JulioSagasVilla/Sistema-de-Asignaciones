package sistema;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}