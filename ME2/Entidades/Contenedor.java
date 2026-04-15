package ME2.Entidades;

public class Contenedor { 
    private String id; 
    private double peso;
    private int prioridad;

    public Contenedor(String id, double peso, int prioridad) {
        this.id = id;
        this.peso = peso;
        this.prioridad = prioridad; 
    }

    public String getId() {
        return id; 
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String toString() {
        return "Contenedor{id='" + id + "', peso=" + peso + ", prioridad=" + prioridad + "}"; 
    }
} 