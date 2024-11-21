package sistema;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelos.Asignacion;
import modelos.Curso;
import modelos.Estudiante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaVerificacionCursos {

    // Simulación de datos
    private List<Asignacion> asignaciones;
    private List<Curso> cursos;

    public VentanaVerificacionCursos() {
        // Inicializar listas de datos simulados
        cursos = obtenerCursos();
        asignaciones = obtenerAsignaciones();
    }

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Verificación de Cursos");

        // ComboBox de cursos
        ComboBox<Curso> comboCursos = new ComboBox<>();
        comboCursos.getItems().addAll(cursos);
        comboCursos.setPromptText("Seleccione un curso");

        // DatePicker para seleccionar fecha
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Seleccione una fecha");

        // Tabla para mostrar asignaciones
        TableView<Asignacion> tablaAsignaciones = new TableView<>();

        TableColumn<Asignacion, String> colCarnet = new TableColumn<>("Carnet");
        colCarnet.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstudiante().getCarnet()));

        TableColumn<Asignacion, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstudiante().getNombre()));

        TableColumn<Asignacion, String> colCorreo = new TableColumn<>("Correo Electrónico");
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstudiante().getCorreoElectronico()));

        TableColumn<Asignacion, LocalDate> colFecha = new TableColumn<>("Fecha de Inscripción");
        colFecha.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaAsignacion()));

        TableColumn<Asignacion, String> colSolvente = new TableColumn<>("Solvente");
        colSolvente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPagado() ? "Sí" : "No"));

        tablaAsignaciones.getColumns().addAll(colCarnet, colNombre, colCorreo, colFecha, colSolvente);

        // Gráfico de pastel
        PieChart graficoPastel = new PieChart();

        // Funcionalidad al seleccionar un curso
        comboCursos.setOnAction(e -> {
            Curso cursoSeleccionado = comboCursos.getValue();
            actualizarTablaYGrafico(cursoSeleccionado, datePicker.getValue(), tablaAsignaciones, graficoPastel);
        });

        // Funcionalidad al seleccionar una fecha
        datePicker.setOnAction(e -> {
            Curso cursoSeleccionado = comboCursos.getValue();
            actualizarTablaYGrafico(cursoSeleccionado, datePicker.getValue(), tablaAsignaciones, graficoPastel);
        });

        // Layouts
        HBox hboxSelectors = new HBox(20, new Label("Curso:"), comboCursos, new Label("Fecha:"), datePicker);
        hboxSelectors.setStyle("-fx-padding: 20; -fx-alignment: center;");

        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.getChildren().addAll(hboxSelectors, tablaAsignaciones, graficoPastel);
        layoutPrincipal.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layoutPrincipal, 800, 600);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void actualizarTablaYGrafico(Curso curso, LocalDate fecha, TableView<Asignacion> tabla, PieChart grafico) {
        List<Asignacion> asignacionesFiltradas = new ArrayList<>();

        for (Asignacion asignacion : asignaciones) {
            boolean coincideCurso = (curso == null) || asignacion.getCurso().equals(curso);
            boolean coincideFecha = (fecha == null) || asignacion.getFechaAsignacion().equals(fecha);

            if (coincideCurso && coincideFecha) {
                asignacionesFiltradas.add(asignacion);
            }
        }

        ObservableList<Asignacion> datosTabla = FXCollections.observableArrayList(asignacionesFiltradas);
        tabla.setItems(datosTabla);

        // Actualizar gráfico
        int total = asignacionesFiltradas.size();
        int solventes = (int) asignacionesFiltradas.stream().filter(Asignacion::isPagado).count();
        int noSolventes = total - solventes;

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList(
                new PieChart.Data("Solventes", solventes),
                new PieChart.Data("No Solventes", noSolventes)
        );

        grafico.setData(datosGrafico);
    }

    // Métodos para simular datos
    private List<Curso> obtenerCursos() {
        List<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(new Curso("C001", "Matemáticas"));
        listaCursos.add(new Curso("C002", "Física"));
        listaCursos.add(new Curso("C003", "Química"));
        return listaCursos;
    }

    private List<Asignacion> obtenerAsignaciones() {
        List<Asignacion> listaAsignaciones = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante("Juan Pérez", "20210001", "5555-5555", "juan@example.com");
        Estudiante estudiante2 = new Estudiante("María García", "20210002", "5555-6666", "maria@example.com");
        Estudiante estudiante3 = new Estudiante("Carlos López", "20210003", "5555-7777", "carlos@example.com");

        Curso curso1 = cursos.get(0);
        Curso curso2 = cursos.get(1);

        listaAsignaciones.add(new Asignacion(estudiante1, curso1, LocalDate.of(2023, 11, 12), true));
        listaAsignaciones.add(new Asignacion(estudiante2, curso1, LocalDate.of(2023, 11, 12), false));
        listaAsignaciones.add(new Asignacion(estudiante3, curso2, LocalDate.of(2023, 11, 13), true));

        return listaAsignaciones;
    }
}