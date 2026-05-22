package ME3;
 
import ME3.Excepciones.*;
import ME3.Modelo.Estudiante;
import ME3.Modelo.Materia;
import ME3.Modelo.Operacion;
import ME3.Servicios.*;

import java.util.Scanner;
 
public class MenuPrincipal {
    private final ServicioEstudiante servicioEstudiante; 
    private final ServicioMateria servicioMateria; 
    private final ServicioHorario servicioHorario;
    private final ServicioRutas servicioRutas;
    private final ReporteAcademico reporteAcademico; 
    private final NavegadorReportes navegadorReportes;
    private final ServicioHistorialOperaciones historialOperaciones;
    private final ServicioBatch servicioBatch;
    private final Scanner scanner;

    public MenuPrincipal(
            ServicioEstudiante servicioEstudiante,
            ServicioMateria servicioMateria,
            ServicioHorario servicioHorario, 
            ServicioRutas servicioRutas, 
            ReporteAcademico reporteAcademico,
            NavegadorReportes navegadorReportes,
            ServicioHistorialOperaciones historialOperaciones,
            ServicioBatch servicioBatch
    ) {
        this.servicioEstudiante = servicioEstudiante;
        this.servicioMateria = servicioMateria;
        this.servicioHorario = servicioHorario;
        this.servicioRutas = servicioRutas;
        this.reporteAcademico = reporteAcademico;
        this.navegadorReportes = navegadorReportes; 
        this.historialOperaciones = historialOperaciones;
        this.servicioBatch = servicioBatch;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            procesarOpcion(opcion);
        } while (opcion != 22);
    } 

    private void mostrarMenu() { 
        System.out.println("============================================================");
        System.out.println("PLANIFICACION ACADEMICA - SISTEMA UNIVERSITARIO");
        System.out.println("============================================================"); 
        System.out.println("=== GESTION DE ESTUDIANTES ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Listar todos los estudiantes");
        System.out.println("4. Eliminar estudiante"); 
        System.out.println("=== GESTION DE MATERIAS ===");
        System.out.println("5. Crear materia");
        System.out.println("6. Agregar pre-requisito");  
        System.out.println("7. Mostrar pre-requisitos");
        System.out.println("8. Inscribir estudiante");
        System.out.println("9. Cancelar inscripcion");
        System.out.println("10. Mostrar cola de espera");
        System.out.println("=== GESTION DE HORARIOS ===");
        System.out.println("11. Reservar horario en aula");
        System.out.println("12. Liberar horario");  
        System.out.println("13. Consultar disponibilidad");
        System.out.println("=== RUTAS ENTRE EDIFICIOS ===");
        System.out.println("14. Agregar conexion entre edificios");
        System.out.println("15. Calcular ruta mas corta");
        System.out.println("=== REPORTES ACADEMICOS ===");
        System.out.println("16. Registrar nota");  
        System.out.println("17. Ver reporte academico");
        System.out.println("18. Navegador de reportes"); 
        System.out.println("=== SISTEMA DESHACER/REHACER ===");
        System.out.println("19. Deshacer ultima operacion");
        System.out.println("20. Rehacer ultima operacion");
        System.out.println("=== PROCESAMIENTO POR LOTES ===");
        System.out.println("21. Procesar archivo CSV");
        System.out.println("=== SALIR ===");
        System.out.println("22. Salir");
    }
 
    private void procesarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> registrarEstudiante();
                case 2 -> buscarEstudiante();
                case 3 -> listarEstudiantes();
                case 4 -> eliminarEstudiante();
                case 5 -> crearMateria();
                case 6 -> agregarPreRequisito(); 
                case 7 -> mostrarPreRequisitos();
                case 8 -> inscribirEstudiante();
                case 9 -> cancelarInscripcion();
                case 10 -> mostrarColaEspera(); 
                case 11 -> reservarHorario();
                case 12 -> liberarHorario();
                case 13 -> consultarDisponibilidad();
                case 14 -> agregarConexion();
                case 15 -> calcularRutaMasCorta();
                case 16 -> registrarNota();
                case 17 -> verReporteAcademico();
                case 18 -> navegarReportes();  
                case 19 -> deshacer();
                case 20 -> rehacer();
                case 21 -> procesarBatch();
                case 22 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    } 

    private void registrarEstudiante() {
        String id = leerTexto("ID: ");
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: "); 
        int semestre = leerEntero("Semestre actual: "); 
        servicioEstudiante.registrarEstudiante(new Estudiante(nombre, id, email, semestre));
        System.out.println("Estudiante registrado exitosamente.");
    }

    private void buscarEstudiante() throws EstudianteNoEncontradoException {
        String id = leerTexto("ID: ");
        Estudiante estudiante = servicioEstudiante.buscarPorId(id);
        System.out.println(estudiante.mostrarInformacion());
    }

    private void listarEstudiantes() {
        for (Estudiante estudiante : servicioEstudiante.listarEstudiantes()) {
            System.out.println(estudiante.mostrarInformacion()); 
        }
    }

    private void eliminarEstudiante() throws EstudianteNoEncontradoException {
        String id = leerTexto("ID: ");
        Estudiante eliminado = servicioEstudiante.eliminarEstudiante(id);
        Operacion op = new Operacion("ELIMINAR_ESTUDIANTE");
        op.setIdEstudiante(eliminado.getId());
        op.setEstadoAnterior(eliminado.mostrarInformacion());
        historialOperaciones.registrarOperacion(op);
        System.out.println("Estudiante eliminado exitosamente.");
    }
 
    private void crearMateria() { 
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        int cupos = leerEntero("Cupos maximos: ");
        int creditos = leerEntero("Creditos: ");
        servicioMateria.crearMateria(new Materia(codigo, nombre, cupos, creditos));
        System.out.println("Materia creada exitosamente.");
    }

    private void agregarPreRequisito() { 
        String codigoMateria = leerTexto("Codigo materia: ");
        String codigoPre = leerTexto("Codigo prerequisito: ");
        servicioMateria.agregarPreRequisito(codigoMateria, codigoPre);
        System.out.println("Pre-requisito agregado.");
    } 

    private void mostrarPreRequisitos() {
        String codigoMateria = leerTexto("Codigo materia: ");
        System.out.println(servicioMateria.buscarMateria(codigoMateria).getPreRequisitos());
    }
 
    private void inscribirEstudiante() throws PreRequisitoNoAprobadoException, CupoLlenoException, EstudianteNoEncontradoException {
        String id = leerTexto("ID estudiante: ");
        String codigoMateria = leerTexto("Codigo materia: ");
        servicioMateria.inscribirEstudiante(id, codigoMateria);
        System.out.println("Inscripcion exitosa.");
    }

    private void cancelarInscripcion() { 
        String id = leerTexto("ID estudiante: ");
        String codigoMateria = leerTexto("Codigo materia: ");
        servicioMateria.cancelarInscripcion(id, codigoMateria);
        System.out.println("Inscripcion cancelada.");
    } 

    private void mostrarColaEspera() {
        String codigoMateria = leerTexto("Codigo materia: ");
        System.out.println(servicioMateria.obtenerColaEspera(codigoMateria));
    }
 
    private void reservarHorario() throws HorarioConflictivoException {
        String aula = leerTexto("Aula: ");
        int dia = leerEntero("Dia: ");
        int hora = leerEntero("Hora: ");
        int duracion = leerEntero("Duracion: ");
        servicioHorario.reservarHorario(aula, dia, hora, duracion);
        System.out.println("Reserva exitosa."); 
    }

    private void liberarHorario() {
        String aula = leerTexto("Aula: "); 
        int dia = leerEntero("Dia: ");
        int hora = leerEntero("Hora: ");
        int duracion = leerEntero("Duracion: ");
        servicioHorario.liberarHorario(aula, dia, hora, duracion);
        System.out.println("Horario liberado.");
    }

    private void consultarDisponibilidad() {
        String aula = leerTexto("Aula: "); 
        int dia = leerEntero("Dia: ");
        int hora = leerEntero("Hora: ");
        System.out.println("Disponible: " + servicioHorario.consultarDisponibilidad(aula, dia, hora)); 
    }

    private void agregarConexion() {
        int origen = leerEntero("Indice origen: ");
        int destino = leerEntero("Indice destino: ");
        int distancia = leerEntero("Distancia: ");
        servicioRutas.agregarConexion(origen, destino, distancia);
        System.out.println("Conexion agregada."); 
    }
 
    private void calcularRutaMasCorta() {
        int origen = leerEntero("Origen: ");
        int destino = leerEntero("Destino: ");
        ServicioRutas.RutaResultado resultado = servicioRutas.calcularRutaMasCorta(origen, destino);
        System.out.println("Distancia total: " + resultado.getDistanciaTotal()); 
        System.out.println("Camino: " + resultado.getCamino());
    }

    private void registrarNota() {
        int semestre = leerEntero("Semestre: ");
        int indice = leerEntero("Indice materia: "); 
        String materia = leerTexto("Nombre materia: ");
        double nota = leerDouble("Nota: ");
        reporteAcademico.registrarNota(semestre, indice, materia, nota);
        System.out.println("Nota registrada.");
    }

    private void verReporteAcademico() { 
        String id = leerTexto("ID estudiante: ");
        String nombre = leerTexto("Nombre estudiante: ");
        String reporte = reporteAcademico.generarReporteCompleto(nombre, id);
        navegadorReportes.agregarReporte(reporte); 
        System.out.println(reporte);
    }

    private void navegarReportes() {
        System.out.println(navegadorReportes.verReporteActual());
    }

    private void deshacer() throws PilaDeshacerVaciaException {
        Operacion operacion = historialOperaciones.deshacer(); 
        System.out.println("Operacion deshecha: " + operacion.getTipo());
    }

    private void rehacer() throws PilaDeshacerVaciaException {
        Operacion operacion = historialOperaciones.rehacer(); 
        System.out.println("Operacion rehecha: " + operacion.getTipo());
    }

    private void procesarBatch() throws EstudianteNoEncontradoException {
        String ruta = leerTexto("Ruta CSV: ");
        try { 
            servicioBatch.cargarArchivoCSV(ruta);
            ServicioBatch.ResultadoBatch resultado = servicioBatch.procesarCola();
            System.out.println(resultado.getDetalle()); 
            System.out.println("Exitosas: " + resultado.getExitosas());
            System.out.println("Fallidas: " + resultado.getFallidas());
        } catch (ArchivoInvalidoException e) {
            System.out.println(e.getMessage()); 
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje); 
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) { 
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Ingrese un numero valido: ");
        }
        int valor = scanner.nextInt(); 
        scanner.nextLine();
        return valor;
    }

    private double leerDouble(String mensaje) {
        System.out.print(mensaje); 
        while (!scanner.hasNextDouble()) {
            scanner.nextLine();
            System.out.print("Ingrese un valor valido: "); 
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor; 
    }
}