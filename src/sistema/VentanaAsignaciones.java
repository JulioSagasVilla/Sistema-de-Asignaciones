package sistema;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaAsignaciones {

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Formulario de Asignaciones");

        // Etiquetas y campos de texto para los datos del estudiante
        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblCarnet = new Label("Número de Carnet:");
        TextField txtCarnet = new TextField();

        Label lblTelefono = new Label("Teléfono:");
        TextField txtTelefono = new TextField();

        Label lblCorreo = new Label("Correo Electrónico:");
        TextField txtCorreo = new TextField();

        // Listas de cursos
        Label lblCursosDisponibles = new Label("Cursos Disponibles:");
        ListView<String> lstCursosDisponibles = new ListView<>();
        lstCursosDisponibles.getItems().addAll("Curso 1", "Curso 2", "Curso 3");

        Label lblCursosSeleccionados = new Label("Cursos Seleccionados:");
        ListView<String> lstCursosSeleccionados = new ListView<>();

        // Botones para agregar y quitar cursos
        Button btnAgregarCurso = new Button("Agregar >>");
        Button btnQuitarCurso = new Button("<< Quitar");

        btnAgregarCurso.setOnAction(e -> {
            String cursoSeleccionado = lstCursosDisponibles.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado != null) {
                lstCursosSeleccionados.getItems().add(cursoSeleccionado);
                lstCursosDisponibles.getItems().remove(cursoSeleccionado);
            }
        });

        btnQuitarCurso.setOnAction(e -> {
            String cursoSeleccionado = lstCursosSeleccionados.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado != null) {
                lstCursosDisponibles.getItems().add(cursoSeleccionado);
                lstCursosSeleccionados.getItems().remove(cursoSeleccionado);
            }
        });

        // Checkbox para pagar ahora
        CheckBox chkPagarAhora = new CheckBox("Pagar Ahora");

        // Campos de texto para los datos de la tarjeta
        Label lblNombreTarjeta = new Label("Nombre del Tarjetahabiente:");
        TextField txtNombreTarjeta = new TextField();

        Label lblNumeroTarjeta = new Label("Número de la Tarjeta:");
        TextField txtNumeroTarjeta = new TextField();

        Label lblFechaExpiracion = new Label("Fecha de Expiración:");
        TextField txtFechaExpiracion = new TextField();

        Label lblCodigoSeguridad = new Label("Código de Seguridad:");
        TextField txtCodigoSeguridad = new TextField();

        // Contenedor para los campos de la tarjeta
        VBox contenedorTarjeta = new VBox(10);
        contenedorTarjeta.getChildren().addAll(
                lblNombreTarjeta, txtNombreTarjeta,
                lblNumeroTarjeta, txtNumeroTarjeta,
                lblFechaExpiracion, txtFechaExpiracion,
                lblCodigoSeguridad, txtCodigoSeguridad
        );
        contenedorTarjeta.setVisible(false);

        // Mostrar u ocultar los campos de la tarjeta según el estado del checkbox
        chkPagarAhora.setOnAction(e -> {
            contenedorTarjeta.setVisible(chkPagarAhora.isSelected());
        });

        // Botón para asignar
        Button btnAsignar = new Button("Asignar");
        btnAsignar.setOnAction((var e) -> {
            
        });

        // Layout para el formulario de datos del estudiante
        GridPane gridFormulario = new GridPane();
        gridFormulario.setHgap(10);
        gridFormulario.setVgap(10);

        gridFormulario.add(lblNombre, 0, 0);
        gridFormulario.add(txtNombre, 1, 0);

        gridFormulario.add(lblCarnet, 0, 1);
        gridFormulario.add(txtCarnet, 1, 1);

        gridFormulario.add(lblTelefono, 0, 2);
        gridFormulario.add(txtTelefono, 1, 2);

        gridFormulario.add(lblCorreo, 0, 3);
        gridFormulario.add(txtCorreo, 1, 3);

        // Layout para las listas de cursos y los botones
        VBox vboxCursosDisponibles = new VBox(5, lblCursosDisponibles, lstCursosDisponibles);
        VBox vboxCursosSeleccionados = new VBox(5, lblCursosSeleccionados, lstCursosSeleccionados);

        VBox vboxBotonesCursos = new VBox(10, btnAgregarCurso, btnQuitarCurso);
        vboxBotonesCursos.setStyle("-fx-alignment: center; -fx-padding: 20 0;");

        HBox hboxListasCursos = new HBox(10, vboxCursosDisponibles, vboxBotonesCursos, vboxCursosSeleccionados);

        // Layout principal
        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.getChildren().addAll(
                gridFormulario,
                hboxListasCursos,
                chkPagarAhora,
                contenedorTarjeta,
                btnAsignar
        );
        layoutPrincipal.setStyle("-fx-padding: 20;");

        // Escena y mostrar ventana
        Scene scene = new Scene(layoutPrincipal, 600, 700);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}