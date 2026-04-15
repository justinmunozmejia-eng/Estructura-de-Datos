package ME2.Servicios;

import ME2.Entidades.Contenedor;
import java.util.Stack;

public class ServicioCarga {
    private Stack<Contenedor> pila;

    public ServicioCarga() {
        pila = new Stack<Contenedor>();
    }

    public void apilar(Contenedor contenedor) {
        pila.push(contenedor);
    }

    public Contenedor desapilar() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.pop();
    }

    public Contenedor retirarFondo() {
        if (pila.isEmpty()) {
            return null;
        }

        Stack<Contenedor> auxiliar = new Stack<Contenedor>();

        while (pila.size() > 1) {
            auxiliar.push(pila.pop());
        }

        Contenedor retirado = pila.pop();

        while (!auxiliar.isEmpty()) {
            pila.push(auxiliar.pop());
        }

        return retirado;
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public void mostrarPila() {
        for (int i = pila.size() - 1; i >= 0; i--) {
            System.out.println(pila.get(i));
        }
    }
}