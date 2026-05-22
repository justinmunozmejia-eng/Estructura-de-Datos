package ME3.Modelo;

import java.util.ArrayList;
import java.util.List;

public class ReporteAcademico {
    private final double[][] notas;
    private final String[][] nombresMaterias;

    public ReporteAcademico() {
        this.notas = new double[10][20];
        this.nombresMaterias = new String[10][20];
    }

    public void registrarNota(int semestre, int indiceMateria, String nombreMateria, double nota) {
        validarSemestre(semestre);
        validarIndice(indiceMateria);
        validarNota(nota);

        notas[semestre - 1][indiceMateria] = nota;
        nombresMaterias[semestre - 1][indiceMateria] = nombreMateria;
    }

    public double calcularPromedioSemestre(int semestre) {
        validarSemestre(semestre);
        double suma = 0.0;
        int contador = 0;

        for (int i = 0; i < 20; i++) {
            if (nombresMaterias[semestre - 1][i] != null) {
                suma += notas[semestre - 1][i];
                contador++;
            }
        }
        return contador == 0 ? 0.0 : suma / contador;
    }

    public double calcularPromedioAcumulado() {
        double suma = 0.0;
        int contador = 0;

        for (int s = 0; s < 10; s++) {
            for (int i = 0; i < 20; i++) {
                if (nombresMaterias[s][i] != null) {
                    suma += notas[s][i];
                    contador++;
                }
            }
        }
        return contador == 0 ? 0.0 : suma / contador;
    }

    public List<String> obtenerMateriasReprobadas() {
        List<String> reprobadas = new ArrayList<>();
        for (int s = 0; s < 10; s++) {
            for (int i = 0; i < 20; i++) {
                if (nombresMaterias[s][i] != null && notas[s][i] < 3.0) {
                    reprobadas.add("Semestre " + (s + 1) + " - " + nombresMaterias[s][i] + ": " + notas[s][i]);
                }
            }
        }
        return reprobadas;
    }

    public String generarReporteCompleto(String nombreEstudiante, String idEstudiante) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE ACADEMICO\n");
        sb.append("Estudiante: ").append(nombreEstudiante).append(" (ID: ").append(idEstudiante).append(")\n");

        for (int s = 0; s < 10; s++) {
            boolean tieneDatos = false;
            for (int i = 0; i < 20; i++) {
                if (nombresMaterias[s][i] != null) {
                    tieneDatos = true;
                    break;
                }
            }

            if (tieneDatos) {
                sb.append("Semestre ").append(s + 1).append(":\n");
                for (int i = 0; i < 20; i++) {
                    if (nombresMaterias[s][i] != null) {
                        sb.append(nombresMaterias[s][i]).append(": ").append(notas[s][i]).append("\n");
                    }
                }
                sb.append("Promedio: ").append(String.format("%.2f", calcularPromedioSemestre(s + 1))).append("\n");
            }
        }

        sb.append("=== RESUMEN ===\n");
        sb.append("Promedio acumulado: ").append(String.format("%.2f", calcularPromedioAcumulado())).append("\n");
        sb.append("Materias reprobadas: ").append(obtenerMateriasReprobadas().size()).append("\n");
        return sb.toString();
    }

    private void validarSemestre(int semestre) {
        if (semestre < 1 || semestre > 10) {
            throw new IllegalArgumentException("El semestre debe estar entre 1 y 10");
        }
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= 20) {
            throw new IllegalArgumentException("El indice de materia debe estar entre 0 y 19");
        }
    }

    private void validarNota(double nota) {
        if (nota < 0.0 || nota > 5.0) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0");
        }
    }
}