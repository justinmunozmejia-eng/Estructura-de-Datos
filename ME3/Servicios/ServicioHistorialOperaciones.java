package ME3.Servicios;

import ME3.Excepciones.PilaDeshacerVaciaException;
import ME3.Modelo.Operacion;

import java.util.Stack;

public class ServicioHistorialOperaciones {
    private final Stack<Operacion> pilaDeshacer;
    private final Stack<Operacion> pilaRehacer;

    public ServicioHistorialOperaciones() {
        this.pilaDeshacer = new Stack<>();
        this.pilaRehacer = new Stack<>();
    }

    public void registrarOperacion(Operacion operacion) {
        if (operacion == null) {
            throw new IllegalArgumentException("La operacion no puede ser nula");
        }
        pilaDeshacer.push(operacion);
        pilaRehacer.clear();
    }

    public Operacion deshacer() throws PilaDeshacerVaciaException {
        if (pilaDeshacer.isEmpty()) {
            throw new PilaDeshacerVaciaException("No hay operaciones para deshacer");
        }
        Operacion operacion = pilaDeshacer.pop();
        pilaRehacer.push(operacion);
        return operacion;
    }

    public Operacion rehacer() throws PilaDeshacerVaciaException {
        if (pilaRehacer.isEmpty()) {
            throw new PilaDeshacerVaciaException("No hay operaciones para rehacer");
        }
        Operacion operacion = pilaRehacer.pop();
        pilaDeshacer.push(operacion);
        return operacion;
    }

    public boolean puedeDeshacer() {
        return !pilaDeshacer.isEmpty();
    }

    public boolean puedeRehacer() {
        return !pilaRehacer.isEmpty();
    }
}