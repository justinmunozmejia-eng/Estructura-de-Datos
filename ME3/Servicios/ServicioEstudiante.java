package ME3.Servicios;

import ME3.Excepciones.EstudianteNoEncontradoException;
import ME3.Modelo.Estudiante;

import java.util.ArrayList;
import java.util.Collection; 
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

public class ServicioEstudiante { 
    private final Map<String, Estudiante> estudiantes; 

    public ServicioEstudiante() { 
        this.estudiantes = new HashMap<>();
    }

    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) { 
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (estudiantes.containsKey(estudiante.getId())) {
            throw new IllegalArgumentException("Ya existe un estudiante con ID: " + estudiante.getId());
        } 
        estudiantes.put(estudiante.getId(), estudiante);
    }
 
    public Estudiante buscarPorId(String id) throws EstudianteNoEncontradoException { 
        Estudiante estudiante = estudiantes.get(id);
        if (estudiante == null) {
            throw new EstudianteNoEncontradoException("No existe estudiante con ID: " + id);
        }
        return estudiante;
    }
 
    public List<Estudiante> listarEstudiantes() {
        Collection<Estudiante> valores = estudiantes.values(); 
        return new ArrayList<>(valores);
    } 

    public Estudiante eliminarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante eliminado = estudiantes.remove(id);
        if (eliminado == null) {
            throw new EstudianteNoEncontradoException("No existe estudiante con ID: " + id); 
        }
        return eliminado;
    }

    public boolean existeEstudiante(String id) { 
        return estudiantes.containsKey(id);
    } 
    public int totalEstudiantes() {
        return estudiantes.size();
    }
}