package ME3.Modelo; 

public class Edificio {
    private String nombre; 

    public Edificio(String nombre) { 
        this.nombre = nombre;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    @Override
    public String toString() {
        return nombre; 
    } 
}