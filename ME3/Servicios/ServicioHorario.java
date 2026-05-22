package ME3.Servicios;

import ME3.Excepciones.HorarioConflictivoException;
import ME3.Modelo.Aula;

import java.util.Map;
import java.util.TreeMap;

public class ServicioHorario {
    private final TreeMap<String, Aula> aulas;

    public ServicioHorario() {
        this.aulas = new TreeMap<>();
    }

    public void registrarAula(Aula aula) {
        if (aula == null) {
            throw new IllegalArgumentException("El aula no puede ser nula");
        }
        aulas.put(aula.getNombre(), aula);
    }

    public Aula buscarAula(String nombre) {
        Aula aula = aulas.get(nombre);
        if (aula == null) {
            throw new IllegalArgumentException("No existe el aula: " + nombre);
        }
        return aula;
    }

    public void reservarHorario(String nombreAula, int dia, int hora, int duracion)
            throws HorarioConflictivoException {
        Aula aula = buscarAula(nombreAula);
        try {
            aula.reservar(dia, hora, duracion);
        } catch (IllegalStateException e) {
            throw new HorarioConflictivoException(e.getMessage());
        }
    }

    public void liberarHorario(String nombreAula, int dia, int hora, int duracion) {
        Aula aula = buscarAula(nombreAula);
        aula.liberar(dia, hora, duracion);
    }

    public boolean consultarDisponibilidad(String nombreAula, int dia, int hora) {
        Aula aula = buscarAula(nombreAula);
        return aula.consultarDisponibilidad(dia, hora);
    }

    public Map<String, Aula> getAulas() {
        return aulas;
    }
}