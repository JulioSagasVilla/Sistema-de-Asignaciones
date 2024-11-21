package utilidades;

public class Validaciones {

    public static boolean esNombreValido(String nombre) {
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public static boolean esCorreoValido(String correo) {
        return correo.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean esNumeroTarjetaValido(String numeroTarjeta) {
        return numeroTarjeta.matches("\\d{16}");
    }

    public static boolean esCodigoSeguridadValido(String codigo) {
        return codigo.matches("\\d{3,4}");
    }

    public static boolean esFechaExpiracionValida(String fechaExpiracion) {
        return fechaExpiracion.matches("(0[1-9]|1[0-2])/\\d{2}");
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("\\d{8,15}");
    }

    public static boolean esCarnetValido(String carnet) {
        return !carnet.trim().isEmpty();
    }
}