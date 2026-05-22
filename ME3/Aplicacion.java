package ME3;

import ME3.Modelo.Aula;
import ME3.Modelo.Edificio;
import ME3.Modelo.Estudiante;
import ME3.Modelo.Materia;
import ME3.Servicios.*;

public class Aplicacion {
    public static void main(String[] args) {
        ServicioEstudiante servicioEstudiante = new ServicioEstudiante();
        ServicioMateria servicioMateria = new ServicioMateria(servicioEstudiante);
        ServicioHorario servicioHorario = new ServicioHorario();
        ServicioRutas servicioRutas = new ServicioRutas(5);
        ReporteAcademico reporteAcademico = new ReporteAcademico();
        NavegadorReportes navegadorReportes = new NavegadorReportes();
        ServicioHistorialOperaciones historialOperaciones = new ServicioHistorialOperaciones();
        ServicioBatch servicioBatch = new ServicioBatch(servicioMateria);

        inicializarDatosBase(servicioEstudiante, servicioMateria, servicioHorario, servicioRutas);
        MenuPrincipal menu = new MenuPrincipal(
                servicioEstudiante,
                servicioMateria,
                servicioHorario,
                servicioRutas,
                reporteAcademico,
                navegadorReportes,
                historialOperaciones,
                servicioBatch
        );
        menu.iniciar();
    }

    private static void inicializarDatosBase(
            ServicioEstudiante servicioEstudiante,
            ServicioMateria servicioMateria,
            ServicioHorario servicioHorario,
            ServicioRutas servicioRutas
    ) {
        servicioEstudiante.registrarEstudiante(
                new Estudiante("Ana Maria Gomez", "2024001", "ana.gomez@universidad.edu", 3)
        );

        servicioMateria.crearMateria(new Materia("CALC101", "Calculo I", 3, 4));
        servicioMateria.crearMateria(new Materia("FIS101", "Fisica I", 2, 3));

        servicioHorario.registrarAula(new Aula("101", 40));
        servicioHorario.registrarAula(new Aula("102", 35));

        servicioRutas.agregarEdificio(new Edificio("Ingenieria"));
        servicioRutas.agregarEdificio(new Edificio("Biblioteca"));
        servicioRutas.agregarEdificio(new Edificio("Cafeteria"));
        servicioRutas.agregarEdificio(new Edificio("Rectoria"));
        servicioRutas.agregarEdificio(new Edificio("Laboratorios"));

        servicioRutas.agregarConexion(0, 1, 120);
        servicioRutas.agregarConexion(0, 2, 150);
        servicioRutas.agregarConexion(2, 3, 180);
        servicioRutas.agregarConexion(1, 3, 200);
        servicioRutas.agregarConexion(3, 4, 90);
    }
}