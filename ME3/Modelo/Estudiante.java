package ME3.Modelo;

import java.util.ArrayList;
import java.util.List;
 
public class Estudiante extends Persona { 
    private int semestreActual;
    private double[][] notas;
    private List<String> historialMaterias;
 
    public Estudiante(String nombre, String id, String email, int semestreActual) { 
        super(nombre, id, email);
        this.semestreActual = semestreActual;
        this.notas = new double[10][20]; 
        this.historialMaterias = new ArrayList<>();
    }

    public int getSemestreActual() { 
        return semestreActual;
    }

    public void setSemestreActual(int semestreActual) {
        this.semestreActual = semestreActual; 
    }

    public double[][] getNotas() {
        return notas; 
    }
 
    public List<String> getHistorialMaterias() {
        return historialMaterias;
    }

    public void agregarMateriaHistorial(String codigoMateria) {
        historialMaterias.add(codigoMateria);
    }
 
    public void registrarNota(int semestre, int indiceMateria, double nota) {
        if (semestre < 1 || semestre > 10) {
            throw new IllegalArgumentException("El semestre debe estar entre 1 y 10");
        }
        if (indiceMateria < 0 || indiceMateria >= 20) { 
            throw new IllegalArgumentException("El indice de materia debe estar entre 0 y 19");
        }
        if (nota < 0.0 || nota > 5.0) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0");
        }
        notas[semestre - 1][indiceMateria] = nota;
    }
 
    public double calcularPromedioSemestre(int semestre) {
        if (semestre < 1 || semestre > 10) {
            throw new IllegalArgumentException("El semestre debe estar entre 1 y 10");
        }
        double suma = 0.0;
        int cantidad = 0; 
        for (double nota : notas[semestre - 1]) {
            if (nota > 0.0) {
                suma += nota;
                cantidad++;
            } 
        }
        return cantidad == 0 ? 0.0 : suma / cantidad;
    }

    public double calcularPromedioAcumulado() {
        double suma = 0.0;
        int cantidad = 0; 
        for (double[] semestre : notas) {
            for (double nota : semestre) {
                if (nota > 0.0) {
                    suma += nota;
                    cantidad++;
                } 
            }
        }
        return cantidad == 0 ? 0.0 : suma / cantidad;
    }

    @Override
    public String mostrarInformacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estudiante{\n"); 
        sb.append("  nombre='").append(getNombre()).append("'\n");
        sb.append("  id='").append(getId()).append("'\n"); 
        sb.append("  email='").append(getEmail()).append("'\n");
        sb.append("  semestreActual=").append(semestreActual).append("\n");
        sb.append("  promedioAcumulado=").append(String.format("%.2f", calcularPromedioAcumulado())).append("\n");
        sb.append("}"); 
        return sb.toString();
    }
}