package ME2.App;

import ME2.Entidades.Contenedor;
import ME2.Servicios.ServicioCarga; 
import ME2.Servicios.ServicioInspeccion;
import ME2.Servicios.ServicioManifiesto;
import ME2.Servicios.ServicioPatio; 

public class Aplicacion {
    public static void main(String[] args) {
        Contenedor[] prueba = {
            new Contenedor("C001", 1200.5, 3), 
            new Contenedor("C002", 950.0, 5),
            new Contenedor("C003", 1400.75, 2),
            new Contenedor("C004", 800.25, 4),
            new Contenedor("C005", 1100.0, 1)
        };

        ServicioManifiesto manifiesto = new ServicioManifiesto(prueba.length);
        ServicioPatio patio = new ServicioPatio(3, 3);
        ServicioInspeccion inspeccion = new ServicioInspeccion();
        ServicioCarga carga = new ServicioCarga();

        System.out.println("=== REGISTRO DE MANIFIESTO ===");
        for (Contenedor contenedor : prueba) {
            manifiesto.registrarContenedor(contenedor);
            System.out.println("Registrado: " + contenedor);
        }
        System.out.println("Peso total del manifiesto: " + manifiesto.calcularPesoTotal());

        System.out.println("\n=== MANIFIESTO COMPLETO ===");
        manifiesto.mostrarManifiesto();

        System.out.println("\n=== UBICACION EN PATIO ===");
        for (Contenedor contenedor : manifiesto.obtenerManifiesto()) {
            boolean ubicado = patio.ubicarContenedor(contenedor);
            if (ubicado) {
                System.out.println("Ubicado en patio: " + contenedor.getId());
            } else {
                System.out.println("Puerto saturado para: " + contenedor.getId());
            }
        }
        patio.mostrarPatio();

        System.out.println("\n=== COLA DE INSPECCION ===");
        for (Contenedor contenedor : manifiesto.obtenerManifiesto()) {
            if (contenedor.getPrioridad() >= 4) {
                inspeccion.encolar(contenedor);
                System.out.println("Encolado para inspeccion: " + contenedor.getId());
            }
        }
        inspeccion.mostrarCola();

        System.out.println("\n=== PROCESO DE INSPECCION ===");
        while (!inspeccion.estaVacia()) {
            Contenedor atendido = inspeccion.atender();
            System.out.println("Inspeccionado: " + atendido);
        }

        System.out.println("\n=== CARGA EN BUQUE ===");
        for (Contenedor contenedor : manifiesto.obtenerManifiesto()) {
            carga.apilar(contenedor);
            System.out.println("Apilado: " + contenedor.getId());
        }
        carga.mostrarPila();

        System.out.println("\n=== RETIRO DEL PRIMER CONTENEDOR INGRESADO ===");
        Contenedor retirado = carga.retirarFondo();
        System.out.println("Retirado del fondo: " + retirado);

        System.out.println("\n=== ESTADO FINAL DE LA PILA ===");
        carga.mostrarPila();
    }
}