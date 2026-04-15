package ME2.Servicios;

import ME2.Entidades.Contenedor;

public class ServicioManifiesto {
    private Contenedor[] manifiesto;
    private int cantidad;

    public ServicioManifiesto(int tamano) {
        manifiesto = new Contenedor[tamano];
        cantidad = 0;
    }

    public boolean registrarContenedor(Contenedor contenedor) {
        if (cantidad >= manifiesto.length) {
            return false;
        }
        manifiesto[cantidad] = contenedor;
        cantidad++;
        return true;
    }

    public double calcularPesoTotal() {
        double total = 0;
        for (int i = 0; i < cantidad; i++) {
            total = total + manifiesto[i].getPeso();
        }
        return total;
    }

    public Contenedor[] obtenerManifiesto() {
        Contenedor[] copia = new Contenedor[cantidad];
        for (int i = 0; i < cantidad; i++) {
            copia[i] = manifiesto[i];
        }
        return copia;
    }

    public void mostrarManifiesto() {
        for (int i = 0; i < cantidad; i++) {
            System.out.println((i + 1) + ". " + manifiesto[i]);
        }
    }
}