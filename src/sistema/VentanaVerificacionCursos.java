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
import java.util.stream.Collectors;

public class VentanaVerificacionCursos {

    private List<Asignacion> asignaciones;
    private List<Curso> cursos;

    public VentanaVerificacionCursos() {
        cursos = obtenerCursos();
        asignaciones = obtenerAsignaciones();
    }

    public void mostrar(Stage stagePrincipal) {
        Stage stage = new Stage();
        stage.setTitle("Verificación de Cursos");

        ComboBox<Curso> comboCursos = new ComboBox<>();
        comboCursos.getItems().addAll(cursos);
        comboCursos.setPromptText("Seleccione un curso");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Seleccione una fecha");

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

        PieChart graficoPastel = new PieChart();

        tablaAsignaciones.setItems(FXCollections.observableArrayList());
        graficoPastel.setData(FXCollections.observableArrayList());

        comboCursos.setOnAction(e -> {
            actualizarTablaYGrafico(comboCursos.getValue(), datePicker.getValue(), tablaAsignaciones, graficoPastel);
        });

        datePicker.setOnAction(e -> {
            actualizarTablaYGrafico(comboCursos.getValue(), datePicker.getValue(), tablaAsignaciones, graficoPastel);
        });

        HBox hboxSelectors = new HBox(20, new Label("Curso:"), comboCursos, new Label("Fecha:"), datePicker);
        hboxSelectors.setStyle("-fx-padding: 20; -fx-alignment: center;");

        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.getChildren().addAll(hboxSelectors, tablaAsignaciones, graficoPastel);
        layoutPrincipal.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layoutPrincipal, 800, 600);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);

        stage.setOnHidden(e -> stagePrincipal.show());

        stage.show();
    }

    private void actualizarTablaYGrafico(Curso curso, LocalDate fecha, TableView<Asignacion> tabla, PieChart grafico) {
        List<Asignacion> asignacionesFiltradas = asignaciones.stream()
                .filter(a -> (curso == null || a.getCurso().equals(curso)) &&
                        (fecha == null || a.getFechaAsignacion().equals(fecha)))
                .collect(Collectors.toList());

        ObservableList<Asignacion> datosTabla = FXCollections.observableArrayList(asignacionesFiltradas);
        tabla.setItems(datosTabla);

        int total = asignacionesFiltradas.size();
        int solventes = (int) asignacionesFiltradas.stream().filter(Asignacion::isPagado).count();
        int noSolventes = total - solventes;

        ObservableList<PieChart.Data> datosGrafico = FXCollections.observableArrayList();

        if (total > 0) {
            if (solventes > 0) {
                datosGrafico.add(new PieChart.Data("Solventes", solventes));
            }
            if (noSolventes > 0) {
                datosGrafico.add(new PieChart.Data("No Solventes", noSolventes));
            }
        }

        grafico.setData(datosGrafico);
    }

    private List<Curso> obtenerCursos() {
        List<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(new Curso("C001", "Matemáticas"));
        listaCursos.add(new Curso("C002", "Física"));
        listaCursos.add(new Curso("C003", "Química"));
        listaCursos.add(new Curso("C004", "Biología"));
        listaCursos.add(new Curso("C005", "Historia"));
        return listaCursos;
    }

    private List<Asignacion> obtenerAsignaciones() {
        List<Asignacion> listaAsignaciones = new ArrayList<>();
        Estudiante estudiante1 = new Estudiante("Juan Pérez", "20210001", "5555-5555", "juan@example.com");
        Estudiante estudiante2 = new Estudiante("María García", "20210002", "5555-6666", "maria@example.com");
        Estudiante estudiante3 = new Estudiante("Carlos López", "20210003", "5555-7777", "carlos@example.com");
        Estudiante estudiante4 = new Estudiante("Ana Torres", "20210004", "5555-8888", "ana@example.com");

        Curso curso1 = cursos.get(0);
        Curso curso2 = cursos.get(1);
        Curso curso3 = cursos.get(2);

        listaAsignaciones.add(new Asignacion(estudiante1, curso1, LocalDate.of(2023, 11, 12), true));
        listaAsignaciones.add(new Asignacion(estudiante2, curso1, LocalDate.of(2023, 11, 12), false));
        listaAsignaciones.add(new Asignacion(estudiante3, curso2, LocalDate.of(2023, 11, 13), true));
        listaAsignaciones.add(new Asignacion(estudiante4, curso1, LocalDate.of(2023, 11, 14), true));
        listaAsignaciones.add(new Asignacion(estudiante2, curso3, LocalDate.of(2023, 11, 14), false));

        return listaAsignaciones;
    }
}