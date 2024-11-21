package modelos;

public class Curso {
    private String codigo;
    private String nombre;

    // Constructor vacío
    public Curso() {
    }

    // Constructor con parámetros
    public Curso(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return "Curso{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}