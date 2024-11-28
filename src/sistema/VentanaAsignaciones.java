package sistema;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelos.Curso;
import modelos.Estudiante;
import modelos.Asignacion;
import bd.ConexionBD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class VentanaAsignaciones {

    private List<Curso> cursosDisponibles;
    private List<Curso> cursosSeleccionados;

    public void mostrar(Stage stagePrincipal) {
        Stage stage = new Stage();
        stage.setTitle("Formulario de Asignaciones");

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblCarnet = new Label("Número de Carnet:");
        TextField txtCarnet = new TextField();

        Label lblTelefono = new Label("Teléfono:");
        TextField txtTelefono = new TextField();

        Label lblCorreo = new Label("Correo Electrónico:");
        TextField txtCorreo = new TextField();

        Label lblCursosDisponibles = new Label("Cursos Disponibles:");
        ListView<Curso> lstCursosDisponibles = new ListView<>();
        cursosDisponibles = obtenerCursos();
        lstCursosDisponibles.getItems().addAll(cursosDisponibles);

        Label lblCursosSeleccionados = new Label("Cursos Seleccionados:");
        ListView<Curso> lstCursosSeleccionados = new ListView<>();
        cursosSeleccionados = new ArrayList<>();

        CheckBox chkPagarAhora = new CheckBox("Pagar Ahora");

        Label lblNombreTarjeta = new Label("Nombre del Tarjetahabiente:");
        TextField txtNombreTarjeta = new TextField();

        Label lblNumeroTarjeta = new Label("Número de la Tarjeta:");
        TextField txtNumeroTarjeta = new TextField();

        Label lblFechaExpiracion = new Label("Fecha de Expiración (MM/YY):");
        TextField txtFechaExpiracion = new TextField();

        Label lblCodigoSeguridad = new Label("Código de Seguridad:");
        TextField txtCodigoSeguridad = new TextField();

        VBox contenedorTarjeta = new VBox(10);
        contenedorTarjeta.getChildren().addAll(
                lblNombreTarjeta, txtNombreTarjeta,
                lblNumeroTarjeta, txtNumeroTarjeta,
                lblFechaExpiracion, txtFechaExpiracion,
                lblCodigoSeguridad, txtCodigoSeguridad
        );
        contenedorTarjeta.setVisible(false);
        contenedorTarjeta.setDisable(true);

        Button btnAsignar = new Button("Asignar");
        btnAsignar.setDisable(true);

        chkPagarAhora.setOnAction(e -> {
            boolean selected = chkPagarAhora.isSelected();
            contenedorTarjeta.setVisible(selected);
            contenedorTarjeta.setDisable(!selected);
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });

        txtCarnet.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { 
                verificarEstudiante(txtCarnet.getText(), txtNombre, txtTelefono, txtCorreo);
            }
        });

        txtNombre.textProperty().addListener((obs, oldText, newText) -> {
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });
        txtCarnet.textProperty().addListener((obs, oldText, newText) -> {
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });
        txtTelefono.textProperty().addListener((obs, oldText, newText) -> {
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });
        txtCorreo.textProperty().addListener((obs, oldText, newText) -> {
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });

        txtNombreTarjeta.textProperty().addListener((obs, oldText, newText) -> {
            if (chkPagarAhora.isSelected()) {
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });
        txtNumeroTarjeta.textProperty().addListener((obs, oldText, newText) -> {
            if (chkPagarAhora.isSelected()) {
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });
        txtFechaExpiracion.textProperty().addListener((obs, oldText, newText) -> {
            if (chkPagarAhora.isSelected()) {
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });
        txtCodigoSeguridad.textProperty().addListener((obs, oldText, newText) -> {
            if (chkPagarAhora.isSelected()) {
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });

        lstCursosSeleccionados.getItems().addListener((javafx.collections.ListChangeListener.Change<? extends Curso> c) -> {
            cursosSeleccionados = new ArrayList<>(lstCursosSeleccionados.getItems());
            validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                    cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                    txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
        });

        Button btnAgregarCurso = new Button("Agregar >>");
        Button btnQuitarCurso = new Button("<< Quitar");

        btnAgregarCurso.setOnAction(e -> {
            Curso cursoSeleccionado = lstCursosDisponibles.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado != null) {
                lstCursosSeleccionados.getItems().add(cursoSeleccionado);
                lstCursosDisponibles.getItems().remove(cursoSeleccionado);
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });

        btnQuitarCurso.setOnAction(e -> {
            Curso cursoSeleccionado = lstCursosSeleccionados.getSelectionModel().getSelectedItem();
            if (cursoSeleccionado != null) {
                lstCursosSeleccionados.getItems().remove(cursoSeleccionado);
                lstCursosDisponibles.getItems().add(cursoSeleccionado);
                validarFormulario(txtNombre, txtCarnet, txtTelefono, txtCorreo,
                        cursosSeleccionados, chkPagarAhora, txtNombreTarjeta, txtNumeroTarjeta,
                        txtFechaExpiracion, txtCodigoSeguridad, btnAsignar);
            }
        });

        btnAsignar.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String carnet = txtCarnet.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            boolean pagado = chkPagarAhora.isSelected();

            List<Curso> cursosAAsignar = new ArrayList<>(lstCursosSeleccionados.getItems());

            try (Connection conn = ConexionBD.obtenerConexion()) {
                conn.setAutoCommit(false);

                String sqlEstudiante = "SELECT carnet FROM estudiantes WHERE carnet = ?";
                PreparedStatement stmtEstudiante = conn.prepareStatement(sqlEstudiante);
                stmtEstudiante.setString(1, carnet);
                ResultSet rsEstudiante = stmtEstudiante.executeQuery();

                if (!rsEstudiante.next()) {
                    String sqlInsertEstudiante = "INSERT INTO estudiantes (carnet, nombre, telefono, correo_electronico) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmtInsertEstudiante = conn.prepareStatement(sqlInsertEstudiante);
                    stmtInsertEstudiante.setString(1, carnet);
                    stmtInsertEstudiante.setString(2, nombre);
                    stmtInsertEstudiante.setString(3, telefono);
                    stmtInsertEstudiante.setString(4, correo);
                    stmtInsertEstudiante.executeUpdate();
                }

                String sqlInsertAsignacion = "INSERT INTO asignaciones (carnet_estudiante, codigo_curso, fecha_asignacion, pagado) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtInsertAsignacion = conn.prepareStatement(sqlInsertAsignacion);

                for (Curso curso : cursosAAsignar) {
                    stmtInsertAsignacion.setString(1, carnet);
                    stmtInsertAsignacion.setString(2, curso.getCodigo());
                    stmtInsertAsignacion.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    stmtInsertAsignacion.setBoolean(4, pagado);
                    stmtInsertAsignacion.executeUpdate();
                }

                conn.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Datos del estudiante:\n");
            mensaje.append("Nombre: ").append(nombre).append("\n");
            mensaje.append("Carnet: ").append(carnet).append("\n");
            mensaje.append("Teléfono: ").append(telefono).append("\n");
            mensaje.append("Correo: ").append(correo).append("\n");
            mensaje.append("Fecha: ").append(LocalDate.now()).append("\n");
            mensaje.append("Pago realizado: ").append(pagado ? "Sí" : "No").append("\n");
            mensaje.append("Cursos asignados:\n");
            for (Curso curso : cursosAAsignar) {
                mensaje.append("- ").append(curso.getNombre()).append("\n");
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Asignación");
            alerta.setHeaderText("Asignación realizada");
            alerta.setContentText(mensaje.toString());
            alerta.showAndWait();

            txtNombre.clear();
            txtCarnet.clear();
            txtTelefono.clear();
            txtCorreo.clear();
            lstCursosSeleccionados.getItems().clear();
            lstCursosDisponibles.getItems().clear();
            cursosDisponibles = obtenerCursos();
            lstCursosDisponibles.getItems().addAll(cursosDisponibles);
            chkPagarAhora.setSelected(false);
            contenedorTarjeta.setVisible(false);
            contenedorTarjeta.setDisable(true);
            txtNombreTarjeta.clear();
            txtNumeroTarjeta.clear();
            txtFechaExpiracion.clear();
            txtCodigoSeguridad.clear();
            btnAsignar.setDisable(true);
        });

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

        VBox vboxCursosDisponibles = new VBox(5, lblCursosDisponibles, lstCursosDisponibles);
        VBox vboxCursosSeleccionados = new VBox(5, lblCursosSeleccionados, lstCursosSeleccionados);

        VBox vboxBotonesCursos = new VBox(10, btnAgregarCurso, btnQuitarCurso);
        vboxBotonesCursos.setStyle("-fx-alignment: center; -fx-padding: 20 0;");

        HBox hboxListasCursos = new HBox(10, vboxCursosDisponibles, vboxBotonesCursos, vboxCursosSeleccionados);

        VBox layoutPrincipal = new VBox(20);
        layoutPrincipal.getChildren().addAll(
                gridFormulario,
                hboxListasCursos,
                chkPagarAhora,
                contenedorTarjeta,
                btnAsignar
        );
        layoutPrincipal.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layoutPrincipal, 600, 700);
        stage.setScene(scene);

        stage.setOnHidden(e -> stagePrincipal.show());

        stage.show();
    }

    private void verificarEstudiante(String carnet, TextField txtNombre, TextField txtTelefono, TextField txtCorreo) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            String sql = "SELECT nombre, telefono, correo_electronico FROM estudiantes WHERE carnet = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, carnet);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtTelefono.setText(rs.getString("telefono"));
                txtCorreo.setText(rs.getString("correo_electronico"));
            } else {
                txtNombre.clear();
                txtTelefono.clear();
                txtCorreo.clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void validarFormulario(TextField txtNombre, TextField txtCarnet, TextField txtTelefono, TextField txtCorreo,
                                   List<Curso> cursosSeleccionados, CheckBox chkPagarAhora,
                                   TextField txtNombreTarjeta, TextField txtNumeroTarjeta, TextField txtFechaExpiracion,
                                   TextField txtCodigoSeguridad, Button btnAsignar) {
        boolean nombreValido = txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        boolean carnetValido = !txtCarnet.getText().trim().isEmpty();
        boolean telefonoValido = !txtTelefono.getText().trim().isEmpty();
        boolean correoValido = txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$");
        boolean cursosSeleccionadosValido = !cursosSeleccionados.isEmpty();

        boolean camposBasicosValidos = nombreValido && carnetValido && telefonoValido && correoValido && cursosSeleccionadosValido;

        if (chkPagarAhora.isSelected()) {
            boolean nombreTarjetaValido = !txtNombreTarjeta.getText().trim().isEmpty();
            boolean numeroTarjetaValido = txtNumeroTarjeta.getText().matches("\\d{16}");
            boolean fechaExpiracionValida = txtFechaExpiracion.getText().matches("(0[1-9]|1[0-2])/\\d{2}");
            boolean codigoSeguridadValido = txtCodigoSeguridad.getText().matches("\\d{3,4}");

            boolean camposTarjetaValidos = nombreTarjetaValido && numeroTarjetaValido && fechaExpiracionValida && codigoSeguridadValido;

            btnAsignar.setDisable(!(camposBasicosValidos && camposTarjetaValidos));
        } else {
            btnAsignar.setDisable(!camposBasicosValidos);
        }
    }
}