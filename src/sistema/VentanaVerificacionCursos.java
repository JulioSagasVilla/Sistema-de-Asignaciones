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
import bd.ConexionBD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class VentanaVerificacionCursos {

    private List<Curso> cursos;

    public VentanaVerificacionCursos() {
        cursos = obtenerCursos();
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
        stage.setScene(scene);

        stage.setOnHidden(e -> stagePrincipal.show());

        stage.show();
    }

    private void actualizarTablaYGrafico(Curso curso, LocalDate fecha, TableView<Asignacion> tabla, PieChart grafico) {
        List<Asignacion> asignacionesFiltradas = new ArrayList<>();
        try (Connection conn = ConexionBD.obtenerConexion()) {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT a.carnet_estudiante, e.nombre, e.correo_electronico, a.fecha_asignacion, a.pagado, c.codigo, c.nombre AS nombre_curso ")
                      .append("FROM asignaciones a ")
                      .append("JOIN estudiantes e ON a.carnet_estudiante = e.carnet ")
                      .append("JOIN cursos c ON a.codigo_curso = c.codigo ");

            List<Object> parametros = new ArrayList<>();
            boolean whereAdded = false;

            if (curso != null) {
                sqlBuilder.append("WHERE c.codigo = ? ");
                parametros.add(curso.getCodigo());
                whereAdded = true;
            }

            if (fecha != null) {
                sqlBuilder.append(whereAdded ? "AND " : "WHERE ").append("a.fecha_asignacion = ? ");
                parametros.add(java.sql.Date.valueOf(fecha));
            }

            PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString());

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                    rs.getString("nombre"),
                    rs.getString("carnet_estudiante"),
                    "",
                    rs.getString("correo_electronico")
                );
                Curso cursoAsignado = new Curso(rs.getString("codigo"), rs.getString("nombre_curso"));
                Asignacion asignacion = new Asignacion(
                    estudiante,
                    cursoAsignado,
                    rs.getDate("fecha_asignacion").toLocalDate(),
                    rs.getBoolean("pagado")
                );
                asignacionesFiltradas.add(asignacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT codigo, nombre FROM cursos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                listaCursos.add(new Curso(codigo, nombre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCursos;
    }
}