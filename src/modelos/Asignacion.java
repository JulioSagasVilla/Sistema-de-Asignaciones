package modelos;

import java.time.LocalDate;

public class Asignacion {
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechaAsignacion;
    private boolean pagado;

    // Constructor vacío
    public Asignacion() {
    }

    // Constructor con parámetros
    public Asignacion(Estudiante estudiante, Curso curso, LocalDate fechaAsignacion, boolean pagado) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.fechaAsignacion = fechaAsignacion;
        this.pagado = pagado;
    }

    // Getters y Setters
    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    // Método toString
    @Override
    public String toString() {
        return "Asignacion{" +
                "estudiante=" + estudiante +
                ", curso=" + curso +
                ", fechaAsignacion=" + fechaAsignacion +
                ", pagado=" + pagado +
                '}';
    }
}