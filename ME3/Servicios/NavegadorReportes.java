package ME3.Servicios;

import java.util.Stack;

public class NavegadorReportes {
    private final Stack<String> pilaReportes;

    public NavegadorReportes() {
        this.pilaReportes = new Stack<>();
    }

    public void agregarReporte(String reporte) {
        if (reporte == null || reporte.isBlank()) {
            throw new IllegalArgumentException("El reporte no puede estar vacio");
        }
        pilaReportes.push(reporte);
    }

    public boolean hayHistorial() {
        return !pilaReportes.isEmpty();
    }

    public String verReporteActual() {
        if (pilaReportes.isEmpty()) {
            return "No hay reportes disponibles";
        }
        return pilaReportes.peek();
    }

    public String volverAtras() {
        if (pilaReportes.isEmpty()) {
            return "No hay reportes para retroceder";
        }
        pilaReportes.pop();
        if (pilaReportes.isEmpty()) {
            return "No hay reportes anteriores";
        }
        return pilaReportes.peek();
    }

    public int totalReportes() {
        return pilaReportes.size();
    }
}