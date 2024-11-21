package datos;

import modelos.Curso;
import modelos.Estudiante;
import modelos.Asignacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatosPrueba {

    private static List<Curso> listaCursos;
    private static List<Asignacion> listaAsignaciones;

    static {
        inicializarCursos();
        inicializarAsignaciones();
    }

    private static void inicializarCursos() {
        listaCursos = new ArrayList<>();
        listaCursos.add(new Curso("C001", "Matemáticas"));
        listaCursos.add(new Curso("C002", "Física"));
        listaCursos.add(new Curso("C003", "Química"));
        listaCursos.add(new Curso("C004", "Biología"));
        listaCursos.add(new Curso("C005", "Historia"));
    }

    private static void inicializarAsignaciones() {
        listaAsignaciones = new ArrayList<>();

        Estudiante estudiante1 = new Estudiante("Juan Pérez", "20210001", "55555555", "juan@example.com");
        Estudiante estudiante2 = new Estudiante("María García", "20210002", "55556666", "maria@example.com");
        Estudiante estudiante3 = new Estudiante("Carlos López", "20210003", "55557777", "carlos@example.com");
        Estudiante estudiante4 = new Estudiante("Ana Torres", "20210004", "55558888", "ana@example.com");

        Curso curso1 = listaCursos.get(0);
        Curso curso2 = listaCursos.get(1);
        Curso curso3 = listaCursos.get(2);

        listaAsignaciones.add(new Asignacion(estudiante1, curso1, LocalDate.of(2023, 11, 12), true));
        listaAsignaciones.add(new Asignacion(estudiante2, curso1, LocalDate.of(2023, 11, 12), false));
        listaAsignaciones.add(new Asignacion(estudiante3, curso2, LocalDate.of(2023, 11, 13), true));
        listaAsignaciones.add(new Asignacion(estudiante4, curso1, LocalDate.of(2023, 11, 14), true));
        listaAsignaciones.add(new Asignacion(estudiante2, curso3, LocalDate.of(2023, 11, 14), false));
    }

    public static List<Curso> obtenerCursos() {
        return listaCursos;
    }

    public static List<Asignacion> obtenerAsignaciones() {
        return listaAsignaciones;
    }
}
