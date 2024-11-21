package modelos;

public class Estudiante {
    private String nombre;
    private String carnet;
    private String telefono;
    private String correoElectronico;

    // Constructor vacío
    public Estudiante() {
    }

    // Constructor con parámetros
    public Estudiante(String nombre, String carnet, String telefono, String correoElectronico) {
        this.nombre = nombre;
        this.carnet = carnet;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    // Método toString
    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", carnet='" + carnet + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}