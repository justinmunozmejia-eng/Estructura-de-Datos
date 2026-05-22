package ME3.Servicios;

import ME3.Excepciones.CupoLlenoException;
import ME3.Excepciones.EstudianteNoEncontradoException; 
import ME3.Excepciones.PreRequisitoNoAprobadoException;
import ME3.Modelo.Estudiante; 
import ME3.Modelo.Materia;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import java.util.Queue;

public class ServicioMateria {
    private final Map<String, Materia> materias;
    private final Map<String, List<String>> inscripcionesPorEstudiante;
    private final Map<String, Queue<String>> colaEsperaPorMateria;
    private final ServicioEstudiante servicioEstudiante;

    public ServicioMateria(ServicioEstudiante servicioEstudiante) { 
        this.materias = new HashMap<>(); 
        this.inscripcionesPorEstudiante = new HashMap<>();
        this.colaEsperaPorMateria = new HashMap<>(); 
        this.servicioEstudiante = servicioEstudiante;
    }

    public void crearMateria(Materia materia) {
        if (materia == null) {
            throw new IllegalArgumentException("La materia no puede ser nula"); 
        }
        materias.put(materia.getCodigo(), materia);
        colaEsperaPorMateria.putIfAbsent(materia.getCodigo(), new ArrayDeque<>());
    } 

    public Materia buscarMateria(String codigo) {
        Materia materia = materias.get(codigo);
        if (materia == null) {
            throw new IllegalArgumentException("No existe la materia con codigo: " + codigo);
        }
        return materia;
    }

    public void agregarPreRequisito(String codigoMateria, String codigoPreRequisito) {
        Materia materia = buscarMateria(codigoMateria); 
        if (!materias.containsKey(codigoPreRequisito)) {
            throw new IllegalArgumentException("No existe la materia prerequisito: " + codigoPreRequisito);
        }
        materia.agregarPreRequisito(codigoPreRequisito);
    }
 
    public void inscribirEstudiante(String idEstudiante, String codigoMateria)
            throws PreRequisitoNoAprobadoException, CupoLlenoException, EstudianteNoEncontradoException {

        Estudiante estudiante = servicioEstudiante.buscarPorId(idEstudiante);
        Materia materia = buscarMateria(codigoMateria);

        validarPreRequisitos(estudiante, materia); 

        if (materia.tieneCupo()) {
            materia.ocuparCupo();
            inscripcionesPorEstudiante.putIfAbsent(idEstudiante, new ArrayList<>());
            List<String> materiasInscritas = inscripcionesPorEstudiante.get(idEstudiante);
            if (!materiasInscritas.contains(codigoMateria)) {
                materiasInscritas.add(codigoMateria);
            } 
            estudiante.agregarMateriaHistorial(codigoMateria);
        } else {
            Queue<String> cola = colaEsperaPorMateria.get(codigoMateria);
            cola.offer(idEstudiante);
            throw new CupoLlenoException("La materia " + codigoMateria + " esta llena. Estudiante agregado a la cola de espera.");
        } 
    }

    public String asignarDesdeCola(String codigoMateria)
            throws CupoLlenoException, EstudianteNoEncontradoException { 
        Materia materia = buscarMateria(codigoMateria);
        Queue<String> cola = colaEsperaPorMateria.get(codigoMateria);
        if (cola == null || cola.isEmpty()) {
            throw new CupoLlenoException("No hay estudiantes en cola de espera para la materia " + codigoMateria);
        }

        if (!materia.tieneCupo()) {
            throw new CupoLlenoException("La materia sigue llena: " + codigoMateria);
        } 

        String idEstudiante = cola.poll();
        materia.ocuparCupo();
        inscripcionesPorEstudiante.putIfAbsent(idEstudiante, new ArrayList<>());
        List<String> materiasInscritas = inscripcionesPorEstudiante.get(idEstudiante); 
        if (!materiasInscritas.contains(codigoMateria)) {
            materiasInscritas.add(codigoMateria);
        } 

        Estudiante estudiante = servicioEstudiante.buscarPorId(idEstudiante);
        estudiante.agregarMateriaHistorial(codigoMateria);

        return idEstudiante;
    }
 
    public void cancelarInscripcion(String idEstudiante, String codigoMateria) {
        Materia materia = buscarMateria(codigoMateria);
        List<String> materiasInscritas = inscripcionesPorEstudiante.get(idEstudiante);
        if (materiasInscritas != null) {
            materiasInscritas.remove(codigoMateria); 
        }
        materia.liberarCupo();
    }

    public Queue<String> obtenerColaEspera(String codigoMateria) {
        return colaEsperaPorMateria.getOrDefault(codigoMateria, new ArrayDeque<>());
    } 

    public List<String> obtenerMateriasInscritas(String idEstudiante) {
        return inscripcionesPorEstudiante.getOrDefault(idEstudiante, new ArrayList<>());
    }
 
    public Map<String, Materia> getMaterias() {
        return materias;
    }

    private void validarPreRequisitos(Estudiante estudiante, Materia materia)
            throws PreRequisitoNoAprobadoException { 
        for (String requisito : materia.getPreRequisitos()) {
            if (!estudiante.getHistorialMaterias().contains(requisito)) {
                throw new PreRequisitoNoAprobadoException(
                        "El estudiante " + estudiante.getId() + " no ha aprobado el prerequisito " + requisito
                );
            }
        }
    }
} 