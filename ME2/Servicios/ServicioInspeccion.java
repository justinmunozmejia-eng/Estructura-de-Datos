package ME2.Servicios;

import ME2.Entidades.Contenedor;
import java.util.LinkedList;
import java.util.Queue;

public class ServicioInspeccion {
    private Queue<Contenedor> cola;

    public ServicioInspeccion() {
        cola = new LinkedList<Contenedor>();
    }

    public void encolar(Contenedor contenedor) {
        cola.add(contenedor);
    }

    public Contenedor atender() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public void mostrarCola() {
        for (Contenedor c : cola) {
            System.out.println(c);
        }
    }
}