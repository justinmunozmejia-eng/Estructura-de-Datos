package ME3.Modelo;


import java.util.ArrayList; 
import java.util.List;

public class Materia {
    private String codigo; 
    private String nombre;
    private int cuposMaximos;
    private int cuposDisponibles;
    private int creditos;
    private List<String> preRequisitos; 

    public Materia(String codigo, String nombre, int cuposMaximos, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMaximos = cuposMaximos;
        this.cuposDisponibles = cuposMaximos;
        this.creditos = creditos; 
        this.preRequisitos = new ArrayList<>(); 
    }

    public String getCodigo() {
        return codigo;
    } 

    public String getNombre() {
        return nombre;
    }

    public int getCuposMaximos() { 
        return cuposMaximos;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public int getCreditos() {
        return creditos; 
    } 

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void agregarPreRequisito(String codigoMateria) {
        if (!preRequisitos.contains(codigoMateria)) {
            preRequisitos.add(codigoMateria);
        }
    }

    public boolean tieneCupo() {
        return cuposDisponibles > 0; 
    }

    public void ocuparCupo() {
        if (cuposDisponibles <= 0) {
            throw new IllegalStateException("No hay cupos disponibles en la materia " + codigo); 
        }
        cuposDisponibles--;
    }

    public void liberarCupo() {
        if (cuposDisponibles < cuposMaximos) { 
            cuposDisponibles++;
        } 
    }

    public boolean cumpleSinRepetir(String codigoMateria) {
        return preRequisitos.contains(codigoMateria);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
} 